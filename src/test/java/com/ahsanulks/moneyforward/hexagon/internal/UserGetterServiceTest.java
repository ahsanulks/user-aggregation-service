package com.ahsanulks.moneyforward.hexagon.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ahsanulks.moneyforward.adapter.FakeUserPortAdapter;
import com.ahsanulks.moneyforward.hexagon.exception.ResourceNotFoundException;

public class UserGetterServiceTest {
    private static UserGetterService userGetter;

    @BeforeAll
    static void setUp() {
        userGetter = new UserGetterService(new FakeUserPortAdapter());
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
        var user = userGetter.getUserAccountById(1);

        var expectedUser = User.builder()
                .id(1)
                .name("test name")
                .build();

        assertThat(user).isEqualTo(expectedUser);
    }
}
