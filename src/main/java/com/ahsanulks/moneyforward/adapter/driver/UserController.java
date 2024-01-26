package com.ahsanulks.moneyforward.adapter.driver;

import com.ahsanulks.moneyforward.hexagon.ports.driver.GetUserService;

public class UserController {
    private final GetUserService getUserService;

    public UserController(GetUserService getUserService) {
        this.getUserService = getUserService;
    }

}
