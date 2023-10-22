package org.example.storage;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void init();
    Resource loadAsResource(String filename);
    String save(String base64);

    void  removeFile(String removeFile);
    Path load(String filename);
    String saveMultipartFile(MultipartFile file);
}
