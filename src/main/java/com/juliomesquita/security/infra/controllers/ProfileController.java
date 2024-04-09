package com.juliomesquita.security.infra.controllers;

import com.juliomesquita.security.infra.dtos.ProfileDTO;
import com.juliomesquita.security.infra.entities.Profile;
import com.juliomesquita.security.infra.persistence.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileRepository profileRepository;

    @PostMapping
    public ResponseEntity<Profile> saveProfile(
            @RequestBody ProfileDTO request
    ){
        Profile profile = Profile
                .builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .build();
        return ResponseEntity.ok(this.profileRepository.save(profile));
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getListProfile(

    ){
        return ResponseEntity.ok(this.profileRepository.findAll());
    }
}
