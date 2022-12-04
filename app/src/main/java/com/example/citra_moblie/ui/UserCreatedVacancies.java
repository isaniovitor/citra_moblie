package com.example.citra_moblie.ui;

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
import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.RecyclerItemClickListener;
import com.example.citra_moblie.adapter.VacancyRecyclerViewAdapter;
import com.example.citra_moblie.databinding.FragmentHomeBinding;
import com.example.citra_moblie.model.Vacancy;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserCreatedVacancies extends Fragment {
    IVacancyDAO vacancyDAO = VacancyDAO.getInstance(getContext());
    IUserDAO userDAO = UserDAO.getInstance(getContext());
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private FloatingActionButton createVacancy;
    private List<Vacancy> userCreatedVacancies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_created_vacancies_list, container, false);
        recyclerView = view.findViewById(R.id.vacancies);
        createVacancy = view.findViewById(R.id.createVacancy);

        // configurar adapter
        for (Vacancy vacancy : vacancyDAO.getVacancies()) {
            if (vacancy.getIdUser().equals(userDAO.getUser().getId())) {
                userCreatedVacancies.add(vacancy);
            }
        }

        // configurar Recyclerview
        VacancyRecyclerViewAdapter adapter = new VacancyRecyclerViewAdapter(userCreatedVacancies);
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
                                bundle.putSerializable("vacancy", vacancyDAO.getVacancy(position));

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}