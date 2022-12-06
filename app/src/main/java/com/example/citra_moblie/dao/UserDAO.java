package com.example.citra_moblie.dao;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.citra_moblie.HomeActivity;
import com.example.citra_moblie.LoginActivity;
import com.example.citra_moblie.RegisterUserActivity;
import com.example.citra_moblie.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class UserDAO implements IUserDAO{
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static Context context;
    private static UserDAO userDAO = null;
    private User user;

    private UserDAO(Context context) {
        UserDAO.context = context;
    }

    public static IUserDAO getInstance(Context context) {
        if (userDAO == null) {
            userDAO = new UserDAO(context);
        }
        return userDAO;
    }

    public void authUser(String email, String password, OnCompleteListener completeListener, ValueEventListener eventListener, boolean isLogged) {
        if (!isLogged) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(completeListener);
        }else{
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();
            userReference.child("usuarios").child(auth.getCurrentUser().getUid()).addValueEventListener(eventListener);
        }
    }

    public void saveUser(OnCompleteListener completeListener, User newUser) {
        auth.createUserWithEmailAndPassword(newUser.getEmail(),
                newUser.getPassword()).addOnCompleteListener(completeListener);
    }

    //edit user

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
