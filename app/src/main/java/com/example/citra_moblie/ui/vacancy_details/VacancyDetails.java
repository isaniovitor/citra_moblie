package com.example.citra_moblie.ui.vacancy_details;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citra_moblie.LoginActivity;
import com.example.citra_moblie.R;
import com.example.citra_moblie.RegisterUserActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VacancyDetails extends Fragment {
    private String lasFragmentName;
    private Button actionUser;
    private FloatingActionButton ownerUserActions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacancy_details, container, false);
        actionUser = view.findViewById(R.id.actionUserActivity);
        ownerUserActions = view.findViewById(R.id.owner_user_actions);

        // descobrindo a activity anterior
        FragmentManager fm = getActivity().getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        lasFragmentName = fm.getBackStackEntryAt(count - 1).getName();

        actionUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lasFragmentName.equals("home_vacancies_list")) {
                    applyForVacancy();
                } else {
                    unsubscribeForVacancy();
                }
            }
        });

        // mudar ação do actionUser
        // fazer floating button
        if (lasFragmentName.equals("home_vacancies_list")) {
            actionUser.setText("Candidatar-se");
            ownerUserActions.setVisibility(View.GONE);
        }else if (lasFragmentName.equals("user_created_vacancy_list")) {
            actionUser.setVisibility(View.GONE);
        }else{
            actionUser.setText("Cancelar");
            ownerUserActions.setVisibility(View.GONE);
        }
        return view;
    }

    public void applyForVacancy() {
        Toast.makeText(getContext(),"Candidatado para a vaga", Toast.LENGTH_SHORT).show();
    }

    public void unsubscribeForVacancy() {
        Toast.makeText(getContext(),"Candidatuta cancelada", Toast.LENGTH_SHORT).show();
    }
}