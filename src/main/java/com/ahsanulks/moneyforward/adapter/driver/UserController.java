package com.ahsanulks.moneyforward.adapter.driver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahsanulks.moneyforward.hexagon.ports.driver.GetUserService;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final GetUserService getUserService;

    public UserController(GetUserService getUserService) {
        this.getUserService = getUserService;
    }

    @GetMapping("users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        var user = getUserService.getUserAccountById(id);
        return ResponseEntity.ok().body(user);
    }
}
