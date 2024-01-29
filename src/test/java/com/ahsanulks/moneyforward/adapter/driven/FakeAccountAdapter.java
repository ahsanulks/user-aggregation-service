package com.ahsanulks.moneyforward.adapter.driven;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountPort;

public class FakeAccountAdapter implements AccountPort {
    private Map<Integer, BigDecimal> accountBalances;

    public FakeAccountAdapter() {
        accountBalances = new HashMap<>();
    }

    public void addAccountBalance(int id, BigDecimal balance) {
        this.accountBalances.put(Integer.valueOf(id), balance);
    }

    @Override
    public Optional<BigDecimal> getAccountBalance(int id) {
        if (accountBalances.containsKey(Integer.valueOf(id))) {
            return Optional.of(accountBalances.get(Integer.valueOf(id)));
        } else {
            return Optional.empty();
        }
    }

}
