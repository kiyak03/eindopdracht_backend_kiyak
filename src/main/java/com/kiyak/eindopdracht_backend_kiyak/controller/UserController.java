package com.kiyak.eindopdracht_backend_kiyak.controller;


import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.payload.request.UpdateUserRequest;
import com.kiyak.eindopdracht_backend_kiyak.service.UserService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@RequestHeader Map<String, String> headers,
                                        @RequestBody UpdateUserRequest updateRequest) {
        return userService.updateUserById(headers.get("authorization"),updateRequest);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    public ResponseEntity<?> findUserByToken(@RequestHeader Map<String, String> headers) {
        return userService.findUserByToken(headers.get("authorization"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") long userId) throws IOException {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Object> deleteUser(@PathVariable("id") long id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PostMapping(value = "")
//    public ResponseEntity<Object> saveUser(@RequestBody User user) {
//        long newId = userService.saveUser(user);
//        return new ResponseEntity<>(newId, HttpStatus.CREATED);
//    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Object> updateUser(@PathVariable("id") int id, @RequestBody User user) {
//        userService.updateUser(id, user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
