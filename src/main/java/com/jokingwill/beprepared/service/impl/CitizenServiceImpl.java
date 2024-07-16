package com.jokingwill.beprepared.service.impl;

import com.jokingwill.beprepared.model.Citizen;
import com.jokingwill.beprepared.service.CitizenService;

import java.util.List;

public class CitizenServiceImpl implements CitizenService {
    @Override
    public String createCitizen(Citizen citizen, Long cityId) {
        return "";
    }

    @Override
    public List<Citizen> getAllCitizens() {
        return List.of();
    }

    @Override
    public List<Citizen> getAllCitizensByCityId(Long cityId) {
        return List.of();
    }

    @Override
    public List<Citizen> getAllCitizensByProvinceId(Long provinceId) {
        return List.of();
    }

    @Override
    public Citizen getCitizenById(Long id) {
        return null;
    }

    @Override
    public String verifyAccount(String otp) {
        return "";
    }
}
