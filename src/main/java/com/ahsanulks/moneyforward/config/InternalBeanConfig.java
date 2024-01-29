package com.ahsanulks.moneyforward.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ahsanulks.moneyforward.hexagon.internal.UserGetterDecorator;
import com.ahsanulks.moneyforward.hexagon.internal.UserGetterService;
import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountPort;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;
import com.ahsanulks.moneyforward.hexagon.ports.driver.GetUserService;

@Configuration
public class InternalBeanConfig {
    @Value("${user.service.decorator.enabled}")
    private boolean userServiceDecoratorEnabled;

    @Bean
    GetUserService userGetterService(UserPort userPort, AccountPort accountPort) {
        GetUserService userService = new UserGetterService(userPort);
        if (userServiceDecoratorEnabled) {
            userService = new UserGetterDecorator(userService, accountPort);
        }
        return userService;
    }
}
