package com.kiyak.eindopdracht_backend_kiyak.service;

import com.kiyak.eindopdracht_backend_kiyak.domain.File;

import java.util.List;
import java.util.Optional;

public interface FileService {


    List<File> getAllFiles();

    Optional<File> getDemoId(Long id);

}
