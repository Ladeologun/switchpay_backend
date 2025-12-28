package com.xproj.SwitchPay.account.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WithDrawalRequestDto {
    private String accountNumber;
    private String amount;
}
