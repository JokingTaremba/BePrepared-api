package com.jokingwill.beprepared.controller;

import com.jokingwill.beprepared.dto.request.UserRequestDto;
import com.jokingwill.beprepared.dto.response.StatsResponse;
import com.jokingwill.beprepared.dto.response.UserResponseDto;
import com.jokingwill.beprepared.mapper.Mapper;
import com.jokingwill.beprepared.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/users")
public class UserController {

    private final Mapper mapper;
    private final UserService userService;


    @PostMapping("/")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.createUser(
                mapper.mapUserRequestToModel(userRequestDto)),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(mapper.mapUserToResponseDtoList(
                userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(mapper.mapUserToResponseDto(
                userService.getUserById(id)
        ));
    }

    @GetMapping("/metrics")
    public ResponseEntity<StatsResponse> getMetrics(){
        return ResponseEntity.ok(userService.getAllStats());
    }
}
