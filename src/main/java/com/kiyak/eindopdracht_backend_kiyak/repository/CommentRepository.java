package com.kiyak.eindopdracht_backend_kiyak.repository;

import com.kiyak.eindopdracht_backend_kiyak.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
