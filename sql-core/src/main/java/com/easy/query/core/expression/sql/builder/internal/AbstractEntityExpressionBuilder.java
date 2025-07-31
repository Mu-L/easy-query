package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationEntityTableAvailable;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/3/6 08:58
 */
public abstract class AbstractEntityExpressionBuilder implements EntityExpressionBuilder {
    protected final ExpressionContext expressionContext;
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLSegmentFactory sqlSegmentFactory;
    protected final EasyQueryOption easyQueryOption;
    protected final Class<?> queryClass;
    protected final List<EntityTableExpressionBuilder> tables;
    protected Map<RelationTableKey, EntityTableExpressionBuilder> relationTables;
    protected Map<RelationTableKey, ManyConfiguration> manyConfigurationMaps;
    protected Map<RelationTableKey,SubQueryModeEnum> manyJoinConfigurationMaps;

    public AbstractEntityExpressionBuilder(ExpressionContext expressionContext, Class<?> queryClass) {
        this.expressionContext = expressionContext;
        this.runtimeContext = expressionContext.getRuntimeContext();

        this.queryClass = queryClass;
        this.tables = new ArrayList<>();
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        this.easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
    }

    @Override
    public Class<?> getQueryClass() {
        return queryClass;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return expressionContext.getRuntimeContext();
    }

    @Override
    public void addSQLEntityTableExpression(EntityTableExpressionBuilder tableExpression) {
        if (tableExpression.getEntityTable().getEntityMetadata().isSharding()) {
            expressionContext.useSharding();
        }
        expressionContext.getTableContext().addTable(tableExpression.getEntityTable());
        tables.add(tableExpression);
    }

    @Override
    public EntityTableExpressionBuilder addRelationEntityTableExpression(RelationTableKey relationTableKey, Function<RelationTableKey, EntityTableExpressionBuilder> tableExpressionSupplier) {
        if (relationTables == null) {
            relationTables = new LinkedHashMap<>();
        }
        EntityTableExpressionBuilder entityTableExpressionBuilder = relationTables.get(relationTableKey);
        if (entityTableExpressionBuilder != null) {
            return entityTableExpressionBuilder;
        }
        EntityTableExpressionBuilder tableExpression = tableExpressionSupplier.apply(relationTableKey);

        //涉及到后续移除问题
        if (tableExpression.getEntityTable().getEntityMetadata().isSharding()) {
            expressionContext.useSharding();
        }
        expressionContext.getTableContext().addTable(tableExpression.getEntityTable());
        if (tableExpression.getEntityTable() instanceof RelationEntityTableAvailable) {
            relationTables.put(relationTableKey, tableExpression);
            return tableExpression;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean hasManyJoinTable(RelationTableKey relationTableKey) {
        if (relationTables == null) {
            return false;
        }
        return relationTables.containsKey(relationTableKey);
    }

    @Override
    public ManyConfiguration getManyConfiguration(RelationTableKey relationTableKey) {
        if (manyConfigurationMaps == null) {
            return null;
        }
        return manyConfigurationMaps.get(relationTableKey);
    }

    @Override
    public Map<RelationTableKey, ManyConfiguration> getManyConfigurations() {
        return manyConfigurationMaps;
    }

    @Override
    public ManyConfiguration putManyConfiguration(RelationTableKey relationTableKey, ManyConfiguration manyConfiguration) {
        if (manyConfigurationMaps == null) {
            this.manyConfigurationMaps = new HashMap<>();
        }
        return manyConfigurationMaps.put(relationTableKey, manyConfiguration);
    }

    @Override
    public void putSubQueryToGroupJoinJoin(RelationTableKey relationTableKey, SubQueryModeEnum subQueryMode) {
        if (manyJoinConfigurationMaps == null) {
            this.manyJoinConfigurationMaps = new HashMap<>();
        }
        manyJoinConfigurationMaps.put(relationTableKey,subQueryMode);
    }

    @Override
    public SubQueryModeEnum getSubQueryToGroupJoin(RelationTableKey relationTableKey) {
        if (manyJoinConfigurationMaps == null) {
            return null;
        }
        return manyJoinConfigurationMaps.get(relationTableKey);
    }
    @Override
    public Map<RelationTableKey,SubQueryModeEnum> getManyJoinConfigurationMaps() {
        return manyJoinConfigurationMaps;
    }
    //
//    @Override
//    public EntityTableExpressionBuilder removeRelationEntityTableExpression(RelationTableKey relationTableKey) {
//        if (relationTables == null) {
//            return null;
//        }
//        EntityTableExpressionBuilder entityTableExpressionBuilder = relationTables.get(relationTableKey);
//        if (entityTableExpressionBuilder != null) {
//            relationTables.remove(relationTableKey);
//            return entityTableExpressionBuilder;
//        }
//        return null;
//    }

    @Override
    public List<EntityTableExpressionBuilder> getTables() {
        return tables;
    }

    @Override
    public Map<RelationTableKey, EntityTableExpressionBuilder> getRelationTables() {
        if (relationTables == null) {
            relationTables = new LinkedHashMap<>();
        }
        return relationTables;
    }

    @Override
    public boolean hasRelationTables() {
        return relationTables != null && !relationTables.isEmpty();
    }

    @Override
    public void setLogicDelete(boolean logicDelete) {
        if (logicDelete) {
            expressionContext.getBehavior().addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        } else {
            expressionContext.getBehavior().removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }
    }
}
