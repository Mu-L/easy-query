package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.dto.MyTaskDTO1;
import com.easy.query.test.mysql8.dto.MyTaskDTO2;
import com.easy.query.test.mysql8.entity.condition.MyTask;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2026/6/18 10:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeConditionTest extends BaseTest {

    @Test
    public void testInclude1() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        System.out.println("------------------");
        List<MyTask> list = easyEntityQuery.queryable(MyTask.class)
                .include2((context, m) -> {
                    context.query(m.myTask1Ext());
                    context.query(m.myTask2Ext());
                    context.query(m.mySubTask1List());
                    context.query(m.mySubTask2List());
                })
                .toList();
        System.out.println(list);
        Assert.assertEquals("[MyTask(id=v1, name=任务1, version=1, mySubTask1List=[MySubTask1(id=v1s1, taskId=v1, name=任务1的子任务1), MySubTask1(id=v1s2, taskId=v1, name=任务1的子任务2)], mySubTask2List=null, myTask1Ext=MyTask1Ext(id=v1, taskId=v1, name=任务1的扩展1), myTask2Ext=null), MyTask(id=v2, name=任务2, version=2, mySubTask1List=null, mySubTask2List=[MySubTask2(id=v2s1, taskId=v2, name=任务2的子任务1), MySubTask2(id=v2s2, taskId=v2, name=任务2的子任务1)], myTask1Ext=null, myTask2Ext=MyTask2Ext(id=v2, taskId=v2, name=任务2的扩展1)), MyTask(id=v3, name=任务3, version=3, mySubTask1List=null, mySubTask2List=null, myTask1Ext=null, myTask2Ext=null)]", list.toString());


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT `id`,`name`,`version` FROM `my_task`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`task_id`,t.`name` FROM `my_task1_ext` t WHERE t.`task_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT t.`id`,t.`task_id`,t.`name` FROM `my_task2_ext` t WHERE t.`task_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
            Assert.assertEquals("SELECT t.`id`,t.`task_id`,t.`name` FROM `my_task_sub1` t WHERE t.`task_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
            Assert.assertEquals("SELECT t.`id`,t.`task_id`,t.`name` FROM `my_task_sub2` t WHERE t.`task_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testInclude2() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        System.out.println("------------------");
        List<MyTask> list = easyEntityQuery.queryable(MyTask.class)
                .include2((context, m) -> {
                    context.query(m.myTask1Ext());
                    context.query(m.myTask2Ext());
                    context.query(m.mySubTask1List());
                    context.query(m.mySubTask2List());
                })
                .where(m -> {
                    m.id().eq("v1");
                })
                .toList();
        System.out.println(list);
        Assert.assertEquals("[MyTask(id=v1, name=任务1, version=1, mySubTask1List=[MySubTask1(id=v1s1, taskId=v1, name=任务1的子任务1), MySubTask1(id=v1s2, taskId=v1, name=任务1的子任务2)], mySubTask2List=null, myTask1Ext=MyTask1Ext(id=v1, taskId=v1, name=任务1的扩展1), myTask2Ext=null)]", list.toString());


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(3, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT `id`,`name`,`version` FROM `my_task` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`task_id`,t.`name` FROM `my_task1_ext` t WHERE t.`task_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT t.`id`,t.`task_id`,t.`name` FROM `my_task_sub1` t WHERE t.`task_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testInclude3() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        System.out.println("------------------");
        List<MyTaskDTO1> list = easyEntityQuery.queryable(MyTask.class)
                .where(m -> {
                    m.id().eq("v1");
                })
                .selectAutoInclude(MyTaskDTO1.class)
                .toList();
        System.out.println(list);
        Assert.assertEquals("[MyTaskDTO1(id=v1, name=任务1, mySubTask1List=[MyTaskDTO1.InternalMySubTask1(id=v1s1, name=任务1的子任务1), MyTaskDTO1.InternalMySubTask1(id=v1s2, name=任务1的子任务2)], myTask2Ext=null)]", list.toString());


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`version` AS `__relation__version` FROM `my_task` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`task_id` AS `__relation__taskId` FROM `my_task_sub1` t WHERE t.`task_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }

    @Test
    public void testInclude4() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        System.out.println("------------------");
        List<MyTaskDTO2> list = easyEntityQuery.queryable(MyTask.class)
                .where(m -> {
                    m.id().eq("v2");
                })
                .selectAutoInclude(MyTaskDTO2.class)
                .toList();
        System.out.println(list);
        Assert.assertEquals("[MyTaskDTO2(id=v2, name=任务2, version=2, mySubTask1List=null, myTask2Ext=MyTaskDTO2.InternalMyTask2Ext(id=v2, name=任务2的扩展1))]", list.toString());


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`version` FROM `my_task` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`task_id` AS `__relation__taskId` FROM `my_task2_ext` t WHERE t.`task_id` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("v2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }
}
