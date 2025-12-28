package com.xproj.SwitchPay.auth.repository;

import com.xproj.SwitchPay.auth.model.PlatformUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformUsersRepository extends JpaRepository<PlatformUsers, Long> {

    @Override
    Optional<PlatformUsers> findById(Long id);
    Optional<PlatformUsers> findByEmail(String email);
    boolean existsByEmail(String email);
}