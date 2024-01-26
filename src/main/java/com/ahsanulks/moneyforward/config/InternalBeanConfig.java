package com.ahsanulks.moneyforward.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ahsanulks.moneyforward.hexagon.internal.UserGetterService;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;

@Configuration
public class InternalBeanConfig {
    @Bean
    UserGetterService userGetterService(UserPort userPort) {
        return new UserGetterService(userPort);
    }
}
