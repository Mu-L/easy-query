package com.easy.query.api.proxy.extension.casewhen;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.extension.casewhen.CaseWhenBuilderExpression;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;

import java.util.Objects;

/**
 * create time 2024/2/27 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenEntityBuilder {
    private final CaseWhenBuilderExpression caseWhenBuilder;
    private final EntitySQLContext entitySQLContext;

    public CaseWhenEntityBuilder(EntitySQLContext entitySQLContext){
        this.entitySQLContext = entitySQLContext;
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        Objects.requireNonNull(entityExpressionBuilder,"CaseWhenEntityBuilder entitySQLContext.getEntityExpressionBuilder() is null");
        this.caseWhenBuilder=new CaseWhenBuilderExpression(entitySQLContext.getRuntimeContext(),entityExpressionBuilder.getExpressionContext());
    }
    public CaseWhenThenEntityBuilder caseWhen(SQLActionExpression sqlActionExpression){
        return new CaseWhenThenEntityBuilder(this,entitySQLContext,caseWhenBuilder,sqlActionExpression);
    }
//    public <TProperty> CaseWhenEntityBuilder<TRProxy,TR> caseWhen(SQLActionExpression sqlActionExpression, PropTypeColumn<TProperty> then){
//        caseWhenBuilder.caseWhen(filter->{
//            entitySQLContext._where(filter, sqlActionExpression);
//        },then);
//        return this;
//    }
    public <TProperty> ColumnFunctionComparableAnyChainExpression<TProperty> elseEnd(Object elseValue){
        SQLFunction sqlFunction = caseWhenBuilder.elseEnd(elseValue);
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(entitySQLContext,null,null,f->sqlFunction);
    }
    public <TProperty> ColumnFunctionComparableAnyChainExpression<TProperty> elseEnd(SQLColumn<?, ?> elseSQLColumn){
        SQLFunction sqlFunction = caseWhenBuilder.elseEndColumn(elseSQLColumn.getTable(),elseSQLColumn.getValue());
        return new ColumnFunctionComparableAnyChainExpressionImpl<>(entitySQLContext,null,null,f->sqlFunction);
    }
}