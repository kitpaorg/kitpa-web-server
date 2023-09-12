package com.kitpa.kitpaserver.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    private String email;
    private String realName;
    private String username;
    private String phoneNumber;
    private String school;
    private String address;
    private String selfPhoto;
    private String identityPhoto;
    private Boolean privacyCheck;
    private String userId;
    private Long exam;
}
