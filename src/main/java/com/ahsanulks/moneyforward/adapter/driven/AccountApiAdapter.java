package com.ahsanulks.moneyforward.adapter.driven;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountPort;
import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("Call Account API with id {}", id);

        try {
            var account = restTemplate.getForObject(accountBaseUrl + "/accounts/{id}", AccountResponseDto.class, id);
            if (account == null) {
                return Optional.empty();
            }
            return Optional.ofNullable(account.getBalance());
        } catch (RestClientException ex) {
            log.error("Error while calling account API: {}", ex.getMessage(), ex);

            if (ex instanceof HttpClientErrorException
                    && ((HttpClientErrorException) ex).getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();
            } else {
                throw new RuntimeException("Internal Server Error", ex);
            }
        }
    }

}
