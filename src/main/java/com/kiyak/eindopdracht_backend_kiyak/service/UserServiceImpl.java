package com.kiyak.eindopdracht_backend_kiyak.service;

import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.exception.DemoStorageException;
import com.kiyak.eindopdracht_backend_kiyak.exception.NotFoundException;
import com.kiyak.eindopdracht_backend_kiyak.payload.request.UpdateUserRequest;
import com.kiyak.eindopdracht_backend_kiyak.payload.response.MessageResponse;
import com.kiyak.eindopdracht_backend_kiyak.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${novi.sec.jwtSecret}")
    private String jwtSecret;

    private static final String PREFIX = "Bearer ";

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).orElse(null);
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteUser(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    public long saveUser(User user) {
        User newUser = userRepository.save(user);
        return newUser.getUserId();
    }

    @Override
    public void updateUser(long id, User user) {
        if (userRepository.existsById(id)) {
            try {
                User existingUser = userRepository.findById(id).orElse(null);
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                userRepository.save(existingUser);
            }
            catch (Exception ex) {
                throw new DemoStorageException();
            }
        }
        else {
            throw new NotFoundException();
        }
    }

    @Override
    public ResponseEntity<?> findUserByToken(String token) {
        String username = getUsernameFromToken(token);

        if(userExists(username)) {
            return ResponseEntity.ok(findUserByUsername(username));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
    }

    private String getUsernameFromToken(String token) {
        String tokenWithoutBearer = removePrefix(token);

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                .parseClaimsJws(tokenWithoutBearer).getBody();

        return claims.getSubject();
    }

    private String removePrefix(String token) {
        return token.replace(PREFIX, "");
    }

    private boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

}
