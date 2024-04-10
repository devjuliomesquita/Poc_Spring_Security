package com.juliomesquita.security.infra.entities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProfileFactory {
    public List<Profile> createProfiles() {
        List<String> profilesNames = new ArrayList<>(
                List.of("user", "backoffice", "institution")
        );
        List<Profile> profiles = profilesNames
                .stream()
                .map(this::createProfile)
                .collect(Collectors.toList());

        return profiles;
    }

    private Profile createProfile(String name) {
        return Profile
                .builder()
                .id(UUID.randomUUID())
                .name(name)
                .description("Perfil do " + name)
                .permissions(Permission.getPermissions(name))
                .build();
    }
}
