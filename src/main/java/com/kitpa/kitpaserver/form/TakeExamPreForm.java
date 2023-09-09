package com.kitpa.kitpaserver.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class TakeExamPreForm {
    @NotNull
    private MultipartFile identityCardPhoto;
    @NotNull
    private MultipartFile selfPhoto;
    @NotNull
    private Boolean privacyCheck;

    @Override
    public String toString() {
        return "TakeExamPreForm{" +
                "identityCardPhoto=" + identityCardPhoto.getOriginalFilename() +
                ", selfPhoto=" + selfPhoto.getOriginalFilename() +
                ", privacyCheck=" + privacyCheck +
                '}';
    }
}
