package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/12/23 23:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyTest1 extends BaseTest{

    @Test
    public void testDraft1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Integer>> list2 = entityQuery.queryable(BlogEntity.class)
                .where(o->{
                    o.title().length().eq(123);
//                    o.createTime().
//                    LocalDateTime.now().plus(1, TimeUnit.MILLISECONDS)
                })
                .groupBy(o -> o.content())
                .selectDraft(o -> Select.draft(
                        o.groupKeys(0).toDraft(String.class),
                        o.content().length()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`content` AS `value1`,CHAR_LENGTH(t.`content`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND CHAR_LENGTH(t.`title`) = ? GROUP BY t.`content`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDraft2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Integer>> list2 = entityQuery.queryable(BlogEntity.class)
                .where(o->{
                    o.title().toNumber(Integer.class).asAny().eq(123);
//                    o.createTime().
//                    LocalDateTime.now().plus(1, TimeUnit.MILLISECONDS)
                })
                .groupBy(o -> o.content())
                .selectDraft(o -> Select.draft(
                        o.groupKeys(0).toDraft(String.class),
                        o.content().length()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`content` AS `value1`,CHAR_LENGTH(t.`content`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND CAST(t.`title` AS SIGNED) = ? GROUP BY t.`content`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDraft3(){
        {
            Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(BlogEntity.class)
                    .selectDraft(o -> Select.draft(
                            o.createTime()
                    )).firstOrNull();
            Assert.assertNotNull(localDateTimeDraft1);
            LocalDateTime value1 = localDateTimeDraft1.getValue1();
            Assert.assertEquals(2020,value1.getYear());
            Assert.assertEquals(1,value1.getMonth().getValue());
            Assert.assertEquals(1,value1.getDayOfMonth());
        }
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(BlogEntity.class)
                .selectDraft(o -> Select.draft(
                        o.createTime().plus(2, TimeUnit.DAYS)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2020,value1.getYear());
        Assert.assertEquals(1,value1.getMonth().getValue());
        Assert.assertEquals(3,value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(t.`create_time`, interval (?) microsecond) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("172800000000(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDraft4(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(BlogEntity.class)
                .selectDraft(o -> Select.draft(
                        o.createTime().plusMonths(2)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2020,value1.getYear());
        Assert.assertEquals(3,value1.getMonth().getValue());
        Assert.assertEquals(1,value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(t.`create_time`, interval (?) month) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testDraft5(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(BlogEntity.class)
                .selectDraft(o -> Select.draft(
                        o.createTime().plusMonths(12).plus(1,TimeUnit.DAYS)
                )).firstOrNull();
        Assert.assertNotNull(localDateTimeDraft1);
        LocalDateTime value1 = localDateTimeDraft1.getValue1();
        Assert.assertEquals(2021,value1.getYear());
        Assert.assertEquals(1,value1.getMonth().getValue());
        Assert.assertEquals(2,value1.getDayOfMonth());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT date_add(date_add(t.`create_time`, interval (?) month), interval (?) microsecond) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12(Integer),86400000000(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
