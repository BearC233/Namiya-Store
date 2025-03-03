package com.namiya.store.project.service.impl;

import com.namiya.store.project.service.FileStorageService;

import com.namiya.store.project.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${file.upload.dir}")
    private String uploadDir;



    @Override
    public String storeFile(MultipartFile file) throws IOException {
        String fileName= UUIDGenerator.Generator(file.getOriginalFilename())+"_image";
        Path targetPath= Paths.get(uploadDir).resolve(fileName);
        Files.createDirectories(targetPath.getParent());
        Files.copy(file.getInputStream(),targetPath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/images/"+fileName;
    }
}
