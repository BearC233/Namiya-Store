package com.namiya.store.project.service;

import com.namiya.store.project.model.Capsule;
import com.namiya.store.project.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface CapsuleService {
    void checkExpiredCapsules();
    Result<Capsule> create(Capsule capsule);
    int del(String capsuleId);
    Result<Capsule> get(String capsuleId);
}
