package com.ahsanulks.moneyforward.adapter.driven;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountResponseDto;
import com.github.javafaker.Faker;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class UserApiAdapterTest {
    private static UserApiAdapter userApiAdapter;
    private static MockRestServiceServer mockServer;
    private static Faker faker;

    @BeforeAll
    static void setUp() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        userApiAdapter = new UserApiAdapter(restTemplate, "http://example.com/api");
        faker = new Faker();
    }

    @Test
    void whenAccountsFound_itShouldReturnListAccounts() {
        var userId = faker.number().randomDigitNotZero();
        var accounts = Arrays.asList(generateAccount(userId), generateAccount(userId), generateAccount(userId));

        var jsonResponse = accounts.stream()
                .map(account -> String.format(
                        "    {\n" +
                                "      \"attributes\": {\n" +
                                "        \"id\": %d,\n" +
                                "        \"user_id\": %d,\n" +
                                "        \"name\": \"%s\",\n" +
                                "        \"balance\": %s\n" +
                                "      }\n" +
                                "    }",
                        account.getId(), account.getUserId(), account.getName(), account.getBalance()))
                .collect(Collectors.joining(",\n", "[\n", "\n]"));

        mockServer.expect(requestTo("http://example.com/api/users/" + userId + "/accounts"))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        List<AccountResponseDto> result = userApiAdapter.getUserAccounts(userId);

        mockServer.verify();

        assertThat(result).isNotNull().hasSize(3);

        for (int i = 0; i < accounts.size(); i++) {
            assertAccountEquals(result.get(i), accounts.get(i));
        }
    }

    @Test
    void whenAccountsNotFound_itShouldReturnEmptyList() {
        var userId = faker.number().randomDigitNotZero();
        mockServer.expect(requestTo("http://example.com/api/users/" + userId + "/accounts"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        List<AccountResponseDto> result = userApiAdapter.getUserAccounts(userId);

        mockServer.verify();

        assertThat(result).isEmpty();
    }

    private void assertAccountEquals(AccountResponseDto actual, AccountResponseDto expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getBalance()).isEqualTo(expected.getBalance());
    }

    private AccountResponseDto generateAccount(int userId) {
        var account = new AccountResponseDto();
        account.setId(faker.number().randomDigitNotZero());
        account.setName(faker.company().name());
        account.setUserId(userId);
        account.setBalance(new BigDecimal(faker.number().randomDigitNotZero()));

        return account;
    }
}
