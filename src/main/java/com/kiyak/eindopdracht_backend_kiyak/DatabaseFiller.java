package com.kiyak.eindopdracht_backend_kiyak;


import com.kiyak.eindopdracht_backend_kiyak.payload.request.SignupRequest;
import com.kiyak.eindopdracht_backend_kiyak.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseFiller implements CommandLineRunner {

    private final AuthorizationService authorizationService;


    @Autowired
    public DatabaseFiller(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void run(String... args) {

        Set<String> rollen = new HashSet<>();
        rollen.add("admin");

        SignupRequest admin = new SignupRequest();
        admin.setUsername("diablo");
        admin.setEmail("diablo@hexagon.nl");
        admin.setPassword("diablo123");
        admin.setRole(rollen);
        authorizationService.registerUser(admin);

        SignupRequest user = new SignupRequest();
        user.setUsername("user");
        user.setEmail("user@user.nl");
        user.setPassword("user123");
        rollen.remove("admin");
        rollen.add("user");
        user.setRole(rollen);
        authorizationService.registerUser(user);


//        SignupRequest superuser = new SignupRequest();
//        superuser.setUsername("superuser");
//        superuser.setEmail("super@user.nl");
//        superuser.setPassword("123456");
//        rollen.add("admin");
//        superuser.setRole(rollen);
//        authorizationService.registerUser(superuser);

    }
}
