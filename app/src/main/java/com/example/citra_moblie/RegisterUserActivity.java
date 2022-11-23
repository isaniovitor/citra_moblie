package com.example.citra_moblie;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import com.example.citra_moblie.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class RegisterUserActivity extends AppCompatActivity {
    public Button registerUserButton;
    private int IMAGE_ACTION_CODE; // code 1 = camera; code 2 = gallery
    private ImageView profileImage;
    private TextView registerUserName;
    private TextView registerUserEmail;
    private TextView registerUserBirthday;
    private TextView registerUserCpf;
    private TextView registerUserPassword;
    private TextView registerUserRepeatPassword;
    private String[] necessaryPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private FirebaseAuth auth;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_user);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        registerUserName = findViewById(R.id.txtEmailRecover);
        registerUserEmail = findViewById(R.id.descriptionVacancyToCreate);
        registerUserBirthday = findViewById(R.id.shiftVacancyToCreate);
        registerUserCpf = findViewById(R.id.typeHiringVacancyToCreate);
        registerUserPassword = findViewById(R.id.salaryVacancyToCreate);
        registerUserRepeatPassword = findViewById(R.id.registerUserRepeatPassword);
        profileImage = findViewById(R.id.profileImage);
        registerUserButton = findViewById(R.id.announceVacancyButton);
        ImageButton gallery = findViewById(R.id.galleryButton);
        ImageButton camera = findViewById(R.id.cameraButton);
        IUserDAO userDAO = UserDAO.getInstance(this);



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

        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaDados();
                uploadImagem();
            }
        });
    }

    private void validaDados(){

        User userModel = new User();



        //userModel.setImage((profileImage.getDrawable()));
        userModel.setName(registerUserName.getText().toString());
        userModel.setEmail(registerUserEmail.getText().toString());
        userModel.setBirthday(registerUserBirthday.getText().toString());
        userModel.setCpf(registerUserCpf.getText().toString());

        if(     registerUserName.getText().toString().equals("") ||
                registerUserEmail.getText().toString().equals("") ||
                registerUserPassword.getText().toString().equals("") ||
                registerUserRepeatPassword.getText().toString().equals("") ||
                registerUserCpf.getText().toString().equals("")){

            Toast.makeText(RegisterUserActivity.this, "Valores incorretos",Toast.LENGTH_LONG).show();
        }
        else if (!registerUserPassword.getText().toString().equals(registerUserRepeatPassword.getText().toString())) {
            Toast.makeText(RegisterUserActivity.this,"Senhas diferentes!", Toast.LENGTH_SHORT).show();

        }else{
            criarConta(userModel.getEmail(),registerUserRepeatPassword.getText().toString());
        }
    }

    private void criarConta(String email, String senha){
        User userModel = new User();
        userModel.setName(registerUserName.getText().toString());
        userModel.setEmail(registerUserEmail.getText().toString());
        userModel.setBirthday(registerUserBirthday.getText().toString());
        userModel.setCpf(registerUserCpf.getText().toString());


        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                userModel.setId(auth.getUid());
                userModel.salvar();
                startActivity(new Intent(this, HomeActivity.class));
            }else{
                Toast.makeText(RegisterUserActivity.this,"Erro ao logar!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadImagem(){
        User userModel = new User();

        StorageReference reference = storage.getReference().child("upload");
        StorageReference nome_imagem = reference.child(userModel.getId()+".jpg");

        BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        UploadTask uploadTask = nome_imagem.putBytes(bytes.toByteArray());

        uploadTask.addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this, "Upload com sucesso", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Erro ao fazer upload", Toast.LENGTH_SHORT).show();
            }
        });
    }
}