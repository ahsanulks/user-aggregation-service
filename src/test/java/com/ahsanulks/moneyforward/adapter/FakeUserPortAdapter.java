package com.ahsanulks.moneyforward.adapter;

import java.util.HashMap;
import java.util.Optional;

import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPortResponseDTO;

public class FakeUserPortAdapter implements UserPort {
    private HashMap<Integer, UserPortResponseDTO> data;

    public FakeUserPortAdapter() {
        data = new HashMap<>();
    }

    @Override
    public Optional<UserPortResponseDTO> getUserById(int id) {
        return Optional.ofNullable(data.get(Integer.valueOf(id)));
    }

    public void addUser(UserPortResponseDTO user) {
        this.data.put(Integer.valueOf(user.getId()), user);
    }
}
