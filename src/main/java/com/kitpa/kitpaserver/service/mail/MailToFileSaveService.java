package com.kitpa.kitpaserver.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Profile("prod")
@Slf4j
@Service
public class MailToFileSaveService implements IMailService {
    @Value("${save.mail}")
    private String mailSavePath;

    @Override
    public String sendMail(MailMessage e) {
        Path path = Paths.get(mailSavePath);
        File file = path.toFile();
        if (!file.exists()) {
            log.error("mail save path not exists");
        }
        String text = e.getText();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))){
            bufferedWriter.newLine();
            bufferedWriter.write(e.toString()+"\n");
            bufferedWriter.flush();
        } catch (IOException ex) {
            log.error("file writer cannot open");
            throw new RuntimeException(ex);
        }

        return e.toString();
    }
}
