package com.alkemy.challenge.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    
    public final String DEFAULT_IMAGE = "src/main/resources/static/uploads/default-image.png";
    private final String DIRECTORY = "src/main/resources/static/uploads";

    public String imageToString(MultipartFile image){
        try{
            
            String fileName = image.getOriginalFilename();

            Path imagePath = Paths.get(DIRECTORY, fileName).toAbsolutePath();
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
            
        }catch(IOException e){
            throw new IllegalArgumentException("Error loading image:" + e.getCause() + e.getMessage() +e.getLocalizedMessage());
        }
    }
}
