package com.ahsanulks.moneyforward.hexagon.ports.driven;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    Optional<UserResponseDto> getUserById(int id);

    List<AccountResponseDto> getUserAccounts(int id);
}
