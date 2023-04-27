package com.easy.query.core.basic.jdbc.executor.internal;

import com.easy.query.core.basic.jdbc.executor.internal.abstraction.AbstractAffectedRowsEasyQueryJdbcExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.unit.EasyExecuteBatchExecutor;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.unit.Executor;

/**
 * create time 2023/4/21 22:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultExecuteBatchEasyQueryJdbcExecutor extends AbstractAffectedRowsEasyQueryJdbcExecutor {
    public DefaultExecuteBatchEasyQueryJdbcExecutor(StreamMergeContext streamMergeContext) {
        super(streamMergeContext);
    }

    @Override
    protected Executor<AffectedRowsExecuteResult> createExecutor() {
        return new EasyExecuteBatchExecutor(streamMergeContext);
    }

}