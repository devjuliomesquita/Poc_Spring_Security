package com.juliomesquita.security.infra.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test1")
@RequiredArgsConstructor
public class Test1Controller {

    @GetMapping()
    @PreAuthorize("hasAuthority('TEST1_READ')")
    public ResponseEntity<String> get(){
        return new ResponseEntity<>("Get::test1 controller", HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('TEST1_WRITE')")
    public String post(){
        return "Post::test1 controller";
    }
    @PutMapping()
    public String put(){
        return "Put::test1 controller";
    }

    @DeleteMapping()
    public String delete(){
        return "Delete::test1 controller";
    }
}
