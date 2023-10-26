package com.showback.controller;

import com.showback.dto.EmailDTO;
import com.showback.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class MailController {

    private final EmailServiceImpl emailService;

    @PostMapping("/auth/email/verify")
    public ResponseEntity<?> emailConfirm(@RequestBody EmailDTO email) throws Exception{
        String code = emailService.sendSimpleMessage(email.getEmail());
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setConfirmCode(code);

        return ResponseEntity.ok().body(emailDTO);
    }
}
