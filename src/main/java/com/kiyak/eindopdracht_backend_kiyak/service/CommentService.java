package com.kiyak.eindopdracht_backend_kiyak.service;

import com.kiyak.eindopdracht_backend_kiyak.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    Comment getCommentById(long id);

    void deleteComment(long id);

    long saveComment(Comment comment);

    void updateComment(long id, Comment feedbackText);
}
