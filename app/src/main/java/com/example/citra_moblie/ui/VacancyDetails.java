package com.example.citra_moblie.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.LoadingDialog;
import com.example.citra_moblie.model.User;
import com.example.citra_moblie.model.Vacancy;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VacancyDetails extends Fragment {
    private IVacancyDAO vacancyDAO;
    private IUserDAO userDAO;
    private LoadingDialog loadingDialog;
    private Button actionUser;
    private String lastFragmentName;
    private List<User> appliedCandidates = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        View view = inflater.inflate(R.layout.fragment_vacancy_details, container, false);
        FloatingActionButton seeCandidatesVacancy = view.findViewById(R.id.seeCandidatesVacancy);
        FloatingActionsMenu ownerUserActions = view.findViewById(R.id.floatingMenu);
        FloatingActionButton deleteVacancy = view.findViewById(R.id.deleteVacancy);
        FloatingActionButton editVacancy = view.findViewById(R.id.editVacancy);
        TextView vacancyName = view.findViewById(R.id.nameVacancy);
        TextView vacancySalaryDetails = view.findViewById(R.id.vacancySalaryDetails);
        TextView vacancyShiftDetails = view.findViewById(R.id.vacancyShiftDetails);
        TextView vacancyTypeHiringDetails = view.findViewById(R.id.vacancyTypeHiringDetails);
        TextView vacancyDescription = view.findViewById(R.id.vacancyDescription);
        ImageView vacancyImageView = view.findViewById(R.id.vacancyImageView);
        actionUser = view.findViewById(R.id.actionUserActivity);
        vacancyDAO = VacancyDAO.getInstance(getContext());
        userDAO = UserDAO.getInstance(getContext());
        loadingDialog = new LoadingDialog(getActivity());

        // descobrindo a activity anterior
        FragmentManager fm = getActivity().getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();

        // recebendo vaga
        Vacancy vacancy = (Vacancy) bundle.getSerializable("vacancy");
        lastFragmentName = fm.getBackStackEntryAt(count - 1).getName();

        // ação do botao principal: usuário não candidatado
        actionUser.setText("Candidatar-se");
        ownerUserActions.setVisibility(View.GONE);
        actionUser.setOnClickListener(view1 -> applyForVacancy(vacancy));

        // pegando usuários cadastrados;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("AllVacancies").child(vacancy.getIdVacancy()).child("appliedCandidates")
               .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // percorrendo usuários cadastrados
                    for(DataSnapshot snap : snapshot.getChildren()){
                        User user = snap.getValue(User.class);

                        if (user.getId().equals(userDAO.getUser().getId())) {
                            actionUser.setText("Cancelar");
                            actionUser.setOnClickListener(view1 -> unsubscribeForVacancy(vacancy));
                        }

                        appliedCandidates.add(user);
                    }
                }

                // mudando fragment de acordo com regras de negócio
                System.out.println(lastFragmentName);
                if (lastFragmentName.equals("user_created_vacancy_list")) {
                    actionUser.setVisibility(View.GONE);
                    ownerUserActions.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // setando os dados
        if (vacancy.getVacancyImage() != null) {
            Picasso.get().load(Uri.parse(vacancy.getVacancyImage()))
                    .into(vacancyImageView);
        }
        vacancyName.setText(vacancy.getVacancyName());
        vacancySalaryDetails.setText(vacancy.getSalarySpinner());
        vacancyShiftDetails.setText(vacancy.getShiftSpinner());
        vacancyTypeHiringDetails.setText(vacancy.getTypeHiringSpinner());
        vacancyDescription.setText(vacancy.getVacancyDescription());

        deleteVacancy.setOnClickListener(view12 -> {
            loadingDialog.startAlertDialog();

            DatabaseReference VacancyReference = FirebaseDatabase.getInstance().getReference().child("AllVacancies");
            VacancyReference.child(vacancy.getIdVacancy()).removeValue()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            vacancyDAO.getVacanciesFromAPI(getActivity());
                            Toast.makeText(getContext(), "Vaga excluida!", Toast.LENGTH_SHORT).show();

                            // deletando imagem
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            StorageReference imageRef = storageReference.child("imagesVacancies").child(vacancy.getIdVacancy());
                            imageRef.delete();

                            loadingDialog.dismissAlertDialog();
                            Fragment fragment = new UserCreatedVacancies();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment_content_home, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }else{
                            Toast.makeText(getContext(),"Erro ao excluir vaga!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        editVacancy.setOnClickListener(view13 -> {
            Bundle bundle1 = new Bundle();
            Fragment fragment = new EditVacancy();
            bundle1.putSerializable("vacancy", vacancy);

            fragment.setArguments(bundle1);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_content_home, fragment );
            transaction.addToBackStack(null);
            transaction.commit();
        });

        seeCandidatesVacancy.setOnClickListener(view14 -> {
            Bundle bundle12 = new Bundle();
            Fragment fragment = new VacancyCandidatesFragment();
            bundle12.putSerializable("appliedCandidates", (Serializable) appliedCandidates);

            fragment.setArguments(bundle12);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_content_home, fragment );
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    public void applyForVacancy(Vacancy vacancy) {
        if (!vacancy.getIdUser().equals(userDAO.getUser().getId())) {
            loadingDialog.startAlertDialog();

            DatabaseReference VacancyReference = FirebaseDatabase.getInstance().getReference().child("AllVacancies");
            VacancyReference.child(vacancy.getIdVacancy()).child("appliedCandidates").child(userDAO.getUser().getId()).
                    setValue(userDAO.getUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            actionUser.setText("Cancelar");
                            actionUser.setOnClickListener(view -> unsubscribeForVacancy(vacancy));
                            Toast.makeText(getContext(),"Candidatado para a vaga", Toast.LENGTH_SHORT).show();

                            loadingDialog.dismissAlertDialog();
                            Fragment fragment = new UserAppliedVacancies();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment_content_home, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Não é possível: você anunciou essa vaga!", Toast.LENGTH_SHORT).show();
        }
    }

    public void unsubscribeForVacancy(Vacancy vacancy) {
        loadingDialog.startAlertDialog();

        DatabaseReference VacancyReference = FirebaseDatabase.getInstance().getReference().child("AllVacancies");
        VacancyReference.child(vacancy.getIdVacancy()).child("appliedCandidates").child(userDAO.getUser().getId()).removeValue()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        actionUser.setText("Candidatar-se");
                        actionUser.setOnClickListener(view -> applyForVacancy(vacancy));
                        Toast.makeText(getContext(), "Candidatuta cancelada", Toast.LENGTH_SHORT).show();

                        loadingDialog.dismissAlertDialog();
                        Fragment fragment = new UserAppliedVacancies();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment_content_home, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
    }
}