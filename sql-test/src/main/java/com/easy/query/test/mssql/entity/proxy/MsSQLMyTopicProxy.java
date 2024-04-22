package com.easy.query.test.mssql.entity.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.test.mssql.entity.MsSQLMyTopic;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.columns.SQLNumberColumn;
import com.easy.query.core.proxy.columns.SQLDateTimeColumn;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 * 如果出现属性冲突请使用@ProxyProperty进行重命名
 *
 * @author easy-query
 */
public class MsSQLMyTopicProxy extends AbstractProxyEntity<MsSQLMyTopicProxy, MsSQLMyTopic> {

    private static final Class<MsSQLMyTopic> entityClass = MsSQLMyTopic.class;

    public static MsSQLMyTopicProxy createTable() {
        return new MsSQLMyTopicProxy();
    }

    public MsSQLMyTopicProxy() {
    }

    /**
     * {@link MsSQLMyTopic#getId}
     */
    public SQLStringColumn<MsSQLMyTopicProxy, java.lang.String> id() {
        return getStringColumn("id", java.lang.String.class);
    }

    /**
     * {@link MsSQLMyTopic#getStars}
     */
    public SQLNumberColumn<MsSQLMyTopicProxy, java.lang.Integer> stars() {
        return getNumberColumn("stars", java.lang.Integer.class);
    }

    /**
     * {@link MsSQLMyTopic#getTitle}
     */
    public SQLStringColumn<MsSQLMyTopicProxy, java.lang.String> title() {
        return getStringColumn("title", java.lang.String.class);
    }

    /**
     * {@link MsSQLMyTopic#getCreateTime}
     */
    public SQLDateTimeColumn<MsSQLMyTopicProxy, java.time.LocalDateTime> createTime() {
        return getDateTimeColumn("createTime", java.time.LocalDateTime.class);
    }


    @Override
    public Class<MsSQLMyTopic> getEntityClass() {
        return entityClass;
    }


    /**
     * 数据库列的简单获取
     *
     * @return
     */
    public MsSQLMyTopicProxyFetcher FETCHER = new MsSQLMyTopicProxyFetcher(this, null, SQLSelectAsExpression.empty);


    public static class MsSQLMyTopicProxyFetcher extends AbstractFetcher<MsSQLMyTopicProxy, MsSQLMyTopic, MsSQLMyTopicProxyFetcher> {

        public MsSQLMyTopicProxyFetcher(MsSQLMyTopicProxy proxy, MsSQLMyTopicProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }


        /**
         * {@link MsSQLMyTopic#getId}
         */
        public MsSQLMyTopicProxyFetcher id() {
            return add(getProxy().id());
        }

        /**
         * {@link MsSQLMyTopic#getStars}
         */
        public MsSQLMyTopicProxyFetcher stars() {
            return add(getProxy().stars());
        }

        /**
         * {@link MsSQLMyTopic#getTitle}
         */
        public MsSQLMyTopicProxyFetcher title() {
            return add(getProxy().title());
        }

        /**
         * {@link MsSQLMyTopic#getCreateTime}
         */
        public MsSQLMyTopicProxyFetcher createTime() {
            return add(getProxy().createTime());
        }


        @Override
        protected MsSQLMyTopicProxyFetcher createFetcher(MsSQLMyTopicProxy cp, AbstractFetcher<MsSQLMyTopicProxy, MsSQLMyTopic, MsSQLMyTopicProxyFetcher> prev, SQLSelectAsExpression sqlSelectExpression) {
            return new MsSQLMyTopicProxyFetcher(cp, this, sqlSelectExpression);
        }
    }

}
