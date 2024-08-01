package com.jokingwill.beprepared.controller;

import com.jokingwill.beprepared.dto.request.AuthenticationRequestForCitizen;
import com.jokingwill.beprepared.dto.request.AuthenticationRequestForUser;
import com.jokingwill.beprepared.dto.response.TokenResponse;
import com.jokingwill.beprepared.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "1. Authentication Controller")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @Operation(summary = "Entre aqui para fazer autenticação como ADMIN",
            description = "Para fazer autenticação como ADMIN, basta já ter criado uma conta e preencher os campos a seguir com o email e a senha")
    public ResponseEntity<TokenResponse> authenticate (@RequestBody AuthenticationRequestForUser authenticationRequest){

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/login")
    @Operation(summary = "Entre aqui para fazer autenticação como Cidadão",
            description = "Para fazer autenticação como CIDADÃO, basta já ter criado uma conta e preencher os campos a seguir com o número de celular e o OTP gerado")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthenticationRequestForCitizen authenticationRequest){

        return ResponseEntity.ok(authenticationService.authenticateCitizen(authenticationRequest));
    }
}
