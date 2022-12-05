package com.example.citra_moblie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citra_moblie.R;
import com.example.citra_moblie.adapter.VacancyRecyclerViewAdapter;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.RecyclerItemClickListener;
import com.example.citra_moblie.model.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {
    private IVacancyDAO vacancyDAO;
    private ImageView clearFilters;
    private Spinner minimumsSalarySpinner;
    private Spinner maximumsSalarySpinner;
    private Spinner shiftSpinner;
    private Spinner typeHiringSpinner;
    private VacancyRecyclerViewAdapter adapter;

    String[] minimumsSalary = new String[]{"Mínimo salário", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000"};
    String[] maximumsSalary = new String[]{"Máximo salário", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "11000"};
    String[] shifts = new String[]{"Turno", "manhã", "tarde", "noite"};
    String[] typesHiring = new String[]{"Tipo de contratação", "CTI", "CTD", "Temporário", "Terceirizado", "Parcial", "Estágio"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.vacancies);
        clearFilters = view.findViewById(R.id.clearFilters);
        minimumsSalarySpinner = view.findViewById(R.id.minimumSalarySpinner);
        maximumsSalarySpinner = view.findViewById(R.id.maximumSalarySpinner);
        shiftSpinner = view.findViewById(R.id.shiftSpinner);
        typeHiringSpinner = view.findViewById(R.id.typeHiringSpinner);
        vacancyDAO = VacancyDAO.getInstance(getContext());

        // pegando novo array de vagas
        vacancyDAO.getVacanciesFromAPI(getActivity());

        adapter = new VacancyRecyclerViewAdapter(vacancyDAO.getVacancies());
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, minimumsSalary){
            @Override
            public boolean isEnabled(int position){
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minimumsSalarySpinner.setAdapter(spinnerAdapter);
        minimumsSalarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vacancyListFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, maximumsSalary){
            @Override
            public boolean isEnabled(int position){
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maximumsSalarySpinner.setAdapter(spinnerAdapter);
        maximumsSalarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vacancyListFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, shifts){
            @Override
            public boolean isEnabled(int position){
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shiftSpinner.setAdapter(spinnerAdapter);
        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vacancyListFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, typesHiring){
            @Override
            public boolean isEnabled(int position){
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeHiringSpinner.setAdapter(spinnerAdapter);
        typeHiringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vacancyListFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        clearFilters.setOnClickListener(view1 -> {
            minimumsSalarySpinner.setSelection(0);
            maximumsSalarySpinner.setSelection(0);
            shiftSpinner.setSelection(0);
            typeHiringSpinner.setSelection(0);

            adapter.setVacancies(vacancyDAO.getVacancies());
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void vacancyListFilter(){
        List<Vacancy> filteredList = new ArrayList<>();

        for (Vacancy vacancy : vacancyDAO.getVacancies()) {
            boolean hasMinimumsSalary = minimumsSalarySpinner.getSelectedItemPosition() == 0 ||
                    Integer.parseInt(vacancy.getSalarySpinner()) >= Integer.parseInt(minimumsSalarySpinner.getSelectedItem().toString());
            boolean hasMaximumsSalary = maximumsSalarySpinner.getSelectedItemPosition() == 0 ||
                    Integer.parseInt(vacancy.getSalarySpinner()) <= Integer.parseInt(maximumsSalarySpinner.getSelectedItem().toString());
            boolean hasShift = shiftSpinner.getSelectedItemPosition() == 0 ||
                    vacancy.getShiftSpinner().equals(shiftSpinner.getSelectedItem());
            boolean typeHiring = typeHiringSpinner.getSelectedItemPosition() == 0 ||
                    vacancy.getTypeHiringSpinner().equals(typeHiringSpinner.getSelectedItem());

            if (hasMinimumsSalary && hasMaximumsSalary && hasShift && typeHiring) {
                filteredList.add(vacancy);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(),"Nenhum item encontrado com esse filtro", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setVacancies(filteredList);
        }
    }
}