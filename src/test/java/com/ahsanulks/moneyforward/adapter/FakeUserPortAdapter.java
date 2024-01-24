package com.ahsanulks.moneyforward.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.ahsanulks.moneyforward.hexagon.ports.driven.AccountResponseDto;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserResponseDto;

public class FakeUserPortAdapter implements UserPort {
    private HashMap<Integer, UserResponseDto> data;
    private HashMap<Integer, List<AccountResponseDto>> accounts;

    public FakeUserPortAdapter() {
        data = new HashMap<>();
        accounts = new HashMap<>();
    }

    @Override
    public Optional<UserResponseDto> getUserById(int id) {
        return Optional.ofNullable(data.get(Integer.valueOf(id)));
    }

    public void addUser(UserResponseDto user) {
        this.data.put(Integer.valueOf(user.getId()), user);
    }

    @Override
    public List<AccountResponseDto> getUserAccounts(int id) {
        var accounts = this.accounts.get(Integer.valueOf(id));
        return accounts == null ? new ArrayList<>() : accounts;
    }

    public void addUserAccount(AccountResponseDto account) {
        if (!this.data.containsKey(Integer.valueOf(account.getUserId())))
            return;

        var accounts = this.accounts.get(Integer.valueOf(account.getId()));
        if (accounts == null) {
            accounts = new ArrayList<>();
        }

        accounts.add(account);
        this.accounts.put(Integer.valueOf(account.getUserId()), accounts);
    }
}
