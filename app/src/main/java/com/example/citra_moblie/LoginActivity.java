package com.example.citra_moblie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.helper.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail;
    private EditText loginPassword;
    private TextView txtRecoverAccount;
    private FirebaseAuth auth;

    private LoginActivity user;

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

    @Override
    protected void onStart() {
        super.onStart();
        verificaLogin();
    }

    private void validaDados(){
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Preencha os campos",Toast.LENGTH_LONG).show();
        }
        else{
            loginConta(email, password);
        }
    }

    private void loginConta(String email, String senha){
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               startActivity(new Intent(LoginActivity.this, HomeActivity.class));
           }
        });
    }

    private void verificaLogin() {
        if (FirebaseHelper.getAutenticado()) {
            startActivity(new Intent(this, HomeActivity.class));
        } else {
        }

    }

    /*
    private void verificaCadastro(String idUser) {
        DatabaseReference loginRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(idUser);
        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(LoginActivity.class);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
            }
        });
    }*/
}