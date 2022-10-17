package com.example.citra_moblie.ui.vacancy_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citra_moblie.R;
import com.example.citra_moblie.model.Vacancy;
import com.example.citra_moblie.ui.editVacancyActivity.EditVacancy;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.Serializable;
import java.util.List;

public class VacancyDetails extends Fragment {
    private String lastFragmentName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacancy_details, container, false);
        FloatingActionButton seeCandidatesVacancy = view.findViewById(R.id.seeCandidatesVacancy);
        FloatingActionsMenu ownerUserActions = view.findViewById(R.id.floatingMenu);
        FloatingActionButton deleteVacancy = view.findViewById(R.id.deleteVacancy);
        FloatingActionButton editVacancy = view.findViewById(R.id.editVacancy);
        Button actionUser = view.findViewById(R.id.actionUserActivity);
        TextView vacancyName = view.findViewById(R.id.nameVacancy);
        TextView vacancyDescription = view.findViewById(R.id.vacancy_description);

        Bundle bundle = getArguments();
        int vacancyPosition = (int) bundle.getSerializable("position");
        List<Vacancy> vacancies = (List<Vacancy>) bundle.getSerializable("vacancies");

        // setando os dados da vaga
        vacancyName.setText(vacancies.get(vacancyPosition).getVacancyName());
        vacancyDescription.setText(vacancies.get(vacancyPosition).getVacancyDescription());

        // descobrindo a activity anterior
        FragmentManager fm = getActivity().getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        lastFragmentName = fm.getBackStackEntryAt(count - 1).getName();

        if (lastFragmentName.equals("home_vacancies_list")) {
            actionUser.setText("Candidatar-se");
            ownerUserActions.setVisibility(View.GONE);
        }else if (lastFragmentName.equals("user_created_vacancy_list")) {
            actionUser.setVisibility(View.GONE);
        }else{
            actionUser.setText("Cancelar");
            ownerUserActions.setVisibility(View.GONE);
        }

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
                Toast.makeText(getContext(),"Vaga excluida", Toast.LENGTH_SHORT).show();
            }
        });

        editVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Fragment fragment = new EditVacancy();

                fragment.setArguments(bundle);
                bundle.putSerializable("position", vacancyPosition);
                bundle.putSerializable("vacancies", (Serializable) vacancies);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_home, fragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        seeCandidatesVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Ir pra tela: ainda n feita", Toast.LENGTH_SHORT).show();
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