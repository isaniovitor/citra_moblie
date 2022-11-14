package com.example.citra_moblie;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.helper.Permission;

public class EditUserActivity extends AppCompatActivity {
    private int IMAGE_ACTION_CODE; // code 1 = camera; code 2 = gallery
    private ImageView profileImage;
    private TextView registerUserName;
    private TextView registerUserEmail;
    private TextView registerUserBirthday;
    private TextView registerUserCpf;
    private TextView registerUserPassword;
    private TextView registerUserRepeatPassword;
    private ImageButton gallery;
    private ImageButton camera;
    private Button announceVacancyButton;
    private String[] necessaryPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_user);

        gallery = findViewById(R.id.galleryButton);
        camera = findViewById(R.id.cameraButton);
        registerUserName = findViewById(R.id.txtEmailRecover);
        registerUserEmail = findViewById(R.id.descriptionVacancyToCreate);
        registerUserBirthday = findViewById(R.id.shiftVacancyToCreate);
        registerUserCpf = findViewById(R.id.typeHiringVacancyToCreate);
        registerUserPassword = findViewById(R.id.salaryVacancyToCreate);
        registerUserRepeatPassword = findViewById(R.id.registerUserRepeatPassword);
        profileImage = findViewById(R.id.profileImage);
        announceVacancyButton = findViewById(R.id.announceVacancyButton);
        IUserDAO userDAO = UserDAO.getInstance(this);

        // setando os dados
        if (userDAO.getUser().getImage() != null) {
            profileImage.setImageBitmap(userDAO.getUser().getImage());
        }

        registerUserName.setText(userDAO.getUser().getName());
        registerUserEmail.setText(userDAO.getUser().getEmail());
        registerUserBirthday.setText(userDAO.getUser().getBirthday());
        registerUserCpf.setText(userDAO.getUser().getCpf());
        registerUserPassword.setText(userDAO.getUser().getPassword());
        registerUserRepeatPassword.setText(userDAO.getUser().getPassword());

        Permission.validatePermissions(necessaryPermissions, this, 1);
        // fazer codio Caso negada a permission

        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
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
                                        image = MediaStore.Images.Media.getBitmap(getContentResolver(), localImage);
                                        break;
                                }

                            }catch (Exception e){
                                // por toast
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
                if (intent.resolveActivity(getPackageManager()) != null) {
                    resultLauncher.launch(intent);
                    IMAGE_ACTION_CODE = 1;
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    resultLauncher.launch(intent);
                    IMAGE_ACTION_CODE = 2;
                }
            }
        });

        announceVacancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerUserPassword.getText().toString().equals(registerUserRepeatPassword.getText().toString())) {
                    //userDAO.getUser().setImage(((BitmapDrawable) profileImage.getDrawable()).getBitmap());
                    userDAO.getUser().setName(registerUserName.getText().toString());
                    userDAO.getUser().setEmail(registerUserEmail.getText().toString());
                    userDAO.getUser().setBirthday(registerUserBirthday.getText().toString());
                    userDAO.getUser().setCpf(registerUserCpf.getText().toString());
                    userDAO.getUser().setPassword( registerUserPassword.getText().toString());

                    Intent intent = new Intent(EditUserActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(EditUserActivity.this,"Senhas diferentes!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}