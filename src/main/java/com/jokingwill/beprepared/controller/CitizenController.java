package com.jokingwill.beprepared.controller;

import com.jokingwill.beprepared.dto.request.CitizenRequestDto;
import com.jokingwill.beprepared.dto.response.CitizenResponseDto;
import com.jokingwill.beprepared.mapper.Mapper;
import com.jokingwill.beprepared.model.Citizen;
import com.jokingwill.beprepared.service.CitizenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "3. Citizen Controller")
public class CitizenController {

    private final Mapper mapper;
    private final CitizenService citizenService;

    @PostMapping("/")
    @Operation(summary = "Entre aqui CIDADÃO para criar uma conta",
            description = "Para criar uma conta como CIDADÃO, basta preencher os campos a seguir e, em seguida, autenticar-se como CIDADÃO")
    public ResponseEntity<String> createCitizen(@Valid @RequestBody CitizenRequestDto citizenRequestDto){
            return new ResponseEntity<>(citizenService.createCitizen(
                    mapper.mapCitizenRequestToModel(citizenRequestDto),
                    citizenRequestDto.getCityId()),
                    HttpStatus.CREATED
            );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Entre aqui ADMIN para ver todos os CIDADÃOS",
            description = "Para ver todos os CIDADÃOS, basta estar autenticado com o seu token de acesso como administrador")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizens(){
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizens()
        ));
    }

    @GetMapping("/province")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Entre aqui ADMIN para ver todos os CIDADÃOS pela província",
            description = "Para ver todos os CIDADÃOS pela província, basta estar autenticado com o seu token de acesso como administrador e preencher o campo a seguir com o ID da província")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizenByProvince(@RequestParam Long id){
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizensByProvinceId(id)
        ));
    }

    @GetMapping("/city")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Entre aqui ADMIN para ver todos os CIDADÃOS pelo distrito",
            description = "Para ver todos os CIDADÃOS pelo ID do distrito, basta estar autenticado com o seu token de acesso como administrador e preencher o campo a seguir com o ID do distrito")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizensByCityId(@RequestParam Long id){
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizensByCityId(id)
        ));
    }

    @GetMapping("/id")
    @Operation(summary = "Entre aqui CIDADÃO para poder ver a sua conta",
            description = "Nesta área, só o CIDADÃO tem acesso para ver a sua conta. Basta já estar autenticado e ter inserido o seu token de acesso")
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
    @Operation(summary = "Entre aqui CIDADÃO ou ADMIN para poder verificar a conta do CIDADÃO",
            description = "Nesta área, tanto o CIDADÃO quanto o ADMIN podem acessar para verificar a conta de um CIDADÃO. Basta inserir o OTP gerado na criação da conta" )
    public ResponseEntity<String> verifyAccount(@RequestParam String otp){
        return ResponseEntity.ok(citizenService.verifyAccount(otp));
    }

    @PutMapping("/new-otp")
    @Operation(summary = "Entre aqui CIDADÃO ou ADMIN para poder gerar um novo OTP para a conta do CIDADÃO",
            description = "Nesta área, tanto o CIDADÃO quanto o ADMIN podem acessar para gerar um novo OTP para a conta de um CIDADÃO. Basta inserir o número de celular da conta" )
    public  ResponseEntity<String> generateOTPForCitizen(@RequestParam String phone){
        return ResponseEntity.ok(citizenService.generateOTPForCitizen(phone));

    }
}
