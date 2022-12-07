package com.example.citra_moblie.dao;

import android.app.Activity;
import android.widget.ImageView;

import com.example.citra_moblie.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.ValueEventListener;

public interface IUserDAO {
    void authUser(String email, String password, OnCompleteListener completeListener, ValueEventListener eventListener, boolean isLogged);
    void saveUser(OnCompleteListener completeListener, User newUser);
    void saveImageUser(Activity activity, ImageView imageView, ValueEventListener eventListener, User editedUser);
    void editUser(ValueEventListener eventListener, User editedUser);
    User getUser();
    void setUser(User user);
}
