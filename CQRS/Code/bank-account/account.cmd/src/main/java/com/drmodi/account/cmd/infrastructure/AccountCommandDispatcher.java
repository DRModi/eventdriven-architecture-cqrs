package com.drmodi.account.cmd.infrastructure;

import com.drmodi.cqrs.core.commands.BaseCommand;
import com.drmodi.cqrs.core.commands.CommandHandlerMethod;
import com.drmodi.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, CommandHandlerMethod> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        routes.put(type, handler);

    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if(handlers == null){
            throw new RuntimeException("No Command Handler was Registered!");
        }
        handlers.handle(command);
    }
}
