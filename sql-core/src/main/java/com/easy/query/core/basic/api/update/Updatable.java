package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.internal.ContextConfigure;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.SQLBatchExecute;
import com.easy.query.core.basic.api.internal.SQLExecuteExpectRows;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: Update.java
 * @Description: 文件说明
 * create time 2023/2/24 22:04
 */
public interface Updatable<T, TChain> extends SQLExecuteExpectRows,
        Interceptable<TChain>,
        LogicDeletable<TChain>,
        TableReNameable<TChain>,
        ContextConfigure<TChain>,
        SQLBatchExecute<TChain> {
    EntityUpdateExpressionBuilder getUpdateExpressionBuilder();
}
