package com.drmodi.cqrs.core.queries;

import com.drmodi.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod <T extends BaseQuery> {
   List<BaseEntity> handle (T query);
}
