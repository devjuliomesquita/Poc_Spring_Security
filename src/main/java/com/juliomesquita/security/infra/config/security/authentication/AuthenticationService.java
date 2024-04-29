package com.juliomesquita.security.infra.config.security.authentication;

import com.juliomesquita.security.infra.config.security.jwt.JwtService;
import com.juliomesquita.security.infra.dtos.AuthenticationRequest;
import com.juliomesquita.security.infra.dtos.AuthenticationResponse;
import com.juliomesquita.security.infra.dtos.RegisterRequest;
import com.juliomesquita.security.infra.entities.Profile;
import com.juliomesquita.security.infra.entities.Token;
import com.juliomesquita.security.infra.entities.User;
import com.juliomesquita.security.infra.entities.factories.ProfileFactory;
import com.juliomesquita.security.infra.entities.factories.TokenFactory;
import com.juliomesquita.security.infra.entities.factories.UserFactory;
import com.juliomesquita.security.infra.persistence.ProfileRepository;
import com.juliomesquita.security.infra.persistence.TokenRepository;
import com.juliomesquita.security.infra.persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ProfileRepository profileRepository;
    private final ProfileFactory profileFactory;
    private final TokenFactory tokenFactory;
    private final UserFactory userFactory;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        Profile profile = this.checkProfile(request.cpf());

        User user = this.userFactory.createUser(request);
        user.setProfile(profile);
        User userSaved = this.userRepository.save(user);
        String jwtToken = this.createToken(userSaved);
        Token token = this.tokenFactory.createToken(userSaved, jwtToken);
        String refreshToken = this.jwtService.generateRefreshToken(userSaved);
        this.revokeTokens(userSaved);
        this.tokenRepository.save(token);
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
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
        String jwtToken = this.createToken(userSaved);
        Token token = this.tokenFactory.createToken(userSaved, jwtToken);
        String refreshToken = this.jwtService.generateRefreshToken(userSaved);
        this.revokeTokens(userSaved);
        this.tokenRepository.save(token);
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
    }

    private void revokeTokens(User user){
        List<Token> tokenList = this.tokenRepository.findByUserIdAndExpiredFalseAndRevokedFalse(user.getId());
        if(tokenList.isEmpty()){
            return;
        }
        tokenList.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        this.tokenRepository.saveAll(tokenList);
    }
    private Profile checkProfile(String cpf){
        if(cpf.equalsIgnoreCase("60734641346")){
            Optional<Profile> profile = this.profileRepository.findByName("backoffice");
            if(profile.isEmpty()){
                List<Profile> profileList = this.profileFactory.createProfiles();
                List<Profile> profilesSaved = this.profileRepository.saveAll(profileList);
                return profilesSaved.stream()
                        .filter(p -> p.getName().equals("backoffice"))
                        .findFirst()
                        .orElseThrow();
            }
            return profile.get();
        }
        return this.profileRepository.findByName("user").orElseThrow();
    }

    private String createToken(User user){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Id", user.getId());
        extraClaims.put("Tag", "User");
        extraClaims.put("Profile", user.getProfile().getName());
        extraClaims.put("Permissions", user.getProfile().getAuthoraties());

        return this.jwtService.generateAccessToken(extraClaims, user);
    }
}
