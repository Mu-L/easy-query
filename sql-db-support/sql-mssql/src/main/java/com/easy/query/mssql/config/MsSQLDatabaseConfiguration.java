package com.easy.query.mssql.config;

import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.configuration.EasyQueryOptionBuilder;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilderFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.mssql.expression.MsSQLExpressionFactory;
import com.easy.query.mssql.func.MsSQLFuncImpl;
import com.easy.query.mssql.migration.MsSQLDatabaseMigrationProvider;
import com.easy.query.mssql.migration.MsSQLMigrationEntityParser;

/**
 * create time 2023/5/10 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLDatabaseConfiguration implements DatabaseConfiguration {
    @Override
    public void configure(ServiceCollection services) {
        services.addService(SQLKeyword.class, MsSQLSQLKeyword.class);
        services.addService(ExpressionFactory.class, MsSQLExpressionFactory.class);
        services.addService(SQLFunc.class, MsSQLFuncImpl.class);
        services.addService(DatabaseMigrationProvider.class, MsSQLDatabaseMigrationProvider.class);
        services.addService(MigrationEntityParser.class, MsSQLMigrationEntityParser.class);
        services.addService(SQLCaseWhenBuilderFactory.class, MsSQLCaseWhenBuilderFactory.class);
        //https://github.com/microsoft/mssql-jdbc/issues/2262 BigDecimal issue

    }

    @Override
    public void optionConfigure(EasyQueryOptionBuilder easyQueryOptionBuilder) {
        if (easyQueryOptionBuilder.getMaxInClauseSize() == EasyQueryOptionBuilder.MAX_IN_CLAUSE_SIZE) {
            easyQueryOptionBuilder.setMaxInClauseSize(32767);
        }
    }
}
