package com.example.citra_moblie.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.model.Vacancy;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class VacancyDetails extends Fragment {
    private String lastFragmentName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        IVacancyDAO vacancyDAO = VacancyDAO.getInstance(getContext());

        View view = inflater.inflate(R.layout.fragment_vacancy_details, container, false);
        FloatingActionButton seeCandidatesVacancy = view.findViewById(R.id.seeCandidatesVacancy);
        FloatingActionsMenu ownerUserActions = view.findViewById(R.id.floatingMenu);
        FloatingActionButton deleteVacancy = view.findViewById(R.id.deleteVacancy);
        FloatingActionButton editVacancy = view.findViewById(R.id.editVacancy);
        Button actionUser = view.findViewById(R.id.actionUserActivity);
        TextView vacancyName = view.findViewById(R.id.nameVacancy);
        TextView vacancySalaryDetails = view.findViewById(R.id.vacancySalaryDetails);
        TextView vacancyShiftDetails = view.findViewById(R.id.vacancyShiftDetails);
        TextView vacancyTypeHiringDetails = view.findViewById(R.id.vacancyTypeHiringDetails);
        TextView vacancyDescription = view.findViewById(R.id.vacancyDescription);
        ImageView vacancyImageView = view.findViewById(R.id.vacancyImageView);

        // descobrindo a activity anterior
        FragmentManager fm = getActivity().getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        int vacancyPosition = (int) bundle.getSerializable("position");
        lastFragmentName = fm.getBackStackEntryAt(count - 1).getName();

        Vacancy currentVacancy;
        if (lastFragmentName.equals("home_vacancies_list")) {
            actionUser.setText("Candidatar-se");
            ownerUserActions.setVisibility(View.GONE);
            currentVacancy = vacancyDAO.getVacancy(vacancyPosition);
        }else if (lastFragmentName.equals("user_created_vacancy_list")) {
            actionUser.setVisibility(View.GONE);
            currentVacancy = vacancyDAO.getCreatedVacancy(vacancyPosition);
        }else{
            actionUser.setText("Cancelar");
            ownerUserActions.setVisibility(View.GONE);
            currentVacancy = vacancyDAO.getAppliedVacancy(vacancyPosition);
        }

        // setando os dados
        if (currentVacancy.getVacancyImage() != null) {
            vacancyImageView.setImageBitmap(currentVacancy.getVacancyImage());
        }

        vacancyName.setText(currentVacancy.getVacancyName());
        vacancySalaryDetails.setText(currentVacancy.getSalarySpinner());
        vacancyShiftDetails.setText(currentVacancy.getShiftSpinner());
        vacancyTypeHiringDetails.setText(currentVacancy.getTypeHiringSpinner());
        vacancyDescription.setText(currentVacancy.getVacancyDescription());

        // buttons
        actionUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastFragmentName.equals("home_vacancies_list")) {
                    applyForVacancy();
                } else {
                    unsubscribeForVacancy();
                }
            }
        });

        deleteVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vacancyDAO.removeVacancy(vacancyDAO.getCreatedVacancy(vacancyPosition))) {
                    Toast.makeText(getContext(),"Sucesso ao excluir Vaga!", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new UserCreatedVacancies();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_content_home, fragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else{
                    Toast.makeText(getContext(),"Erro ao excluir Vaga!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Fragment fragment = new EditVacancy();
                bundle.putSerializable("position", vacancyPosition);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_home, fragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        seeCandidatesVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Fragment fragment = new VacancyCandidatesFragment();
                bundle.putSerializable("position", vacancyPosition);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_home, fragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    public void applyForVacancy() {
        Toast.makeText(getContext(),"Candidatado para a vaga", Toast.LENGTH_SHORT).show();
    }

    public void unsubscribeForVacancy() {
        Toast.makeText(getContext(),"Candidatuta cancelada", Toast.LENGTH_SHORT).show();
    }
}