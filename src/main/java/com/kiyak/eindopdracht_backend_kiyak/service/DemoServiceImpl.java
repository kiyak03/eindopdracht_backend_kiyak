package com.kiyak.eindopdracht_backend_kiyak.service;


import com.kiyak.eindopdracht_backend_kiyak.domain.Demo;
import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.exception.DemoStorageException;
import com.kiyak.eindopdracht_backend_kiyak.exception.NotFoundException;
import com.kiyak.eindopdracht_backend_kiyak.payload.response.DemoResponse;
import com.kiyak.eindopdracht_backend_kiyak.repository.DemoRepository;
import com.kiyak.eindopdracht_backend_kiyak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.File;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private UserRepository userRepository;

    public static String uploadDir = System.getProperty("user.dir") + "/demoFiles/";

    @Override
    public ResponseEntity<DemoResponse> uploadFile(MultipartFile file, Principal principal, String name, String email, String comment) throws IOException {
        file.transferTo(new File(uploadDir + file.getOriginalFilename()));

        long currentUserId = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId();
        Optional<User> optionalUser = userRepository.findById(currentUserId);

        String currentUserName = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUsername();
        Optional<User> optionalUserName = userRepository.findByUsername(currentUserName);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("files/download/")
                .path(file.getOriginalFilename())
                .toUriString();

        Demo files = new Demo();

        files.setName(optionalUserName.get().getUsername());
        files.setDemo(StringUtils.cleanPath(file.getOriginalFilename()));
        files.setComment(comment);
        files.setUser(optionalUser.get());
        files.setDownloadUrl(fileDownloadUri);
        files.setUploadDir(uploadDir + file.getOriginalFilename());
        files.setContentType(file.getContentType());
//        files.setData(file.getBytes());
//        files.setSize(file.getSize());

        demoRepository.save(files).getId();
        return ResponseEntity.ok(new DemoResponse("File uploaded successfully!"));
    }

    @Override
    public List<Demo> getAllFiles() {
        return demoRepository.findAll();
    }

    @Override
    public Demo getFileById(long id) {
        if (demoRepository.existsById(id)) {
            return demoRepository.findById(id).orElse(null);
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Demo> getAllFilesForUser(Principal principal) {
        Long userId = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId();
        List<Demo> projects = demoRepository.findByUserId(userId);
        return projects;
    }

    @Override
    public ResponseEntity<DemoResponse> updateFile(long id, String feedback) {
        if (demoRepository.existsById(id)) {
            try {
                Demo existingDemo = demoRepository.findById(id).orElse(null);
                existingDemo.setComment(existingDemo.getComment());
                demoRepository.save(existingDemo);
            }
            catch (Exception ex) {
                throw new DemoStorageException();
            }
        }
        else {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(new DemoResponse("Comment is updated!"));
    }















}

//    @Autowired
//    public FileServiceImpl(FilesRepository filesRepository) {
//        this.filesRepository = filesRepository;
//    }
//
//    public void save(MultipartFile file, File dFile) throws IOException {
////        String currentUserName = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUsername();
////        Optional<User> optUser = userRepository.findByUsername(currentUserName);
//////        file.setName(optUser.get());
//
//        File files = new File();
//        files.setName(StringUtils.cleanPath(file.getOriginalFilename()));
//        files.setContentType(file.getContentType());
//        files.setData(file.getBytes());
//        files.setSize(file.getSize());
//
//        filesRepository.save(files);
//    }
//
//    public static Optional<File> getFile(long id) {
//        return filesRepository.findById(id);
//    }
//
//    public List<File> getAllFiles() {
//        return filesRepository.findAll();
//    }
//
//    public Optional<File> getDemoId(Long id) { return filesRepository.findById(id);}
//
//
//    @Override
//    public ResponseEntity uploadFile(MultipartFile file, Principal principal, String name, String email, String message) throws IOException {
//        return null;
//    }
//
//    @Override
//    public List<File> getAll() {
//        return null;
//    }
//
//    @Override
//    public File getFileById(long id) {
//        return null;
//    }
//
//    @Override
//    public List<File> getAllFilesForUser(Principal principal) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<FileResponse> updateFile(long id, String feedback) {
//        return null;
//    }
//}

//    public DemoFiles store(MultipartFile file) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        DemoFiles DemoFiles = new DemoFiles(fileName, file.getContentType(), file.getBytes());
//
//        return demoFilesRepository.save(DemoFiles);
//    }
//
//    public DemoFiles getFile(String id) {
//        return demoFilesRepository.findById(id).get();
//    }
//
//    public Stream<DemoFiles> getAllFiles() {
//        return demoFilesRepository.findAll().stream();
//    }


