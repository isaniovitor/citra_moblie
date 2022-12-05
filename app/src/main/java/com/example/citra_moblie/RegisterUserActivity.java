package com.example.citra_moblie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.citra_moblie.helper.LoadingDialog;
import com.example.citra_moblie.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity {
    private LoadingDialog loadingDialog = new LoadingDialog(this);
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

                    saveUser(user);
                }else{
                    Toast.makeText(RegisterUserActivity.this,"Senhas diferentes!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegisterUserActivity.this,"Campos vazios!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveUser(User user){
        loadingDialog.startAlertDialog();
        auth.createUserWithEmailAndPassword(user.getEmail(),
                user.getPassword()).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                user.setId(auth.getUid());

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("usuarios").child(user.getId()).setValue(user);

                loadingDialog.dismissAlertDialog();
                Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                loadingDialog.dismissAlertDialog();
                Toast.makeText(RegisterUserActivity.this,"Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}