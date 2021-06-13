package com.kiyak.eindopdracht_backend_kiyak.repository;

import com.kiyak.eindopdracht_backend_kiyak.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Talks with Database
@Repository
public interface FilesRepository extends JpaRepository<File, String> {

    Optional<File> findById(Long id);
}
