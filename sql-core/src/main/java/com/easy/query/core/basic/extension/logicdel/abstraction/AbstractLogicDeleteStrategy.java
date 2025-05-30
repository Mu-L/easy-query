package com.easy.query.core.basic.extension.logicdel.abstraction;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.metadata.LogicDeleteMetadata;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Set;

/**
 * 逻辑删除抽象类
 */
public abstract class AbstractLogicDeleteStrategy implements LogicDeleteStrategy {

    @Override
    public LogicDeleteMetadata configureBuild(LogicDeleteBuilder builder) {
        Set<Class<?>> allowTypes = allowedPropertyTypes();
        if (allowTypes == null || allowTypes.isEmpty()) {
            throw new EasyQueryException("plz set expectPropertyTypes values");
        }
        String propertyName = builder.getPropertyName();
        Class<?> propertyType = builder.getPropertyType();
        if (!allowTypes.contains(propertyType)) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(builder.getEntityClass()) + "." + propertyName + " logic delete not support, property type not allowed");
        }
        SQLActionExpression1<WherePredicate<Object>> predicateFilterExpression = getPredicateFilterExpression(builder, propertyName);
        SQLActionExpression1<ColumnSetter<Object>> deletedSQLExpression = getDeletedSQLExpression(builder, propertyName);
        return builder.build(predicateFilterExpression, deletedSQLExpression);
    }

    protected abstract SQLActionExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName);

    /**
     *
     * 错误的用法
     * <pre>{@code
     *     long now = LocalDateTime.now();
     *     return o->o.set(lambdaProperty, now);
     * }</pre>
     * 正确的用法
     * <pre>{@code
     *     return o->o.set(lambdaProperty, LocalDateTime.now());
     * }</pre>
     *
     * 因为局部变量如果是被先提取那么他就是一个确认值,因为lambda具有延迟执行的特性所以必须要在执行时获取对应的值
     * 包括线程上下文的 ThreadLocal 也必须要在lambda内获取而不是提前获取
     * @param builder
     * @param propertyName
     * @return
     */
    protected abstract SQLActionExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName);
}
