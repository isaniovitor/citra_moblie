package com.example.citra_moblie.dao;

import android.content.Context;
import android.content.Intent;

import com.example.citra_moblie.LoginActivity;
import com.example.citra_moblie.RegisterUserActivity;
import com.example.citra_moblie.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicBoolean;

public class UserDAO implements IUserDAO{
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

//    @Override
//    public void createUserMock() {
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lice);
//        user = new User(bitmap, "alice", "alice@com","12/02/2002", "02193243234", "alice");
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
