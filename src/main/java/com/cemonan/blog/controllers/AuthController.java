package com.cemonan.blog.controllers;

import com.cemonan.blog.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class AuthController {

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {

        return new ResponseEntity("", HttpStatus.OK);
    }
}
