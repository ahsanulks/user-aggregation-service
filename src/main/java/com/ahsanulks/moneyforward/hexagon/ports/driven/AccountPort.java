package com.ahsanulks.moneyforward.hexagon.ports.driven;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountPort {
    Optional<BigDecimal> getAccountBalance(int id);
}
