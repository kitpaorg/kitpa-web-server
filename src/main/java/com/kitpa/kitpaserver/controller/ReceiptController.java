package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.annotation.CurrentEmail;
import com.kitpa.kitpaserver.dto.ExamDto;
import com.kitpa.kitpaserver.dto.Response;
import com.kitpa.kitpaserver.service.ExamService;
import com.kitpa.kitpaserver.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

import java.security.Principal;

import static com.kitpa.kitpaserver.utils.PagingUtils.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    @GetMapping("/list")
    public String receiptListView(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  Model model) {
        Page<ExamDto> examDtos = receiptService.getPagedExamWhenCanReceipt(page - 1, size);
        model.addAttribute("exams", examDtos);
        injectPaging(model, examDtos);
        return "receipt/receipt";
    }

    @ResponseBody
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> receiptRegister(@CurrentEmail String email,
                                             @RequestParam Long examId){
        receiptService.receiptExam(email, examId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cancel")
    public String receiptCancelView(@CurrentEmail String email,
                                    @RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    Model model) {
        Page<ExamDto> examDtos = receiptService.getReceiptCanCancellation(page - 1, size, email);
        model.addAttribute("exams", examDtos);
        injectPaging(model, examDtos);
        return "receipt/cancel-receipt";
    }

    @ResponseBody
    @PostMapping("/cancel")
    public ResponseEntity<?> receiptCancel(@CurrentEmail String email,
                                           @RequestParam Long examId){
        receiptService.cancellationExam(email, examId);
        return ResponseEntity.noContent().build();
    }
}
