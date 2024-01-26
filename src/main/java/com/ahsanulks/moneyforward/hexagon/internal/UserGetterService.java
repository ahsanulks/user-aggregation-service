package com.ahsanulks.moneyforward.hexagon.internal;

import java.util.List;
import java.util.stream.Collectors;

import com.ahsanulks.moneyforward.hexagon.exception.ResourceNotFoundException;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;
import com.ahsanulks.moneyforward.hexagon.ports.driver.GetUserService;

public class UserGetterService implements GetUserService {
   private final UserPort userPort;

   public UserGetterService(
         UserPort userPort) {
      this.userPort = userPort;
   }

   public User getUserAccountById(int id) {
      var user = getUserDataById(id);

      var accounts = getUserAccounts(id);
      user.setAccounts(accounts);

      return user;
   }

   private User getUserDataById(int id) {
      var userResponse = userPort.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
      return User.builder()
            .id(userResponse.getId())
            .name(userResponse.getName())
            .build();
   }

   private List<Account> getUserAccounts(int id) {
      return this.userPort.getUserAccounts(id).stream()
            .map(accountResponse -> {
               return Account.builder()
                     .name(accountResponse.getName())
                     .balance(accountResponse.getBalance())
                     .build();
            }).collect(Collectors.toList());
   }
}
