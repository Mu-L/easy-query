package com.easy.query.core.proxy.core.flat.casewhen;

import com.easy.query.api.proxy.util.EasyProxyParamExpressionUtil;
import com.easy.query.core.expression.builder.impl.AggregateFilterImpl;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilder;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2024/2/27 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenThenFlatJoinEntityBuilder {

    private final FlatElementCaseWhenEntityBuilder caseWhenEntityBuilder;
    private final EntitySQLContext entitySQLContext;
    private final SQLCaseWhenBuilder sqlCaseWhenBuilder;
    private final SQLAggregatePredicateExpression sqlAggregatePredicateExpression;

    public CaseWhenThenFlatJoinEntityBuilder(FlatElementCaseWhenEntityBuilder caseWhenEntityBuilder, EntitySQLContext entitySQLContext, SQLCaseWhenBuilder sqlCaseWhenBuilder, SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {
        this.caseWhenEntityBuilder = caseWhenEntityBuilder;
        this.entitySQLContext = entitySQLContext;
        this.sqlCaseWhenBuilder = sqlCaseWhenBuilder;
        this.sqlAggregatePredicateExpression = sqlAggregatePredicateExpression;
    }
    public <TV> FlatElementCaseWhenEntityBuilder then(TV then) {
        sqlCaseWhenBuilder.caseWhen(filter -> {
            sqlAggregatePredicateExpression.accept(new AggregateFilterImpl(filter.getExpressionContext(),filter.getRootPredicateSegment()));
        }, EasyProxyParamExpressionUtil.getParamExpression(entitySQLContext,then));
        return caseWhenEntityBuilder;
    }

    //
//        if(column instanceof SQLColumn){
//        selector.expression((SQLColumn)column);
//    }else if(column instanceof Query){
//        selector.expression((Query)column);
//    }else if(column instanceof DSLSQLFunctionAvailable){
//        selector.expression((DSLSQLFunctionAvailable)column);
//    }else if(column instanceof SQLFunction){
//        selector.expression((SQLFunction)column);
//    }else {
//        throw new UnsupportedOperationException();
//    }
//    public <T extends SQLTableOwner & DSLSQLFunctionAvailable> CaseWhenEntityBuilder then(T then) {
//        SQLFunction sqlFunction = then.func().apply(entitySQLContext.getRuntimeContext().fx());
//        ExpressionContext expressionContext = entitySQLContext.getEntityExpressionBuilder().getExpressionContext();
//        SQLSegmentParamExpressionImpl sqlSegmentParamExpression = new SQLSegmentParamExpressionImpl(sqlFunction, expressionContext, then.getTable(), expressionContext.getRuntimeContext(), null);
////        new
//        caseWhenBuilderExpression.caseWhen(filter -> {
//            entitySQLContext._where(filter, sqlActionExpression);
//        }, sqlSegmentParamExpression);
//        return caseWhenEntityBuilder;
//    }
//    public <TProperty> CaseWhenEntityBuilder<TRProxy,TR> caseWhen(SQLActionExpression sqlActionExpression, PropTypeColumn<TProperty> then){
//        caseWhenBuilder.caseWhen(filter->{
//            entitySQLContext._where(filter, sqlActionExpression);
//        },then);
//        return this;
//    }
}