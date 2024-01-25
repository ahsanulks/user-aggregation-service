package com.ahsanulks.moneyforward.hexagon.ports.driven;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
public class UserResponseDto {
    private UserAttribute attributes;

    public UserResponseDto() {
        this.attributes = new UserAttribute();
    }

    public int getId() {
        return this.attributes.getId();
    }

    public String getName() {
        return this.attributes.getName();
    }

    public List<Integer> getAccountIds() {
        return this.attributes.getAccountIds();
    }

    public void setId(int id) {
        this.attributes.setId(id);
    }

    public void setName(String name) {
        this.attributes.setName(name);
    }

    public void setAccountIds(List<Integer> accountIds) {
        this.attributes.setAccountIds(accountIds);
    }
}

@Setter
@Getter
class UserAttribute {
    private int id;
    private String name;

    @JsonProperty("account_ids")
    private List<Integer> accountIds;
}
