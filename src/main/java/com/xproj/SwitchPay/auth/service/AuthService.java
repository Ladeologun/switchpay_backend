package com.xproj.SwitchPay.auth.service;

import com.xproj.SwitchPay.account.dtos.CreateAccountRequestDto;
import com.xproj.SwitchPay.account.model.Currency;
import com.xproj.SwitchPay.account.service.AccountService;
import com.xproj.SwitchPay.auth.dtos.LoginRequestDto;
import com.xproj.SwitchPay.auth.dtos.RegisterRequestDto;
import com.xproj.SwitchPay.auth.model.PlatformUsers;
import com.xproj.SwitchPay.auth.repository.PlatformUsersRepository;
import com.xproj.SwitchPay.config.JwtService;
import com.xproj.SwitchPay.execptionHandlers.ApplicationRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PlatformUsersRepository platformUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private  final AccountService accountService;
    private final UserDetailsService userDetailsService;


    public Map<String, String> signInUser(LoginRequestDto loginRequestDto) {
        UserDetails userDetails = null;
        try {
            System.out.println(loginRequestDto.getUsername());
            userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        } catch (Exception e) {
            throw new ApplicationRuntimeException(null,"submitted details do not match our records");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userDetails.getPassword())) {
            throw new ApplicationRuntimeException(null,"submitted details do not match our records");
        }

        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
            );
            if (authentication.isAuthenticated()) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("role", userDetails.getAuthorities());
                String token = jwtService.generateToken(claims, userDetails);
                return Map.of("token", token);
            }else {
                throw new RuntimeException("Authentication failed");
            }
        } catch (Exception e) {
            throw new ApplicationRuntimeException(null, e.getMessage());
        }
    }


    @Transactional
    public Map<String, String> registerUser(RegisterRequestDto registerRequestDto) {

        boolean usernameExists = platformUsersRepository.existsByEmail(registerRequestDto.getEmail());
        if (usernameExists) {
            throw new ApplicationRuntimeException(null,"email already exists");
        }
        String role = registerRequestDto.getRole();
        if (role == null || role.isEmpty()) {
            registerRequestDto.setRole("user");
        }
        String passwordBeforeHash = registerRequestDto.getPassword();
        String hashPassword = passwordEncoder.encode(registerRequestDto.getPassword());
        registerRequestDto.setPassword(hashPassword);
        PlatformUsers savedUser = platformUsersRepository.save(registerRequestDto.convertToPlatformUser());

        //Automatocally create an NGN and USD account for the user
        List<Currency> automaticallyCreatedCurrencies = List.of(Currency.NGN, Currency.USD);
        for (Currency currency : automaticallyCreatedCurrencies) {
            CreateAccountRequestDto createAccountRequestDto = CreateAccountRequestDto.builder()
                    .currency(currency.name())
                    .email(savedUser.getEmail())
                    .build();
            accountService.createAccount(createAccountRequestDto);
        }
        //Automically login the user
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username(registerRequestDto.getEmail())
                .password(passwordBeforeHash)
                .build();
        Map<String, String>  response = signInUser(loginRequestDto);
        return response;
    }
}