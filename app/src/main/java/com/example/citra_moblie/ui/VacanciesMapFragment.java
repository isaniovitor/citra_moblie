package com.example.citra_moblie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.model.Vacancy;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class VacanciesMapFragment extends Fragment {
    IVacancyDAO vacancyDAO = VacancyDAO.getInstance(getContext());

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            for (Vacancy vacancy : vacancyDAO.getVacancies()){
                LatLng newMarker = new LatLng(Double.parseDouble(vacancy.getVacancyLat()), Double.parseDouble(vacancy.getVacancyLog()));
                Marker marker = googleMap.addMarker(new MarkerOptions().position(newMarker).title("vacancy.getVacancyName()").snippet("descrição"));
                marker.showInfoWindow();
            }

            googleMap.setOnMarkerClickListener(marker -> {
                for (int i = 0; i < vacancyDAO.getVacancies().size(); i++) {
                    if (i == Integer.parseInt(String.valueOf(marker.getId().charAt(1)))){
                        Bundle bundle = new Bundle();
                        Fragment fragment = new VacancyDetails();
                        bundle.putSerializable("vacancy", vacancyDAO.getVacancy(i));

                        fragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment_content_home, fragment, "vacancies_map");
                        transaction.addToBackStack("vacancies_map");
                        transaction.commit();
                        return true;
                    }
                }
                return false;
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vacancies_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}