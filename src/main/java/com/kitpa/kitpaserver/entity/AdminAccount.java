package com.kitpa.kitpaserver.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class AdminAccount {
    @Id @GeneratedValue
    private Long id;

    private String uuid;


    protected AdminAccount() {
    }

    public AdminAccount(String uuid) {
        this.uuid = uuid;
    }
}
