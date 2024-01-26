package com.ahsanulks.moneyforward.hexagon.internal;

import java.util.HashMap;
import java.util.Map;

import com.ahsanulks.moneyforward.hexagon.exception.ResourceNotFoundException;
import com.ahsanulks.moneyforward.hexagon.ports.driver.GetUserService;

public class FakeUserGetterService implements GetUserService {
    private Map<Integer, User> data;

    public FakeUserGetterService() {
        this.data = new HashMap<>();
    }

    @Override
    public User getUserAccountById(int id) {
        if (data.containsKey(Integer.valueOf(id))) {
            return data.get(Integer.valueOf(id));
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public void addUser(User user) {
        this.data.put(Integer.valueOf(user.getId()), user);
    }
}
