package com.ahsanulks.moneyforward.factory;

import java.math.BigDecimal;

import com.ahsanulks.moneyforward.hexagon.internal.Account;
import com.github.javafaker.Faker;

public class AccountFactory {
    private Faker faker;

    public AccountFactory() {
        faker = new Faker();
    }

    public Account createAccount() {
        var account = Account.builder()
                .name(faker.name().fullName())
                .balance(new BigDecimal(faker.number().randomDigitNotZero()))
                .build();

        return account;
    }
}
