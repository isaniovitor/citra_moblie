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
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView loginEmail;
    private TextView loginPassword;
    private TextView txtRecoverAccount;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        Button registerUserButton = findViewById(R.id.announceVacancyButton);
        Button loginButton = findViewById(R.id.btnLogin);
        txtRecoverAccount = findViewById(R.id.txtRecoverAccount);
        loginEmail = findViewById(R.id.txtEmailRecover);
        loginPassword = findViewById(R.id.descriptionVacancyToCreate);
        IUserDAO userDAO = UserDAO.getInstance(this);

        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(view -> validaDados());

        txtRecoverAccount.setOnClickListener(view ->
                startActivity(new Intent(this, RecoverAccountActivity.class)));


    }

    private void validaDados(){
        if(loginEmail.getText().toString().equals("") || loginPassword.getText().toString().equals("")){
            Toast.makeText(this, "Preencha os campos",Toast.LENGTH_LONG).show();
        }
        else{
            loginConta(loginEmail.getText().toString(), loginPassword.getText().toString());
        }
    }

    private void loginConta(String email, String senha){
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                startActivity(new Intent(this, HomeActivity.class));
            }else{
                Toast.makeText(LoginActivity.this,"Erro ao logar!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}