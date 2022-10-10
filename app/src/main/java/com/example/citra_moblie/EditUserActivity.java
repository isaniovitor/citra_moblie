package com.example.citra_moblie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_user);
    }
}