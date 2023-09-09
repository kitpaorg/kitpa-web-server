package com.kitpa.kitpaserver.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Account extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = true)
    private String userId;
    private String email;
    private String realName;
    private String username;
    private String address;
    private String school;
    private String password;
    private String phoneNumber;
    private String selfPhoto;
    private String identityPhoto;
    private Boolean privacyCheck;
    private Long exam;

    @OneToMany(mappedBy = "account")
    private List<AccountProblem> accountProblems = new ArrayList<>();

    public static Account createAccount(String email, String userId, String realName, String phoneNumber, String password, String school, String address, Long exam) {
        Account account = new Account();
        account.email = email;
        account.userId = userId;
        account.realName = realName;
        account.password = password;
        account.phoneNumber = phoneNumber;
        account.school = school;
        account.address = address;
        account.exam = exam;
        return account;
    }

    public void updatePassword(String hashedNewPass) {
        this.password = hashedNewPass;
    }

    public void updatePreData(String selfPhoto, String identityPhoto, Boolean privacyCheck) {
        setSelfPhoto(selfPhoto);
        setIdentityPhoto(identityPhoto);
        setPrivacyCheck(privacyCheck);
    }

    private void setPrivacyCheck(Boolean privacyCheck) {
        this.privacyCheck = privacyCheck;
    }
    private void setSelfPhoto(String fileName) {
        this.selfPhoto = fileName;
    }

    private void setIdentityPhoto(String fileName) {
        this.identityPhoto = fileName;
    }
}
