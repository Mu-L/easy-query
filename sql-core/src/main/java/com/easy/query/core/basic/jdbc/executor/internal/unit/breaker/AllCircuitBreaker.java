package com.easy.query.core.basic.jdbc.executor.internal.unit.breaker;

import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.Collection;

/**
 * create time 2023/5/8 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
@Deprecated
public final class AllCircuitBreaker extends AbstractCircuitBreaker{

    public static final CircuitBreaker INSTANCE=new AllCircuitBreaker();

    @Override
    protected <TResult> boolean SequenceTerminated(StreamMergeContext streamMergeContext,Collection<TResult> results) {
        return doTerminated(streamMergeContext,results);
    }

    @Override
    protected <TResult> boolean RandomTerminated(StreamMergeContext streamMergeContext,Collection<TResult> results) {
        return doTerminated(streamMergeContext,results);
    }
    private <TResult> boolean doTerminated(StreamMergeContext streamMergeContext,Collection<TResult> results) {
        return EasyCollectionUtil.isEmpty(results)||EasyCollectionUtil.any(results,o->{
            if(o instanceof QueryExecuteResult){
                StreamResultSet resultSet = ((QueryExecuteResult) o).getStreamResultSet();
                if(resultSet.hasElement()){
                    try {
                        return resultSet.getLong(1)==0L;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return false;
        });
    }
}
