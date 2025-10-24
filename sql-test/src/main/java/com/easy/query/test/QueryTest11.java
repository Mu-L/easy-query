package com.easy.query.test;

import com.easy.query.api.proxy.base.IntegerProxy;
import com.easy.query.api.proxy.base.LongProxy;
import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.BlogEntityTest;
import com.easy.query.test.dto.BlogEntityTest2;
import com.easy.query.test.dto.TopicSubQueryBlog;
import com.easy.query.test.dto.proxy.BlogEntityTest2Proxy;
import com.easy.query.test.dto.proxy.BlogEntityTestProxy;
import com.easy.query.test.dto.proxy.TopicSubQueryBlogProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeArrayJson;
import com.easy.query.test.entity.TopicTypeArrayJson2;
import com.easy.query.test.entity.TopicTypeJsonValue;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.proxy.BlogEntityVO1Proxy;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/12/29 16:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest11 extends BaseTest {

    @Test
    public void testx() {

//        EntityQueryable<BlogEntityProxy, BlogEntity> where1 = easyEntityQuery.queryable(BlogEntity.class)
//                .where(o -> o.id().eq("1")).toCteAs();
//        List<Topic> x = easyEntityQuery
//                .queryable(Topic.class).where(o -> o.expression().exists(()->{
//                    return where1.where(q -> q.id().eq(o.id()));
//                })).toList();


        EntityQueryable<BlogEntityProxy, BlogEntity> where = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1"));
        List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    o.expression().exists(where.where(q -> q.id().eq(o.id())));
                }).toList();

        EntityQueryable<StringProxy, String> idQuery = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1"))
                .select(o -> new StringProxy(o.id()));

        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().in(idQuery))
                .toList();

        easyEntityQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t1, t2) -> t1.id().eq(t2.id()))
                .where((t1, t2) -> t2.title().isNotNull())
                .groupBy((t1, t2) -> GroupKeys.of(t2.id()))
                .select(g -> new BlogEntityProxy().adapter(r -> {
                    r.id().set(g.key1());
                    r.score().set(g.groupTable().t2.score().sum());
                }))
                .toPageResult(1, 20);

        EntityQueryable<TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
//                .filterConfigure()
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy().selectExpression(o.id(), o.title()));

        EntityQueryable<TopicProxy, Topic> query2 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy());//这样等于selectAll


        Query<Topic> query1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(o -> new TopicProxy().selectExpression(o.id(), o.title()));


        List<Topic> list = query.leftJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t, t1) -> {
                    t1.id().eq("123");
                    t.id().eq("456");
                }).toList();

    }

    @Test
    public void testx9() {
        {
            EntityQueryable<TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                    .limit(100);
            EntityQueryable2<TopicProxy, Topic, BlogEntityProxy, BlogEntity> join = query.select(o -> o.FETCHER.id().stars().fetchProxy())
                    .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()));
            EntityQueryable2<BlogEntityTestProxy, BlogEntityTest, BlogEntityProxy, BlogEntity> join2 = join.select((t, t1) -> {
                        BlogEntityTestProxy r = new BlogEntityTestProxy();
                        r.url().set(t.id());
                        r.title().set(t1.title());
                        return r;
                    })
                    .innerJoin(BlogEntity.class, (t, t1) -> {
                        t.url().eq(t1.id());
                    });
            List<BlogEntity> list = join2.select(BlogEntity.class, (t, t1) -> {
                return Select.of(
                        t.url().as(BlogEntityTest::getUrl),
                        t.title().as(BlogEntity::getContent)
                );
            }).toList();

//            var query = rep.GetLambdaQuery().Take(100);
//            var join = query.Select(b => new { a1 = b.Id, a2 = b.F_String }).Join<TestEntityItem>((a, b) => a.a1 == b.TestEntityId);//第一次关联
//            var join2 = join.Select((a, b) => new { a3 = a.a1, a4 = b.Name })
//                .Join<TestEntity>((a, b) => a.a3 == b.Id);//第二次关联
//            join2.Select((a, b) => new
//            {
//                a.a4,
//                        b.Id
//            });

        }
        {
            List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class).limit(100)
                    .select(o -> Select.DRAFT.of(o.id(), o.stars()))
                    .leftJoin(BlogEntity.class, (t, t1) -> t.value1().eq(t1.id()))
                    .select((a, b) -> Select.DRAFT.of(a.value1(), b.url()))
                    .innerJoin(BlogEntity.class, (t, t1) -> t.value2().eq(t1.id()))
                    .select((a, b) -> Select.DRAFT.of(a.value1(), b.url())).toList();
        }
    }

    @Test
    public void testx1() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> {
                        o.createTime().format("yyyy-MM-dd").likeMatchLeft("2023");
                        o.expression().exists(
                                easyEntityQuery.queryable(Topic.class)
                                        .where(x -> x.id().eq(o.id()))
                        );
                    })
                    .select(o -> new BlogEntityProxy().adapter(r -> {
                        PropTypeColumn<BigDecimal> integerPropTypeColumn = o.expression().sqlSegment("1").asAnyType(BigDecimal.class);
                        r.score().set(integerPropTypeColumn);
                    })).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT 1 AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND DATE_FORMAT(t.`create_time`,'%Y-%m-%d') LIKE ? AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),2023%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            EntityQueryable<TopicProxy, Topic> sql = easyEntityQuery
                    .queryable(Topic.class)
                    .where(o -> o.id().eq("3"));

            List<BlogEntity> topics = easyEntityQuery
                    .queryable(BlogEntity.class)
                    .leftJoin(sql, (a, b) -> a.id().eq(b.id()))
                    .where((a, b) -> {
                        a.id().isNotNull();
                        b.id().isNotNull();
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) t2 ON t.`id` = t2.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t2.`id` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String, Object>> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(f -> f.id().eq("1"))
                    .select(s -> new MapProxy().adapter(r -> {
                        r.put("id", s.id());
                        r.put("name", s.stars());
//                        r.put("abc",s.createTime().dayOfWeek());
                    }))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `id`,t.`stars` AS `name` FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String, Object>> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(f -> f.id().eq("1"))
                    .select(s -> new MapProxy().selectAll(s))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String, Object>> list2 = easyEntityQuery.queryable(Topic.class)
                    .where(f -> f.id().eq("1"))
                    .select(s -> new MapProxy())
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void testx2() {
        {

            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("2"))
                    .select(o -> {
                        BlogEntityVO1Proxy r = new BlogEntityVO1Proxy();
                        r.score().set(o.order());
                        return r;
                    })
                    .limit(1).toSQL();
            Assert.assertEquals("SELECT t.`order` AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
        }
        {

            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .where(o -> o.id().eq("2"))
                    .select(o -> {
                        BlogEntityVO1Proxy r = new BlogEntityVO1Proxy();
                        r.score().set(o.order());
                        return r;
                    })
                    .limit(1).toSQL();
            Assert.assertEquals("SELECT t.`order` AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
        }
        {

            EntityQueryable<BlogEntityTest2Proxy, BlogEntityTest2> queryable = easyEntityQuery.queryable(BlogEntity.class)
                    .select(o -> new BlogEntityTest2Proxy().selectAll(o).selectIgnores(o.title()).adapter(r -> {
                        r.url().set(o.url());
                    }));
            String sql1 = queryable.toSQL();
            Assert.assertEquals("SELECT t.`content`,t.`url` AS `my_url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`url` AS `my_url` FROM `t_blog` t WHERE t.`deleted` = ?", sql1);

        }
    }

    @Test
    public void testx3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("2");
                    o.id().eq("3");
                    o.or(() -> {
                        o.id().eq("4");
                        o.id().eq("5");
                    });
                })
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? AND `id` = ? AND (`id` = ? OR `id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2(String),3(String),4(String),5(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TopicSubQueryBlog> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o -> new TopicSubQueryBlogProxy().adapter(r -> {
                    r.selectAll(o);
                    Query<Long> subQuery = easyEntityQuery.queryable(BlogEntity.class).where(x -> x.id().eq(o.id())).selectCount();//count(*)
                    r.blogCount().setSubQuery(subQuery);
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(SELECT COUNT(*) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TopicSubQueryBlog> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().isNotNull())
                .select(o -> new TopicSubQueryBlogProxy().adapter(r -> {
                    r.selectAll(o);
                    EntityQueryable<LongProxy, Long> subQuery = easyEntityQuery.queryable(BlogEntity.class).where(x -> x.id().eq(o.id())).select(x -> new LongProxy(x.star().sum()));//SUM(t1.`star`)
                    r.blogCount().setSubQuery(subQuery);
                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(SELECT SUM(t1.`star`) FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<BlogEntityProxy, BlogEntity> subQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1"));

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.expression().exists(
                        subQueryable.where(q -> q.id().eq(o.id()))
                )).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ? AND t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<BlogEntityProxy, BlogEntity> subQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1"));

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.expression().notExists(subQueryable.where(q -> q.id().eq(o.id())))).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE NOT EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ? AND t1.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx8() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<StringProxy, String> idQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("123"))
                .select(o -> new StringProxy(o.id()));//如果子查询in string那么就需要select string，如果integer那么select要integer 两边需要一致

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().in(idQueryable)).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx10() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<StringProxy, String> idQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("123"))
                .select(o -> new StringProxy(o.id()));//如果子查询in string那么就需要select string，如果integer那么select要integer 两边需要一致

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().notIn(idQueryable)).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` NOT IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx11() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Query<Integer> query = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> o.id().eq("123")).select(o -> new IntegerProxy(o.topicType().asAnyType(Integer.class)));
        List<TopicTypeTest1> list = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.topicType().asAnyType(Integer.class).in(query);
        }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`topic_type`,t.`create_time` FROM `t_topic_type` t WHERE t.`topic_type` IN (SELECT t1.`topic_type` FROM `t_topic_type` t1 WHERE t1.`id` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx12() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Integer> list = easyEntityQuery.queryable(TopicTypeTest1.class).selectColumn(o -> o.topicType().toNumber(Integer.class)).toList();
        Integer integer = list.get(0);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT CAST(t.`topic_type` AS SIGNED) FROM `t_topic_type` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx13() {
        ArrayList<TopicTypeEnum> topicTypeEnums = new ArrayList<>();
        topicTypeEnums.add(TopicTypeEnum.CLASSER);
        topicTypeEnums.add(TopicTypeEnum.STUDENT);
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeTest1> list = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.topicType().in(topicTypeEnums);
        }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE `topic_type` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("9(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx14() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeTest1> list = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.id().subString(1, 2).eq("1");
            o.id().subString(1, 2).isNotNull();
            o.id().subString(1, 2).isBlank();
            o.id().subString(1, 2).isNotBlank();
            o.id().subString(1, 2).isEmpty();
            o.id().subString(1, 2).isNotEmpty();
            o.id().isBlank();
        }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE SUBSTR(`id`,2,2) = ? AND SUBSTR(`id`,2,2) IS NOT NULL AND (SUBSTR(`id`,2,2) IS NULL OR SUBSTR(`id`,2,2) = '' OR LTRIM(SUBSTR(`id`,2,2)) = '') AND (SUBSTR(`id`,2,2) IS NOT NULL AND SUBSTR(`id`,2,2) <> '' AND LTRIM(SUBSTR(`id`,2,2)) <> '') AND (SUBSTR(`id`,2,2) IS NULL OR SUBSTR(`id`,2,2) = '') AND (SUBSTR(`id`,2,2) IS NOT NULL AND SUBSTR(`id`,2,2) <> '') AND (`id` IS NULL OR `id` = '' OR LTRIM(`id`) = '')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx15() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeTest1> list = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.createTime().ge(o.expression().now().plusMonths(-3));
        }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE  `create_time` >= date_add(NOW(), interval (?) month)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("-3(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx16() {

        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 1, 1);
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeTest1> list = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.expression().constant(begin).le(o.createTime());
            o.createTime().le(end);
            o.createTime().le(o.expression().constant(end).plusMonths(-3));
        }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE ? <= `create_time` AND `create_time` <= ? AND  `create_time` <= date_add(?, interval (?) month)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-01-01T01:01(LocalDateTime),2022-01-01T01:01(LocalDateTime),2022-01-01T01:01(LocalDateTime),-3(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx17() {

        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 1, 1);
        List<TopicTypeTest1> list1 = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.expression().constant(end).le(o.expression().constant(end).plusMonths(-3));
        }).toList();
        Assert.assertEquals(0, list1.size());
        List<TopicTypeTest1> list2 = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.expression().constant(end).ge(o.expression().constant(end).plusMonths(-3));
        }).toList();
        Assert.assertTrue(list2.size() > 0);
    }

    @Test
    public void testx18() {

        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 1, 1);
        List<TopicTypeTest1> list1 = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.expression().constant(end).le(o.expression().constant(end).plusMonths(-3));
        }).toList();
        Assert.assertEquals(0, list1.size());
        List<TopicTypeTest1> list2 = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.expression().constant(end).ge(o.expression().constant(end).plusMonths(-3));
        }).toList();
        Assert.assertTrue(list2.size() > 0);
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeTest1> list3 = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
            o.id().concat(c -> {
                c.expression(o.title());
                c.expression(o.title().subString(1, 2));
            }).eq("123");

        }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE CONCAT(`id`,`title`,SUBSTR(`title`,2,2)) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Data
    public static class TopicCount {
        private String type;
        private Long count;
    }


    @Test
    public void testx20() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        //SELECT 'type1' AS `type`,COUNT(t.`id`) AS `count` FROM `t_topic` t WHERE t.`title` = ?
        EntityQueryable<Draft2Proxy<String, Long>, Draft2<String, Long>> select1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().eq("123"))
                .select(o -> Select.DRAFT.of(
                        o.expression().constant("type1"),
                        o.expression().count()
                ));
        EntityQueryable<Draft2Proxy<String, Long>, Draft2<String, Long>> select2 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().eq("123"))
                .select(o -> Select.DRAFT.of(
                        o.expression().constant("type2"),
                        o.expression().count()
                ));
        EntityQueryable<Draft2Proxy<String, Long>, Draft2<String, Long>> select3 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().eq("123"))
                .select(o -> Select.DRAFT.of(
                        o.expression().constant("type3"),
                        o.expression().count()
                ));
        List<Draft2<String, Long>> list1 = select1.unionAll(select2, select3).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t6.`value1` AS `value1`,t6.`value2` AS `value2` FROM ( (SELECT ? AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t WHERE t.`title` = ?)  UNION ALL  (SELECT ? AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t2 WHERE t2.`title` = ?)  UNION ALL  (SELECT ? AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t4 WHERE t4.`title` = ?) ) t6", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("type1(String),123(String),type2(String),123(String),type3(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx21() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
//        SELECT
//        YEAR(日期) AS 年份，
//        MONTH(日期) AS 月份
//        SUM(收入) AS 月收入
//        FROM
//                your_table
//        WHERE
//        日期 >= CURDATE()- INTERVAL 3 MONTH
//        GROUP BY
//        年份，月份
//        ORDER BY
//        年份，月份;

        List<Draft3<Integer, Integer, Integer>> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.createTime().gt(Expression.of(o).now().plusMonths(-3)))
                .groupBy(o -> {
                    return GroupKeys.of(o.createTime().year(), o.createTime().month());
                })
                .orderBy(o -> {
                    o.key1().asc();
                    o.key2().asc();
//                    o.key2().nullOrDefault(1).asc();
//                    o.key2().asc(OrderByModeEnum.NULLS_FIRST);
                }).select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.key2(),
                        o.groupTable().star().sum().asAnyType(Integer.class)
                )).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT YEAR(t.`create_time`) AS `value1`,MONTH(t.`create_time`) AS `value2`,SUM(t.`star`) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? AND  t.`create_time` > date_add(NOW(), interval (?) month) GROUP BY YEAR(t.`create_time`),MONTH(t.`create_time`) ORDER BY YEAR(t.`create_time`) ASC,MONTH(t.`create_time`) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),-3(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testx22() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.deleted().not().eq(false)).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (NOT (`deleted`)) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testx23() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {

                    Query<Long> longQuery = easyEntityQuery.queryable(Topic.class)
                            .where(x -> x.id().eq(o.id())).selectCount();

                    o.expression().constant(0L)
                            .eq(longQuery);

                    Query<Integer> intQuery = easyEntityQuery.queryable(Topic.class)
                            .where(x -> x.id().eq(o.id())).selectCount(Integer.class);

                    o.star().eq(intQuery);
                }).select(o -> Select.DRAFT.of(
                        o.id(),
                        o.url()
                )).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t.`url` AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND ? = (SELECT COUNT(*) FROM `t_topic` t1 WHERE t1.`id` = t.`id`) AND t.`star` = (SELECT COUNT(*) FROM `t_topic` t2 WHERE t2.`id` = t.`id`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),0(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testx24() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<String, String, Long>> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("123");
                }).select(o -> Select.DRAFT.of(
                        o.id(),
                        o.url(),
                        o.expression().subQueryColumn(
                                easyEntityQuery.queryable(Topic.class).where(x -> x.id().eq(o.id())).selectCount()
                        )
                )).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//        Assert.assertEquals("SELECT t.`id` AS `value1`,t.`url` AS `value2`,(SELECT COUNT(*) FROM `t_topic` t1 WHERE t1.`id` = t.`id`) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("SELECT t.`id` AS `value1`,t.`url` AS `value2`,(SELECT COUNT(*) FROM `t_topic` t2 WHERE t2.`id` = t.`id`) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("SELECT t.`id` AS `value1`,t.`url` AS `value2`,(SELECT COUNT(*) FROM `t_topic` t1 WHERE t1.`id` = t.`id`) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testCTE1() {
        String sql = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().isNotNull())
                .asTreeCTE()
                .toSQL();
        System.out.println(sql);
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) ) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,t.`cte_deep` FROM `as_tree_cte` t", sql);
    }

    @Test
    public void testx26() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("123");
                    o.id().nullOrDefault(o.title().subString(1, 2)).eq("1");
                })
                .select(o -> new BlogEntityProxy().adapter(x -> {
                    x.id().set(o.id());
                    x.isTop().set(o.id().equalsWith("1"));
                    x.isTop().set(o.id().subString(1, 2).equalsWith(o.title().toLower()));
                    x.content().set(o.id().nullOrDefault(o.title().subString(1, 2)));
                })).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `id`,(CASE t.`id` WHEN ? THEN 1 ELSE 0 END) AS `is_top`,(CASE SUBSTR(t.`id`,2,2) WHEN LOWER(t.`title`) THEN 1 ELSE 0 END) AS `is_top`,IFNULL(t.`id`,SUBSTR(t.`title`,2,2)) AS `content` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND IFNULL(t.`id`,SUBSTR(t.`title`,2,2)) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),false(Boolean),123(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx27() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeTest1> list1 = easyEntityQuery.queryable(TopicTypeTest1.class)
                .where(o -> {
                    o.topicType().nullOrDefault(TopicTypeEnum.CLASSER).eq(TopicTypeEnum.STUDENT);
                    o.topicType().eq(TopicTypeEnum.STUDENT);
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE IFNULL(`topic_type`,?) = ? AND `topic_type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("9(Integer),1(Integer),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testx28() {
        ArrayList<TopicTypeEnum> topicTypeEnums = new ArrayList<>();
        topicTypeEnums.add(TopicTypeEnum.STUDENT);
        topicTypeEnums.add(TopicTypeEnum.CLASSER);
        topicTypeEnums.add(TopicTypeEnum.TEACHER);
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<TopicTypeTest1> list1 = easyEntityQuery.queryable(TopicTypeTest1.class)
                .where(o -> {
                    o.topicType().nullOrDefault(TopicTypeEnum.CLASSER).in(topicTypeEnums);
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE IFNULL(`topic_type`,?) IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("9(Integer),1(Integer),9(Integer),3(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testdraft()
    {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, Long>> list = easyEntityQuery
                .queryable(Topic.class)
                .groupBy(t -> GroupKeys.of(t.id()))
                .select(t -> Select.DRAFT.of(
                        t.key1(),
                        t.count()
                ))
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,COUNT(*) AS `value2` FROM `t_topic` t GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }
    @Test
    public void testOrder()
    {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Topic> list3 = easyEntityQuery.queryable(Topic.class)
                    .where(o -> {
                        o.title().eq("title" );
                        o.id().eq("1" );
                    })
                    .orderBy(o -> {
                        o.createTime().format("yyyy-MM-dd HH:mm:ss" ).asc();
                        o.stars().asc(OrderByModeEnum.NULLS_LAST);
                        o.createTime().format("yyyy-MM-dd HH:mm:ss" ).desc();
                        o.stars().desc(OrderByModeEnum.NULLS_FIRST);
                    })
                    .select(o -> new TopicProxy().selectExpression(o.FETCHER.title().id(), o.createTime().format("yyyy-MM-dd HH:mm:ss" )))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic` t WHERE t.`title` = ? AND t.`id` = ? ORDER BY DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') ASC,CASE WHEN t.`stars` IS NULL THEN 1 ELSE 0 END ASC,t.`stars` ASC,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') DESC,CASE WHEN t.`stars` IS NULL THEN 0 ELSE 1 END ASC,t.`stars` DESC" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("title(String),1(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            TopicTypeJsonValue topicTypeJsonValue1 = new TopicTypeJsonValue();
            topicTypeJsonValue1.setName("123");
            List<Topic> list3 = easyEntityQuery.queryable(TopicTypeArrayJson.class)
                    .where(o -> {
                        o.title2().likeRaw("123");
                        o.title2().eq(Collections.singletonList(topicTypeJsonValue1));
                        o.title().likeRaw("456");
                        TopicTypeJsonValue topicTypeJsonValue = new TopicTypeJsonValue();
                        topicTypeJsonValue.setAge(1);
                        topicTypeJsonValue.setName("1");
                        o.title().eq(topicTypeJsonValue);
                        o.id().eq("1" );
                    })
                    .orderBy(o -> {
                        o.title2().asc();
                    })
                    .select(o -> new TopicProxy().selectExpression(o.FETCHER.title().id(), o.createTime().format("yyyy-MM-dd HH:mm:ss" )))
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`title`,t.`id`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') FROM `t_topic_type_array` t WHERE t.`title2` LIKE ? AND t.`title2` = ? AND t.`title` LIKE ? AND t.`title` = ? AND t.`id` = ? ORDER BY t.`title2` ASC" , jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%123%(String),[{\"age\":null,\"name\":\"123\"}](String),%456%(String),{\"age\":1,\"name\":\"1\"}(String),1(String)" , EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
            easyEntityQuery.queryable(TopicTypeArrayJson2.class)
                    .where(t -> {
                        t.title3().eq(new Integer[1]);
                    });
        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<TopicTypeTest1> list = easyEntityQuery.queryable(TopicTypeTest1.class).where(o -> {
                o.topicType().likeRaw(TopicTypeEnum.STUDENT.getCode());
            }).toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE `topic_type` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }
}
