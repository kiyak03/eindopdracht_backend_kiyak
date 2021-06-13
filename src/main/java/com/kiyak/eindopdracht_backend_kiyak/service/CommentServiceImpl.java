package com.kiyak.eindopdracht_backend_kiyak.service;


import com.kiyak.eindopdracht_backend_kiyak.domain.Comment;
import com.kiyak.eindopdracht_backend_kiyak.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }
}
