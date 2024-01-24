package com.ahsanulks.moneyforward.hexagon.internal;

import com.ahsanulks.moneyforward.hexagon.exception.ResourceNotFoundException;
import com.ahsanulks.moneyforward.hexagon.ports.driven.UserPort;

public class UserGetterService {
   private final UserPort userPort;

   public UserGetterService(
         UserPort userPort) {
      this.userPort = userPort;
   }

   public void getUserAccountById(int id) {
      getUserDataById(id);
   }

   private void getUserDataById(int id) {
      userPort.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
   }
}
