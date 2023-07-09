package com.kitpa.kitpaserver.service;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.repository.AccountRepository;
import com.kitpa.kitpaserver.form.AccountForm;
import com.kitpa.kitpaserver.service.mail.IMailService;
import com.kitpa.kitpaserver.service.mail.MailMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountRegisterService {
    private final IMailService iMailService;
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountDto createAccount(AccountForm accountForm) {
        String genPass = passwordGenerator.generatePassword();
        Account account = Account.createAccount(
                accountForm.getEmail(),
                accountForm.getRealName(),
                accountForm.getPhoneNumber(),
                passwordEncoder.encode(genPass));
        Account saved = accountRepository.save(account);

        MailMessage mailMessage = new MailMessage();
        mailMessage.setSubject("계정이 생성되었습니다.");
        mailMessage.setTo(accountForm.getEmail());
        mailMessage.setText("초기 계정 비밀번호 : " + genPass);
        iMailService.sendMail(mailMessage);

        return mapper.map(saved, AccountDto.class);
    }
}
