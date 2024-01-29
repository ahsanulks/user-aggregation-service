package com.ahsanulks.moneyforward.factory;

import java.math.BigDecimal;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountResponseDto;
import com.github.javafaker.Faker;

public class AccountApiResponseFactory {
    private Faker faker;

    public AccountApiResponseFactory() {
        faker = new Faker();
    }

    public AccountResponseDto createAccountResponse(int userId) {
        var account = new AccountResponseDto();
        account.setId(faker.number().randomDigitNotZero());
        account.setName(faker.company().name());
        account.setUserId(userId);
        account.setBalance(new BigDecimal(faker.number().randomDigitNotZero()));

        return account;
    }
}
