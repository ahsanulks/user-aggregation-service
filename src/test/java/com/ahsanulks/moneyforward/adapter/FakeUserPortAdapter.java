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

    public FakeUserPortAdapter() {
        data = new HashMap<>();
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
        return new ArrayList<>();
    }
}
