package com.ahsanulks.moneyforward.hexagon.ports.driven;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponseDto {
    private int id;
    private int userId;
    private String name;
    private BigDecimal balance;
}
