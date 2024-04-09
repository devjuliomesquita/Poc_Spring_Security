package com.juliomesquita.security.infra.config.security.authentication;

import com.juliomesquita.security.infra.config.security.jwt.JwtService;
import com.juliomesquita.security.infra.dtos.AuthenticationRequest;
import com.juliomesquita.security.infra.dtos.AuthenticationResponse;
import com.juliomesquita.security.infra.dtos.RegisterRequest;
import com.juliomesquita.security.infra.entities.Profile;
import com.juliomesquita.security.infra.entities.User;
import com.juliomesquita.security.infra.persistence.ProfileRepository;
import com.juliomesquita.security.infra.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ProfileRepository profileRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        Profile profile = this.checkProfile(request.cpf());
        User user = User
                .builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .cpf(request.cpf())
                .email(request.email())
                .password(this.passwordEncoder.encode(request.password()))
                .build();
        user.setProfile(profile);
        User userSaved = this.userRepository.save(user);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Email", userSaved.getEmail());
        extraClaims.put("Profile", userSaved.getProfile().getName());

        String jwtToken = this.jwtService.generateToken(extraClaims, userSaved);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationRequest login) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.cpf(),
                        login.password()
                )
        );
        User userSaved = this.userRepository.findByCpf(login.cpf())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Email", userSaved.getEmail());
        extraClaims.put("Profile", userSaved.getProfile().getName());

        String jwtToken = this.jwtService.generateToken(extraClaims, userSaved);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    private Profile checkProfile(String cpf){
        if(cpf.equalsIgnoreCase("60734641346")){
            Optional<Profile> profile = this.profileRepository.findByName("BackOffice");

            return profile.get();
        }
        return this.profileRepository.findByName("User").orElseThrow();
    }

}
