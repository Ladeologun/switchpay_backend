package com.xproj.SwitchPay.auth.dtos;

import com.xproj.SwitchPay.auth.model.PlatformUsers;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public RegisterResponseDto(PlatformUsers user) {

        this.id = user.getId().toString();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}