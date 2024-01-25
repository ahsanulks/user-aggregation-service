package com.ahsanulks.moneyforward.adapter.driven;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountResponseDto;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        try {
            return Optional.ofNullable(
                    restTemplate.getForObject(userBaseUrl + "/users/{id}", UserResponseDto.class, id));
        } catch (RestClientException ex) {
            log.error("Error while calling user API: {}", ex.getMessage(), ex);

            if (ex instanceof HttpClientErrorException
                    && ((HttpClientErrorException) ex).getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();
            } else {
                throw new RuntimeException("Internal Server Error", ex);
            }
        }
    }

    @Override
    public List<AccountResponseDto> getUserAccounts(int id) {
        try {
            ResponseEntity<List<AccountResponseDto>> responseEntity = restTemplate.exchange(
                    userBaseUrl + "/users/{id}/accounts",
                    HttpMethod.valueOf("GET"),
                    null,
                    new ParameterizedTypeReference<List<AccountResponseDto>>() {
                    },
                    id);

            return responseEntity.getBody();
        } catch (RestClientException ex) {
            log.error("Error while calling user accounts API: {}", ex.getMessage(), ex);

            if (ex instanceof HttpClientErrorException
                    && ((HttpClientErrorException) ex).getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList();
            } else {
                throw new RuntimeException("Internal Server Error", ex);
            }
        }
    }

}
