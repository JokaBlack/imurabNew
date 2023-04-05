package com.attractorschool.imurab.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    void uploadFile(MultipartFile file, String directory);
}
