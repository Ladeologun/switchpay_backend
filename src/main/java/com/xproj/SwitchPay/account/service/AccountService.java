package com.xproj.SwitchPay.account.service;

import com.xproj.SwitchPay.account.dtos.*;
import com.xproj.SwitchPay.account.model.Account;
import com.xproj.SwitchPay.account.model.Currency;
import com.xproj.SwitchPay.account.repository.AccountRepository;
import com.xproj.SwitchPay.auth.model.PlatformUsers;
import com.xproj.SwitchPay.auth.repository.PlatformUsersRepository;
import com.xproj.SwitchPay.execptionHandlers.ApplicationRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PlatformUsersRepository platformUsersRepository;

    public CreateAccountResponseDto createAccount(CreateAccountRequestDto requestDto) {

        PlatformUsers user = platformUsersRepository
                .findByEmail(requestDto.getEmail()).orElseThrow(()->(new ApplicationRuntimeException(null,("user not found"))));
        if (user.getAccounts().size() >= 3) {
            throw new ApplicationRuntimeException(null,"user has reached the maximum number of accounts");
        }
        Currency currency = null;
        try {
            currency = Currency.valueOf(requestDto.getCurrency());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency: " + requestDto.getCurrency());
        }
        String accountNumber = generateAccountNumber();
        while (accountRepository.existsByAccountNumber(accountNumber)) {
            accountNumber = generateAccountNumber();
        }

        Account account = requestDto.toAccount();
        account.setAccountNumber(accountNumber);
        //give it random balance between 50000 and 250000
        account.setBalance(new BigDecimal(Math.random() * (250000 - 50000) + 50000));
        account.setCurrency(currency);
        account.setUser(user);
        Account savedAccount = accountRepository.save(account);
        return CreateAccountResponseDto.builder()
                .currency(savedAccount.getCurrency().name())
                .balance(savedAccount.getBalance())
                .accountNumber(savedAccount.getAccountNumber())
                .build();

    }


    @Transactional
    public WithdrawalResponseDto withDrawFromAccount(WithDrawalRequestDto requestDto) {
        Optional<Account> accountOptional = accountRepository.findForUpdateByAccountNumber(requestDto.getAccountNumber());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            BigDecimal amount = new BigDecimal(requestDto.getAmount());
            if (account.getBalance().compareTo(amount) >= 0) {
                account.withdraw(amount);
                Account savedAccount =  accountRepository.save(account);
                return WithdrawalResponseDto.builder()
                        .accountNumber(account.getAccountNumber())
                        .withdrawalAmount(requestDto.getAmount())
                        .currency(account.getCurrency().getDescription())
                        .balance(savedAccount.getBalance().toString())
                        .build();
            }else {
                throw new IllegalArgumentException("Insufficient balance");
            }

        }else {
            throw new IllegalArgumentException("Account not found");
        }

    }

    public List<AccountResponseDto> fetchAccountsByUser( String email) {
        PlatformUsers user = platformUsersRepository
                .findByEmail(email).orElseThrow(()->(new ApplicationRuntimeException(null,("user not found"))));

        List<Account> accounts = accountRepository.findAccountsByUser(user);
        return accounts.stream().map(account -> AccountResponseDto.builder()
                .id(account.getId())
                .currency(account.getCurrency().name())
                .curencyCode(account.getCurrency().getDescription())
                .balance(account.getBalance())
                .accountNumber(account.getAccountNumber())
                .build()).toList();

    }

    public String generateAccountNumber() {
        long a = (long) (Math.random() * 1000_000_000_00L);
        return String.valueOf(a);
    }

}
