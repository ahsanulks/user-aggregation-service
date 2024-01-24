package com.ahsanulks.moneyforward.hexagon.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ahsanulks.moneyforward.adapter.FakeUserPortAdapter;
import com.ahsanulks.moneyforward.hexagon.exception.ResourceNotFoundException;
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

        var expectedUser = User.builder()
                .id(userAdapterData.getId())
                .name(userAdapterData.getName())
                .build();

        assertThat(user.getId()).isEqualTo(expectedUser.getId());
        assertThat(user.getName()).isEqualTo(expectedUser.getName());
        assertThat(user.getAccounts()).isEmpty();
    }

    private UserResponseDto generateUserAdapterData() {
        var userAdapterData = new UserResponseDto();
        userAdapterData.setId(faker.number().randomDigitNotZero());
        userAdapterData.setName(faker.name().fullName());
        userAdapterData.setAccountIds(new ArrayList<>());

        fakeUserPortAdapter.addUser(userAdapterData);

        return userAdapterData;
    }
}
