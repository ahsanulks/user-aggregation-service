package com.ahsanulks.moneyforward.hexagon.internal;

import java.math.BigDecimal;
import java.util.List;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountPort;
import com.ahsanulks.moneyforward.hexagon.ports.driver.GetUserService;

public class UserGetterDecorator implements GetUserService {
    private final GetUserService getUserService;
    private final AccountPort accountPort;

    public UserGetterDecorator(
            GetUserService getUserService,
            AccountPort accountPort) {
        this.getUserService = getUserService;
        this.accountPort = accountPort;
    }

    @Override
    public User getUserAccountById(int id) {
        var user = this.getUserService.getUserAccountById(id);

        fetchAccountBalance(user.getAccounts());

        return user;
    }

    private void fetchAccountBalance(List<Account> accounts) {
        accounts.stream().forEach(account -> {
            var balance = accountPort.getAccountBalance(account.getId())
                    .orElse(new BigDecimal(0));
            account.setBalance(balance);
        });
    }

}
