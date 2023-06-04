package com.kitpa.kitpaserver.login;

import com.kitpa.kitpaserver.entity.Account;
import com.kitpa.kitpaserver.entity.AccountRepository;
import com.kitpa.kitpaserver.entity.PasswordGenerator;
import com.kitpa.kitpaserver.mail.IMailService;
import com.kitpa.kitpaserver.mail.MailMessage;
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
    public AccountDto createAccount(LoginForm loginForm) {
        String genPass = passwordGenerator.generatePassword();
        Account account = Account.createAccount(
                loginForm.getEmail(),
                loginForm.getRealName(),
                loginForm.getPhoneNumber(),
                passwordEncoder.encode(genPass));
        Account saved = accountRepository.save(account);

        MailMessage mailMessage = new MailMessage();
        mailMessage.setSubject("계정이 생성되었습니다.");
        mailMessage.setTo(loginForm.getEmail());
        mailMessage.setText("초기 계정 비밀번호 : " + genPass);
        iMailService.sendMail(mailMessage);

        return mapper.map(saved, AccountDto.class);
    }
}
