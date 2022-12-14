package com.example.citra_moblie.model;

public class User {
    private String id;
    private String image;
    private String name;
    private String email;
    private String birthday;
    private String cpf;
    private String password;

    public User() {
    }

    public User(String id, String image, String name, String email, String birthday, String cpf, String password) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
