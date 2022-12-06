package com.example.citra_moblie.dao;

import com.example.citra_moblie.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.ValueEventListener;

public interface IUserDAO {
    void authUser(String email, String password, OnCompleteListener completeListener, ValueEventListener eventListener, boolean isLogged);
    void saveUser(OnCompleteListener completeListener, User newUser);
    User getUser();
    void setUser(User user);
}
