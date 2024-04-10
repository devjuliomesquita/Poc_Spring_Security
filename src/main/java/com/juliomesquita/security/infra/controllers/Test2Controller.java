package com.juliomesquita.security.infra.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test2")
@RequiredArgsConstructor
public class Test2Controller {

    @GetMapping()
    public String get(){
        return "Get::test2 controller";
    }

    @PostMapping()
    public String post(){
        return "Post::test2 controller";
    }
    @PutMapping()
    public String put(){
        return "Put::test2 controller";
    }

    @DeleteMapping()
    public String delete(){
        return "Delete::test2 controller";
    }
}
