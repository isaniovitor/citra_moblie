package com.example.citra_moblie.ui.user_applyed_vacancies_list;

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
import com.example.citra_moblie.RecyclerItemClickListener;
import com.example.citra_moblie.adapter.VacancyRecyclerViewAdapter;
import com.example.citra_moblie.databinding.FragmentHomeBinding;
import com.example.citra_moblie.model.Vacancy;
import com.example.citra_moblie.ui.vacancy_details.VacancyDetails;

import java.util.ArrayList;
import java.util.List;

public class user_applyed_vacancies extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private List<Vacancy> vacancies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.vacancies);

        // vacancies mock
        this.createVacanciesMock();

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
                                // como passar atributo
                                Fragment fragment = new VacancyDetails();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.nav_host_fragment_content_home, fragment, "user_applyed_vacancies_list");
                                transaction.addToBackStack("user_applyed_vacancies_list");
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

        vacancy = new Vacancy(null, "Pedreiro bom", "ser um bom pedreito");
        vacancies.add(vacancy);
    }
}