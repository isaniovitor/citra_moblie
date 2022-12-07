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
import com.example.citra_moblie.adapter.CandidateRecyclerViewAdapter;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.RecyclerItemClickListener;
import com.example.citra_moblie.adapter.VacancyRecyclerViewAdapter;
import com.example.citra_moblie.databinding.FragmentHomeBinding;
import com.example.citra_moblie.model.User;
import com.example.citra_moblie.model.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class VacancyCandidatesFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vacancy_canditates, container, false);

        Bundle bundle = getArguments();
        recyclerView = view.findViewById(R.id.candidates);

        // configurar adapter
        List<User> appliedCandidates = (List<User>) bundle.getSerializable("appliedCandidates");
        CandidateRecyclerViewAdapter adapter = new CandidateRecyclerViewAdapter(appliedCandidates);

        // configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}