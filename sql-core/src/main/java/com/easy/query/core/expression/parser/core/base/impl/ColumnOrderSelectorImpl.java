package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.scec.ColumnConstExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.ColumnConstExpressionContextImpl;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 */
public class ColumnOrderSelectorImpl<T1> implements ColumnOrderSelector<T1> {
    protected final TableAvailable table;
    private final OrderSelector orderSelector;
    protected boolean asc;

    public ColumnOrderSelectorImpl(TableAvailable table, OrderSelector orderSelector) {
        this.table = table;
        this.orderSelector = orderSelector;
    }

    @Override
    public OrderSelector getOrderSelector() {
        return orderSelector;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnOrderSelector<T1> column(String property) {
        orderSelector.column(table,property);
        return this;
    }

    @Override
    public ColumnOrderSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        orderSelector.columnFunc(table,columnPropertyFunction);
        return this;
    }

    @Override
    public ColumnOrderSelector<T1> columnConst(String columnConst, SQLExpression1<ColumnConstExpressionContext> contextConsume) {
        orderSelector.columnConst(columnConst,context->{
            contextConsume.apply(new ColumnConstExpressionContextImpl(table,context));
        });
        return this;
    }

//    @Override
//    public ColumnOrderSelector<T1> columnConst(String columnConst) {
//        orderSelector.columnConst(columnConst);
//        return this;
//    }


    public void setAsc(boolean asc) {
        orderSelector.setAsc(asc);
    }

}
