package com.jokingwill.beprepared.controller;

import com.jokingwill.beprepared.dto.request.CitizenRequestDto;
import com.jokingwill.beprepared.dto.response.CitizenResponseDto;
import com.jokingwill.beprepared.mapper.Mapper;
import com.jokingwill.beprepared.model.Citizen;
import com.jokingwill.beprepared.service.CitizenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/citizens")
public class CitizenController {

    private final Mapper mapper;
    private final CitizenService citizenService;

    @PostMapping("/")
    public ResponseEntity<String> createCitizen(@Valid @RequestBody CitizenRequestDto citizenRequestDto){
            return new ResponseEntity<>(citizenService.createCitizen(
                    mapper.mapCitizenRequestToModel(citizenRequestDto),
                    citizenRequestDto.getCityId()),
                    HttpStatus.CREATED
            );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizens(){
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizens()
        ));
    }

    @GetMapping("/province")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizenByProvince(@RequestParam Long id){
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizensByProvinceId(id)
        ));
    }

    @GetMapping("/city")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizensByCityId(@RequestParam Long id){
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizensByCityId(id)
        ));
    }

    @GetMapping("/id")
    public ResponseEntity<CitizenResponseDto> getCitizenById(@AuthenticationPrincipal Citizen citizen){
        return ResponseEntity.ok(mapper.mapCitizenToResponseDto(
                citizenService.getCitizenById(citizen.getId())
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenResponseDto> getCitizenById(@PathVariable Long id){
        return ResponseEntity.ok(mapper.mapCitizenToResponseDto(
                citizenService.getCitizenById(id)
        ));
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String otp){
        return ResponseEntity.ok(citizenService.verifyAccount(otp));
    }

    @PutMapping("/new-otp")
    public  ResponseEntity<String> generateOTPForCitizen(@RequestParam String phone){
        return ResponseEntity.ok(citizenService.generateOTPForCitizen(phone));

    }
}
