package com.drmodi.account.query.infrastructure.handlers;

import com.drmodi.account.common.events.AccountClosedEvent;
import com.drmodi.account.common.events.AccountOpenedEvent;
import com.drmodi.account.common.events.FundsDepositedEvent;
import com.drmodi.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
