package com.easy.query.core.basic.api.select;

import com.easy.query.core.basic.api.select.executor.ResultSetAble;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.easy.query.core.basic.api.select.executor.Fillable;
import com.easy.query.core.basic.api.select.executor.MapAble;
import com.easy.query.core.basic.api.select.executor.QueryExecutable;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.exception.EasyQueryFirstNotNullException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.exception.EasyQuerySingleNotNullException;
import com.easy.query.core.expression.lambda.SQLConsumer;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.fill.FillExpression;
import com.easy.query.core.expression.sql.fill.FillParams;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * create time 2023/3/3 16:30
 * 提供的查询基础表达式接口
 *
 * @author xuejiaming
 */
public interface Query<T> extends QueryAvailable<T>, QueryExecutable<T>, MapAble<T>, Fillable<T>, ResultSetAble, RuntimeContextAvailable {

    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    @NotNull
    Query<T> cloneQueryable();

    /**
     * 生成with cte as临时表可复用
     *
     * @return 返回表达式
     */
    @NotNull
    default Query<T> toCteAs() {
        return toCteAs(getRuntimeContext().getCteTableNamedProvider().getDefaultCteTableName(queryClass()));
    }

    /**
     * 生成with cte as临时表可复用
     *
     * @param tableName 手动指定表名
     * @return 返回表达式
     */
    @NotNull
    default Query<T> toCteAs(String tableName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 当前eq的运行时上下文
     *
     * @return 运行时上下文
     */
    @NotNull
    @Override
    default QueryRuntimeContext getRuntimeContext() {
        return getSQLEntityExpressionBuilder().getRuntimeContext();
    }

    /**
     * 当前的查询表达式
     *
     * @return
     */
    @NotNull
    EntityQueryExpressionBuilder getSQLEntityExpressionBuilder();

    /**
     * 设置column所有join表都会生效
     * queryable.select(" t.name,t.age ")通过字符串实现要查询的列
     *
     * @param columns
     * @return
     */
    @NotNull
    Query<T> select(@NotNull String columns);

    /**
     * 返回执行sql
     *
     * @return
     */
    @NotNull
    default String toSQL() {
        return toSQL(queryClass());
    }

    /**
     * 传入生成sql的上下文用来获取生成sql后的表达式内部的参数
     *
     * @param toSQLContext
     * @return
     */
    @NotNull
    default String toSQL(@NotNull ToSQLContext toSQLContext) {
        return toSQL(queryClass(), toSQLContext);
    }

    /**
     * 传入生成sql的上下文用来获取生成sql后的表达式内部的参数
     *
     * @return 包含sql和sql结果比如参数
     */
    @NotNull
    default ToSQLResult toSQLResult() {
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(getSQLEntityExpressionBuilder().getExpressionContext().getTableContext());
        String sql = toSQL(queryClass(), toSQLContext);
        return new ToSQLResult(sql, toSQLContext);
    }

    /**
     * 返回执行sql
     *
     * @param resultClass
     * @param <TR>
     * @return
     */
    @NotNull
    default <TR> String toSQL(@NotNull Class<TR> resultClass) {
        TableContext tableContext = getSQLEntityExpressionBuilder().getExpressionContext().getTableContext();
        return toSQL(resultClass, DefaultToSQLContext.defaultToSQLContext(tableContext));
    }

    @NotNull
    <TR> String toSQL(@NotNull Class<TR> resultClass,@NotNull ToSQLContext toSQLContext);

    /**
     * 返回long类型的数量结果
     * eg. SELECT  COUNT(*)  FROM table t [WHERE t.`columns` = ?]
     *
     * @return
     */
    long count();

    /**
     * 返回int类型的数量结果
     * eg. SELECT  COUNT(*)  FROM table t [WHERE t.`columns` = ?]
     *
     * @return
     */

    default int intCount() {
        return (int) count();
    }

    /**
     * 判断是否存在
     * eg. SELECT  1  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @return 如果有行数那么就就是返回true表示存在，否则返回false表示不存在
     */
    boolean any();

    default void required() {
        required(null, null);
    }

    default void required(@Nullable String msg) {
        required(() -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createRequiredException(this, msg, null));
    }

    default void required(@Nullable String msg,@Nullable String code) {
        required(() -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createRequiredException(this, msg, code));
    }

    default void required(@NotNull Supplier<RuntimeException> throwFunc) {
        boolean any = any();
        if (!any) {
            throw throwFunc.get();
        }
    }

    /**
     * 当未查询到结果返回null
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @return
     */
    default @Nullable T firstOrNull() {
        return firstOrNull(queryClass());
    }

    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?] LIMIT 1
     *
     * @param msg
     * @param code
     * @return
     */
    default @NotNull T firstNotNull(@Nullable String msg,@Nullable String code) {
        return firstNotNull(queryClass(), msg, code);
    }

    default <TR> @NotNull TR firstNotNull(@NotNull Class<TR> resultClass,@Nullable String msg,@Nullable String code) {
        return firstNotNull(resultClass, () -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createFirstNotNullException(this, msg, code));
    }

    default @NotNull T firstNotNull(@NotNull Supplier<RuntimeException> throwFunc) {
        return firstNotNull(queryClass(), throwFunc);
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     *
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    default @Nullable T singleOrNull() {
        return singleOrNull(queryClass());
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * 返回值包装成 Optional 以便后续调用
     *
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    default @NotNull Optional<T> singleOptional() {
        return Optional.ofNullable(singleOrNull(queryClass()));
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * 查询出来的结果值如果为 null 则返回默认值
     *
     * @param defaultValue 默认值， 当查询结果为 null 时返回默认值
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    default @NotNull T singleOrDefault(@NotNull T defaultValue) {
        T singleOrNull = singleOrNull(queryClass());
        return singleOrNull == null ? defaultValue : singleOrNull;
    }

    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * 查询出来的结果值如果经 usingDefault 判断为 true 则返回 defaultValue
     *
     * @param defaultValue 默认值， 当 usingDefault.test(singleOrNullValue) 为 true 时返回默认值
     * @param usingDefault 判断是否使用默认值
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     */
    @Nullable
    default T singleOrDefault(@NotNull T defaultValue, @Nullable Predicate<T> usingDefault) {
        T singleOrNullValue = singleOrNull(queryClass());
        return (usingDefault != null && usingDefault.test(singleOrNullValue)) ? defaultValue : singleOrNullValue;
    }


    /**
     * 返回数据且断言至多一条数据,如果大于一条数据将会抛出 {@link EasyQuerySingleMoreElementException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param msg
     * @param code
     * @return
     * @throws EasyQuerySingleMoreElementException 如果大于一条数据
     * @throws EasyQuerySingleNotNullException     如果查询不到数据
     */
    @NotNull
    default T singleNotNull(@Nullable String msg,@Nullable String code) {
        return singleNotNull(queryClass(), msg, code);
    }
    @NotNull
    default <TR> TR singleNotNull(@NotNull Class<TR> resultClass,@Nullable String msg,@Nullable String code) {
        return singleNotNull(resultClass, () -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createSingleNotNullException(this, msg, code));
    }
    @NotNull
    default T singleNotNull(@NotNull Supplier<RuntimeException> throwFunc) {
        return singleNotNull(queryClass(), throwFunc);
    }


    /**
     * 当未查询到结果 将会抛出 {@link EasyQueryFirstNotNullException}
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     * <blockquote><pre>
     * {@code
     *
     * @EasyAssertMessage(
     *         notNull = "未找到主题信息"
     * )
     * public class Topic{}
     *
     *
     * @EasyAssertMessage(
     *         //notNull = "未找到主题信息",
     *         findNotNull = "未找到主题信息",
     *         firstNotNull = "未找到主题信息",
     *         singleNotNull = "未找到主题信息",
     *         singleMoreThan = "找到多条主题信息"
     * )
     * public class Topic{}
     *                    }
     * </pre></blockquote>
     *
     * @param id
     * @param msg
     * @param code
     * @return 返回一个不能为空的结果
     * @throws com.easy.query.core.exception.EasyQueryMultiPrimaryKeyException 如果存在多个主键
     * @throws com.easy.query.core.exception.EasyQueryNoPrimaryKeyException    如果没有主键
     * @throws com.easy.query.core.exception.EasyQueryFindNotNullException     可以通过 {@link AssertExceptionFactory#createFindNotNullException(Query, String, String)} 自定义
     */
    @NotNull
    default T findNotNull(@NotNull Object id,@Nullable String msg,@Nullable String code) {
        return findNotNull(id, () -> getSQLEntityExpressionBuilder().getRuntimeContext().getAssertExceptionFactory().createFindNotNullException(this, msg, code));
    }

    /**
     * 返回所有的查询结果集
     * eg. SELECT  projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @return 获取查询结果集, 如果查询不到结果则返回 {@code new ArrayList<>(0)}
     */
    @NotNull
    default List<T> toList() {
        return toList(queryClass(), queryEntityMetadata());
    }


    /**
     * 可迭代的流式结果集
     *
     * <blockquote><pre>
     *     {@code
     *
     * try(JdbcStreamResult<BlogEntity> streamResult = easyQuery.queryable(BlogEntity.class).toStreamResult()){
     *
     *             int i = 0;
     *             for (BlogEntity blog : streamResult.getStreamIterable()) {
     *                 String indexStr = String.valueOf(i);
     *                 Assert.assertEquals(indexStr, blog.getId());
     *                 Assert.assertEquals(indexStr, blog.getCreateBy());
     *                 Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
     *                 Assert.assertEquals(indexStr, blog.getUpdateBy());
     *                 i++;
     *             }
     *         } catch (SQLException e) {
     *             throw new RuntimeException(e);
     *         }
     * </pre></blockquote>
     *
     * @return
     */
    @NotNull
    default JdbcStreamResult<T> toStreamResult(@NotNull SQLConsumer<Statement> configurer) {
        return toStreamResult(queryClass(), configurer);
    }

    /**
     * 去重
     * eg. SELECT DISTINCT projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @return
     */
    @NotNull
    default Query<T> distinct() {
        return distinct(true);
    }


    /**
     * 去重 {@param condition} 为true就使用distinct,false则不使用
     * eg. SELECT DISTINCT projects  FROM table t [WHERE t.`columns` = ?]
     *
     * @param condition
     * @return
     */
    @NotNull
    Query<T> distinct(boolean condition);

    @NotNull
    default Query<T> limit(long rows) {
        return limit(true, rows);
    }

    @NotNull
    default Query<T> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @NotNull
    default Query<T> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @NotNull
    Query<T> limit(boolean condition, long offset, long rows);

    @NotNull
    Query<T> asTracking();

    @NotNull
    Query<T> asNoTracking();

    @NotNull
    Query<T> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @NotNull
    Query<T> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @NotNull
    Query<T> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * <blockquote><pre>
     *     {@code
     *          List<Province> provinces =  easyQuery.queryable(Province.class)
     *                 .fillMany(()->{
     *                     return easyQuery.queryable(City.class).where(c -> c.eq(City::getCode, "3306"));
     *                 },"provinceCode", "code", (x, y) -> {
     *                     x.setCities(new ArrayList<>(y));
     *                 },false).toList();
     *      }
     * </pre></blockquote>
     *
     * @param fillSetterExpression
     * @param targetProperty
     * @param selfProperty
     * @param produce
     * @param consumeNull
     * @param <TREntity>
     * @return
     */
    @NotNull
    @Override
    default <TREntity> Query<T> fillMany(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T, Collection<TREntity>> produce, boolean consumeNull) {
        SQLFuncExpression1<FillParams, Query<?>> fillQueryableExpression = EasySQLExpressionUtil.getFillSQLExpression(fillSetterExpression, targetProperty, consumeNull);
        FillExpression fillExpression = new FillExpression(queryClass(), true, targetProperty, selfProperty, fillQueryableExpression);
        fillExpression.setProduceMany(EasyObjectUtil.typeCastNullable(produce));
        getSQLEntityExpressionBuilder().getExpressionContext().getFills().add(fillExpression);
        return this;
    }

    /**
     * <blockquote><pre>
     *     {@code
     *         List<City> cities = easyQuery.queryable(City.class)
     *                 .fillOne(()->{
     *                     return easyQuery.queryable(Province.class);
     *                 },"code","provinceCode", (x, y) -> {
     *                     x.setProvince(y);
     *                 },false)
     *                 .toList();
     *      }
     * </pre></blockquote>
     *
     * @param fillSetterExpression
     * @param targetProperty
     * @param selfProperty
     * @param produce
     * @param consumeNull
     * @param <TREntity>
     * @return
     */
    @NotNull
    @Override
    default <TREntity> Query<T> fillOne(SQLFuncExpression<Query<TREntity>> fillSetterExpression, String targetProperty, String selfProperty, BiConsumer<T, TREntity> produce, boolean consumeNull) {
        SQLFuncExpression1<FillParams, Query<?>> fillQueryableExpression = EasySQLExpressionUtil.getFillSQLExpression(fillSetterExpression, targetProperty, consumeNull);
        FillExpression fillExpression = new FillExpression(queryClass(), false, targetProperty, selfProperty, fillQueryableExpression);
        fillExpression.setProduceOne(EasyObjectUtil.typeCastNullable(produce));
        getSQLEntityExpressionBuilder().getExpressionContext().getFills().add(fillExpression);
        return this;
    }

    //    @Override
//    default  <TREntity> Query<T> fillMany(SQLFuncExpression1<FillSelector, ClientQueryable<TREntity>> fillSetterExpression, String targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
//        SQLFuncExpression1<FillParams, ClientQueryable<?>> fillQueryableExpression = fillParams -> {
//            FillSelectorImpl fillSelector = new FillSelectorImpl(runtimeContext, fillParams);
//            return fillSetterExpression.apply(fillSelector);
//        };
//        FillExpression fillExpression = new FillExpression(queryClass(), true, targetProperty, EasyObjectUtil.typeCastNullable(selfProperty), fillQueryableExpression);
//        fillExpression.setProduceMany(EasyObjectUtil.typeCastNullable(produce));
//        entityQueryExpressionBuilder.getExpressionContext().getFills().add(fillExpression);
//        return this;
//    }
//
//    @Override
//    public <TREntity> Query<T1> fillOne(SQLFuncExpression1<FillSelector, ClientQueryable<TREntity>> fillSetterExpression, String targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce) {
//        SQLFuncExpression1<FillParams, ClientQueryable<?>> fillQueryableExpression = fillParams -> {
//            FillSelectorImpl fillSelector = new FillSelectorImpl(runtimeContext, fillParams);
//            return fillSetterExpression.apply(fillSelector);
//        };
//        FillExpression fillExpression = new FillExpression(queryClass(), false, targetProperty, EasyObjectUtil.typeCastNullable(selfProperty), fillQueryableExpression);
//        fillExpression.setProduceOne(EasyObjectUtil.typeCastNullable(produce));
//        entityQueryExpressionBuilder.getExpressionContext().getFills().add(fillExpression);
//        return this;
//    }
}
