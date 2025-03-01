package com.namiya.store.project.service;

import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    Result<User> signIn(String userId);
    Result<User> register(User user);
    Result<User> login(User user);
    Result<User> findById(String userId);
    Result<User> findByName(String userName);
    Result<List<User>> getByName(String userName);
    int del(String userId);
    Result<List<User>> findAll();
    Result<Integer> update(User user);
}
