package com.kitpa.kitpaserver.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("local")
@Slf4j
@Service
public class ConsoleIMailService implements IMailService {
    @Override
    public String sendMail(MailMessage e) {
        log.info("mail={}", e.toString());
        return e.toString();
    }
}
