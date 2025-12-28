package com.xproj.SwitchPay.auth.dtos;

import com.xproj.SwitchPay.auth.model.PlatformUsers;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;


    public PlatformUsers convertToPlatformUser() {
        return PlatformUsers.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }
}