package com.kitpa.kitpaserver.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Account extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String realName;
    private String username;
    private String password;
    private String phoneNumber;

    public static Account createAccount(String email, String realName, String phoneNumber, String password) {
        Account account = new Account();
        account.email = email;
        account.realName = realName;
        account.password = password;
        account.phoneNumber = phoneNumber;
        return account;
    }

}
