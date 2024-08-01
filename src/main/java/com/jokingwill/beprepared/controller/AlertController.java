package com.jokingwill.beprepared.controller;

import com.jokingwill.beprepared.dto.request.AlertRequestDto;
import com.jokingwill.beprepared.dto.response.AlertResponseDto;
import com.jokingwill.beprepared.mapper.Mapper;
import com.jokingwill.beprepared.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alerts")
@Tag(name = "2. Alert Controller")
public class AlertController {

    private final Mapper mapper;
    private final AlertService alertService;

    @PostMapping("/")
    @Operation(summary = "Entrem aqui CIDADÃO e ADMIN para criar um novo alerta",
            description = "Para criar um novo alerta, basta já estar autenticado e inserido o seu token de acesso, e em seguida preencher os campos vazios que seguem")
    public ResponseEntity<String> createAlert(@Valid @RequestBody AlertRequestDto alertRequestDto){
        return new ResponseEntity<>(alertService.createAlert(mapper.mapAlertRequestToModel(alertRequestDto),alertRequestDto.getCityId(),
                alertRequestDto.getProvinceId()), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Entre aqui ADMIN para ver todos os alertas",
            description = "Esta área é só para os administradores, que podem ter acesso a todos os alertas")
    public ResponseEntity<List<AlertResponseDto>> getAllAlerts(){
        return ResponseEntity.ok(mapper.mapAlertToResponseDtoList(
                alertService.getAllAlerts()
        ));
    }

    @GetMapping("/active")
    @Operation(summary = "Entrem aqui CIDADÃO e ADMIN para ver todos os alertas ativos")
    public ResponseEntity<List<AlertResponseDto>> getAllActiveAlerts(){
        return ResponseEntity.ok(mapper.mapAlertToResponseDtoList(
           alertService.getAllActiveAlerts()
        ));
    }

    @GetMapping("/city")
    @Operation(summary = "Entrem aqui CIDADÃO e ADMIN para ver todos os alertas pelo distrito")
    public ResponseEntity<List<AlertResponseDto>> getAllAlertsByCity(@RequestParam Long cityId){
        return ResponseEntity.ok(mapper.mapAlertToResponseDtoList(
                alertService.getAllAlertsByCityId(cityId)
        ));
    }

    @GetMapping("/province")
    @Operation(summary = "Entrem aqui CIDADÃO e ADMIN para ver todos os alertas pela província")
    public ResponseEntity<List<AlertResponseDto>> getAllAlertsByProvince(@RequestParam Long provinceId){
        return ResponseEntity.ok(mapper.mapAlertToResponseDtoList(
                alertService.getAllAlertsByProvinceId(provinceId)
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Entrem aqui CIDADÃO e ADMIN para ver alertas pelo ID",
            description = "Esta área é para os cidadãos, assim como para os administradores, e podem ver os alertas pelo ID. Basta inserir o ID do alerta que pretende ver")
    public ResponseEntity<AlertResponseDto> getAlertById(@PathVariable Long id){
        return ResponseEntity.ok(mapper.mapAlertToResponseDto(
                alertService.getAlertById(id)
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Entre aqui ADMIN para ativar o alerta",
            description = "Esta área é só para os administradores, que podem ativar os alertas. Basta inserir o ID do alerta que pretende ativar")
    public ResponseEntity<String> activeAlert(@PathVariable Long id){
        return ResponseEntity.ok(alertService.activeAlert(id));
    }

}
