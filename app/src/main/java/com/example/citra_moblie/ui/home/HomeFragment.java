package com.example.citra_moblie.ui.home;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citra_moblie.R;
import com.example.citra_moblie.RecyclerItemClickListener;
import com.example.citra_moblie.adapter.VacancyRecyclerViewAdapter;
import com.example.citra_moblie.databinding.FragmentHomeBinding;
import com.example.citra_moblie.model.Vacancy;
import com.example.citra_moblie.ui.vacancy_details.VacancyDetails;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private List<Vacancy> vacancies = new ArrayList<>();
    private Spinner numberSpinner;
    private Spinner shiftSpinner;
    private Spinner typeHiring;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.vacancies);
        numberSpinner = view.findViewById(R.id.salatySpinner);
        shiftSpinner = view.findViewById(R.id.shiftSpinner);
        typeHiring = view.findViewById(R.id.typeHiringSpinner);

        // vacancies mock
        this.createVacanciesMock();

        // recyclerView
        VacancyRecyclerViewAdapter adapter = new VacancyRecyclerViewAdapter(vacancies);

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
                                // como passar atributo
                                Fragment fragment = new VacancyDetails();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.nav_host_fragment_content_home, fragment, "home_vacancies_list");
                                transaction.addToBackStack("home_vacancies_list");
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

        // spinners
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.salary, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberSpinner.setAdapter(spinnerAdapter);
//        numberSpinner.getBackground().setColorFilter(R.color.red), PorterDuff.Mode.SRC_ATOP);
        numberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected =  adapterView.getSelectedItem().toString();
                Toast.makeText(getContext(),"item selecionado: " + itemSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.shifts, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shiftSpinner.setAdapter(spinnerAdapter);
        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected =  adapterView.getSelectedItem().toString();
                Toast.makeText(getContext(),"item selecionado: " + itemSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.typeHiring, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeHiring.setAdapter(spinnerAdapter);
        typeHiring.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected =  adapterView.getSelectedItem().toString();
                Toast.makeText(getContext(),"item selecionado: " + itemSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

        vacancy = new Vacancy(null, "Pedreiro bom", "ser um bom pedreito");
        vacancies.add(vacancy);

        vacancy = new Vacancy(null, "Pedreiro do brabo", "ser um bom pedreito");
        vacancies.add(vacancy);

        vacancy = new Vacancy(null, "Pedreiro Eiro", "ser um bom pedreito");
        vacancies.add(vacancy);

        vacancy = new Vacancy(null, "Pedro eiro", "ser um bom pedreito");
        vacancies.add(vacancy);
    }
}