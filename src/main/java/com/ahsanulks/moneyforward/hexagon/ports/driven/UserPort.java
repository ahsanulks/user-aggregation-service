package com.ahsanulks.moneyforward.hexagon.ports.driven;

import java.util.Optional;

public interface UserPort {
    Optional<UserPortResponseDTO> getUserById(int id);
}
