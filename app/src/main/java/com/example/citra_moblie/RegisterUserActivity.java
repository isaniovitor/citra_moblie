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
import com.example.citra_moblie.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Button registerUserButton;
    private TextView registerUserName;
    private TextView registerUserEmail;
    private TextView registerUserBirthday;
    private TextView registerUserCpf;
    private TextView registerUserPassword;
    private TextView registerUserRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_user);

        registerUserName = findViewById(R.id.nameVacancyToCreate);
        registerUserEmail = findViewById(R.id.descriptionVacancyToCreate);
        registerUserBirthday = findViewById(R.id.shiftVacancyToCreate);
        registerUserCpf = findViewById(R.id.typeHiringVacancyToCreate);
        registerUserPassword = findViewById(R.id.salaryVacancyToCreate);
        registerUserRepeatPassword = findViewById(R.id.registerUserRepeatPassword);
        registerUserButton = findViewById(R.id.announceVacancyButton);
        IUserDAO userDAO = UserDAO.getInstance(this);

        registerUserButton.setOnClickListener(view -> {
            if (registerUserPassword.getText().toString().equals(registerUserRepeatPassword.getText().toString())) {
                // ((BitmapDrawable) profileImage.getDrawable()).getBitmap()
                User user = new User(
                        null,
                        null,
                        registerUserName.getText().toString(),
                        registerUserEmail.getText().toString(),
                        registerUserBirthday.getText().toString(),
                        registerUserCpf.getText().toString(),
                        registerUserPassword.getText().toString()
                );

                saveUser(user);
            }else{
                Toast.makeText(RegisterUserActivity.this,"Senhas diferentes!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveUser(User user){
        auth.createUserWithEmailAndPassword(user.getEmail(),
                user.getPassword()).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                user.setId(auth.getUid());

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("usuarios").child(user.getId()).setValue(user);

                Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(RegisterUserActivity.this,"Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}