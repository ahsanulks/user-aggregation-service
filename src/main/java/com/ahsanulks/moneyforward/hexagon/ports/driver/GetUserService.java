package com.ahsanulks.moneyforward.hexagon.ports.driver;

import com.ahsanulks.moneyforward.hexagon.internal.User;

public interface GetUserService {
    User getUserAccountById(int id);
}
