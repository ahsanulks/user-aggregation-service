package com.ahsanulks.moneyforward.adapter;

import java.util.Optional;

import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPortResponseDTO;

public class FakeUserPortAdapter implements UserPort {

    @Override
    public Optional<UserPortResponseDTO> getUserById(int id) {
        return Optional.empty();
    }

}
