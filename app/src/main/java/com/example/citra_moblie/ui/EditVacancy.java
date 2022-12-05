package com.example.citra_moblie.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.LoadingDialog;
import com.example.citra_moblie.helper.Permission;
import com.example.citra_moblie.model.Vacancy;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class EditVacancy extends Fragment {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String lat = "";
    private String log = "";
    private IVacancyDAO vacancyDAO;
    private LoadingDialog loadingDialog;
    private TextView nameVacancyToEdit;
    private TextView descriptionVacancyToEdit;
    private Spinner shiftVacancyToEdit;
    private Spinner typeHiringVacancyToEdit;
    private TextView salaryVacancyToEdit;
    private int IMAGE_ACTION_CODE; // code 1 = camera; code 2 = gallery
    private ImageView vacancyImageToCreate;
    private ImageButton gallery;
    private ImageButton camera;
    private String[] necessaryPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    String[] shifts = new String[]{"manhã", "tarde", "noite"};
    String[] typesHiring = new String[]{"CTI", "CTD", "Temporário", "Terceirizado", "Parcial", "Estágio"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_vacancy, container, false);

        gallery = view.findViewById(R.id.galleryButton);
        camera = view.findViewById(R.id.cameraButton);
        vacancyImageToCreate = view.findViewById(R.id.vacancyImageToCreate);
        nameVacancyToEdit = view.findViewById(R.id.nameVacancyToCreate);
        descriptionVacancyToEdit = view.findViewById(R.id.descriptionVacancyToCreate);
        shiftVacancyToEdit = view.findViewById(R.id.shiftVacancyToEdit);
        typeHiringVacancyToEdit = view.findViewById(R.id.typeHiringVacancyToEdit);
        salaryVacancyToEdit = view.findViewById(R.id.salaryVacancyToCreate);
        Button editVacancyButton = view.findViewById(R.id.announceVacancyButton);
        Button vacancyLocation = view.findViewById(R.id.vacancyLocation2);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        loadingDialog = new LoadingDialog(getActivity());
        vacancyDAO = VacancyDAO.getInstance(getContext());

        Bundle bundle = getArguments();
        Vacancy vacancy = (Vacancy) bundle.getSerializable("vacancy");

        // setando os dados
        if (vacancy.getVacancyImage() != null) {
            Picasso.get().load(Uri.parse(vacancy.getVacancyImage()))
                    .into(vacancyImageToCreate);
        }

        // spinners
        ArrayAdapter<String> shiftVacancyAdapter = new ArrayAdapter<String>(
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
        shiftVacancyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shiftVacancyToEdit.setAdapter(shiftVacancyAdapter);

        ArrayAdapter<String> typeHiringVacancyAdapter = new ArrayAdapter<String>(
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
        typeHiringVacancyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeHiringVacancyToEdit.setAdapter(typeHiringVacancyAdapter);

        nameVacancyToEdit.setText(vacancy.getVacancyName());
        descriptionVacancyToEdit.setText(vacancy.getVacancyDescription());
        salaryVacancyToEdit.setText(vacancy.getSalarySpinner());
        shiftVacancyToEdit.setSelection(shiftVacancyAdapter.getPosition(vacancy.getShiftSpinner()));
        typeHiringVacancyToEdit.setSelection(typeHiringVacancyAdapter.getPosition(vacancy.getTypeHiringSpinner()));
        lat = vacancy.getVacancyLat();
        log = vacancy.getVacancyLog();

        Permission.validatePermissions(necessaryPermissions, getActivity(), 1);

        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bitmap image = null;

                        try {
                            switch (IMAGE_ACTION_CODE){
                                case 1:
                                    image = (Bitmap) result.getData().getExtras().get("data");
                                    break;
                                case 2:
                                    Uri localImage = result.getData().getData();
                                    image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), localImage);
                                    break;
                            }
                        }catch (Exception e){
                            // por toast
                        }

                        if (image != null) {
                            vacancyImageToCreate.setImageBitmap(image);
                        }
                    }
                });

        // botao localização
        vacancyLocation.setOnClickListener(view14 -> getLocation());

        camera.setOnClickListener(view1 -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                resultLauncher.launch(intent);
                IMAGE_ACTION_CODE = 1;
            }
        });

        gallery.setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                resultLauncher.launch(intent);
                IMAGE_ACTION_CODE = 2;
            }
        });

        editVacancyButton.setOnClickListener(view13 -> {
            Vacancy editedVacancy = new Vacancy(
                    vacancy.getIdVacancy(),
                    vacancy.getIdUser(),
                    null,
                    nameVacancyToEdit.getText().toString(),
                    descriptionVacancyToEdit.getText().toString(),
                    shiftVacancyToEdit.getSelectedItem().toString(),
                    typeHiringVacancyToEdit.getSelectedItem().toString(),
                    salaryVacancyToEdit.getText().toString(),
                    vacancy.getVacancyLat(),
                    vacancy.getVacancyLog()
            );

            if (!nameVacancyToEdit.getText().toString().equals("") && !descriptionVacancyToEdit.getText().toString().equals("") &&
                    !salaryVacancyToEdit.getText().toString().equals("") && !lat.equals("") && !log.equals("")) {
                saveVacancyImage(vacancy, editedVacancy);
            }else{
                Toast.makeText(getContext(),"Campos vazios!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {

                        // ativar localização é necessário
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                lat = Double.toString(addresses.get(0).getLatitude());
                                log = Double.toString(addresses.get(0).getLongitude());

                                Toast.makeText(getContext(), "localização encontrada!", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getContext(), "Não foi possível obter a localização!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveVacancyImage(Vacancy vacancy, Vacancy editedVacancy) {
        loadingDialog.startAlertDialog();

        // salvando imagem
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap vacancyImage = ((BitmapDrawable) vacancyImageToCreate.getDrawable()).getBitmap();
        vacancyImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // converte pixels em uma matriz de bytes
        byte[] imageData = baos.toByteArray();

        // definindo nó
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child("imagesVacancies").child(vacancy.getIdVacancy());

        // objeto que controla upload
        UploadTask uploadTask = imageRef.putBytes(imageData);

        // tratando respostas
        uploadTask.addOnSuccessListener(getActivity(), taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                editedVacancy.setVacancyImage(uri.toString());
                saveVacancy(vacancy, editedVacancy);
            });
        });

        uploadTask.addOnFailureListener(getActivity(), e ->
                Toast.makeText(getContext(), "Não foi possível salvar imagem!", Toast.LENGTH_SHORT).show());
                saveVacancy(vacancy, editedVacancy);
    }

    private void saveVacancy(Vacancy vacancy, Vacancy editedVacancy) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("AllVacancies").child(vacancy.getIdVacancy()).setValue(editedVacancy)
                        .addOnCompleteListener(task -> {
                            vacancyDAO.getVacanciesFromAPI(getActivity());
                            Toast.makeText(getContext(),"Sucesso ao editar vaga!", Toast.LENGTH_SHORT).show();

                            loadingDialog.dismissAlertDialog();
                            Fragment fragment = new UserCreatedVacancies();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment_content_home, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        });
    }
}