package com.namiya.store.project.service;

import com.namiya.store.project.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    boolean sendNotification(String email,String message);
}
