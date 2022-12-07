package com.example.citra_moblie;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.helper.LoadingDialog;
import com.example.citra_moblie.helper.Permission;
import com.example.citra_moblie.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditUserActivity extends AppCompatActivity {
    private IUserDAO userDAO = UserDAO.getInstance(this);
    private LoadingDialog loadingDialog = new LoadingDialog(this);
    private FirebaseAuth auth = FirebaseAuth.getInstance();
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
        registerUserName = findViewById(R.id.nameVacancyToCreate);
        registerUserEmail = findViewById(R.id.descriptionVacancyToCreate);
        registerUserBirthday = findViewById(R.id.shiftVacancyToCreate);
        registerUserCpf = findViewById(R.id.typeHiringVacancyToCreate);
        registerUserPassword = findViewById(R.id.salaryVacancyToCreate);
        registerUserRepeatPassword = findViewById(R.id.registerUserRepeatPassword);
        profileImage = findViewById(R.id.profileImage);
        announceVacancyButton = findViewById(R.id.announceVacancyButton);

        // setando os dados
        if (userDAO.getUser().getImage() != null) {
            Picasso.get().load(Uri.parse(userDAO.getUser().getImage()))
                    .into(profileImage);
        }
        registerUserName.setText(userDAO.getUser().getName());
        registerUserEmail.setText(userDAO.getUser().getEmail());
        registerUserBirthday.setText(userDAO.getUser().getBirthday());
        registerUserCpf.setText(userDAO.getUser().getCpf());
        registerUserPassword.setText(userDAO.getUser().getPassword());
        registerUserRepeatPassword.setText(userDAO.getUser().getPassword());

        Permission.validatePermissions(necessaryPermissions, this, 1);

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
                                    image = MediaStore.Images.Media.getBitmap(getContentResolver(), localImage);
                                    break;
                            }

                        }catch (Exception e){
                            Toast.makeText(EditUserActivity.this,"Não foi possível recuperar imagem!", Toast.LENGTH_SHORT).show();
                        }

                        if (image != null) {
                            profileImage.setImageBitmap(image);
                        }
                    }
                });

        camera.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                resultLauncher.launch(intent);
                IMAGE_ACTION_CODE = 1;
            }
        });

        gallery.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (intent.resolveActivity(getPackageManager()) != null) {
                resultLauncher.launch(intent);
                IMAGE_ACTION_CODE = 2;
            }
        });

        announceVacancyButton.setOnClickListener(view -> {
            if (!registerUserName.getText().toString().equals("") && !registerUserEmail.getText().toString().equals("") &&
                    !registerUserBirthday.getText().toString().equals("") && !registerUserCpf.getText().toString().equals("") &&
                    !registerUserPassword.getText().toString().equals("") && !registerUserRepeatPassword.getText().toString().equals("")) {
                if (registerUserPassword.getText().toString().equals(registerUserRepeatPassword.getText().toString())) {
                    User editedUser = new User(
                            userDAO.getUser().getId(),
                            null,
                            registerUserName.getText().toString(),
                            registerUserEmail.getText().toString(),
                            registerUserBirthday.getText().toString(),
                            registerUserCpf.getText().toString(),
                            registerUserPassword.getText().toString()
                    );

                    EditUser editUser = new EditUser(this, profileImage,  editedUser);
                    editUser.start();
                }else{
                    Toast.makeText(EditUserActivity.this,"Senhas diferentes!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(EditUserActivity.this,"Campos vazios!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class EditUser extends Thread {
        private Activity activity;
        private User user;
        private ImageView imageView;

        public EditUser(Activity activity, ImageView imageView, User editedUser) {
            this.activity = activity;
            this.user = editedUser;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Toast.makeText(activity,"Sucesso ao editar usuário!", Toast.LENGTH_SHORT).show();

                    User user = snapshot.getValue(User.class);
                    userDAO.setUser(user);

                    activity.runOnUiThread((Runnable) () -> loadingDialog.dismissAlertDialog());
                    Intent intent = new Intent(activity, HomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            activity.runOnUiThread((Runnable) () -> loadingDialog.startAlertDialog());
            userDAO.saveImageUser(activity, imageView, eventListener, user);
        }
    }
}