package com.ahsanulks.moneyforward.hexagon.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ahsanulks.moneyforward.adapter.driven.FakeAccountAdapter;
import com.ahsanulks.moneyforward.factory.UserFactory;
import com.ahsanulks.moneyforward.hexagon.ports.driver.GetUserService;

public class UserGetterDecoratorTest {
    private static GetUserService userService;
    private static FakeAccountAdapter accountPort;
    private static FakeUserGetterService fakeUserGetterService;
    private static UserFactory userFactory;

    @BeforeAll
    static void setUp() {
        fakeUserGetterService = new FakeUserGetterService();
        accountPort = new FakeAccountAdapter();
        userService = new UserGetterDecorator(fakeUserGetterService, accountPort);
        userFactory = new UserFactory();
    }

    @Test
    void whenUserServiceThrowError_shouldRethrowError() {
        int userId = 123;

        assertThatThrownBy(() -> userService.getUserAccountById(userId))
                .isInstanceOf(RuntimeException.class).hasMessage("Internal server error");
    }

    @Test
    void whenUserFound_itShouldGetBalanceFromAccountPort() {
        var user = userFactory.createUserWithAccount();
        fakeUserGetterService.addUser(user);

        var userAccount = user.getAccounts();
        userAccount.stream().forEach(account -> {
            accountPort.addAccountBalance(account.getId(), new BigDecimal(1000));
        });

        var result = userService.getUserAccountById(user.getId());
        var accountResult = result.getAccounts().get(0);

        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getName()).isEqualTo(user.getName());
        assertThat(accountResult.getBalance()).isEqualTo(new BigDecimal(1000));
        assertThat(accountResult.getName()).isEqualTo(userAccount.get(0).getName());
        assertThat(accountResult.getId()).isEqualTo(userAccount.get(0).getId());
    }
}
