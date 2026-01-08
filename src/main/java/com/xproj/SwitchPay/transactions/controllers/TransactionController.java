package com.xproj.SwitchPay.transactions.controllers;

import com.xproj.SwitchPay.utils.ApplicationResponseWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @GetMapping("/health-check")
    public ApplicationResponseWrapper<Map<String, String>> healthCheck() {
        Map<String, String> response = Map.of("status", "OK");
        ApplicationResponseWrapper<Map<String, String>> applicationResponseWrapper = new ApplicationResponseWrapper<>("success", response);
        return applicationResponseWrapper;
    }
}
