package com.juliomesquita.security.infra.controllers;

import com.juliomesquita.security.infra.dtos.ProfileDTO;
import com.juliomesquita.security.infra.entities.enums.Permission;
import com.juliomesquita.security.infra.entities.Profile;
import com.juliomesquita.security.infra.persistence.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileRepository profileRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('BACKOFFICE_WRITE')")
    public ResponseEntity<Profile> saveProfile(
            @RequestBody ProfileDTO request
    ){
        Set<Permission> permissions = new HashSet<>();
        permissions.add(request.permission());
        Profile profile = Profile
                .builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .permissions(permissions)
                .build();
        return ResponseEntity.ok(this.profileRepository.save(profile));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('BACKOFFICE_READ')")
    public ResponseEntity<List<Profile>> getListProfile(

    ){
        return ResponseEntity.ok(this.profileRepository.findAll());
    }
}
