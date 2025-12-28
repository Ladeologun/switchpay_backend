package com.xproj.SwitchPay.account.controllers;

import com.xproj.SwitchPay.account.dtos.AccountResponseDto;
import com.xproj.SwitchPay.account.service.AccountService;
import com.xproj.SwitchPay.utils.ApplicationResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/my-accounts")
    public ResponseEntity<ApplicationResponseWrapper<List<AccountResponseDto>>> fetchMyAccounts(Principal principal){
        List<AccountResponseDto> accounts = accountService.fetchAccountsByUser(principal.getName());
        ApplicationResponseWrapper<List<AccountResponseDto>> applicationResponseWrapper = new ApplicationResponseWrapper<>("success", accounts);
        return ResponseEntity.ok(applicationResponseWrapper);
    }




}
