package com.kitpa.kitpaserver.controller.admin;

import com.kitpa.kitpaserver.dto.PagingResponse;
import com.kitpa.kitpaserver.dto.ProblemDto;
import com.kitpa.kitpaserver.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api/v1/problems")
public class AdminProblemApiController {
    private final ProblemService problemService;

    @GetMapping("/list")
    public ResponseEntity<PagingResponse<List<ProblemDto>>> getProblemListIfNotAssigned(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<ProblemDto> pagedProblems = problemService.getPagedProblemIfNotAssigned(page, size);
        return ResponseEntity.ok(PagingResponse.success(pagedProblems));
    }
}