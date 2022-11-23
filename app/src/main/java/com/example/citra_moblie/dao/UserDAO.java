package com.example.citra_moblie.dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.example.citra_moblie.R;
import com.example.citra_moblie.model.User;

public class UserDAO implements IUserDAO{
    private static Context context;
    private static UserDAO userDAO = null;
    private User user;

    private UserDAO(Context context) {
        UserDAO.context = context;
        createUserMock();
    }

    public static IUserDAO getInstance(Context context) {
        if (userDAO == null) {
            userDAO = new UserDAO(context);
        }
        return userDAO;
    }

    @Override
    public void createUserMock() {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lice);
        user = new User("1", bitmap, "alice", "alice@com","12/02/2002", "02193243234", "alice");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
