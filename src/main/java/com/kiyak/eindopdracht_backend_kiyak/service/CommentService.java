package com.kiyak.eindopdracht_backend_kiyak.service;

import com.kiyak.eindopdracht_backend_kiyak.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    Comment getCommentById(long id);

    void deleteComment(long id);

    long saveComment(String feedback);

    void updateComment(long id, String comment);
}
