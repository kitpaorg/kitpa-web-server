package com.kitpa.kitpaserver.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Profile("prod")
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceI implements IMailService {
    private final JavaMailSender javaMailSender;
    @Override
    public String sendMail(MailMessage e) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setTo(e.getTo());
            mimeMessageHelper.setSubject(e.getSubject());
            mimeMessageHelper.setText(e.getText(), true);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException messagingException) {
            log.error("[HtmlMailService.send] MessagingException", messagingException);
            throw new RuntimeException(messagingException);
        }
        return null;
    }
}
