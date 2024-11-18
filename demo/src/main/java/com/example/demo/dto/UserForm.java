package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserForm {

	@NotEmpty(message = "{username.notempty}") /* message = "Username cannot be empty") */
    private String username;

    @NotEmpty(message = "{password.notempty}")
    private String password;

}
