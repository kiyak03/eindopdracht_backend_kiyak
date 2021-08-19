package com.kiyak.eindopdracht_backend_kiyak.service;


import com.kiyak.eindopdracht_backend_kiyak.domain.File;
import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.payload.response.FileResponse;
import com.kiyak.eindopdracht_backend_kiyak.repository.FilesRepository;
import com.kiyak.eindopdracht_backend_kiyak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService{

    private static FilesRepository filesRepository;

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    public FileServiceImpl(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public void save(MultipartFile file, File dFile) throws IOException {
//        String currentUserName = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUsername();
//        Optional<User> optUser = userRepository.findByUsername(currentUserName);
////        file.setName(optUser.get());

        File files = new File();
        files.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        files.setContentType(file.getContentType());
        files.setData(file.getBytes());
        files.setSize(file.getSize());

        filesRepository.save(files);
    }

    public static Optional<File> getFile(long id) {
        return filesRepository.findById(id);
    }

    public List<File> getAllFiles() {
        return filesRepository.findAll();
    }

    public Optional<File> getDemoId(Long id) { return filesRepository.findById(id);}


    @Override
    public ResponseEntity uploadFile(MultipartFile file, Principal principal, String name, String email, String message) throws IOException {
        return null;
    }

    @Override
    public List<File> getAll() {
        return null;
    }

    @Override
    public File getFileById(long id) {
        return null;
    }

    @Override
    public List<File> getAllFilesForUser(Principal principal) {
        return null;
    }

    @Override
    public ResponseEntity<FileResponse> updateFile(long id, String feedback) {
        return null;
    }
}

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


