package com.drmodi.account.cmd.api.commands;

import com.drmodi.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}
