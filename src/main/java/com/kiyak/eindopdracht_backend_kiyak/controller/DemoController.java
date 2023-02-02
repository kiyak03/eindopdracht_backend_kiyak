package com.kiyak.eindopdracht_backend_kiyak.controller;


import com.kiyak.eindopdracht_backend_kiyak.domain.Demo;
import com.kiyak.eindopdracht_backend_kiyak.payload.response.DemoResponse;
import com.kiyak.eindopdracht_backend_kiyak.service.DemoService;
import com.kiyak.eindopdracht_backend_kiyak.service.DemoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController("/files")
@RequestMapping(value = "/files")
public class DemoController {

    @Autowired
    DemoService demoService;

//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserService userService;

    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DemoResponse> upload(@RequestParam("file") MultipartFile file, Principal principal,
                                               @RequestParam("message") String message,
                                               @RequestParam("name") String name,
                                               @RequestParam("email") String email) throws IllegalStateException, IOException {

        return demoService.uploadFile(file, principal, email, name, message);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAllFiles() {
        List<Demo> demos = demoService.getAllFiles();
        return new ResponseEntity<>(demos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUploadById(@PathVariable("id") long id) {
        Demo demo = demoService.getFileById(id);
        return new ResponseEntity<>(demo, HttpStatus.OK);
    }

        @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("download/{fileName}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
        System.out.println("ik ben hier");
        Path path = Paths.get( System.getProperty("user.dir") + "/demoFiles/" + fileName);
        UrlResource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/files")
    public ResponseEntity<Object> getAllFilesForUser(Principal principal) {
        List<Demo> projects = demoService.getAllFilesForUser(principal);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PutMapping(value = "/files/{id}")
    public ResponseEntity<DemoResponse> updateUpload(@PathVariable("id") long id, @RequestParam("comment") String comment) {
        return demoService.updateFile(id, comment);
    }


















}
//
//        try {
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
//            if (userOptional.isPresent()){
//                dFile.setUser(userOptional.get());
//            }
//           fileServiceImpl.save(file, dFile);
//
//            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
////                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
//        } catch (Exception e) {
//            return new ResponseEntity<>("Could not upload the file!", HttpStatus.INTERNAL_SERVER_ERROR);
////            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
//        }
//    }

//
//    @GetMapping
//    public List<FileResponse> list() {
//        return fileServiceImpl.getAllFiles()
//                .stream()
//                .map(this::mapToFileResponse)
//                .collect(Collectors.toList());
//    }
//
//    private FileResponse mapToFileResponse(File file) {
//        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/files/")
//                .path(file.getName())
//                .toUriString();
//        FileResponse fileResponse = new FileResponse("","","",0,0,"");
//        fileResponse.setId(file.getId());
//        fileResponse.setName(file.getName());
//        fileResponse.setContenttype(file.getContentType());
//        fileResponse.setSize(file.getSize());
//        fileResponse.setUrl(downloadURL);
//
//
//        return fileResponse;
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable("id") long id) {
//        Optional<File> demoFilesOptional = FileServiceImpl.getFile(id);
//
//
//        if (!demoFilesOptional.isPresent()) {
//            return ResponseEntity.notFound()
//                    .build();
//        }
//
//        File file = demoFilesOptional.get();
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
//                .contentType(MediaType.valueOf(file.getContentType()))
//                .body(file.getData());
//    }


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
