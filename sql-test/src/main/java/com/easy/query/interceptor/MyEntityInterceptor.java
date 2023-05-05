package com.easy.query.interceptor;

import com.easy.query.core.basic.plugin.interceptor.EasyEntityInterceptor;
import com.easy.query.core.basic.plugin.interceptor.EasyUpdateSetInterceptor;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.entity.TopicInterceptor;
import com.easy.query.logicdel.CurrentUserHelper;

import java.time.LocalDateTime;

/**
 * create time 2023/4/3 21:13
 * 如果是spring项目添加@Component，如果是非spring项目直接添加到EasyQueryConfiguration.applyEasyInterceptor
 *
 * @author xuejiaming
 */
public class MyEntityInterceptor implements EasyEntityInterceptor, EasyUpdateSetInterceptor {
    @Override
    public void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpressionBuilder, Object entity) {
        TopicInterceptor topicInterceptor = (TopicInterceptor) entity;
        if (topicInterceptor.getCreateTime() == null) {
            topicInterceptor.setCreateTime(LocalDateTime.now());
        }
        if (topicInterceptor.getCreateBy() == null) {
            topicInterceptor.setCreateBy(CurrentUserHelper.getUserId());
        }
        if (topicInterceptor.getUpdateTime() == null) {
            topicInterceptor.setUpdateTime(LocalDateTime.now());
        }
        if (topicInterceptor.getUpdateBy() == null) {
            topicInterceptor.setUpdateBy(CurrentUserHelper.getUserId());
        }
//        if (topicInterceptor.getTenantId() == null) {
//            topicInterceptor.setTenantId(CurrentUserHelper.getTenantId());
//        }
    }

    @Override
    public void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, Object entity) {

        TopicInterceptor topicInterceptor = (TopicInterceptor) entity;
        topicInterceptor.setUpdateTime(LocalDateTime.now());
        topicInterceptor.setUpdateBy(CurrentUserHelper.getUserId());
    }

    @Override
    public String name() {
        return "MyEntityInterceptor";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return TopicInterceptor.class.isAssignableFrom(entityClass);
    }

    @Override
    public void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, SqlColumnSetter<Object> sqlColumnSetter) {
        String updateBy = "updateBy";//属性名用来动态创建lambda
        String updateTime = "updateTime";//属性名用来动态创建lambda
        //是否已经set了
        if(!entityUpdateExpressionBuilder.getSetColumns().containsOnce(entityClass,updateBy)){
            String userId = CurrentUserHelper.getUserId();
            //获取updateBy属性的lambda表达式
            Property<Object, ?> propertyLambda = EasyUtil.getPropertyGetterLambda(entityClass, updateBy, String.class);
            sqlColumnSetter.set(propertyLambda,userId);
        }
        if(!entityUpdateExpressionBuilder.getSetColumns().containsOnce(entityClass,updateTime)){
            //获取updateTime属性的lambda表达式
            Property<Object, ?> propertyLambda = EasyUtil.getPropertyGetterLambda(entityClass, updateTime, LocalDateTime.class);
            sqlColumnSetter.set(propertyLambda,LocalDateTime.now());
        }
    }
}
