package com.easy.query.test;

import com.alibaba.fastjson2.annotation.JSONField;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.internal.command.JdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.StreamIterable;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.columns.types.SQLIntegerTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.grouping.proxy.Grouping2Proxy;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.entity.BaseEntity;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/3/11 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest23 extends BaseTest {


    @Test
    public void testElement3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                    .manyJoin(x -> x.bankCards())
                    .select(user -> Select.DRAFT.of(
                            user.bankCards().where(o -> o.type().eq("123")).max(x -> x.code()),
                            user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                    )).toList();
        } catch (Exception ex) {

        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__max2__` AS `value1`,t6.`type` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE ? END)) AS `__max2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN (SELECT t4.`id` AS `id`,t4.`uid` AS `uid`,t4.`code` AS `code`,t4.`type` AS `type`,t4.`bank_id` AS `bank_id` FROM (SELECT t3.`id`,t3.`uid`,t3.`code`,t3.`type`,t3.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t3 WHERE t3.`type` = ?) t4 WHERE t4.`__row__` = ?) t6 ON t6.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),null(null),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .manyJoin(x -> x.schoolTeachers())
                .where(s -> {
                    s.schoolTeachers().where(x -> x.name().like("小明")).count().eq(1L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t LEFT JOIN (SELECT t2.`class_id` AS `class_id`,COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE ? END)) AS `__count2__` FROM `school_teacher` t1 INNER JOIN `school_class_teacher` t2 ON t1.`id` = t2.`teacher_id` GROUP BY t2.`class_id`) t4 ON t4.`class_id` = t.`id` WHERE IFNULL(t4.`__count2__`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),1(Integer),null(null),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void fetcherOrder() {
        List<Draft1<BigDecimal>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.expression().sqlSegment("({0}+{1})/{2}", c -> {
                            c.expression(t_blog.score()).expression(t_blog.star()).expression(t_blog.order());
                        }, BigDecimal.class)
                )).toList();


        easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.expression().caseWhen(() -> {
                                }).then(1)
                                .elseEnd(
                                        t_blog.expression().sqlSegment("({0}+{1})/{2}", c -> {
                                            c.expression(t_blog.score()).expression(t_blog.star()).expression(t_blog.order());
                                        }, BigDecimal.class)
                                )
                )).toList();

//
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                 .where(t_blog -> t_blog.createTime().gt(LocalDateTime.of(2020, 1, 1, 0, 0)))
//                 .select(t_blog -> t_blog.FETCHER.id().score())
//                 .orderBy(t_blog -> {
//                     t_blog.score().asc();
//                 }).toList();
    }

    @Test
    public void testAddMulty() {

//        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
//                .where(t_blog -> {
//                    t_blog.createTime().duration(LocalDateTime.now()).toDays().gt(5L);
//
//                    t_blog.expression().now()
//                            .duration(t_blog.createTime()).toDays().gt(5L);
//                })
//                .toList();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                .configure(o -> o.setPrintSQL(false))
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.order().add(t_blog.star()).divide(t_blog.score())
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ((t.`order` + t.`star`) / t.`score`) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testPageDistinct() {


        {
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            EasyPageResult<Draft2<String, String>> pageResult = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.or(() -> {
                            bank_card.id().eq("123");
                            bank_card.id().isNotNull();
                        });
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.code(),
                            bank_card.user().name()
                    )).distinct().toPageResult(1, 2);

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT COUNT(*) FROM (SELECT DISTINCT t.`code` AS `value1`,t1.`name` AS `value2` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE (t.`id` = ? OR t.`id` IS NOT NULL)) t2", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("SELECT t.`name`,t.`class_id` AS `__relation__classId` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
        }
    }

    @Test
    public void testPageDistinct2() {


        {
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.or(() -> {
                            bank_card.id().eq("123");
                            bank_card.id().isNotNull();
                        });
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.code(),
                            bank_card.user().name()
                    )).distinct().toList();

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT DISTINCT t.`code` AS `value1`,t1.`name` AS `value2` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE (t.`id` = ? OR t.`id` IS NOT NULL)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("SELECT t.`name`,t.`class_id` AS `__relation__classId` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
        }
    }
//     @Test
//     public void testAddMulty2(){
//
//         ListenerContext listenerContext = new ListenerContext();
//         listenerContextManager.startListen(listenerContext);
//
//         List<Draft2<BigDecimal,BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
//                 .configure(o->o.setPrintSQL(false))
//                 .select(t_blog -> Select.DRAFT.of(
//                         t_blog.expression().caseWhen(()->{
//                             t_blog.id().isNull();
//                         }).then(1).elseEnd(t_blog.order().add(t_blog.star()).divide(t_blog.score()),BigDecimal.class),
//                         t_blog.order().add(t_blog.star()).divide(t_blog.score())
//                 )).toList();
//         for (Draft2<BigDecimal,BigDecimal> bigDecimalDraft1 : list) {
//             System.out.println(bigDecimalDraft1.getValue1());
//             System.out.println(bigDecimalDraft1.getValue2());
//         }
//
//         listenerContextManager.clear();
//         Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//         JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//         Assert.assertEquals("SELECT ((t.`order` + t.`star`) / t.`score`) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//         Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//
//     }


    @Test
    public void testPageDistinct3() {


        {
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            EasyPageResult<Draft1<String>> pageResult = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.or(() -> {
                            bank_card.id().eq("123");
                            bank_card.id().isNotNull();
                        });
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.user().name()
                    )).distinct().toPageResult(1, 2);

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT COUNT(DISTINCT t1.`name`) FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE (t.`id` = ? OR t.`id` IS NOT NULL)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("SELECT t.`name`,t.`class_id` AS `__relation__classId` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
        }
//        SQLFunc fx = easyEntityQuery.getRuntimeContext().fx();
//        SQLFunction caseWhenXxxxxx = fx.anySQLFunction("case when xxxxxx", c -> {
//        });
//        List<Draft1<BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
//                .select(t_blog -> Select.DRAFT.of(
//                        t_blog.expression().sqlSegment("({0} - {1})", c -> {
//                            c.expression(t_blog.star()).subQuery(
//                                    easyEntityQuery.queryable(Topic.class)
//                                            .where(t_topic -> {
//                                                t_topic.id().eq(t_blog.id());
//                                            }).selectColumn(t_topic -> t_topic.stars().sum())
//                            );
//                        }, BigDecimal.class)
//                )).toList();
//
//
//        List<Draft1<BigDecimal>> list1 = easyEntityQuery.queryable(DocUser.class)
//                .manyJoin(x->x.bankCards())
//                .select(user -> Select.DRAFT.of(
//                        user.bankCards().sum(x -> x.code().toNumber(BigDecimal.class))
//                )).toList();


//        easyEntityQuery.queryable(BlogEntity.class)
//                .where(t_blog -> {
//                    t_blog.expression().sql("id > 1");
//                })

    }


    @Test
    public void subquery() {

//        try (JdbcStreamResult<BlogEntity> streamResult = easyEntityQuery.queryable(BlogEntity.class)
//                .toStreamResult(100)) {
//            StreamIterable<BlogEntity> streamIterable = streamResult.getStreamIterable();
//            Iterator<BlogEntity> iterator = streamIterable.iterator();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try (JdbcStreamResult<Map> streamResult = easyEntityQuery.getEasyQueryClient()
                .queryable("select * from t_blog where id <> ?", Map.class, Arrays.asList("1"))
                .toStreamResult(100)) {
            JdbcCommand<QueryExecuteResult> jdbcCommand = streamResult.getJdbcCommand();
            QueryExecuteResult execute = jdbcCommand.execute();
            StreamResultSet streamResultSet = execute.getStreamResultSet();
            ResultSetMetaData metaData = streamResultSet.getMetaData();
            Assert.assertNotNull(metaData);

            StreamIterable<Map> streamIterable = streamResult.getStreamIterable();
            for (Map map : streamIterable) {
                System.out.println(map);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testAdd() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    Expression expression = t_blog.expression();
                    t_blog.id().eq("123");
                    expression.exists(() -> {
                        return expression.subQueryable(Topic.class)
                                .where(t_topic -> {
                                    t_blog.id().eq(t_topic.id().nullOrDefault("1"));
                                }).groupBy(t_topic -> GroupKeys.of(t_topic.id()))
                                .having(group -> t_blog.id().count().gt(1L)).orderBy(t_topic -> {
                                    t_blog.id().asc();
                                });
                    });

                })
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE  t.`id` = IFNULL(t1.`id`,?) GROUP BY t1.`id` HAVING COUNT(t.`id`) > ? ORDER BY t.`id` ASC)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),1(String),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

//        easyEntityQuery.queryable(DocBankCard.class)
//                .include(s->s.bank())
//                .where(bank_card -> {
//                    bank_card.bank()
//                })

    }

    @Test
    public void anyTest() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<String>> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.phone().eq("123");
                    user.userBooks().any(x -> {
                        user.name().eq(x.name());
                        user.phone().eq("123");
                    });
                    user.phone().eq("123");
                }).select(user -> Select.DRAFT.of(
                        user.bankCards().where(x -> {
                            user.name().eq(x.code());
                            user.phone().eq("123");
                            x.code().eq("123");
                        }).max(x -> x.type())
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT MAX(t2.`type`) FROM `doc_bank_card` t2 WHERE t2.`uid` = t.`id` AND t.`name` = t2.`code` AND t.`phone` = ? AND t2.`code` = ?) AS `value1` FROM `doc_user` t WHERE t.`phone` = ? AND EXISTS (SELECT 1 FROM `doc_user_book` t1 WHERE t1.`uid` = t.`id` AND t.`name` = t1.`name` AND t.`phone` = ? LIMIT 1) AND t.`phone` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testaaa() {
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .where(t_blog -> {
//                    t_blog.createTime().plusYears(1).plusMonths(6).plus(10, TimeUnitEnum.DAYS).eq(LocalDateTime.now());
//                }).toList();

//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(o -> {
//                    Expression expression = o.expression();
//
//                    expression.exists(() -> {
//                        return expression.subQueryable(BlogEntity.class).where(q -> {
//                            q.id().eq("1" );
//                            q.id().eq(o.id());
//                        });
//                    });
//                }).toList();

//
//        EntityQueryable<BlogEntityProxy, BlogEntity> subQueryable = easyEntityQuery.queryable(BlogEntity.class)
//                .where(o -> q.id().eq("1"));
//
//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(o -> {
//                    Expression expression = o.expression();
//                    expression.notExists(() -> {
//                        return expression.subQueryable(BlogEntity.class).where(q -> {
//                            q.id().eq("1");
//                            q.id().eq(o.id());
//                        });
//                    });
//                }).toList();


        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    Query<String> stringQuery = o.expression().subQueryable(BlogEntity.class)
                            .where(x -> o.id().eq("123"))
                            .selectColumn(x -> x.id());

                    o.id().in(
                            stringQuery
                    );
                }).toList();
    }
//
//    @Data
//    public static class AA{
//        private String firstName;
//        private String lastName;
//
//        public String getFullName(){
//            return firstName+lastName;
//        }
//    }

}
