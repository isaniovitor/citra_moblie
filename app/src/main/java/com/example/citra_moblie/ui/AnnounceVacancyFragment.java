package com.example.citra_moblie.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.Permission;
import com.example.citra_moblie.model.Vacancy;

public class AnnounceVacancyFragment extends Fragment {
    private TextView nameVacancyToCreate;
    private TextView descriptionVacancyToCreate;
    private Spinner shiftVacancyToCreate;
    private Spinner typeHiringVacancyToCreate;
    private TextView salaryVacancyToCreate;
    private int IMAGE_ACTION_CODE; // code 1 = camera; code 2 = gallery
    private ImageView profileImage;
    private String[] necessaryPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
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
        nameVacancyToCreate = view.findViewById(R.id.txtEmailRecover);
        descriptionVacancyToCreate = view.findViewById(R.id.descriptionVacancyToCreate);
        shiftVacancyToCreate = view.findViewById(R.id.shiftSpinnertoCreate);
        typeHiringVacancyToCreate = view.findViewById(R.id.typeHiringVacancyToCreatee);
        salaryVacancyToCreate = view.findViewById(R.id.salaryVacancyToCreate);
        IVacancyDAO vacancyDAO = VacancyDAO.getInstance(getContext());

        // spinners
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, shifts){
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
        shiftVacancyToCreate.setAdapter(spinnerAdapter);

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
                TextView tv = (TextView) view;

                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeHiringVacancyToCreate.setAdapter(spinnerAdapter);

        Permission.validatePermissions(necessaryPermissions, getActivity(), 1); // fazer codio Caso negada a permission
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // There are no request codes

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
                                Toast.makeText(getContext(),"Erro ao acessar imagem!", Toast.LENGTH_SHORT).show();
                            }

                            if (image != null) {
                                profileImage.setImageBitmap(image);
                            }
                        }
                    }
                });

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
                Vacancy vacancy = new Vacancy(((BitmapDrawable) profileImage.getDrawable()).getBitmap(),
                        nameVacancyToCreate.getText().toString(), descriptionVacancyToCreate.getText().toString(),
                        shiftVacancyToCreate.getSelectedItem().toString(), typeHiringVacancyToCreate.getSelectedItem().toString(),
                        salaryVacancyToCreate.getText().toString(), null);

                if (vacancyDAO.addVacancy(vacancy)) {
                    Toast.makeText(getContext(),"Sucesso ao anunciar Vaga!", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new UserCreatedVacancies();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_content_home, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else{
                    Toast.makeText(getContext(),"Erro ao anunciar Vaga!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}