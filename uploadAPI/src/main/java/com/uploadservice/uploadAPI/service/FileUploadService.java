package com.uploadservice.uploadAPI.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.uploadservice.uploadAPI.configuration.FileUploadConfiguration;



@Service
public class FileUploadService {


    private final Path fileStorageLocation; 

    public FileUploadService(FileUploadConfiguration fileUploadConfiguration){ 
        this.fileStorageLocation = Paths.get(fileUploadConfiguration.getUploadDir())
        .toAbsolutePath().normalize();
    }

    public ResponseEntity<String> uploadFile(MultipartFile file){ 
        String fileName =StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Path targetLocation = fileStorageLocation.resolve(fileName);
                file.transferTo(targetLocation);
                return ResponseEntity.ok().body("UPLOAD COMPLETED");
            } catch (IOException exception) {
                exception.printStackTrace();
                return ResponseEntity.badRequest().body("FILE UPLOAD FAILED.");
            }
    }


}
