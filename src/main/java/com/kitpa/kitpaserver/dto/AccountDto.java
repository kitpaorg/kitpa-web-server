package com.kitpa.kitpaserver.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    private String email;
    private String realName;
    private String username;
    private String password;
    private String phoneNumber;
}
