package com.easy.query.sql.starter;

import com.easy.query.core.abstraction.*;
import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.api.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.api.def.DefaultEasySqlApiFactory;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.DefaultEasyExecutor;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.types.DefaultJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.config.NameConversion;
import com.easy.query.core.config.UnderlinedNameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.DefaultEntityMetadataManager;
import com.easy.query.mysql.MySQLSqlExpressionFactory;
import com.easy.query.mysql.config.MySQLDialect;
import com.easy.query.sql.starter.config.EasyQueryProperties;
import com.easy.query.sql.starter.logging.Slf4jImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @FileName: EasyQueryStarter.java
 * @Description: 文件说明
 * @Date: 2023/3/11 12:47
 * @Created by xuejiaming
 */
@Configuration
@EnableConfigurationProperties(EasyQueryProperties.class)
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@ConditionalOnProperty(
        prefix = "easy-query",
        value = {"enable"},
        matchIfMissing = true
)
public class EasyQueryStarterAutoConfiguration {
    public EasyQueryStarterAutoConfiguration() {
        LogFactory.useCustomLogging(Slf4jImpl.class);
//        LogFactory.useStdOutLogging();
    }

    @Bean
    public EasyConnectionManager easyConnectionManager(DataSource dataSource) {
        return new SpringConnectionManager(dataSource);
    }

    @Bean
    public EasyExecutor easyExecutor() {
        return new DefaultEasyExecutor();
    }

    @Bean
    public EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager() {
        return new DefaultJdbcTypeHandlerManager();
    }

    @Bean
    public EasyQueryConfiguration easyQueryConfiguration() {
        EasyQueryConfiguration configuration = new EasyQueryConfiguration();

        NameConversion nameConversion = new UnderlinedNameConversion();
        MySQLDialect sqlDialect = new MySQLDialect();
        configuration.setNameConversion(nameConversion);
        configuration.setDialect(sqlDialect);
        return configuration;
    }

    @Bean
    public EntityMetadataManager entityMetadataManager(EasyQueryConfiguration configuration) {
        return new DefaultEntityMetadataManager(configuration);
    }

    @Bean
    public EasyQueryLambdaFactory easyQueryLambdaFactory() {
        return new DefaultEasyQueryLambdaFactory();
    }

    @Bean
    public EasySqlExpressionFactory easySqlExpressionFactory() {
        return new MySQLSqlExpressionFactory();
    }

    @Bean
    public EasySqlApiFactory easySqlApiFactory(EasySqlExpressionFactory easySqlExpressionFactory) {
        return new DefaultEasySqlApiFactory(easySqlExpressionFactory);
    }

    @Bean
    public EasyQueryRuntimeContext easyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration,
                                                           EntityMetadataManager entityMetadataManager,
                                                           EasyQueryLambdaFactory easyQueryLambdaFactory,
                                                           EasyConnectionManager easyConnectionManager,
                                                           EasyExecutor easyExecutor,
                                                           EasyJdbcTypeHandlerManager easyJdbcTypeHandler,
                                                           EasySqlApiFactory easyQueryableFactory,
                                                           EasySqlExpressionFactory easySqlExpressionFactory) {
        return new DefaultEasyQueryRuntimeContext(
                easyQueryConfiguration,
                entityMetadataManager,
                easyQueryLambdaFactory,
                easyConnectionManager,
                easyExecutor,
                easyJdbcTypeHandler,
                easyQueryableFactory,
                easySqlExpressionFactory
        );
    }

    @Bean
    public EasyQuery easyQuery(EasyQueryRuntimeContext easyQueryRuntimeContext) {
        return new DefaultEasyQuery(easyQueryRuntimeContext);
    }
}