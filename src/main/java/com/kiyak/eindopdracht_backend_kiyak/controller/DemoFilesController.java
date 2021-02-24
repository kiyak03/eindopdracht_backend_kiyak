package com.kiyak.eindopdracht_backend_kiyak.controller;


import com.kiyak.eindopdracht_backend_kiyak.domain.DemoFiles;
import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.payload.response.FileResponse;
import com.kiyak.eindopdracht_backend_kiyak.repository.UserRepository;
import com.kiyak.eindopdracht_backend_kiyak.service.StorageServiceImpl;
import com.kiyak.eindopdracht_backend_kiyak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/files")
public class DemoFilesController {

    private final StorageServiceImpl storageServiceImpl;

    @Autowired
    UserRepository userRepository;


    @Autowired
    public DemoFilesController(StorageServiceImpl storageServiceImpl) {
        this.storageServiceImpl = storageServiceImpl;
    }

//    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
//    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Authentication authentication) {


//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
//        if (userOptional.isPresent()){
//            file.setName(userOptional.get());
//        }

//        public ResponseEntity<JwtResponse> authenticateUserByToken(@Valid TokenRequest tokenRequest) {
//
//            // Username extract
//            String username = jwtUtils.getUserNameFromJwtToken(tokenRequest.getTokenString());
//            // Get user instantie
//            User tokenUser =  userRepository.findByUsername(username);
//            // Make token voor authenticateUser
//            String password = tokenUser.getPassword();
//            LoginRequest loginRequest = null;
//            loginRequest.setPassword(password);
//            loginRequest.setUsername(username);
//            //userinfo via user instantie
//            return authenticateUser(loginRequest);
//        }
        try {
            storageServiceImpl.save(file);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping
    public List<FileResponse> list() {
        return storageServiceImpl.getAllFiles()
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private FileResponse mapToFileResponse(DemoFiles demoFiles) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(demoFiles.getId())
                .toUriString();
        FileResponse fileResponse = new FileResponse("","","",0,"","");
        fileResponse.setId(demoFiles.getId());
        fileResponse.setName(demoFiles.getName());
        fileResponse.setContenttype(demoFiles.getContentType());
        fileResponse.setSize(demoFiles.getSize());
        fileResponse.setUrl(downloadURL);


        return fileResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<DemoFiles> demoFilesOptional = StorageServiceImpl.getFile(id);

        if (!demoFilesOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        DemoFiles demoFiles = demoFilesOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + demoFiles.getName() + "\"")
                .contentType(MediaType.valueOf(demoFiles.getContentType()))
                .body(demoFiles.getData());
    }

}
//    @Autowired
//    FileStorageService storageService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
//        String message = "";
//        try {
//            storageService.store(file);
//
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
//        }
//    }
//
//    @GetMapping("/files")
//    public ResponseEntity<List<FileResponse>> getListFiles() {
//        List<FileResponse> files = storageService.getAllFiles().map(demoFile -> {
//            String fileDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/files/")
//                    .path(demoFile.getId())
//                    .toUriString();
//
//            return new FileResponse(
//                    demoFile.getName(),
//                    fileDownloadUri,
//                    demoFile.getType(),
//                    demoFile.getData().length);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(files);
//    }
//
//    @GetMapping("/files/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        DemoFiles demoFiles = storageService.getFile(id);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + demoFiles.getName() + "\"")
//                .body(demoFiles.getData());
//    }
//
