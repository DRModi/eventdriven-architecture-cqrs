package com.drmodi.account.query.infrastructure;

import com.drmodi.cqrs.core.domain.BaseEntity;
import com.drmodi.cqrs.core.infrastructure.QueryDispatcher;
import com.drmodi.cqrs.core.queries.BaseQuery;
import com.drmodi.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {
    //private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();
    private final Map<Class<? extends BaseQuery>, QueryHandlerMethod> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        /*List<QueryHandlerMethod> handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);*/ //Modified the routes hashmap dictionary to have single handler rather than list.
        routes.put(type, handler);

    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        /*var handlers = routes.get(query.getClass());
        if(handlers == null || handlers.size() == 0){
            throw new RuntimeException("No query handler was registered!");
        }
        if(handlers.size() > 1){
            throw new RuntimeException("Can not send query to more than one handler!");
        }
         return handlers.get(0).handle(query);
        *///Modified the routes hashmap dictionary to have single handler rather than list.

        var handlers = routes.get(query.getClass());
        if(handlers == null){
            throw new RuntimeException("No query handler was registered!");
        }

        return handlers.handle(query);

    }
}
