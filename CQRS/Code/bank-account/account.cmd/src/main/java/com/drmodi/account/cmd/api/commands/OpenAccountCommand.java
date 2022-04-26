package com.drmodi.account.cmd.api.commands;

import com.drmodi.account.common.dto.AccountType;
import com.drmodi.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
