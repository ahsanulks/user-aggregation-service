package com.ahsanulks.moneyforward.adapter.driven;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.ahsanulks.moneyforward.factory.AccountApiResponseFactory;
import com.github.javafaker.Faker;

public class AccountApiAdapterTest {
    private AccountApiAdapter accountApiAdapter;
    private MockRestServiceServer mockServer;
    private static Faker faker;
    private static String baseUrl;
    private static AccountApiResponseFactory accountfactory;

    @BeforeAll
    static void setUpAll() {
        faker = new Faker();
        baseUrl = "http://example.com/api";
        accountfactory = new AccountApiResponseFactory();
    }

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        accountApiAdapter = new AccountApiAdapter(restTemplate, baseUrl);
    }

    @Test
    void whenAccountNotFound_itShouldReturnEmpty() {
        var accountId = faker.number().randomDigitNotZero();
        mockServer.expect(requestTo(baseUrl + "/accounts/" + accountId))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        var result = accountApiAdapter.getAccountBalance(accountId);

        mockServer.verify();

        assertThat(result).isEmpty();
    }

    @Test
    void whenAccountEndpointError_itShouldThrowError() {
        var accountId = faker.number().randomDigitNotZero();
        mockServer.expect(requestTo(baseUrl + "/accounts/" + accountId))
                .andRespond(withServerError());

        var throwable = assertThrows(RuntimeException.class, () -> accountApiAdapter.getAccountBalance(accountId));

        mockServer.verify();

        assertThat(throwable.getMessage()).isEqualTo("Internal Server Error");
    }

    @Test
    void whenAccountFound_itShouldReturnAccountBalance() {
        var accountId = faker.number().randomDigitNotZero();
        var expectedAccount = accountfactory.createAccountResponse(faker.number().randomDigitNotZero());

        var jsonResponse = "{ \"attributes\": { \"id\": " + expectedAccount.getId() + ", \"name\": \""
                + expectedAccount.getName() + "\", \"user_id\": " + expectedAccount.getUserId() + ", \"balance\": "
                + expectedAccount.getBalance() + " } }";
        mockServer.expect(requestTo(baseUrl + "/accounts/" + accountId))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        var result = accountApiAdapter.getAccountBalance(accountId);

        mockServer.verify();

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedAccount.getBalance());
    }
}
