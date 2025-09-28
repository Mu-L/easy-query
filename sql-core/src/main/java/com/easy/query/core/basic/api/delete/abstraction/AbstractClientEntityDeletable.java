package com.easy.query.core.basic.api.delete.abstraction;

import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionPrepareExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.builder.impl.OnlySelectorImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.parser.core.base.impl.ColumnOnlySelectorImpl;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurerImpl;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractEntityDelete.java
 * @Description: 文件说明
 * create time 2023/2/28 12:33
 */
public abstract class AbstractClientEntityDeletable<T> extends AbstractSQLExecuteRows<ClientEntityDeletable<T>> implements ClientEntityDeletable<T> {
    protected final List<T> entities = new ArrayList<>();
    protected final EntityTableExpressionBuilder table;
    protected final EntityDeleteExpressionBuilder entityDeleteExpressionBuilder;

    public AbstractClientEntityDeletable(Class<T> clazz,Collection<T> entities, EntityDeleteExpressionBuilder entityDeleteExpressionBuilder) {
        super(entityDeleteExpressionBuilder);
        if (entities == null) {
            throw new EasyQueryException("entities can not be null");
        }
        this.entities.addAll(entities);
        this.entityDeleteExpressionBuilder = entityDeleteExpressionBuilder;

        QueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.NONE, entityDeleteExpressionBuilder.getExpressionContext());
        this.entityDeleteExpressionBuilder.addSQLEntityTableExpression(table);
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return entityDeleteExpressionBuilder.getExpressionContext();
    }

    @Override
    public EntityDeleteExpressionBuilder getDeleteExpressionBuilder() {
        return entityDeleteExpressionBuilder;
    }

    @Override
    public ClientEntityDeletable<T> whereColumns(boolean condition, SQLActionExpression1<ColumnOnlySelector<T>> columnSelectorExpression) {
        if (condition) {
            ColumnOnlySelectorImpl<T> columnSelector = new ColumnOnlySelectorImpl<>(table.getEntityTable(), new OnlySelectorImpl(entityDeleteExpressionBuilder.getRuntimeContext(), entityDeleteExpressionBuilder.getExpressionContext(), entityDeleteExpressionBuilder.getWhereColumns()));
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public long executeRows() {

        if (!entities.isEmpty()) {
            QueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
            EntityExpressionPrepareExecutor entityExpressionPrepareExecutor = runtimeContext.getEntityExpressionPrepareExecutor();
            long executeRows = entityExpressionPrepareExecutor.executeRows(ExecutorContext.create(getExpressionContext(), false, ExecuteMethodEnum.DELETE), entityDeleteExpressionBuilder, entities);
            removeTrackEntities(entities);
            return executeRows;
        }
        return 0;
    }

    @Override
    public List<T> getEntities() {
        return this.entities;
    }

    @Override
    public ClientEntityDeletable<T> useLogicDelete(boolean enable) {
        entityDeleteExpressionBuilder.setLogicDelete(enable);
        return this;
    }


    @Override
    public ClientEntityDeletable<T> allowDeleteStatement(boolean allow) {
        entityDeleteExpressionBuilder.getExpressionContext().deleteThrow(!allow);
        return this;
    }

    @Override
    public ClientEntityDeletable<T> asTable(Function<String, String> tableNameAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public ClientEntityDeletable<T> asSchema(Function<String, String> schemaAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setSchemaAs(schemaAs);
        return this;
    }

    @Override
    public ClientEntityDeletable<T> asAlias(String alias) {
        entityDeleteExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }
    @Override
    public ClientEntityDeletable<T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        if(configurer!=null){
            configurer.apply(new ContextConfigurerImpl(entityDeleteExpressionBuilder.getExpressionContext()));
        }
        return this;
    }
    @Override
    public ClientEntityDeletable<T> asTableLink(Function<String, String> linkAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableLinkAs(linkAs);
        return this;
    }

    @Override
    public ClientEntityDeletable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableSegmentAs(segmentAs);
        return this;
    }

    @Override
    public ClientEntityDeletable<T> ignoreVersion(boolean ignored) {
        if(ignored){
            entityDeleteExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.IGNORE_VERSION);
        }else{
            entityDeleteExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.IGNORE_VERSION);
        }
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return toSQLWithParam(toSQLContext);
    }

    private String toSQLWithParam(ToSQLContext toSQLContext) {
        return entityDeleteExpressionBuilder.toExpression().toSQL(toSQLContext);
    }
}
