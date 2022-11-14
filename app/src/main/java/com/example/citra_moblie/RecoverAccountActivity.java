package com.example.citra_moblie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RecoverAccountActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView txtEmailRecover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_account);
        auth = FirebaseAuth.getInstance();

        Button btnRecover = findViewById(R.id.btnRecover);
        txtEmailRecover = findViewById(R.id.txtEmailRecover);

        btnRecover.setOnClickListener(view -> validaDados());
    }

    private void validaDados(){
        if(txtEmailRecover.getText().toString().equals("")){
            Toast.makeText(this, "Informe o email",Toast.LENGTH_LONG).show();
        }
        else{
            recuperaConta(txtEmailRecover.getText().toString());
        }
    }

    private void recuperaConta(String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this,"Verifique seu e-mail!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Ocorreu um erro.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}