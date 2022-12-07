package com.example.citra_moblie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.helper.LoadingDialog;
import com.example.citra_moblie.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private IUserDAO userDAO;
    private LoadingDialog loadingDialog = new LoadingDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        Button registerUserButton = findViewById(R.id.announceVacancyButton);
        Button loginButton = findViewById(R.id.login);
        TextView loginEmail = findViewById(R.id.nameVacancyToCreate);
        TextView loginPassword = findViewById(R.id.descriptionVacancyToCreate);
        userDAO = UserDAO.getInstance(this);

        registerUserButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            if (!loginEmail.getText().toString().equals("") &&
                    !loginPassword.getText().toString().equals("")) {
                AuthUser authUser = new AuthUser(this, loginEmail.getText().toString(),  loginPassword.getText().toString());
                authUser.start();
            }else{
                Toast.makeText(LoginActivity.this,"Credenciais erradas ou vazias!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        AuthUser authUser = new AuthUser(this);
        authUser.start();
    }

    class AuthUser extends Thread {
        private String email;
        private String password;
        private Activity activity;
        private boolean isLogged = auth.getCurrentUser() != null;

        public AuthUser(Activity activity) {
            this.activity = activity;
        }

        public AuthUser(Activity activity, String email, String password) {
            this.activity = activity;
            this.email = email;
            this.password = password;
        }

        @Override
        public void run() {
            // login
            OnCompleteListener completeListener = task -> {
                if(task.isSuccessful()){
                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();
                    userReference.child("usuarios").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Thread.currentThread().interrupt();

                            User user = snapshot.getValue(User.class);
                            userDAO.setUser(user);

                            activity.runOnUiThread((Runnable) () -> loadingDialog.dismissAlertDialog());
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        };
                    });
                }else{
                    activity.runOnUiThread((Runnable) () -> loadingDialog.dismissAlertDialog());
                    Toast.makeText(LoginActivity.this,"Erro ao logar!", Toast.LENGTH_SHORT).show();
                }
            };

            // alreadyLogged
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Thread.currentThread().interrupt();

                    User user = snapshot.getValue(User.class);
                    userDAO.setUser(user);

                    activity.runOnUiThread((Runnable) () -> loadingDialog.dismissAlertDialog());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            // quando está logado
            if (isLogged || (email != null && password != null)) {
                activity.runOnUiThread((Runnable) () -> loadingDialog.startAlertDialog());
                userDAO.authUser(email, password, completeListener, eventListener, isLogged);
            }
        }
    }
}