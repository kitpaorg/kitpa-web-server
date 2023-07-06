package com.kitpa.kitpaserver.form;

import lombok.Data;

@Data
public class LoginForm {
    private String realName;
    private String email;
    private String phoneNumber;
}
