package com.example.notifications.controller.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogInAdminResponse {

    String token;

    String username;

    String success = "Successfully logged in";

}
