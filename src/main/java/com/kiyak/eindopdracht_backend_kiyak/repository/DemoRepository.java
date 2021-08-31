package com.kiyak.eindopdracht_backend_kiyak.repository;

import com.kiyak.eindopdracht_backend_kiyak.domain.Demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Talks with Database
public interface DemoRepository extends JpaRepository<Demo, Long> {

    default List<Demo> findByUserId(Long userId) {
        return null;
    }
}
