package com.ahsanulks.moneyforward.adapter.driven;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountResponseDto;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserResponseDto;

@Component
public class UserApiAdapter implements UserPort {

    private final String userBaseUrl;
    private final RestTemplate restTemplate;

    public UserApiAdapter(RestTemplate restTemplate, @Value("${user.api.url}") String userBaseUrl) {
        this.restTemplate = restTemplate;
        this.userBaseUrl = userBaseUrl;
    }

    @Override
    public Optional<UserResponseDto> getUserById(int id) {
        return Optional.empty();
    }

    @Override
    public List<AccountResponseDto> getUserAccounts(int id) {
        return new ArrayList<>();
    }

}
