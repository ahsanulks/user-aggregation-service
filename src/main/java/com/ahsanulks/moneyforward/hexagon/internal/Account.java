package com.ahsanulks.moneyforward.hexagon.internal;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Account {
    private int id;
    private String name;
    private BigDecimal balance;

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
