package com.kiyak.eindopdracht_backend_kiyak.service;

import com.kiyak.eindopdracht_backend_kiyak.domain.Demo;
import com.kiyak.eindopdracht_backend_kiyak.payload.response.DemoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface DemoService {
    ResponseEntity uploadFile(MultipartFile file, Principal principal, String name, String email, String message) throws IOException;
    List<Demo> getAllFiles();
    Demo getFileById(long id);
    List<Demo> getAllFilesForUser(Principal principal);
    ResponseEntity<DemoResponse> updateFile(long id, String feedback);

//    Long getDemoIdByUsername(String );
}
