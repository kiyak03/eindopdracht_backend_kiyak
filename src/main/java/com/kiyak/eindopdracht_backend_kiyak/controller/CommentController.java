package com.kiyak.eindopdracht_backend_kiyak.controller;

import com.kiyak.eindopdracht_backend_kiyak.domain.Comment;
import com.kiyak.eindopdracht_backend_kiyak.service.CommentService;
import com.kiyak.eindopdracht_backend_kiyak.service.DemoServiceImpl;
import com.kiyak.eindopdracht_backend_kiyak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable("id") long id) {
        Comment comment = commentService.getCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("id") long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<Object> saveComment(@RequestParam("feedback") String feedback) {
        long newId = commentService.saveComment(feedback);
        return new ResponseEntity<>(newId, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping(value = "")
//    public ResponseEntity<Object> saveComment(@RequestBody Comment comment) {
//        long newId = commentService.saveComment(comment);
//        return new ResponseEntity<>(newId, HttpStatus.CREATED);
//    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable("id") long id, @RequestParam("feedback") String comment)  {
        commentService.updateComment(id, comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Object> updateComment(@PathVariable("id") long id, @RequestBody Comment comment) {
//        commentService.updateComment(id, comment);
//        return new ResponseEntity<>(HttpStatus.OK);



}
