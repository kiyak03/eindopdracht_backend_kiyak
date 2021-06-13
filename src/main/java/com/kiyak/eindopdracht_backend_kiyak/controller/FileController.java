package com.kiyak.eindopdracht_backend_kiyak.controller;


import com.kiyak.eindopdracht_backend_kiyak.domain.File;
import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.payload.response.FileResponse;
import com.kiyak.eindopdracht_backend_kiyak.repository.UserRepository;
import com.kiyak.eindopdracht_backend_kiyak.service.FileServiceImpl;
import com.kiyak.eindopdracht_backend_kiyak.service.UserDetailsImpl;
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


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileServiceImpl fileServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    public FileController(FileServiceImpl fileServiceImpl) {
        this.fileServiceImpl = fileServiceImpl;
    }

//    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
//    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    @PostMapping
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Authentication authentication, File dFile) throws IOException{




//        @RequestParam("userId") long userId, File dfile) throws IOException
//        @AuthenticationPrincipal Authentication authentication)
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
//        if (userOptional.isPresent()){
//            dFile.setName(userOptional.get());
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
//            User user = userService.getUserById(userId);
//            dFile.setUser(user);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
            if (userOptional.isPresent()){
                dFile.setUser(userOptional.get());
            }
           fileServiceImpl.save(file, dFile);

            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
//                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return new ResponseEntity<>("Could not upload the file!", HttpStatus.INTERNAL_SERVER_ERROR);
//            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @GetMapping
    public List<FileResponse> list() {
        return fileServiceImpl.getAllFiles()
                .stream()
                .map(this::mapToFileResponse)
                .collect(Collectors.toList());
    }

    private FileResponse mapToFileResponse(File file) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(file.getName())
                .toUriString();
        FileResponse fileResponse = new FileResponse("","","",0,0,"");
        fileResponse.setId(file.getId());
        fileResponse.setName(file.getName());
        fileResponse.setContenttype(file.getContentType());
        fileResponse.setSize(file.getSize());
        fileResponse.setUrl(downloadURL);


        return fileResponse;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") long id) {
        Optional<File> demoFilesOptional = FileServiceImpl.getFile(id);


        if (!demoFilesOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        File file = demoFilesOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.valueOf(file.getContentType()))
                .body(file.getData());
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
