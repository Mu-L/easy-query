package com.easy.query.core.expression.sql.builder.factory;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ContextTypeEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EasyExpressionContext;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.MapUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousDefaultTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousManyJoinDefaultTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.DeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.InsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.InsertMapExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.QueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.DefaultTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.UpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.UpdateMapExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/4/2 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyExpressionBuilderFactory implements ExpressionBuilderFactory {
    @Override
    public ExpressionContext createExpressionContext(QueryRuntimeContext runtimeContext, ContextTypeEnum contextType) {
        return new EasyExpressionContext(runtimeContext, contextType);
    }

    @Override
    public EntityTableExpressionBuilder createEntityTableExpressionBuilder(TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, ExpressionContext expressionContext) {
        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        DefaultTableExpressionBuilder defaultTableExpressionBuilder = new DefaultTableExpressionBuilder(tableAvailable, multiTableType, expressionContext);
        EntityMetadata entityMetadata = tableAvailable.getEntityMetadata();
        Supplier<Query<?>> cteViewerCreator = entityMetadata.getCteViewerCreator();
        if (cteViewerCreator != null) {
            List<ExpressionBuilder> declareExpressions = expressionContext.getDeclareExpressions();
            Query<?> query = cteViewerCreator.get();
            defaultTableExpressionBuilder.setTableNameAs(o -> {
                return entityMetadata.getTableName();
            });

            expressionContext.extract(query.getSQLEntityExpressionBuilder().getExpressionContext());
            if (!EasySQLExpressionUtil.withTableInDeclareExpressions(declareExpressions, entityMetadata.getEntityClass(), entityMetadata.getTableName())) {
                ExpressionBuilderFactory expressionBuilderFactory = runtimeContext.getExpressionBuilderFactory();
                EntityQueryExpressionBuilder sqlEntityExpressionBuilder = query.getSQLEntityExpressionBuilder();

                EntityQueryExpressionBuilder anonymousWithTableQueryExpressionBuilder = expressionBuilderFactory.createAnonymousWithTableQueryExpressionBuilder(entityMetadata.getTableName(), sqlEntityExpressionBuilder, expressionContext, entityMetadata.getEntityClass());
                declareExpressions.add(anonymousWithTableQueryExpressionBuilder);
            }

        }

        return defaultTableExpressionBuilder;
    }

    @Override
    public EntityTableExpressionBuilder createAnonymousEntityTableExpressionBuilder(TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return new AnonymousDefaultTableExpressionBuilder(tableAvailable, multiTableType, entityQueryExpressionBuilder);
    }

    @Override
    public AnonymousManyJoinEntityTableExpressionBuilder createAnonymousManyGroupEntityTableExpressionBuilder(EntityExpressionBuilder mainEntityExpressionBuilder,ExpressionContext expressionContext, TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder, String[] defaultKeys) {
        return new AnonymousManyJoinDefaultTableExpressionBuilder(mainEntityExpressionBuilder,expressionContext,tableAvailable, multiTableType, entityQueryExpressionBuilder, defaultKeys);
    }

    @Override
    public EntityQueryExpressionBuilder createEntityQueryExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> queryClass) {
        return new QueryExpressionBuilder(sqlExpressionContext, queryClass);
    }

    @Override
    public EntityInsertExpressionBuilder createEntityInsertExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> entityClass) {
        return new InsertExpressionBuilder(sqlExpressionContext, entityClass);
    }

    @Override
    public EntityUpdateExpressionBuilder createEntityUpdateExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> entityClass, boolean expression) {
        return new UpdateExpressionBuilder(sqlExpressionContext, entityClass, expression);
    }

    @Override
    public MapUpdateExpressionBuilder createMapUpdateExpressionBuilder(ExpressionContext sqlExpressionContext) {
        return new UpdateMapExpressionBuilder(sqlExpressionContext);
    }

    @Override
    public EntityDeleteExpressionBuilder createEntityDeleteExpressionBuilder(ExpressionContext sqlExpressionContext, Class<?> entityClass, boolean expression) {
        return new DeleteExpressionBuilder(sqlExpressionContext, entityClass, expression);
    }

    @Override
    public EntityInsertExpressionBuilder createMapInsertExpressionBuilder(ExpressionContext sqlExpressionContext) {
        return new InsertMapExpressionBuilder(sqlExpressionContext);
    }
}
