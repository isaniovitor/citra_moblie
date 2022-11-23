package com.example.citra_moblie.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.FirebaseHelper;
import com.example.citra_moblie.helper.RecyclerItemClickListener;
import com.example.citra_moblie.adapter.VacancyRecyclerViewAdapter;
import com.example.citra_moblie.databinding.FragmentHomeBinding;
import com.example.citra_moblie.model.Vacancy;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserCreatedVacancies extends Fragment implements VacancyRecyclerViewAdapter.Onclick {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private FloatingActionButton createVacancy;

    private Vacancy vacancy;

    private List<Vacancy> vacancies = new ArrayList<>();

    private VacancyRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_created_vacancies_list, container, false);
        recyclerView = view.findViewById(R.id.vacancies);
        createVacancy = view.findViewById(R.id.createVacancy);

        configRvVacancies();

        // configurar adapter
        //IVacancyDAO vacancyDAO = VacancyDAO.getInstance(getContext());
        //adapter = new VacancyRecyclerViewAdapter(vacancyDAO.getUserCreatedVacancies());

        //configRecycleView(adapter);

        createVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AnnounceVacancyFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_home, fragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperaVacanciesUser();
    }

    private void configRecycleView(VacancyRecyclerViewAdapter adapter) {
        // configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
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
    }

    public void configRvVacancies(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new VacancyRecyclerViewAdapter(vacancies, this);
        recyclerView.setAdapter(adapter);
    }

    public void recuperaVacanciesUser(){
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("vacancies")
                .child(FirebaseHelper.getIdFirebase());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vacancies.clear();
                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        vacancy = snap.getValue(Vacancy.class);
                        vacancies.add(vacancy);
                    }
                }else{

                }
                Collections.reverse(vacancies);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClickListener(Vacancy vacancy) {
        //Intent intent = new Intent(requireContext(), DetalhesTreinoActivity.class);
        Toast.makeText(getActivity(), "Nome da vaga: " + vacancy.getVacancyName(),Toast.LENGTH_LONG).show();
        //startActivity(intent);

    }

}