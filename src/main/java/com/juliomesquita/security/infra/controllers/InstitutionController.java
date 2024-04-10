package com.juliomesquita.security.infra.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/institution")
@RequiredArgsConstructor
public class InstitutionController {

    @GetMapping()
    @PreAuthorize("hasAuthority('BACKOFFICE_INSTITUTION_READ')")
    public String get(){
        return "Get::institution controller";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('BACKOFFICE_INSTITUTION_WRITE')")
    public String post(){
        return "Post::institution controller";
    }
    @PutMapping()
    @PreAuthorize("hasAuthority('BACKOFFICE_INSTITUTION_UPDATE')")
    public String put(){
        return "Put::institution controller";
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('BACKOFFICE_INSTITUTION_DELETE')")
    public String delete(){
        return "Delete::institution controller";
    }
}
