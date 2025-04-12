package com.easy.query.core.expression.implicit;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.PartitionByRelationTableKey;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.include.getter.EqualsDirectToOneGetter;
import com.easy.query.core.expression.include.getter.EqualsManyToManyGetter;
import com.easy.query.core.expression.include.getter.EqualsManyToOneGetter;
import com.easy.query.core.expression.include.getter.EqualsOneToManyGetter;
import com.easy.query.core.expression.include.getter.EqualsOneToOneGetter;
import com.easy.query.core.expression.include.getter.RelationIncludeGetter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * create time 2025/3/19 16:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class GenericEntityRelationToImplicitProvider implements EntityRelationPropertyProvider, EntityRelationToImplicitGroupProvider, EntityRelationToImplicitPartitionByProvider {

    public static final EntityRelationPropertyProvider INSTANCE = new GenericEntityRelationToImplicitProvider();

    @Override
    public TableAvailable toImplicitJoin(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property) {
        return EasyRelationalUtil.getRelationTable(entityExpressionBuilder, leftTable, property);
    }

    @Override
    public <T> ClientQueryable<T> toImplicitSubQuery(EntityExpressionBuilder entityExpressionBuilder,TableAvailable leftTable, NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext) {

        ClientQueryable<?> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getNavigatePropertyType(), runtimeContext);
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            ClientQueryable<?> mappingQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getMappingClass(), runtimeContext);
            clientQueryable.where(x -> {
                x.and(() -> {
                    ClientQueryable<?> subMappingQueryable = mappingQueryable.where(m -> {
                        m.multiEq(true, x, navigateMetadata.getTargetMappingProperties(), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext));
                        m.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getSelfMappingProperties(), navigateMetadata.getSelfPropertiesOrPrimary());
                        navigateMetadata.predicateMappingClassFilterApply(m);
                    }).limit(1);
                    x.exists(subMappingQueryable);
                    navigateMetadata.predicateFilterApply(x);
                });
            });
        } else {
            clientQueryable.where(t -> {
                t.and(() -> {
                    t.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                    navigateMetadata.predicateFilterApply(t);
                });
            });
        }
        return EasyObjectUtil.typeCastNullable(clientQueryable);
    }


    @Override
    public String getName() {
        return "";
    }


    @Override
    public AnonymousManyJoinEntityTableExpressionBuilder toImplicitGroup(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, ManyConfiguration manyConfiguration) {
        return EasyRelationalUtil.getManyJoinRelationTable(entityExpressionBuilder, leftTable, navigateMetadata, manyConfiguration);
    }

    @Override
    public <T1> AnonymousManyJoinEntityTableExpressionBuilder toImplicitPartitionBy(Class<T1> entityClass, EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, int index, QueryRuntimeContext runtimeContext, SQLExpression1<ClientQueryable<T1>> clientQueryableSQLExpression) {
        //获取表达式配置信息
        ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(new DefaultRelationTableKey(leftTable, navigateMetadata.getPropertyName()));
        //创建分区分组查询表达式
        ClientQueryable<?> clientQueryable = createPartitionQueryable(entityClass, entityExpressionBuilder.getRuntimeContext(), navigateMetadata, manyConfiguration, clientQueryableSQLExpression);

        String queryableKey = EasySQLUtil.toQueryableKey(clientQueryable);
        RelationTableKey partitionByRelationTableKey = new PartitionByRelationTableKey(leftTable, navigateMetadata.getPropertyName(), index, queryableKey);

        return EasyRelationalUtil.getManySingleJoinRelationTable(partitionByRelationTableKey, entityExpressionBuilder, leftTable, navigateMetadata, index, clientQueryable);

    }


    private <T1> ClientQueryable<?> createPartitionQueryable(Class<T1> entityClass, QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, ManyConfiguration manyConfiguration, SQLExpression1<ClientQueryable<T1>> clientQueryableSQLExpression) {

        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);

        ClientQueryable<T1> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(entityClass, runtimeContext);
        SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> queryableSQLFuncExpression1 = Optional.ofNullable(manyConfiguration).map(x -> x.getConfigureExpression()).orElse(null);
        if (queryableSQLFuncExpression1 != null) {
            clientQueryable = EasyObjectUtil.typeCastNullable(queryableSQLFuncExpression1.apply(clientQueryable));
        }


        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            clientQueryable.innerJoin(navigateMetadata.getMappingClass(), (target, middle) -> {
                        target.multiEq(true, middle, navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getTargetMappingProperties());
                    })
                    .where((target, middle) -> {
                        navigateMetadata.predicateMappingClassFilterApply(middle);
                        navigateMetadata.predicateFilterApply(target);
                    }).select(Map.class, (target, middle) -> {
                        EntityMetadata middleEntityMetadata = middle.getEntityMetadata();
                        for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                            ColumnMetadata columnMetadata = middleEntityMetadata.getColumnNotNull(selfMappingProperty);
                            middle.columnAs(selfMappingProperty, columnMetadata.getName());
                        }
                    });
        } else {
            clientQueryable.where(t -> {
                navigateMetadata.predicateFilterApply(t);

            });
        }
        clientQueryableSQLExpression.apply(clientQueryable);

        OrderBySQLBuilderSegment order = clientQueryable.getSQLEntityExpressionBuilder().getOrder();
        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        order.copyTo(orderBySQLBuilderSegment);
        order.clear();

        return clientQueryable.select(Map.class, x -> {
            x.columnAll();


            PartitionBySQLFunction partitionBySQLFunction = runtimeContext.fx().rowNumberOver(s -> {
                for (String column : targetPropertiesOrPrimary) {
                    s.column(column);
                }
            });
            if(EasySQLSegmentUtil.isNotEmpty(orderBySQLBuilderSegment)){
                partitionBySQLFunction.addOrder(orderBySQLBuilderSegment);
            }
            x.sqlFuncAs(partitionBySQLFunction, "__row__");

        });
    }

    @Override
    public void relationMultiIdsFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<List<Object>> relationIds) {
        targetWherePredicate.relationIn(targetProps, relationIds);
    }

    @Override
    public void relationMultiIdFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<Object> relationIds) {
        targetWherePredicate.multiEq(true, targetProps, relationIds);
    }


    @Override
    public RelationIncludeGetter getOneToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] selfRelationColumn, Collection<RelationExtraEntity> entities) {
        return new EqualsOneToOneGetter(selfRelationColumn, entities);
    }

    @Override
    public RelationIncludeGetter getDirectToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, List<RelationExtraEntity> includes, List<Object> mappingRow) {
        return new EqualsDirectToOneGetter(runtimeContext, navigateMetadata, includes, mappingRow);
    }

    @Override
    public RelationIncludeGetter getManyToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        return new EqualsManyToOneGetter(targetPropertyNames, includes);
    }

    @Override
    public RelationIncludeGetter getOneToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        return new EqualsOneToManyGetter(navigateMetadata, targetPropertyNames, includes);
    }

    @Override
    public RelationIncludeGetter getManyToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes, List<Object> mappingRows) {
        return new EqualsManyToManyGetter(runtimeContext, navigateMetadata, targetPropertyNames, includes, mappingRows);
    }
}
