package com.ahsanulks.moneyforward.hexagon.internal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountPort;
import com.ahsanulks.moneyforward.hexagon.ports.driver.GetUserService;

public class UserGetterDecoratorTest {
    private static GetUserService userService;
    private static AccountPort accountPort;

    @BeforeAll
    static void setUp() {
        userService = new UserGetterDecorator(new FakeUserGetterService(), accountPort);
    }

    @Test
    void whenUserServiceThrowError_shouldRethrowError() {
        int userId = 123;

        assertThatThrownBy(() -> userService.getUserAccountById(userId))
                .isInstanceOf(RuntimeException.class).hasMessage("Internal server error");
    }
}
