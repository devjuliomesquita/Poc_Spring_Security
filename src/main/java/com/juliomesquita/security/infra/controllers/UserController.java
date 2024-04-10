package com.juliomesquita.security.infra.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping()
    @PreAuthorize("hasAuthority('BACKOFFICE_USER_READ')")
    public ResponseEntity<String> get(){
        return new ResponseEntity<>("Get::user controller", HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('BACKOFFICE_USER_WRITE')")
    public String post(){
        return "Post::user controller";
    }
    @PutMapping()
    @PreAuthorize("hasAuthority('BACKOFFICE_USER_UPDATE')")
    public String put(){
        return "Put::user controller";
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('BACKOFFICE_USER_DELETE')")
    public String delete(){
        return "Delete::user controller";
    }
}
