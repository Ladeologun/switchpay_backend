package com.xproj.SwitchPay.account.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateAccountResponseDto {

    private String currency;
    private BigDecimal balance;
    private String accountNumber;
}