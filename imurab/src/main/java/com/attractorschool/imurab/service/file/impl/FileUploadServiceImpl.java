package com.attractorschool.imurab.service.file.impl;

import com.attractorschool.imurab.service.file.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public void uploadFile(MultipartFile file, String directory) {
        if (!file.isEmpty()) {
            try {
                String uploadDirPath = Paths.get(System.getProperty("user.dir"), "projectImages", directory).toString();
                File uploadDir = new File(uploadDirPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                file.transferTo(new File(uploadDir, requireNonNull(file.getOriginalFilename())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
