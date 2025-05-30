package com.easy.query.test;

import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.test.entity.LogicDelTopic;
import com.easy.query.test.entity.LogicDelTopicCustom;
import com.easy.query.test.entity.proxy.LogicDelTopicProxy;
import com.easy.query.test.logicdel.CurrentUserHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2023/3/28 09:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class LogicDeleteTest extends BaseTest {

    @Test
    public void Test1(){
        String logicDeleteSQL = easyEntityQuery.queryable(LogicDelTopic.class)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`deleted`,`create_time` FROM `t_logic_del_topic` WHERE `deleted` = ?",logicDeleteSQL);
        List<LogicDelTopic> logicDelTopics = easyEntityQuery.queryable(LogicDelTopic.class).toList();
        Assert.assertTrue(EasyCollectionUtil.isNotEmpty(logicDelTopics));
        Assert.assertEquals(100,logicDelTopics.size());
    }
    @Test
    public void Test2(){
        String logicDeleteSql = easyEntityQuery.deletable(LogicDelTopic.class)
                .whereById("111")
                .toSQL();
        Assert.assertEquals("UPDATE `t_logic_del_topic` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ?",logicDeleteSql);
        long l = easyEntityQuery.deletable(LogicDelTopic.class)
                .whereById("111")
                .executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void Test3(){
        LogicDelTopic logicDelTopic = new LogicDelTopic();
        logicDelTopic.setId("111");
        String logicDeleteSql = easyEntityQuery.deletable(logicDelTopic)
                .toSQL();
        Assert.assertEquals("UPDATE `t_logic_del_topic` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ?",logicDeleteSql);

        long l = easyEntityQuery.deletable(logicDelTopic)
                .executeRows();
        Assert.assertEquals(0,l);
    }


    @Test
    public void Test4(){
        String logicDeleteSql = easyEntityQuery.queryable(LogicDelTopic.class)
                .disableLogicDelete()
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`deleted`,`create_time` FROM `t_logic_del_topic`",logicDeleteSql);
        List<LogicDelTopic> logicDelTopics = easyEntityQuery.queryable(LogicDelTopic.class).disableLogicDelete().toList();
        Assert.assertTrue(EasyCollectionUtil.isNotEmpty(logicDelTopics));
        Assert.assertEquals(100,logicDelTopics.size());
    }
    @Test
    public void Test5(){
        String logicDeleteSql = easyEntityQuery.deletable(LogicDelTopic.class)
                .disableLogicDelete()
                .whereById("111xx")
                .toSQL();
        Assert.assertEquals("DELETE FROM `t_logic_del_topic` WHERE `id` = ?",logicDeleteSql);
        long l = easyEntityQuery.deletable(LogicDelTopic.class)
                .disableLogicDelete()
                .whereById("111xx")
                .executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void Test6(){

        LogicDelTopic logicDelTopic = easyEntityQuery.queryable(LogicDelTopic.class)
                .disableLogicDelete().firstOrNull();
        Assert.assertNotNull(logicDelTopic);
        ExpressionUpdatable<LogicDelTopicProxy, LogicDelTopic> logicDelTopicExpressionUpdatable = easyEntityQuery.updatable(LogicDelTopic.class)
                .disableLogicDelete()
                .setColumns(l -> {
                    l.title().set(logicDelTopic.getTitle());
                })
                .whereById(logicDelTopic.getId());
        String s = logicDelTopicExpressionUpdatable.toSQL();
        Assert.assertEquals("UPDATE `t_logic_del_topic` SET `title` = ? WHERE `id` = ?",s);

        long l = logicDelTopicExpressionUpdatable.executeRows();
        Assert.assertEquals(1,l);
    }
    @Test
    public void Test7(){
        String logicDeleteSql = easyEntityQuery.queryable(LogicDelTopicCustom.class)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`deleted_at`,`deleted_user`,`create_time` FROM `t_logic_del_topic_custom` WHERE `deleted_at` IS NULL",logicDeleteSql);
        List<LogicDelTopicCustom> logicDelTopics = easyEntityQuery.queryable(LogicDelTopicCustom.class).toList();
        Assert.assertTrue(EasyCollectionUtil.isNotEmpty(logicDelTopics));
        Assert.assertEquals(100,logicDelTopics.size());
    }
    @Test
    public void Test8(){
        String logicDeleteSql = easyEntityQuery.queryable(LogicDelTopicCustom.class)
                .where(o->o.id().eq("1"))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`deleted_at`,`deleted_user`,`create_time` FROM `t_logic_del_topic_custom` WHERE `deleted_at` IS NULL AND `id` = ?",logicDeleteSql);
        LogicDelTopicCustom logicDelTopic = easyEntityQuery.queryable(LogicDelTopicCustom.class)
                .where(o->o.id().eq("1")).firstOrNull();
        Assert.assertNotNull(logicDelTopic);
        long l = easyEntityQuery.updatable(logicDelTopic).executeRows();
        Assert.assertEquals(1,l);
    }
    @Test
    public void Test9(){
        String logicDeleteSql = easyEntityQuery.queryable(LogicDelTopicCustom.class)
                .where(o->o.id().eq("1"))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`deleted_at`,`deleted_user`,`create_time` FROM `t_logic_del_topic_custom` WHERE `deleted_at` IS NULL AND `id` = ?",logicDeleteSql);
        LogicDelTopicCustom logicDelTopic = easyEntityQuery.queryable(LogicDelTopicCustom.class)
                .where(o->o.id().eq("1")).firstOrNull();
        Assert.assertNotNull(logicDelTopic);
        logicDelTopic.setId("11xx");
        CurrentUserHelper.setUserId("easy-query");
        long l = easyEntityQuery.deletable(logicDelTopic).executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void Test10(){
        String logicDeleteSql = easyEntityQuery.queryable(LogicDelTopicCustom.class)
                .disableLogicDelete()
                .where(o->o.id().eq("1"))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`deleted_at`,`deleted_user`,`create_time` FROM `t_logic_del_topic_custom` WHERE `id` = ?",logicDeleteSql);
        LogicDelTopicCustom logicDelTopic = easyEntityQuery.queryable(LogicDelTopicCustom.class)
                .disableLogicDelete()
                .where(o->o.id().eq("1")).firstOrNull();
        Assert.assertNotNull(logicDelTopic);
    }
}
