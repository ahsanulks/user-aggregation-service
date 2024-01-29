package com.ahsanulks.moneyforward.factory;

import java.util.Arrays;

import com.ahsanulks.moneyforward.hexagon.internal.User;
import com.github.javafaker.Faker;

public class UserFactory {
    private Faker faker;
    private AccountFactory accountFactory;

    public UserFactory() {
        faker = new Faker();
        accountFactory = new AccountFactory();
    }

    public User createUserWithAccount() {
        var account = accountFactory.createAccount();
        return User.builder()
                .id(faker.number().randomDigitNotZero())
                .name(faker.name().fullName())
                .accounts(Arrays.asList(account))
                .build();
    }
}
