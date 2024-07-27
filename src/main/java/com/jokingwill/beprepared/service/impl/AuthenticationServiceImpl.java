package com.jokingwill.beprepared.service.impl;

import com.jokingwill.beprepared.dto.request.AuthenticationRequestForUser;
import com.jokingwill.beprepared.dto.response.TokenResponse;
import com.jokingwill.beprepared.model.Token;
import com.jokingwill.beprepared.model.User;
import com.jokingwill.beprepared.repository.TokenRepository;
import com.jokingwill.beprepared.repository.UserRepository;
import com.jokingwill.beprepared.security.JWTService;
import com.jokingwill.beprepared.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public TokenResponse authenticate(AuthenticationRequestForUser authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user);
        saveUserToken(user, token);
        return TokenResponse.builder()
                .accessToken(token)
                .build();
    }

    private void saveUserToken(User user, String token){

        var jwtToken = Token.builder()
                .user(user)
                .token(token)
                .expired(false)
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .build();

        tokenRepository.save(jwtToken);
    }
}
