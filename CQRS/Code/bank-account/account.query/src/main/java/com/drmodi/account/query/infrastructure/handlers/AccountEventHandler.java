package com.drmodi.account.query.infrastructure.handlers;

import com.drmodi.account.common.events.AccountClosedEvent;
import com.drmodi.account.common.events.AccountOpenedEvent;
import com.drmodi.account.common.events.FundsDepositedEvent;
import com.drmodi.account.common.events.FundsWithdrawnEvent;
import com.drmodi.account.query.domain.AccountRepository;
import com.drmodi.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountEventHandler implements EventHandler{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        BankAccount bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());

    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        Optional<BankAccount> bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);
        accountRepository.save(bankAccount.get());

    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());

    }
}
