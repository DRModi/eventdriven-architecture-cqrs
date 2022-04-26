package com.drmodi.account.query.api.queries;

import com.drmodi.account.query.api.dto.EqualityType;
import com.drmodi.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
