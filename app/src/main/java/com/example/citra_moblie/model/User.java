package com.example.citra_moblie.model;

import android.graphics.Bitmap;

import com.example.citra_moblie.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String id;
    private Bitmap image;
    private String name;
    private String email;
    private String birthday;
    private String cpf;
    private String password;

    public User() {
    }

    public void salvar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(this.getId());
        reference.setValue(this);
    }

    public User(String id, Bitmap image, String name, String email, String birthday, String cpf, String password) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.cpf = cpf;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
