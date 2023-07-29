package com.easy.query.core.expression.segment.factory;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnConstSegment;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.ColumnWithSelfSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.OrderFuncColumnSegment;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.impl.AnonymousColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.ColumnConstSegmentImpl;
import com.easy.query.core.expression.segment.impl.ColumnInsertSegmentImpl;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.ColumnWithSelfSegmentImpl;
import com.easy.query.core.expression.segment.impl.FuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.GroupByConstSegmentImpl;
import com.easy.query.core.expression.segment.impl.GroupColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.OrderByConstSegmentImpl;
import com.easy.query.core.expression.segment.impl.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.OrderFuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.SQLColumnAsSegmentImpl;
import com.easy.query.core.expression.segment.impl.SelectConstSegmentImpl;
import com.easy.query.core.expression.segment.impl.SubQueryColumnSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLConstExpressionContext;

/**
 * create time 2023/5/30 12:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLSegmentFactory implements SQLSegmentFactory {
    @Override
    public SelectConstSegment createSelectConstSegment(String projects) {
        return new SelectConstSegmentImpl(projects);
    }

    @Override
    public ColumnSegment createColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String alias) {
        return new ColumnSegmentImpl(table, propertyName, runtimeContext, alias);
    }

    @Override
    public ColumnSegment createAnonymousColumnSegment(TableAvailable table, QueryRuntimeContext runtimeContext, String alias) {
        return new AnonymousColumnSegmentImpl(table, runtimeContext, alias);
    }

//    @Override
//    public ColumnAsConstSegment createColumnAsConstSegment(TableAvailable table, QueryRuntimeContext runtimeContext, String columnConst, String alias) {
//        return new ColumnAsConstSegmentImpl(table, runtimeContext, columnConst, alias);
//    }

    @Override
    public ColumnConstSegment createColumnConstSegment(QueryRuntimeContext runtimeContext, String columnConst, SQLConstExpressionContext sqlConstExpressionContext) {
        return new ColumnConstSegmentImpl(runtimeContext,columnConst,sqlConstExpressionContext);
    }

    @Override
    public ColumnInsertSegment createColumnInsertSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext) {
        return new ColumnInsertSegmentImpl(table, propertyName, runtimeContext);
    }

    @Override
    public ColumnWithSelfSegment createColumnWithSelfSegment(boolean increment, TableAvailable table, String propertyName, Object val, SQLPredicateCompare compare, QueryRuntimeContext runtimeContext) {
        return new ColumnWithSelfSegmentImpl(increment, table, propertyName, val, compare, runtimeContext);
    }

    @Override
    public FuncColumnSegment createFuncColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, String alias) {
        return new FuncColumnSegmentImpl(table, propertyName, runtimeContext, columnFunction, alias);
    }

    @Override
    public GroupByColumnSegment createGroupByColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext) {
        return new GroupColumnSegmentImpl(table, propertyName, runtimeContext);
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, boolean asc) {
        return new OrderColumnSegmentImpl(table, propertyName, runtimeContext, asc);
    }

    @Override
    public OrderFuncColumnSegment createOrderFuncColumnSegment(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, boolean asc) {
        return new OrderFuncColumnSegmentImpl(table, propertyName, runtimeContext, columnFunction, asc);
    }

    @Override
    public OrderBySegment createOrderByConstSegment(QueryRuntimeContext runtimeContext, String columnConst, SQLConstExpressionContext sqlConstExpressionContext, boolean asc) {
        return new OrderByConstSegmentImpl(runtimeContext, columnConst,sqlConstExpressionContext, asc);
    }

    @Override
    public GroupByColumnSegment createGroupByConstSegment(QueryRuntimeContext runtimeContext, String columnConst, SQLConstExpressionContext sqlConstExpressionContext) {
        return new GroupByConstSegmentImpl(runtimeContext, columnConst,sqlConstExpressionContext);
    }

    @Override
    public SubQueryColumnSegment createSubQueryColumnSegment(TableAvailable table, Query<?> subQuery, String alias, QueryRuntimeContext runtimeContext) {
        return new SubQueryColumnSegmentImpl(table, subQuery, alias, runtimeContext);
    }

    @Override
    public SQLColumnSegment createSQLColumnAsSegment(SQLColumnSegment sqlColumnSegment, String alias,QueryRuntimeContext runtimeContext) {
        return new SQLColumnAsSegmentImpl(sqlColumnSegment,alias,runtimeContext);
    }
}
