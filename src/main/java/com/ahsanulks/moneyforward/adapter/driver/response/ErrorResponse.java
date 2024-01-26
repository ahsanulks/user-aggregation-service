package com.ahsanulks.moneyforward.adapter.driver.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private String type;
}
