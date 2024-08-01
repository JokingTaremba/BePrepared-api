package com.jokingwill.beprepared.controller;

import com.jokingwill.beprepared.dto.response.CityResponseDto;
import com.jokingwill.beprepared.dto.response.ProvinceResponseDto;
import com.jokingwill.beprepared.mapper.Mapper;
import com.jokingwill.beprepared.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
@Tag(name = "4. Location Controller")
public class LocationController {

    private final Mapper mapper;
    private final LocationService locationService;

    @GetMapping("/provinces")
    @Operation(summary = "Entrem aqui CIDADÃO e ADMIN para poder ver todas as províncias",
            description = "Área de acesso total: tanto os CIDADÃOS quanto os ADMINISTRADORES podem ver todas as províncias")
    public ResponseEntity<List<ProvinceResponseDto>> getAllProvinces(){
        return ResponseEntity.ok(mapper.mapProvinceToResponseDtoList(locationService.getAllProvinces()));
    }

    @GetMapping("/province")
    @Operation(summary = "Entrem aqui CIDADÃO e ADMIN para poder ver as províncias pelo ID",
            description = "Área de acesso total: tanto os CIDADÃOS quanto os ADMINISTRADORES podem ver as províncias pelo ID")
    public ResponseEntity<ProvinceResponseDto> getProvinceById(@RequestParam Long id){
        return ResponseEntity.ok(mapper.mapProvinceToResponseDto(locationService.getProvinceById(id)));
    }

    @GetMapping("/cities")
    @Operation(summary = "Entre aqui CIDADÃO e ADMIN para poder ver todos os distritos",
            description = "Área de acesso total: tanto os CIDADÃOS quanto os ADMINISTRADORES podem ver todos os distritos")
    public ResponseEntity<List<CityResponseDto>> getAllCities(){
        return ResponseEntity.ok(mapper.mapCityToResponseDtoList(locationService.getAllCities()));
    }

    @GetMapping("/cities/{provinceId}")
    @Operation(summary = "Entre aqui CIDADÃO e ADMIN para poder ver todos os distritos pelo ID da província",
            description = "Área de acesso total: tanto os CIDADÃOS quanto os ADMINISTRADORES podem ver todos os distritos pelo ID da província")
    public ResponseEntity<List<CityResponseDto>> getCityByProvinceId(@PathVariable Long provinceId){
        return ResponseEntity.ok(mapper.mapCityToResponseDtoList(locationService.getAllCitiesProvinceId(provinceId)));
    }

    @GetMapping("/city")
    @Operation(summary = "Entre aqui CIDADÃO e ADMIN para poder ver os distritos pelo ID",
            description = "Área de acesso total: tanto os CIDADÃOS quanto os ADMINISTRADORES podem ver os distritos pelo ID")
    public ResponseEntity<CityResponseDto> getCityById(@RequestParam Long id){
        return ResponseEntity.ok(mapper.mapCityToResponseDto(locationService.getCityById(id)));
    }
}
