package com.jokingwill.beprepared.service.impl;

import com.jokingwill.beprepared.dto.response.StatsResponse;
import com.jokingwill.beprepared.exception.BadRequestException;
import com.jokingwill.beprepared.exception.EntityNotFoundException;
import com.jokingwill.beprepared.model.User;
import com.jokingwill.beprepared.repository.AlertRepository;
import com.jokingwill.beprepared.repository.CitizenRepository;
import com.jokingwill.beprepared.repository.UserRepository;
import com.jokingwill.beprepared.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final CitizenRepository citizenRepository;

    @Override
    @Transactional
    public String createUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new BadRequestException("Já existe um usáurio com esse e-mail!");
        }
        userRepository.save(user);
        return "Usuário criado com sucesso!";
    }

    @Override
    @Transactional
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Usuário não encontrado!"));
    }

    @Override
    @Transactional(readOnly = true)
    public StatsResponse getAllStats() {
        return StatsResponse.builder()
                .citizens(citizenRepository.count())
                .totalAlerts(alertRepository.count())
                .activeAlerts(alertRepository.countByActive(true))
                .build();
    }
}
