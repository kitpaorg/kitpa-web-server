package com.kitpa.kitpaserver.mail;

import lombok.Data;

@Data
public class MailMessage {
    private String to;
    private String subject;
    private String text;
}
