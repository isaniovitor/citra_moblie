package com.example.citra_moblie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.RecyclerItemClickListener;
import com.example.citra_moblie.adapter.VacancyRecyclerViewAdapter;
import com.example.citra_moblie.databinding.FragmentHomeBinding;
import com.example.citra_moblie.model.Vacancy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private Spinner salarySpinner;
    private Spinner shiftSpinner;
    private Spinner typeHiringSpinner;

    String[] shift = new String[]{"Escolher", "manhã", "tarde", "noite"};
    String[] typeHiring = new String[]{"Escolher", "CLT", "STE", "SDE", "EEL", "SEW"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.vacancies);
//        salarySpinner = view.findViewById(R.id.salarySpinner);
        shiftSpinner = view.findViewById(R.id.shiftSpinner);
        typeHiringSpinner = view.findViewById(R.id.typeHiringSpinner);

        IVacancyDAO vacancyDAO = VacancyDAO.getInstance(getContext());
        VacancyRecyclerViewAdapter adapter = new VacancyRecyclerViewAdapter(vacancyDAO.getHomeVacancies());

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

//        // spinners
//        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.salary, android.R.layout.simple_spinner_item);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        salarySpinner.setAdapter(spinnerAdapter);
//        salarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String itemSelected =  adapterView.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, shift){
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
        shiftSpinner.setAdapter(spinnerAdapter);
        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected =  adapterView.getSelectedItem().toString();

                if (itemSelected.equals("Escolher")) {
                    adapter.setVacancies(vacancyDAO.getHomeVacancies());
                    return;
                }

                List<Vacancy> filteredList = new ArrayList<>();
                for (Vacancy vacancy : vacancyDAO.getHomeVacancies()) {
                    if (vacancy.getShiftSpinner().equals(itemSelected)) {
                        Toast.makeText(getContext(),"filtro" + vacancy.getVacancyName(), Toast.LENGTH_SHORT).show();
                        filteredList.add(vacancy);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(getContext(),"Nenhum item encontrado com esse filtro", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.setVacancies(filteredList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, typeHiring){
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
        typeHiringSpinner.setAdapter(spinnerAdapter);
        typeHiringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String itemSelected =  adapterView.getSelectedItem().toString();

                if (itemSelected.equals("Escolher")) {
                    adapter.setVacancies(vacancyDAO.getHomeVacancies());
                    return;
                }

                List<Vacancy> filteredList = new ArrayList<>();
                for (Vacancy vacancy : vacancyDAO.getHomeVacancies()) {
                    if (vacancy.getTypeHiringSpinner().equals(itemSelected)) {
                        Toast.makeText(getContext(),"filtro" + vacancy.getVacancyName(), Toast.LENGTH_SHORT).show();
                        filteredList.add(vacancy);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(getContext(),"Nenhum item encontrado com esse filtro", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.setVacancies(filteredList);
                }
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
}