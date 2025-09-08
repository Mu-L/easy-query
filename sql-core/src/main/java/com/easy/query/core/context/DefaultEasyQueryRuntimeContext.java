package com.easy.query.core.context;

//import com.easy.query.core.api.SQLApiFactory;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.api.dynamic.executor.query.WhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.sort.ObjectSortQueryExecutor;
import com.easy.query.core.basic.api.cte.CteTableNamedProvider;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.extension.print.JdbcSQLPrinter;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionPrepareExecutor;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.thread.ShardingExecutorService;
import com.easy.query.core.common.MapColumnNameChecker;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.column2mapkey.Column2MapKeyConversion;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.nameconversion.MapKeyNameConversion;
import com.easy.query.core.datasource.DataSourceManager;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.expression.builder.core.ValueFilterFactory;
import com.easy.query.core.expression.include.IncludeProcessorFactory;
import com.easy.query.core.expression.many2group.SubQueryExtraPredicateProvider;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.predicate.SmartPredicateAnonymousExpressionBuilderProvider;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.include.IncludeParserEngine;
import com.easy.query.core.expression.sql.include.IncludeProvider;
import com.easy.query.core.expression.sql.include.RelationNullValueValidator;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.expression.sql.include.relation.RelationValueFactory;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilderFactory;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.job.EasyTimeJobManager;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.comparer.ShardingComparer;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.core.sql.JdbcSQLExecutor;

/**
 * @author xuejiaming
 * @FileName: DefaultJQDCRuntimeContext.java
 * @Description: 文件说明
 * create time 2023/2/11 13:47
 */
public class DefaultEasyQueryRuntimeContext implements QueryRuntimeContext {
    private final ServiceProvider serviceProvider;
    private final EasyQueryDataSource easyQueryDataSource;
    private final QueryConfiguration easyQueryConfiguration;
    private final EntityMetadataManager entityMetadataManager;
    private final SQLExpressionInvokeFactory easyQueryLambdaFactory;
    private final ConnectionManager easyConnectionManager;
    private final EntityExpressionPrepareExecutor entityExpressionPrepareExecutor;
    //    private final EasyQueryExecutor easyQueryExecutor;
    private final JdbcTypeHandlerManager easyJdbcTypeHandler;
    //    private final SQLApiFactory easyQueryableFactory;
    private final ExpressionBuilderFactory expressionBuilderFactory;
    private final TrackManager trackManager;
    private final EasyPageResultProvider easyPageResultProvider;
    private final ShardingExecutorService easyShardingExecutorService;
    private final ExpressionFactory easyExpressionFactory;
    private final TableRouteManager tableRouteManager;
    private final DataSourceRouteManager dataSourceRouteManager;
    private final ShardingComparer shardingComparer;
    private final ShardingQueryCountManager shardingQueryCountManager;
    private final DataSourceUnitFactory dataSourceUnitFactory;
    private final SQLSegmentFactory sqlSegmentFactory;
    private final SQLClientApiFactory sqlEntityApiFactory;
    private final DataSourceManager dataSourceManager;
    private final EasyTimeJobManager easyTimeJobManager;
    private final IncludeProcessorFactory includeProcessorFactory;
    private final IncludeParserEngine includeParserEngine;
    private final WhereObjectQueryExecutor whereObjectQueryExecutor;
    private final ObjectSortQueryExecutor objectSortQueryExecutor;
    private final JdbcExecutorListener jdbcExecutorListener;
    private final AssertExceptionFactory assertExceptionFactory;
    private final SQLParameterPrintFormat sqlParameterPrintFormat;
    private final SQLFunc sqlFunc;
    private final Column2MapKeyConversion column2MapKeyConversion;
    private final JdbcSQLPrinter jdbcSQLPrinter;
    private final RelationValueFactory relationValueFactory;
    private final RelationValueColumnMetadataFactory relationValueColumnMetadataFactory;
    private final MapColumnNameChecker mapColumnNameChecker;
    private final PropertyDescriptorMatcher propertyDescriptorMatcher;
    private final ValueFilterFactory valueFilterFactory;
    private final EntityMappingRule entityMappingRule;
    private final MigrationsSQLGenerator migrationsSQLGenerator;
    private final CteTableNamedProvider cteTableNamedProvider;
    private final MapKeyNameConversion mapKeyNameConversion;
    private final DatabaseCodeFirst databaseCodeFirst;
    private final IncludeProvider includeProvider;
    private final RelationNullValueValidator relationNullValueValidator;
    private final SQLCaseWhenBuilderFactory sqlCaseWhenBuilderFactory;
    private final JdbcSQLExecutor jdbcSQLExecutor;
    private final SubQueryExtraPredicateProvider subQueryExtraPredicateProvider;
    private final SmartPredicateAnonymousExpressionBuilderProvider smartPredicateAnonymousExpressionBuilderProvider;

    public DefaultEasyQueryRuntimeContext(ServiceProvider serviceProvider,
                                          EasyQueryDataSource easyQueryDataSource,
                                          QueryConfiguration easyQueryConfiguration,
                                          EntityMetadataManager entityMetadataManager,
                                          SQLExpressionInvokeFactory easyQueryLambdaFactory,
                                          ConnectionManager easyConnectionManager,
                                          EntityExpressionPrepareExecutor entityExpressionPrepareExecutor,
                                          JdbcTypeHandlerManager easyJdbcTypeHandler,
//                                          SQLApiFactory easyQueryableFactory,
                                          ExpressionBuilderFactory expressionBuilderFactory,
                                          TrackManager trackManager,
                                          EasyPageResultProvider easyPageResultProvider,
                                          ShardingExecutorService easyShardingExecutorService,
                                          ExpressionFactory easyExpressionFactory,
                                          TableRouteManager tableRouteManager,
                                          DataSourceRouteManager dataSourceRouteManager,
                                          ShardingComparer shardingComparer,
                                          ShardingQueryCountManager shardingQueryCountManager,
                                          DataSourceUnitFactory dataSourceUnitFactory,
                                          SQLSegmentFactory sqlSegmentFactory,
                                          SQLClientApiFactory sqlEntityApiFactory,
                                          DataSourceManager dataSourceManager,
                                          EasyTimeJobManager easyTimeJobManager,
                                          IncludeProcessorFactory includeProcessorFactory,
                                          IncludeParserEngine includeParserEngine,
                                          WhereObjectQueryExecutor whereObjectQueryExecutor,
                                          ObjectSortQueryExecutor objectSortQueryExecutor,
                                          JdbcExecutorListener jdbcExecutorListener,
                                          AssertExceptionFactory assertExceptionFactory,
                                          SQLParameterPrintFormat sqlParameterPrintFormat,
                                          SQLFunc sqlFunc,
                                          Column2MapKeyConversion column2MapKeyConversion,
                                          JdbcSQLPrinter jdbcSQLPrinter,
                                          RelationValueFactory relationValueFactory,
                                          RelationValueColumnMetadataFactory relationValueColumnMetadataFactory,
                                          MapColumnNameChecker mapColumnNameChecker,
                                          PropertyDescriptorMatcher propertyDescriptorMatcher,
                                          ValueFilterFactory valueFilterFactory,
                                          EntityMappingRule entityMappingRule,
                                          MigrationsSQLGenerator migrationsSQLGenerator,
                                          CteTableNamedProvider cteTableNamedProvider,
                                          MapKeyNameConversion mapKeyNameConversion,
                                          DatabaseCodeFirst databaseCodeFirst,
                                          IncludeProvider includeProvider,
                                          RelationNullValueValidator relationNullValueValidator,
                                          SQLCaseWhenBuilderFactory sqlCaseWhenBuilderFactory,
                                          JdbcSQLExecutor jdbcSQLExecutor,
                                          SubQueryExtraPredicateProvider subQueryExtraPredicateProvider,
                                          SmartPredicateAnonymousExpressionBuilderProvider smartPredicateAnonymousExpressionBuilderProvider) {
        this.serviceProvider = serviceProvider;
        this.easyQueryDataSource = easyQueryDataSource;
        this.easyQueryConfiguration = easyQueryConfiguration;
        this.entityMetadataManager = entityMetadataManager;
        this.easyQueryLambdaFactory = easyQueryLambdaFactory;
        this.easyConnectionManager = easyConnectionManager;
        this.entityExpressionPrepareExecutor = entityExpressionPrepareExecutor;
        this.easyJdbcTypeHandler = easyJdbcTypeHandler;
//        this.easyQueryableFactory = easyQueryableFactory;
        this.expressionBuilderFactory = expressionBuilderFactory;
        this.trackManager = trackManager;
        this.easyPageResultProvider = easyPageResultProvider;
        this.easyShardingExecutorService = easyShardingExecutorService;
        this.easyExpressionFactory = easyExpressionFactory;
        this.tableRouteManager = tableRouteManager;
        this.dataSourceRouteManager = dataSourceRouteManager;
        this.shardingComparer = shardingComparer;
        this.shardingQueryCountManager = shardingQueryCountManager;
        this.dataSourceUnitFactory = dataSourceUnitFactory;
        this.sqlSegmentFactory = sqlSegmentFactory;
        this.sqlEntityApiFactory = sqlEntityApiFactory;
        this.dataSourceManager = dataSourceManager;
        this.easyTimeJobManager = easyTimeJobManager;
        this.includeProcessorFactory = includeProcessorFactory;
        this.includeParserEngine = includeParserEngine;
        this.whereObjectQueryExecutor = whereObjectQueryExecutor;
        this.objectSortQueryExecutor = objectSortQueryExecutor;
        this.jdbcExecutorListener = jdbcExecutorListener;
        this.assertExceptionFactory = assertExceptionFactory;
        this.sqlParameterPrintFormat = sqlParameterPrintFormat;
        this.sqlFunc = sqlFunc;
        this.column2MapKeyConversion = column2MapKeyConversion;
        this.jdbcSQLPrinter = jdbcSQLPrinter;
        this.relationValueFactory = relationValueFactory;
        this.relationValueColumnMetadataFactory = relationValueColumnMetadataFactory;
        this.mapColumnNameChecker = mapColumnNameChecker;
        this.propertyDescriptorMatcher = propertyDescriptorMatcher;
        this.valueFilterFactory = valueFilterFactory;
        this.entityMappingRule = entityMappingRule;
        this.migrationsSQLGenerator = migrationsSQLGenerator;
        this.cteTableNamedProvider = cteTableNamedProvider;
        this.mapKeyNameConversion = mapKeyNameConversion;
        this.databaseCodeFirst = databaseCodeFirst;
        this.includeProvider = includeProvider;
        this.relationNullValueValidator = relationNullValueValidator;
        this.sqlCaseWhenBuilderFactory = sqlCaseWhenBuilderFactory;
        this.jdbcSQLExecutor = jdbcSQLExecutor;
        this.subQueryExtraPredicateProvider = subQueryExtraPredicateProvider;
        this.smartPredicateAnonymousExpressionBuilderProvider = smartPredicateAnonymousExpressionBuilderProvider;
    }

    @Override
    public <T> T getService(Class<T> serviceType) {
        return serviceProvider.getService(serviceType);
    }

    @Override
    public EasyQueryDataSource getEasyQueryDataSource() {
        return easyQueryDataSource;
    }

    @Override
    public QueryConfiguration getQueryConfiguration() {
        return easyQueryConfiguration;
    }

    @Override
    public EntityMetadataManager getEntityMetadataManager() {
        return entityMetadataManager;
    }

    @Override
    public SQLExpressionInvokeFactory getSQLExpressionInvokeFactory() {
        return easyQueryLambdaFactory;
    }

    @Override
    public ConnectionManager getConnectionManager() {
        return easyConnectionManager;
    }

    @Override
    public EntityExpressionPrepareExecutor getEntityExpressionPrepareExecutor() {
        return entityExpressionPrepareExecutor;
    }

    @Override
    public JdbcTypeHandlerManager getJdbcTypeHandlerManager() {
        return easyJdbcTypeHandler;
    }

//    @Override
//    public SQLApiFactory getSQLApiFactory() {
//        return easyQueryableFactory;
//    }

    @Override
    public SQLClientApiFactory getSQLClientApiFactory() {
        return sqlEntityApiFactory;
    }

    @Override
    public ExpressionBuilderFactory getExpressionBuilderFactory() {
        return expressionBuilderFactory;
    }

    @Override
    public ExpressionFactory getExpressionFactory() {
        return easyExpressionFactory;
    }

    @Override
    public TrackManager getTrackManager() {
        return trackManager;
    }

    @Override
    public EasyPageResultProvider getEasyPageResultProvider() {
        return easyPageResultProvider;
    }

    @Override
    public ShardingExecutorService getShardingExecutorService() {
        return easyShardingExecutorService;
    }

    @Override
    public TableRouteManager getTableRouteManager() {
        return tableRouteManager;
    }

    @Override
    public DataSourceRouteManager getDataSourceRouteManager() {
        return dataSourceRouteManager;
    }

    @Override
    public ShardingComparer getShardingComparer() {
        return shardingComparer;
    }

    @Override
    public ShardingQueryCountManager getShardingQueryCountManager() {
        return shardingQueryCountManager;
    }

    @Override
    public DataSourceUnitFactory getDataSourceUnitFactory() {
        return dataSourceUnitFactory;
    }

    @Override
    public SQLSegmentFactory getSQLSegmentFactory() {
        return sqlSegmentFactory;
    }

    @Override
    public DataSourceManager getDataSourceManager() {
        return dataSourceManager;
    }

    @Override
    public EasyTimeJobManager getEasyTimeJobManager() {
        return easyTimeJobManager;
    }

    @Override
    public IncludeProcessorFactory getIncludeProcessorFactory() {
        return includeProcessorFactory;
    }

    @Override
    public IncludeParserEngine getIncludeParserEngine() {
        return includeParserEngine;
    }

    @Override
    public WhereObjectQueryExecutor getWhereObjectQueryExecutor() {
        return whereObjectQueryExecutor;
    }

    @Override
    public ObjectSortQueryExecutor getObjectSortQueryExecutor() {
        return objectSortQueryExecutor;
    }

    @Override
    public SQLFunc fx() {
        return sqlFunc;
    }

    @Override
    public JdbcExecutorListener getJdbcExecutorListener() {
        return jdbcExecutorListener;
    }

    @Override
    public AssertExceptionFactory getAssertExceptionFactory() {
        return assertExceptionFactory;
    }

    @Override
    public SQLParameterPrintFormat getSQLParameterPrintFormat() {
        return sqlParameterPrintFormat;
    }

    @Override
    public Column2MapKeyConversion getColumn2MapKeyConversion() {
        return column2MapKeyConversion;
    }

    @Override
    public JdbcSQLPrinter getJdbcSQLPrinter() {
        return jdbcSQLPrinter;
    }

    @Override
    public RelationValueFactory getRelationValueFactory() {
        return relationValueFactory;
    }

    @Override
    public RelationValueColumnMetadataFactory getRelationValueColumnMetadataFactory() {
        return relationValueColumnMetadataFactory;
    }

    @Override
    public MapColumnNameChecker getMapColumnNameChecker() {
        return mapColumnNameChecker;
    }

    @Override
    public PropertyDescriptorMatcher getPropertyDescriptorMatcher() {
        return propertyDescriptorMatcher;
    }

    @Override
    public ValueFilterFactory getValueFilterFactory() {
        return valueFilterFactory;
    }

    @Override
    public EntityMappingRule getEntityMappingRule() {
        return entityMappingRule;
    }

    @Override
    public MigrationsSQLGenerator getMigrationsSQLGenerator() {
        return migrationsSQLGenerator;
    }

    @Override
    public CteTableNamedProvider getCteTableNamedProvider() {
        return cteTableNamedProvider;
    }

    @Override
    public MapKeyNameConversion getMapKeyNameConversion() {
        return mapKeyNameConversion;
    }

    @Override
    public DatabaseCodeFirst getDatabaseCodeFirst() {
        return databaseCodeFirst;
    }

    @Override
    public IncludeProvider getIncludeProvider() {
        return includeProvider;
    }

    @Override
    public RelationNullValueValidator getRelationNullValueValidator() {
        return relationNullValueValidator;
    }

    @Override
    public SQLCaseWhenBuilderFactory getSQLCaseWhenBuilderFactory() {
        return sqlCaseWhenBuilderFactory;
    }

    @Override
    public JdbcSQLExecutor getJdbcSQLExecutor() {
        return jdbcSQLExecutor;
    }

    @Override
    public SubQueryExtraPredicateProvider getSubQueryExtraPredicateProvider() {
        return subQueryExtraPredicateProvider;
    }

    @Override
    public SmartPredicateAnonymousExpressionBuilderProvider getSmartPredicateAnonymousExpressionBuilderProvider() {
        return smartPredicateAnonymousExpressionBuilderProvider;
    }
}
