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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.citra_moblie.R;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.helper.Permission;


public class EditVacancy extends Fragment {
    private TextView nameVacancyToEdit;
    private TextView descriptionVacancyToEdit;
    private TextView shiftVacancyToEdit;
    private TextView typeHiringVacancyToEdit;
    private TextView salaryVacancyToEdit;
    private Button editVacancyButton;
    private int IMAGE_ACTION_CODE; // code 1 = camera; code 2 = gallery
    private ImageView vacancyImageToCreate;
    private ImageButton gallery;
    private ImageButton camera;
    private String[] necessaryPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_vacancy, container, false);

        gallery = view.findViewById(R.id.galleryButton);
        camera = view.findViewById(R.id.cameraButton);
        vacancyImageToCreate = view.findViewById(R.id.vacancyImageToCreate);
        nameVacancyToEdit = view.findViewById(R.id.nameVacancyToCreate);
        descriptionVacancyToEdit = view.findViewById(R.id.descriptionVacancyToCreate);
        shiftVacancyToEdit = view.findViewById(R.id.shiftVacancyToCreate);
        typeHiringVacancyToEdit = view.findViewById(R.id.typeHiringVacancyToCreate);
        salaryVacancyToEdit = view.findViewById(R.id.salaryVacancyToCreate);
        editVacancyButton = view.findViewById(R.id.announceVacancyButton);

        Bundle bundle = getArguments();
        IVacancyDAO vacancyDAO = VacancyDAO.getInstance(getContext());
        int vacancyPosition = (int) bundle.getSerializable("position");

        // setando os dados
        if (vacancyDAO.getVacancy(vacancyPosition).getVacancyImage() != null) {
            vacancyImageToCreate.setImageBitmap(vacancyDAO.getVacancy(vacancyPosition).getVacancyImage());
        }

        nameVacancyToEdit.setText(vacancyDAO.getCreatedVacancy(vacancyPosition).getVacancyName());
        descriptionVacancyToEdit.setText(vacancyDAO.getCreatedVacancy(vacancyPosition).getVacancyDescription());
        shiftVacancyToEdit.setText(vacancyDAO.getCreatedVacancy(vacancyPosition).getShiftSpinner());
        typeHiringVacancyToEdit.setText(vacancyDAO.getCreatedVacancy(vacancyPosition).getTypeHiringSpinner());
        salaryVacancyToEdit.setText(vacancyDAO.getCreatedVacancy(vacancyPosition).getSalatySpinner());

        Permission.validatePermissions(necessaryPermissions, getActivity(), 1);
        // fazer codio Caso negada a permission

        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
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

        // salvar mudança
        editVacancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vacancyDAO.getCreatedVacancy(vacancyPosition).setVacancyImage(((BitmapDrawable) vacancyImageToCreate.getDrawable()).getBitmap());
                vacancyDAO.getCreatedVacancy(vacancyPosition).setVacancyName(nameVacancyToEdit.getText().toString());
                vacancyDAO.getCreatedVacancy(vacancyPosition).setVacancyDescription(descriptionVacancyToEdit.getText().toString());
                vacancyDAO.getCreatedVacancy(vacancyPosition).setShiftSpinner(shiftVacancyToEdit.getText().toString());
                vacancyDAO.getCreatedVacancy(vacancyPosition).setTypeHiringSpinner(typeHiringVacancyToEdit.getText().toString());
                vacancyDAO.getCreatedVacancy(vacancyPosition).setSalatySpinner(salaryVacancyToEdit.getText().toString());

                Fragment fragment = new UserCreatedVacancies();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_content_home, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}