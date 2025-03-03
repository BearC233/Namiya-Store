package com.namiya.store.project.service;

import com.namiya.store.project.model.Capsule;
import com.namiya.store.project.model.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CapsuleService {
    void checkExpiredCapsules();
    Result<Capsule> create(Capsule capsule);
    Result<Capsule> create(Capsule capsule, MultipartFile image);
    int del(String capsuleId);
    Result<Capsule> get(String capsuleId);
}
