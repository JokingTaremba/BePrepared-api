package com.jokingwill.beprepared.controller;

import com.jokingwill.beprepared.dto.response.CityResponseDto;
import com.jokingwill.beprepared.dto.response.ProvinceResponseDto;
import com.jokingwill.beprepared.mapper.Mapper;
import com.jokingwill.beprepared.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final Mapper mapper;
    private final LocationService locationService;

    @GetMapping("/provinces")
    public ResponseEntity<List<ProvinceResponseDto>> getAllProvinces(){
        return ResponseEntity.ok(mapper.mapProvinceToResponseDtoList(locationService.getAllProvinces()));
    }

    @GetMapping("/province")
    public ResponseEntity<ProvinceResponseDto> getProvinceById(@RequestParam Long id){
        return ResponseEntity.ok(mapper.mapProvinceToResponseDto(locationService.getProvinceById(id)));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityResponseDto>> getAllCities(){
        return ResponseEntity.ok(mapper.mapCityToResponseDtoList(locationService.getAllCities()));
    }

    @GetMapping("/cities/{provinceId}")
    public ResponseEntity<List<CityResponseDto>> getCityByProvinceId(@PathVariable Long provinceId){
        return ResponseEntity.ok(mapper.mapCityToResponseDtoList(locationService.getAllCitiesProvinceId(provinceId)));
    }

    @GetMapping("/city")
    public ResponseEntity<CityResponseDto> getCityById(@RequestParam Long id){
        return ResponseEntity.ok(mapper.mapCityToResponseDto(locationService.getCityById(id)));
    }
}
