package com.xproj.SwitchPay.account.model;

public enum Currency {
    USD("$"),
    EUR("€"),
    JPY("¥"),
    BRL("R$"),
    KRW("₩"),
    CNY("¥"),
    AUD("$"),
    CAD("$"),
    CHF("CHF"),
    NGN("₦");

    private String description;

    Currency(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }


}


