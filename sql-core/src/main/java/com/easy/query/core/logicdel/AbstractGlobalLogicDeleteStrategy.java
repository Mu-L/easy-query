package com.easy.query.core.logicdel;

import com.easy.query.core.abstraction.metadata.LogicDeleteMetadata;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.util.ClassUtil;

import java.util.Set;

/**
 * @FileName: AbstractGlobalLogicDeleteStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/7 08:04
 * @Created by xuejiaming
 */
public abstract class AbstractGlobalLogicDeleteStrategy implements GlobalLogicDeleteStrategy {

    @Override
    public void configure(EntityMetadata entityMetadata, String propertyName, Class<?> propertyType) {
        Set<Class<?>> allowTypes = expectPropertyTypes();
        if(allowTypes==null||allowTypes.isEmpty()){
            throw new EasyQueryException("plz set expectPropertyTypes values");
        }
        if(!allowTypes.contains(propertyType)){
            throw new EasyQueryException(ClassUtil.getSimpleName(entityMetadata.getEntityClass())+"."+propertyName+" logic delete not support, property type not allowed");
        }
        Property<?, ?> lambdaProperty = EasyUtil.getLambdaProperty(entityMetadata.getEntityClass(), propertyName, propertyType);
        SqlExpression<SqlPredicate<?>> queryFilterExpression = getQueryFilterExpression(entityMetadata,lambdaProperty);
        SqlExpression<SqlColumnSetter<?>> deletedSqlExpression = getDeletedSqlExpression(entityMetadata,lambdaProperty);
        entityMetadata.setLogicDeleteMetadata(new LogicDeleteMetadata(queryFilterExpression, deletedSqlExpression));
    }
    protected abstract SqlExpression<SqlPredicate<?>> getQueryFilterExpression(EntityMetadata entityMetadata,Property lambdaProperty);
    protected abstract SqlExpression<SqlColumnSetter<?>> getDeletedSqlExpression(EntityMetadata entityMetadata,Property lambdaProperty);
}