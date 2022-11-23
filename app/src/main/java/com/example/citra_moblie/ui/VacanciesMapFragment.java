package com.example.citra_moblie.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.citra_moblie.R;

import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.model.Vacancy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
            // pegar so as que ele pode se cadastrar
            for (Vacancy vacancy : vacancyDAO.getHomeVacancies()){
                LatLng newMarker = new LatLng(Double. parseDouble(vacancy.getVacancyLat()), Double.parseDouble(vacancy.getVacancyLog()));
                googleMap.addMarker(new MarkerOptions()
                                .position(newMarker)
                                .title(vacancy.getVacancyName())
                                .snippet("descrição")
                                .icon(BitmapDescriptorFactory.fromBitmap(
                                        Bitmap.createScaledBitmap(vacancy.getVacancyImage(), 100, 100, false))));
            }

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    for (int i = 0; i < vacancyDAO.getHomeVacancies().size(); i++) {
                        Toast.makeText(getContext(), "c: " + (char) i + " e " + marker.getId(), Toast.LENGTH_SHORT).show();
                        if (i == Integer.parseInt(String.valueOf(marker.getId().charAt(1)))){
                            Bundle bundle = new Bundle();
                            Fragment fragment = new VacancyDetails();
                            bundle.putSerializable("position", i);

                            fragment.setArguments(bundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment_content_home, fragment, "vacancies_map");
                            transaction.addToBackStack("vacancies_map");
                            transaction.commit();
                            return true;
                        }
                    }
                    return false;
                }
            });

//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(newMarker));
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