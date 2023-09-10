package com.kitpa.kitpaserver.controller;

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

