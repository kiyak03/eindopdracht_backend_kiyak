package com.kiyak.eindopdracht_backend_kiyak.service;

import com.kiyak.eindopdracht_backend_kiyak.domain.File;
import com.kiyak.eindopdracht_backend_kiyak.payload.response.FileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface FileService {
    ResponseEntity uploadFile(MultipartFile file, Principal principal, String name, String email, String message) throws IOException;
    List<File> getAllFiles();
    File getFileById(long id);
    List<File> getAllFilesForUser(Principal principal);
    ResponseEntity<FileResponse> updateFile(long id, String feedback);
}
