package com.ahsanulks.moneyforward.hexagon.ports.driven;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private int id;
    private String name;
    private List<Integer> accountIds;
}
