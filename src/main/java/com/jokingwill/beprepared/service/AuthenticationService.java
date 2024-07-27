package com.jokingwill.beprepared.service;

import com.jokingwill.beprepared.dto.request.AuthenticationRequestForCitizen;
import com.jokingwill.beprepared.dto.request.AuthenticationRequestForUser;
import com.jokingwill.beprepared.dto.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse authenticate(AuthenticationRequestForUser authenticationRequest);

    TokenResponse authenticateCitizen(AuthenticationRequestForCitizen authenticationRequest);
}
