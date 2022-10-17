package com.example.citra_moblie.ui.user_created_vacancies_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.citra_moblie.R;
import com.example.citra_moblie.helper.RecyclerItemClickListener;
import com.example.citra_moblie.adapter.VacancyRecyclerViewAdapter;
import com.example.citra_moblie.databinding.FragmentHomeBinding;
import com.example.citra_moblie.model.Vacancy;
import com.example.citra_moblie.ui.announce_vacancy.Announce_vacancyFragment;
import com.example.citra_moblie.ui.editVacancyActivity.EditVacancy;
import com.example.citra_moblie.ui.vacancy_details.VacancyDetails;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class user_created_vacancies_list extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private FloatingActionButton createVacancy;
    private List<Vacancy> vacancies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_created_vacancies_list, container, false);
        recyclerView = view.findViewById(R.id.vacancies);
        createVacancy = view.findViewById(R.id.createVacancy);
        Bundle bundle = getArguments();

        // vacancies mock
        if (bundle != null) {
            List<Vacancy> newVacancies = (List<Vacancy>) bundle.getSerializable("vacancies");
            vacancies = newVacancies;
        }else {
            this.createVacanciesMock();
        }

        // configurar adapter
        VacancyRecyclerViewAdapter adapter = new VacancyRecyclerViewAdapter(vacancies);

        // configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);

        // evento click
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext().getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Bundle bundle = new Bundle();
                                Fragment fragment = new VacancyDetails();

                                bundle.putSerializable("position", position);
                                bundle.putSerializable("vacancies", (Serializable) vacancies);

                                fragment.setArguments(bundle);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.nav_host_fragment_content_home, fragment, "user_created_vacancy_list");
                                transaction.addToBackStack("user_created_vacancy_list");
                                transaction.commit();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        createVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Fragment fragment = new Announce_vacancyFragment();

                fragment.setArguments(bundle);
                bundle.putSerializable("vacancies", (Serializable) vacancies);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_home, fragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void createVacanciesMock(){
        Vacancy vacancy = new Vacancy(null, "Pedreiro", "ser um bom pedreito");
        vacancies.add(vacancy);
    }
}