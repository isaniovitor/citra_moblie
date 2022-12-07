package com.example.citra_moblie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.citra_moblie.dao.IUserDAO;
import com.example.citra_moblie.dao.UserDAO;
import com.example.citra_moblie.helper.LoadingDialog;
import com.example.citra_moblie.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity {
    private LoadingDialog loadingDialog = new LoadingDialog(this);
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private IUserDAO userDAO;
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
        userDAO = UserDAO.getInstance(this);

        registerUserButton.setOnClickListener(view -> {
            if (!registerUserName.getText().toString().equals("") && !registerUserEmail.getText().toString().equals("") &&
                    !registerUserBirthday.getText().toString().equals("") && !registerUserCpf.getText().toString().equals("") &&
                    !registerUserPassword.getText().toString().equals("") && !registerUserRepeatPassword.getText().toString().equals("")) {
                if (registerUserPassword.getText().toString().equals(registerUserRepeatPassword.getText().toString())) {
                    User user = new User(
                            null,
                            null,
                            registerUserName.getText().toString(),
                            registerUserEmail.getText().toString(),
                            registerUserBirthday.getText().toString(),
                            registerUserCpf.getText().toString(),
                            registerUserPassword.getText().toString()
                    );

                    SaveUser saveUser = new SaveUser(this, user);
                    saveUser.start();
                }else{
                    Toast.makeText(RegisterUserActivity.this,"Senhas diferentes!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegisterUserActivity.this,"Campos vazios!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class SaveUser extends Thread {
        private Activity activity;
        private User user;

        public SaveUser(Activity activity, User user) {
            this.activity = activity;
            this.user = user;
        }

        @Override
        public void run() {
            OnCompleteListener completeListener = task -> {
                if(task.isSuccessful()){
                    Thread.currentThread().interrupt();
                    user.setId(auth.getUid());

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("usuarios").child(user.getId()).setValue(user);

                    activity.runOnUiThread((Runnable) () -> loadingDialog.dismissAlertDialog());
                    Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    activity.runOnUiThread((Runnable) () -> loadingDialog.dismissAlertDialog());
                    Toast.makeText(RegisterUserActivity.this,"Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
                }
            };

            activity.runOnUiThread((Runnable) () -> loadingDialog.startAlertDialog());
            userDAO.saveUser(completeListener, user);
        }
    }
}