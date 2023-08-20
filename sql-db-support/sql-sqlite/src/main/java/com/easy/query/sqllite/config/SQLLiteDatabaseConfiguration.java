package com.easy.query.sqllite.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.sqllite.expression.SQLiteExpressionFactory;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLLiteDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(Dialect.class, SQLLiteDialect.class);
        services.addService(ExpressionFactory.class, SQLiteExpressionFactory.class);
    }
}