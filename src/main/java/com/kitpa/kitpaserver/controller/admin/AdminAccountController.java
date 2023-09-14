package com.kitpa.kitpaserver.controller.admin;

import com.kitpa.kitpaserver.dto.AccountDto;
import com.kitpa.kitpaserver.form.AccountForm;
import com.kitpa.kitpaserver.service.AccountLookupService;
import com.kitpa.kitpaserver.service.AccountRegisterService;
import com.kitpa.kitpaserver.utils.AccountCSVParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.kitpa.kitpaserver.utils.PagingUtils.injectPaging;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/accounts")
public class AdminAccountController {
    private final AccountRegisterService registerService;
    private final AccountLookupService lookupService;

    @Value("${save.path}")
    private String path;

    @GetMapping("/bulk-register")
    public String accountBulkRegisterView() {
        return "account/bulk-import-account";
    }

    @PostMapping("/bulk-register")
    public String bulkRegister(@RequestParam("file") MultipartFile multipartFile, Model model) throws IOException {
        List<AccountForm> accounts = AccountCSVParser.parse(multipartFile);
        List<AccountDto> accountDtos = registerService.createAccount(accounts);
        model.addAttribute("accounts", accountDtos);
        return "account/bulk-import-result";
    }

    @GetMapping("/list")
    public String accountListView(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "") String search,
                                  Model model) {
        Page<AccountDto> accountDtoPage = lookupService.getPagedAccounts(page - 1, size, search);
        model.addAttribute("accounts", accountDtoPage);
        model.addAttribute("search", search);
        injectPaging(model, accountDtoPage);
        return "account/account-list";
    }

    @GetMapping("/image/{file:.+}")
    public void getImage(@PathVariable String file, HttpServletResponse response) {
        Path imgPath = Paths.get(path);
        Path resolve = imgPath.resolve(file);
        File file1 = resolve.toFile();
        if(!file1.exists()){
            return;
        }
        String ext = StringUtils.substringAfterLast(file, ".");
        response.setContentType("image/"+ext);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage read = ImageIO.read(file1);
            ImageIO.write(read, ext, baos);
            baos.flush();
            InputStream byteArrayInputStream = new ByteArrayInputStream(baos.toByteArray());
            IOUtils.copy(byteArrayInputStream, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
