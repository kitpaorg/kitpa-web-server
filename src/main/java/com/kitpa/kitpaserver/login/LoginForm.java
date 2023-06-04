package com.kitpa.kitpaserver.login;

import lombok.Data;

@Data
public class LoginForm {
    private String realName;
    private String email;
    private String phoneNumber;
}
