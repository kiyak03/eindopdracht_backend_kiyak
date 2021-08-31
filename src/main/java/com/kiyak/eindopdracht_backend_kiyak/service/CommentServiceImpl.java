package com.kiyak.eindopdracht_backend_kiyak.service;


import com.kiyak.eindopdracht_backend_kiyak.domain.Comment;
import com.kiyak.eindopdracht_backend_kiyak.exception.DemoStorageException;
import com.kiyak.eindopdracht_backend_kiyak.exception.NotFoundException;
import com.kiyak.eindopdracht_backend_kiyak.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;


    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(long id) {
        if (commentRepository.existsById(id)) {
            return commentRepository.findById(id).orElse(null);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteComment(long id) {
            if(commentRepository.existsById(id)){ 
                commentRepository.deleteById(id);
            }
            else {
                throw new NotFoundException();
            }
        }

    @Override
    public long saveComment(Comment comment) {
            Comment newComment = commentRepository.save(comment);
            return newComment.getId();
    }

    @Override
    public void updateComment(long id, Comment comment) {
            if (commentRepository.existsById(id)) {
                try {
                    Comment existingComment = commentRepository.findById(id).orElse(null);
                    existingComment.setComment(comment.getComment());
                    commentRepository.save(existingComment);
                }
                catch (Exception ex) {
                    throw new DemoStorageException();
                }
            }
            else {
                throw new NotFoundException();
            }
        }
}
