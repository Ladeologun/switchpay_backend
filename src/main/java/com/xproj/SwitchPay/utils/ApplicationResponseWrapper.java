package com.xproj.SwitchPay.utils;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApplicationResponseWrapper<T> {
    private String message;
    private T data;

    public ApplicationResponseWrapper(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
