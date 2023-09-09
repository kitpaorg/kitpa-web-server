package com.kitpa.kitpaserver.utils;

import com.kitpa.kitpaserver.form.AccountForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AccountCSVParser {
    public static List<AccountForm> parse(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();

        List<AccountForm> accountForms = new ArrayList<>();
        try (CSVParser parse = CSVParser.parse(inputStream, Charset.defaultCharset(), CSVFormat.DEFAULT)) {
            List<CSVRecord> records = parse.getRecords();
            for (CSVRecord record : records) {
                try {
                    AccountForm accountForm = parse(record);
                    accountForms.add(accountForm);
                } catch (Throwable t) {
                    log.error("parse csv to accountForm, record={}, e=", record.toString(), t);
                }
            }

        } catch (IllegalArgumentException | IOException iae) {
            log.error("account csv parse fail, e=", iae);
        }
        return accountForms;
    }

    private static AccountForm parse(CSVRecord csvRecord) {
        String email = StringUtils.defaultString(csvRecord.get(0));
        String name = StringUtils.defaultString(csvRecord.get(1));
        String school = StringUtils.defaultString(csvRecord.get(2));
        String address = StringUtils.defaultString(csvRecord.get(3));
        String phone = StringUtils.defaultString(csvRecord.get(4));
        String userId = csvRecord.get(5);

        AccountForm accountForm = new AccountForm();
        accountForm.setEmail(email);
        accountForm.setRealName(name);
        accountForm.setSchool(school);
        accountForm.setAddress(address);
        accountForm.setPhoneNumber(phone);
        accountForm.setUserId(userId);

        return accountForm;
    }
}
