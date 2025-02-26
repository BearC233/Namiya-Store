package com.namiya.store.project.service;

import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Result<User> signIn(String userId);
}
