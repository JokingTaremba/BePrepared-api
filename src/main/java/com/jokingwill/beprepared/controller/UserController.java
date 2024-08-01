package com.jokingwill.beprepared.controller;

import com.jokingwill.beprepared.dto.request.UserRequestDto;
import com.jokingwill.beprepared.dto.response.StatsResponse;
import com.jokingwill.beprepared.dto.response.UserResponseDto;
import com.jokingwill.beprepared.mapper.Mapper;
import com.jokingwill.beprepared.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "5. User Controller")
public class UserController {

    private final Mapper mapper;
    private final UserService userService;


    @PostMapping("/")
    @Operation(summary = "Entre aqui para criar um usuário como ADMIN",
            description = "Para criar um usuário como ADMIN, basta preencher os campos a seguir devidamente e, em seguida, autenticar-se como administrador")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.createUser(
                mapper.mapUserRequestToModel(userRequestDto)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Entre aqui ADMIN para ver todos os usuarios",
            description = "Esta área é só para os administradores. Para poder ver todos os usuários, basta já estar autenticado e ter inserido o seu token de acesso")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(mapper.mapUserToResponseDtoList(
                userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Entre aqui ADMIN para ver os usuários pelo ID",
            description = "Esta área é só para os administradores. Para poder ver os usuários pelo ID, basta já estar autenticado e ter inserido o seu token de acesso")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(mapper.mapUserToResponseDto(
                userService.getUserById(id)
        ));
    }

    @GetMapping("/metrics")
    @Operation(summary = "Entre aqui ADMIN para ver as estatísticas",
            description = "Esta área é só para os administradores. Para poder ver as estatísticas, basta já estar autenticado e ter inserido o seu token de acesso")
    public ResponseEntity<StatsResponse> getMetrics(){
        return ResponseEntity.ok(userService.getAllStats());
    }
}
