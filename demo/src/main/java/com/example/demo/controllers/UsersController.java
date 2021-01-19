package com.example.demo.controllers;

import com.example.demo.response.UserRest;
import org.apache.catalina.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    @GetMapping("/status/check")
    public String status(){
        return "Working..";
    }

    @PreAuthorize("#id==#jwt.subject")
//    @PreAuthorize("hasAuthority('ROLE_Developer') or #id==#jwt.subject")
//    @PostAuthorize()
//    @Secured("ROLE_Developer")
    @DeleteMapping(path="/{id}")
    public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
        return "Delete user with id : "+id +"and JWT subject"+jwt.getSubject();
    }

    @PostAuthorize("returnObject.userId==#jwt.subject")
//    @PreAuthorize("hasAuthority('ROLE_Developer') or #id==#jwt.subject")
//    @PostAuthorize()
//    @Secured("ROLE_Developer")
    @GetMapping(path="/{id}")
    public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
        return new UserRest("a", "b", "1b2cee1f-a11c-4f2e-8ec1-4c80430dc50b");
    }
}
