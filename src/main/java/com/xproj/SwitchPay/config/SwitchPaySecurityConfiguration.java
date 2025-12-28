package com.xproj.SwitchPay.config;

import com.xproj.SwitchPay.execptionHandlers.CustomAccessDeniedHandler;
import com.xproj.SwitchPay.execptionHandlers.CustomBasicAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SwitchPaySecurityConfiguration {

    private final JsonWebTokenAuthenticationFilter jsonWebTokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain handlePlatformSecurity(HttpSecurity http) throws Exception{
        http.
                requiresChannel(rcc->rcc.anyRequest().requiresSecure()).
                csrf((csrf) -> csrf.disable()).
                authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/api/v1/auth/**","/ws/**","/error").permitAll()
                            .anyRequest().authenticated();
                });
        http.formLogin(fml-> fml.disable());
        http.httpBasic(htb->htb.disable());
        http.addFilterBefore(jsonWebTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(hbc-> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(hge->hge.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
