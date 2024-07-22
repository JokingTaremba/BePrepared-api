package com.jokingwill.beprepared.service;

import com.jokingwill.beprepared.dto.response.StatsResponse;
import com.jokingwill.beprepared.model.User;

import java.util.List;

public interface UserService {

    String createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    StatsResponse getAllStats();
}
