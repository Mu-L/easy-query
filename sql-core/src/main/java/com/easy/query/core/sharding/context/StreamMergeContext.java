package com.easy.query.core.sharding.context;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyGroup;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyOrder;

import java.util.List;

/**
 * create time 2023/4/13 11:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StreamMergeContext extends AutoCloseable {
    EasyQueryOption getEasyQueryOption();
    ConnectionModeEnum getConnectionMode();
    void terminatedBreak();
    boolean isTerminated();
    List<PropertyOrder> getOrders();
    List<PropertyGroup> getGroups();

    ExecutorContext getExecutorContext();

    boolean hasBehavior(MergeBehaviorEnum mergeBehavior);

    /**
     * 当前没有分片
     * @return
     */
    boolean isSharding();
    default boolean isShardingMerge(){
        return isSharding()&&isQuery()&&getExecutionUnits().size()!=1;
    }

    List<ExecutionUnit> getExecutionUnits();

    EasyQueryRuntimeContext getRuntimeContext();
    int getMaxShardingQueryLimit();

    boolean isQuery();

     boolean isSeqQuery();
    boolean isReverseMerge();

    /**
     * group和order前半段匹配支持stream merger
     * @return
     */
    boolean isPaginationQuery();
    long getOriginalOffset();
    long getOriginalRows();
    long getMergeOffset();
    long getMergeRows();
    long getRewriteOffset();
    long getRewriteRows();
    SqlBuilderSegment getSelectColumns();
    SqlBuilderSegment getGroupColumns();
    ExecuteMethodEnum getExecuteMethod();

    List<EasyConnection> getEasyConnections(ConnectionModeEnum connectionMode, String dataSourceName, int createDbConnectionCount);
}