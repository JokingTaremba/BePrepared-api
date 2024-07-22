package com.jokingwill.beprepared.service.impl;

import com.jokingwill.beprepared.exception.EntityNotFoundException;
import com.jokingwill.beprepared.model.City;
import com.jokingwill.beprepared.model.Province;
import com.jokingwill.beprepared.repository.CityRepository;
import com.jokingwill.beprepared.repository.ProvinceRepository;
import com.jokingwill.beprepared.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getAllCitiesProvinceId(Long provinceId) {
        return cityRepository.findAllByProvinceId(provinceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Province getProvinceById(Long provinceId) {
        return provinceRepository.findById(provinceId).orElseThrow(() ->
                new EntityNotFoundException("Província não encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public City getCityById(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() ->
                new EntityNotFoundException("Distrito não encontrada"));
    }
}
