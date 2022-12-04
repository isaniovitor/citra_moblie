package com.example.citra_moblie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.IVacancyDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.dao.VacancyDAO;
import com.example.citra_moblie.model.User;
import com.example.citra_moblie.model.Vacancy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    IUserDAO userDAO = UserDAO.getInstance(this);
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        Button registerUserButton = findViewById(R.id.announceVacancyButton);
        Button loginButton = findViewById(R.id.login);
        TextView loginEmail = findViewById(R.id.nameVacancyToCreate);
        TextView loginPassword = findViewById(R.id.descriptionVacancyToCreate);

        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!loginEmail.getText().toString().equals("") &&
                        !loginPassword.getText().toString().equals("")) {
                    authUser(loginEmail.getText().toString(),  loginPassword.getText().toString());
                }else{
                    Toast.makeText(LoginActivity.this,"Credenciais erradas ou vazias!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void authUser(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        // buncando usuário que se cadastrou
                        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();
                        userReference.child("usuarios").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                userDAO.setUser(user);

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        Toast.makeText(LoginActivity.this,"Erro ao logar!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            // buncando usuário que se cadastrou
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();
            userReference.child("usuarios").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    userDAO.setUser(user);

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}