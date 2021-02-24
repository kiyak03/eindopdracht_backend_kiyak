package com.kiyak.eindopdracht_backend_kiyak.repository;

import com.kiyak.eindopdracht_backend_kiyak.domain.DemoFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Talks with Database
@Repository
public interface DemoFilesRepository extends JpaRepository<DemoFiles, String> {



}
