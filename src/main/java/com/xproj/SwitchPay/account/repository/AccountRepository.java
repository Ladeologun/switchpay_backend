package com.xproj.SwitchPay.account.repository;

import com.xproj.SwitchPay.account.model.Account;
import com.xproj.SwitchPay.auth.model.PlatformUsers;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String number);
    boolean existsByAccountNumber(String number);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :number")
    Optional<Account> findForUpdateByAccountNumber(@Param("number") String number);
    List<Account> findAccountsByUser(PlatformUsers user);

}