package com.ahsanulks.moneyforward.hexagon.internal;

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
        return this.getUserService.getUserAccountById(id);
    }

}
