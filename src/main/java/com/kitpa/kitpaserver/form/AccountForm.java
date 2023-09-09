package com.kitpa.kitpaserver.form;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AccountForm {
    private String realName;
    private String userId;
    private String email;
    private String school;
    private String address;
    private String phoneNumber;
    private String originPass;
    private String newPass;
    private Long exam;

    public boolean isUpdatePassword() {
        return StringUtils.isNotBlank(originPass) && StringUtils.isNotBlank(newPass);
    }
}
