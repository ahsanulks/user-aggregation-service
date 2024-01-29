package com.ahsanulks.moneyforward.adapter.driven;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountPort;

@Component
public class AccountApiAdapter implements AccountPort {

    private final String accountBaseUrl;
    private final RestTemplate restTemplate;

    public AccountApiAdapter(RestTemplate restTemplate, @Value("${account.api.url}") String userBaseUrl) {
        this.restTemplate = restTemplate;
        this.accountBaseUrl = userBaseUrl;
    }

    @Override
    public Optional<BigDecimal> getAccountBalance(int id) {
        return Optional.empty();
    }

}
