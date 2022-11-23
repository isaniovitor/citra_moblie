package com.example.citra_moblie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.UserDAO;

public class LoginActivity extends AppCompatActivity {
    private TextView loginName;
    private TextView loginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        Button registerUserButton = findViewById(R.id.announceVacancyButton);
        Button loginButton = findViewById(R.id.login);
        TextView loginEmail = findViewById(R.id.nameVacancyToCreate);
        TextView loginPassword = findViewById(R.id.descriptionVacancyToCreate);
        IUserDAO userDAO = UserDAO.getInstance(this);

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
                if (loginEmail.getText().toString().equals(userDAO.getUser().getEmail()) &&
                        loginPassword.getText().toString().equals(userDAO.getUser().getPassword())) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Credenciais erradas ou vazias!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}