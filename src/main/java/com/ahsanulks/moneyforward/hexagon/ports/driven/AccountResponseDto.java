package com.ahsanulks.moneyforward.hexagon.ports.driven;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
public class AccountResponseDto {
    public AccountResponseDto() {
        this.attributes = new AccountAttribute();
    }

    @JsonProperty("attributes")
    private AccountAttribute attributes;

    public int getId() {
        return this.attributes.getId();
    }

    public int getUserId() {
        return this.attributes.getUserId();
    }

    public String getName() {
        return this.attributes.getName();
    }

    public BigDecimal getBalance() {
        return this.attributes.getBalance();
    }

    public void setId(int id) {
        this.attributes.setId(id);
    }

    public void setUserId(int id) {
        this.attributes.setUserId(id);
    }

    public void setName(String name) {
        this.attributes.setName(name);
    }

    public void setBalance(BigDecimal balance) {
        this.attributes.setBalance(balance);
    }
}

@Getter
@Setter
class AccountAttribute {
    private int id;

    @JsonProperty("user_id")
    private int userId;

    private String name;

    private BigDecimal balance;
}
