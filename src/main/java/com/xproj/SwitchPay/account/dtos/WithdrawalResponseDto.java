package com.xproj.SwitchPay.account.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WithdrawalResponseDto {
    private String accountNumber;
    private String withdrawalAmount;
    private String currency;
    private String balance;
}
