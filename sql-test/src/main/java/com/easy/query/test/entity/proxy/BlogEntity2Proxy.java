package com.easy.query.test.entity.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.test.entity.BlogEntity2;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBigDecimalTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBooleanTypeColumn;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 * 如果出现属性冲突请使用@ProxyProperty进行重命名
 *
 * @author easy-query
 */
public class BlogEntity2Proxy extends AbstractProxyEntity<BlogEntity2Proxy, BlogEntity2> {

    private static final Class<BlogEntity2> entityClass = BlogEntity2.class;

    public static final BlogEntity2Proxy TABLE = createTable().createEmpty();

    public static BlogEntity2Proxy createTable() {
        return new BlogEntity2Proxy();
    }

    public BlogEntity2Proxy() {
    }

    /**
     * 标题
     * {@link BlogEntity2#getTitle}
     */
    public SQLStringTypeColumn<BlogEntity2Proxy> title() {
        return getStringTypeColumn("title");
    }

    /**
     * 内容
     * {@link BlogEntity2#getContent}
     */
    public SQLStringTypeColumn<BlogEntity2Proxy> content() {
        return getStringTypeColumn("content");
    }

    /**
     * 博客链接
     * {@link BlogEntity2#getUrl}
     */
    public SQLStringTypeColumn<BlogEntity2Proxy> url() {
        return getStringTypeColumn("url");
    }

    /**
     * 点赞数
     * {@link BlogEntity2#getStar}
     */
    public SQLBigDecimalTypeColumn<BlogEntity2Proxy> star() {
        return getBigDecimalTypeColumn("star");
    }

    /**
     * {@link BlogEntity2#getId}
     */
    public SQLStringTypeColumn<BlogEntity2Proxy> id() {
        return getStringTypeColumn("id");
    }

    /**
     * 创建时间;创建时间
     * {@link BlogEntity2#getCreateTime}
     */
    public SQLLocalDateTimeTypeColumn<BlogEntity2Proxy> createTime() {
        return getLocalDateTimeTypeColumn("createTime");
    }

    /**
     * 修改时间;修改时间
     * {@link BlogEntity2#getUpdateTime}
     */
    public SQLLocalDateTimeTypeColumn<BlogEntity2Proxy> updateTime() {
        return getLocalDateTimeTypeColumn("updateTime");
    }

    /**
     * 创建人;创建人
     * {@link BlogEntity2#getCreateBy}
     */
    public SQLStringTypeColumn<BlogEntity2Proxy> createBy() {
        return getStringTypeColumn("createBy");
    }

    /**
     * 修改人;修改人
     * {@link BlogEntity2#getUpdateBy}
     */
    public SQLStringTypeColumn<BlogEntity2Proxy> updateBy() {
        return getStringTypeColumn("updateBy");
    }

    /**
     * 是否删除;是否删除
     * {@link BlogEntity2#getDeleted}
     */
    public SQLBooleanTypeColumn<BlogEntity2Proxy> deleted() {
        return getBooleanTypeColumn("deleted");
    }


    @Override
    public Class<BlogEntity2> getEntityClass() {
        return entityClass;
    }


    /**
     * 数据库列的简单获取
     *
     * @return
     */
    public BlogEntity2ProxyFetcher FETCHER = new BlogEntity2ProxyFetcher(this, null, SQLSelectAsExpression.empty);


    public static class BlogEntity2ProxyFetcher extends AbstractFetcher<BlogEntity2Proxy, BlogEntity2, BlogEntity2ProxyFetcher> {

        public BlogEntity2ProxyFetcher(BlogEntity2Proxy proxy, BlogEntity2ProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }


        /**
         * 标题
         * {@link BlogEntity2#getTitle}
         */
        public BlogEntity2ProxyFetcher title() {
            return add(getProxy().title());
        }

        /**
         * 内容
         * {@link BlogEntity2#getContent}
         */
        public BlogEntity2ProxyFetcher content() {
            return add(getProxy().content());
        }

        /**
         * 博客链接
         * {@link BlogEntity2#getUrl}
         */
        public BlogEntity2ProxyFetcher url() {
            return add(getProxy().url());
        }

        /**
         * 点赞数
         * {@link BlogEntity2#getStar}
         */
        public BlogEntity2ProxyFetcher star() {
            return add(getProxy().star());
        }

        /**
         * {@link BlogEntity2#getId}
         */
        public BlogEntity2ProxyFetcher id() {
            return add(getProxy().id());
        }

        /**
         * 创建时间;创建时间
         * {@link BlogEntity2#getCreateTime}
         */
        public BlogEntity2ProxyFetcher createTime() {
            return add(getProxy().createTime());
        }

        /**
         * 修改时间;修改时间
         * {@link BlogEntity2#getUpdateTime}
         */
        public BlogEntity2ProxyFetcher updateTime() {
            return add(getProxy().updateTime());
        }

        /**
         * 创建人;创建人
         * {@link BlogEntity2#getCreateBy}
         */
        public BlogEntity2ProxyFetcher createBy() {
            return add(getProxy().createBy());
        }

        /**
         * 修改人;修改人
         * {@link BlogEntity2#getUpdateBy}
         */
        public BlogEntity2ProxyFetcher updateBy() {
            return add(getProxy().updateBy());
        }

        /**
         * 是否删除;是否删除
         * {@link BlogEntity2#getDeleted}
         */
        public BlogEntity2ProxyFetcher deleted() {
            return add(getProxy().deleted());
        }


        @Override
        protected BlogEntity2ProxyFetcher createFetcher(BlogEntity2Proxy cp, AbstractFetcher<BlogEntity2Proxy, BlogEntity2, BlogEntity2ProxyFetcher> prev, SQLSelectAsExpression sqlSelectExpression) {
            return new BlogEntity2ProxyFetcher(cp, this, sqlSelectExpression);
        }
    }

}
