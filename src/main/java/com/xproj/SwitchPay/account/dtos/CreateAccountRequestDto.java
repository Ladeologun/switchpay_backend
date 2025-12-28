package com.xproj.SwitchPay.account.dtos;

import com.xproj.SwitchPay.account.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateAccountRequestDto {

    private String currency;
    private String  email;

    public Account toAccount() {
        return Account.builder()
                .build();
    }


}

