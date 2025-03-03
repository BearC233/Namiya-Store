package com.namiya.store.project.service;

import io.lettuce.core.dynamic.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileStorageService {
    public String storeFile(MultipartFile file) throws IOException;
}
