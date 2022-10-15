package com.example.citra_moblie.ui.vacancy_details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.citra_moblie.R;
import com.example.citra_moblie.ui.editVacancyActivity.EditVacancy;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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

        // descobrindo a activity anterior
        FragmentManager fm = getActivity().getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        lastFragmentName = fm.getBackStackEntryAt(count - 1).getName();

        // mudar ação do actionUser
        // fazer floating button
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
                Fragment fragment = new EditVacancy();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_home, fragment, "edit_vacancy");
                transaction.addToBackStack("edit_vacancy");
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