package com.kiyak.eindopdracht_backend_kiyak.service;


import com.kiyak.eindopdracht_backend_kiyak.domain.DemoFiles;
import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.repository.DemoFilesRepository;
import com.kiyak.eindopdracht_backend_kiyak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class StorageServiceImpl {

    private static DemoFilesRepository demoFilesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public StorageServiceImpl(DemoFilesRepository demoFilesRepository) {
        this.demoFilesRepository = demoFilesRepository;
    }

    public void save(MultipartFile file) throws IOException {
//        String currentUserName = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUsername();
//        Optional<User> optUser = userRepository.findByUsername(currentUserName);
////        file.setName(optUser.get());

        DemoFiles demoFiles = new DemoFiles();
        demoFiles.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        demoFiles.setContentType(file.getContentType());
        demoFiles.setData(file.getBytes());
        demoFiles.setSize(file.getSize());

        demoFilesRepository.save(demoFiles);
    }

    public static Optional<DemoFiles> getFile(String id) {
        return demoFilesRepository.findById(id);
    }

    public List<DemoFiles> getAllFiles() {
        return demoFilesRepository.findAll();
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


