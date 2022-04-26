package com.drmodi.account.cmd.domain;

import com.drmodi.account.cmd.api.commands.OpenAccountCommand;
import com.drmodi.account.common.events.AccountClosedEvent;
import com.drmodi.account.common.events.AccountOpenedEvent;
import com.drmodi.account.common.events.FundsDepositedEvent;
import com.drmodi.account.common.events.FundsWithdrawnEvent;
import com.drmodi.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private boolean active;
    private double balance;

    public double getBalance(){
       return this.balance;
    }

    public boolean getActive(){
       return this.active;
    }


    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent.builder()
                            .id(command.getId())
                            .accountHolder(command.getAccountHolder())
                            .createdDate(new Date())
                            .accountType(command.getAccountType())
                            .openingBalance(command.getOpeningBalance())
                            .build());
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount){
        if(!this.active){
            throw new IllegalStateException("Funds cannot be deposited into a close account!");
        }
        if(amount <= 0){
            throw new IllegalStateException("The deposit amount must be greater than 0!");
        }

        raiseEvent(FundsDepositedEvent.builder()
                    .id(this.id)
                    .amount(amount)
                    .build());

    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount){
        if(!this.active){
            throw new IllegalStateException("Funds cannot be withdrawn into a close account!");
        }

        raiseEvent(FundsWithdrawnEvent.builder()
                    .id(this.id)
                    .amount(amount)
                    .build());
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        this.balance -= event.getAmount();
    }


    public void closeAccount(){
        if(!this.active){
            throw new IllegalStateException("The account has already been closed!");
        }

        raiseEvent(AccountClosedEvent.builder()
                    .id(this.id)
                    .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }

}
