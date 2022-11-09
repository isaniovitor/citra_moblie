package com.example.citra_moblie.model;

import android.graphics.Bitmap;

public class User {
    private Bitmap image;
    private String name;
    private String email;
    private String birthday;
    private String cpf;
    private String password;

    public User() {
    }

    public User(Bitmap image, String name, String email, String birthday, String cpf, String password) {
        this.image = image;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.cpf = cpf;
        this.password = password;
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