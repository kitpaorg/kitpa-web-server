package com.kitpa.kitpaserver.entity;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PasswordGenerator {
    @Value("${password.length.min}")
    private Integer passMin;

    public String generatePassword() {
        return new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.ASCII_ALPHA_NUMERALS)
                .build().generate(passMin);
    }
}
