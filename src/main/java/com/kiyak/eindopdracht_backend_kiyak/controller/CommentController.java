package com.kiyak.eindopdracht_backend_kiyak.controller;

import com.kiyak.eindopdracht_backend_kiyak.domain.Comment;
import com.kiyak.eindopdracht_backend_kiyak.domain.File;
import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.service.CommentService;
import com.kiyak.eindopdracht_backend_kiyak.service.FileServiceImpl;
import com.kiyak.eindopdracht_backend_kiyak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final FileServiceImpl fileServiceImpl;

    @Autowired

    public CommentController(CommentService commentService, UserService userService, FileServiceImpl fileServiceImpl) {
        this.commentService = commentService;
        this.userService = userService;
        this.fileServiceImpl = fileServiceImpl;
    }


//    @GetMapping(value = "/{id}")
//    public String commentDemoId(@PathVariable Long id,
//                                    Principal principal,
//                                    Model model) {
//
//        Optional<File> demoFiles = fileServiceImpl.getDemoId(id);
//
//        if (demoFiles.isPresent()) {
//            Optional<User> user = userService.findByUsername(principal.getName());
//
//            if (user.isPresent()) {
//                Comment comment = new Comment();
//                comment.setUser(user.get());
//                comment.setDemoFiles(demoFiles.get());
//
//                model.addAttribute("comment", comment);
//
//                return "/commentForm";
//
//            } else {
//                return "/error";
//            }
//
//        } else {
//            return "/error";
//        }
//    }

//    @PostMapping
//    public String Newcomment(@Valid Comment comment,
//                                BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return "/commentForm";
//
//        } else {
//            commentService.save(comment);
//            return "redirect:/" + comment.getDemoFiles().getId();
//        }
//    }

    @PostMapping
    public ResponseEntity<Object> saveComment(@RequestBody Comment comment) {
        Comment newId = commentService.save(comment);
        return new ResponseEntity<>(newId, HttpStatus.CREATED);
    }






}
