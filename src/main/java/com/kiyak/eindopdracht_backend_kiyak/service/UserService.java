package com.kiyak.eindopdracht_backend_kiyak.service;


import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.payload.request.UpdateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


public interface UserService {
    List<User> getAllUsers();

    User getUserById(long id);

    void deleteUser(long id);

    long saveUser(User user);

    void updateUser(long id, User user);

    ResponseEntity<?> findUserByToken(String token);
}
