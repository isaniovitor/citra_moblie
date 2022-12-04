package com.example.citra_moblie.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.Permission;
import com.example.citra_moblie.model.Vacancy;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AnnounceVacancyFragment extends Fragment {
    IUserDAO userDAO = UserDAO.getInstance(getContext());
    IVacancyDAO vacancyDAO = VacancyDAO.getInstance(getContext());
    FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;
    private String lat;
    private String log;
    private TextView nameVacancyToCreate;
    private TextView descriptionVacancyToCreate;
    private Spinner shiftVacancyToCreate;
    private Spinner typeHiringVacancyToCreate;
    private TextView salaryVacancyToCreate;
    private int IMAGE_ACTION_CODE; // code 1 = camera; code 2 = gallery
    private ImageView profileImage;
    private String[] necessaryPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    String[] shifts = new String[]{"manhã", "tarde", "noite"};
    String[] typesHiring = new String[]{"CTI", "CTD", "Temporário", "Terceirizado", "Parcial", "Estágio"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announce_vacancy, container, false);

        ImageButton gallery = view.findViewById(R.id.galleryButton);
        ImageButton camera = view.findViewById(R.id.cameraButton);
        profileImage = view.findViewById(R.id.vacancyImageToCreate);
        Button announceVacancyButton = view.findViewById(R.id.announceVacancyButton);
        nameVacancyToCreate = view.findViewById(R.id.nameVacancyToCreate);
        descriptionVacancyToCreate = view.findViewById(R.id.descriptionVacancyToCreate);
        shiftVacancyToCreate = view.findViewById(R.id.shiftSpinnertoCreate);
        typeHiringVacancyToCreate = view.findViewById(R.id.typeHiringVacancyToCreatee);
        salaryVacancyToCreate = view.findViewById(R.id.salaryVacancyToCreate);
        Button vacancyLocation = view.findViewById(R.id.vacancyLocation);
        Permission.validatePermissions(necessaryPermissions, getActivity(), 1); // fazer codio Caso negada a permission
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        vacancyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        // spinners
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, shifts) {
            @Override
            public boolean isEnabled(int position) {
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
        shiftVacancyToCreate.setAdapter(spinnerAdapter);

        spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, typesHiring) {
            @Override
            public boolean isEnabled(int position) {
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
        typeHiringVacancyToCreate.setAdapter(spinnerAdapter);

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // There are no request codes

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap image = null;

                            try {
                                switch (IMAGE_ACTION_CODE) {
                                    case 1:
                                        image = (Bitmap) result.getData().getExtras().get("data");
                                        break;
                                    case 2:
                                        Uri localImage = result.getData().getData();
                                        image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), localImage);
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Erro ao acessar imagem!", Toast.LENGTH_SHORT).show();
                            }

                            if (image != null) {
                                profileImage.setImageBitmap(image);
                            }
                        }
                    }
                });

        //tirar
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    resultLauncher.launch(intent);
                    IMAGE_ACTION_CODE = 1;
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    resultLauncher.launch(intent);
                    IMAGE_ACTION_CODE = 2;
                }
            }
        });

        announceVacancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                // ((BitmapDrawable) profileImage.getDrawable()).getBitmap()
                Vacancy vacancy = new Vacancy(reference.push().getKey(), userDAO.getUser().getId(), null,
                        nameVacancyToCreate.getText().toString(), descriptionVacancyToCreate.getText().toString(),
                        shiftVacancyToCreate.getSelectedItem().toString(), typeHiringVacancyToCreate.getSelectedItem().toString(),
                        salaryVacancyToCreate.getText().toString(), lat, log);

                if (vacancyDAO.addVacancy(vacancy)) {
                    Toast.makeText(getContext(), "Sucesso ao anunciar Vaga!", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new UserCreatedVacancies();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_content_home, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Erro ao anunciar Vaga!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            // ativar localização é necessário
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                List<Address> addresses;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    lat = Double.toString(addresses.get(0).getLatitude());
                                    log = Double.toString(addresses.get(0).getLongitude());

                                    Toast.makeText(getContext(), "localização encontrada ", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Toast.makeText(getContext(), "Não foi possível obter a localização", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}