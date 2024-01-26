package com.ahsanulks.moneyforward.adapter.driver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ahsanulks.moneyforward.factory.AccountFactory;
import com.ahsanulks.moneyforward.hexagon.internal.Account;
import com.ahsanulks.moneyforward.hexagon.internal.FakeUserGetterService;
import com.ahsanulks.moneyforward.hexagon.internal.User;
import com.github.javafaker.Faker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserControllerTest {
    private static MockMvc mockMvc;

    private static UserController userController;
    private static FakeUserGetterService userService;
    private static Faker faker;

    @BeforeAll
    static void setUpAll() {
        userService = new FakeUserGetterService();
        userController = new UserController(userService);
        faker = new Faker();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void whenUserFound_itShouldRerturnUserWithAccounts() throws Exception {
        int userId = faker.number().randomDigitNotZero();
        var expectedUser = User.builder()
                .id(userId)
                .name(faker.name().fullName())
                .accounts(generateAccount())
                .build();

        userService.addUser(expectedUser);

        var accountMatcher = assertAccounts(expectedUser.getAccounts()).toArray(new ResultMatcher[0]);
        if (accountMatcher == null) {
            accountMatcher = new ResultMatcher[0];
        }

        mockMvc.perform(get("/api/v1/users/{userId}", userId)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)),
                        jsonPath("$.id").value(expectedUser.getId()),
                        jsonPath("$.name").value(expectedUser.getName()),
                        jsonPath("$.accounts").isArray())
                .andExpectAll(accountMatcher);
    }

    private List<Account> generateAccount() {
        var factory = new AccountFactory();
        return Arrays.asList(factory.createAccount(), factory.createAccount(), factory.createAccount());
    }

    private List<ResultMatcher> assertAccounts(List<Account> accounts) {
        return IntStream.range(0, accounts.size())
                .mapToObj(index -> {
                    Account account = accounts.get(index);
                    return Arrays.asList(
                            jsonPath("$.accounts[" + index + "].name").value(account.getName()),
                            jsonPath("$.accounts[" + index + "].balance").isNumber(),
                            jsonPath("$.accounts[" + index + "].balance").value(account.getBalance()));
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
