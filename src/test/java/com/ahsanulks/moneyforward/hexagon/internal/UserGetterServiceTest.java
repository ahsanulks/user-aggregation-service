package com.ahsanulks.moneyforward.hexagon.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ahsanulks.moneyforward.adapter.FakeUserPortAdapter;
import com.ahsanulks.moneyforward.hexagon.exception.ResourceNotFoundException;
import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountResponseDto;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserResponseDto;
import com.github.javafaker.Faker;

public class UserGetterServiceTest {
    private static UserGetterService userGetter;
    private static FakeUserPortAdapter fakeUserPortAdapter;
    private static Faker faker;

    @BeforeAll
    static void setUp() {
        fakeUserPortAdapter = new FakeUserPortAdapter();
        userGetter = new UserGetterService(fakeUserPortAdapter);
        faker = new Faker();
    }

    @Test
    void whenUserNotFound_itShouldError() {
        var exception = assertThrows(ResourceNotFoundException.class, () -> {
            userGetter.getUserAccountById(123);
        });
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    void whenAccountEmpty_itShouldReturnUserWithEmptyAccount() {
        var userAdapterData = generateUserAdapterData();

        var user = userGetter.getUserAccountById(userAdapterData.getId());

        assertResult(user);
    }

    @Test
    void whenAccountFound_itShouldReturnUserWithAccounts() {
        var userAdapterData = generateUserAdapterData();
        generateUserAccount(userAdapterData.getId());
        generateUserAccount(userAdapterData.getId());
        generateUserAccount(userAdapterData.getId());

        var user = userGetter.getUserAccountById(userAdapterData.getId());

        assertResult(user);
    }

    private UserResponseDto generateUserAdapterData() {
        var userAdapterData = new UserResponseDto();
        userAdapterData.setId(faker.number().randomDigitNotZero());
        userAdapterData.setName(faker.name().fullName());
        userAdapterData.setAccountIds(new ArrayList<>());

        fakeUserPortAdapter.addUser(userAdapterData);

        return userAdapterData;
    }

    private AccountResponseDto generateUserAccount(int userId) {
        var account = new AccountResponseDto();
        account.setId(faker.number().randomDigitNotZero());
        account.setName(faker.company().name());
        account.setUserId(userId);
        account.setBalance(new BigDecimal(faker.number().randomDigitNotZero()));

        fakeUserPortAdapter.addUserAccount(account);

        return account;
    }

    private void assertResult(User result) {
        var expectedUser = fakeUserPortAdapter.getUserById(result.getId()).get();

        assertThat(result.getId()).isEqualTo(expectedUser.getId());
        assertThat(result.getName()).isEqualTo(expectedUser.getName());

        assertAccounts(result);
    }

    private void assertAccounts(User result) {
        var expectedAccounts = fakeUserPortAdapter.getUserAccounts(result.getId());
        var accounts = result.getAccounts();

        if (expectedAccounts.isEmpty()) {
            assertThat(accounts).isEmpty();
        } else {
            assertThat(accounts).hasSize(expectedAccounts.size());
            for (int i = 0; i < expectedAccounts.size(); i++) {
                assertThat(accounts.get(i).getBalance()).isEqualTo(expectedAccounts.get(i).getBalance());
                assertThat(accounts.get(i).getName()).isEqualTo(expectedAccounts.get(i).getName());
            }
        }
    }
}
