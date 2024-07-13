package com.jokingwill.beprepared.service.impl;

import com.jokingwill.beprepared.model.City;
import com.jokingwill.beprepared.model.Province;
import com.jokingwill.beprepared.service.LocationService;

import java.util.List;

public class LocationServiceImpl implements LocationService {
    @Override
    public List<Province> getAllProvinces() {
        return List.of();
    }

    @Override
    public List<City> getAllCities() {
        return List.of();
    }

    @Override
    public List<City> getAllCitiesProvinceId(Long provinceId) {
        return List.of();
    }

    @Override
    public Province getProvinceById(Long provinceId) {
        return null;
    }

    @Override
    public City getCityById(Long cityId) {
        return null;
    }
}
