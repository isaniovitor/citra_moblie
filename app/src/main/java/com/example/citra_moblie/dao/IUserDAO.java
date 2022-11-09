package com.example.citra_moblie.dao;

import com.example.citra_moblie.model.User;

public interface IUserDAO {
    void createUserMock();
    User getUser();
    void setUser(User user);
}
