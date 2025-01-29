package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstExecutable;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationContext;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.common.MD5Util;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyMigrationBlog;
import com.easy.query.test.entity.MyMigrationBlog0;
import com.easy.query.test.entity.NewTopic;
import com.easy.query.test.entity.NewTopic3;
import com.easy.query.test.entity.Topic;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2025/1/14 14:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class MigrationTest extends BaseTest {

    @Test
    public void createTableSQLTest1() {
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(Arrays.asList(Topic.class, BlogEntity.class, MyMigrationBlog.class));
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        Assert.assertEquals(1, migrationCommands.size());
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        MigrationCommand migrationCommand = migrationCommands.get(0);
        Assert.assertEquals("CREATE TABLE IF NOT EXISTS `my_migration_blog` ( \n" +
                "`id` VARCHAR(255) NOT NULL ,\n" +
                "`create_time` DATETIME(3) NULL ,\n" +
                "`update_time` DATETIME(3) NULL ,\n" +
                "`create_by` VARCHAR(255) NULL ,\n" +
                "`update_by` VARCHAR(255) NULL ,\n" +
                "`deleted` TINYINT(1) NULL ,\n" +
                "`title` VARCHAR(255) NULL  COMMENT '标题',\n" +
                "`content` VARCHAR(255) NULL  COMMENT '内容',\n" +
                "`url` VARCHAR(255) NULL  COMMENT '博客链接',\n" +
                "`star` INT(11) NULL ,\n" +
                "`publish_time` DATETIME(3) NULL ,\n" +
                "`score` DECIMAL(16,2) NULL ,\n" +
                "`status` INT(11) NULL ,\n" +
                "`order` DECIMAL(16,2) NULL ,\n" +
                "`is_top` TINYINT(1) NULL ,\n" +
                "`top` TINYINT(1) NULL , \n" +
                " PRIMARY KEY (`id`)\n" +
                ") Engine=InnoDB;", migrationCommand.toSQL());
    }

    @Test
    public void createTableSQLTest2() {
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(Arrays.asList(NewTopic.class));
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        Assert.assertEquals(2, migrationCommands.size());
        MigrationCommand migrationCommand0 = migrationCommands.get(0);
        Assert.assertEquals("ALTER TABLE `t_topic` RENAME TO `t_topic2`;", migrationCommand0.toSQL());
        MigrationCommand migrationCommand1 = migrationCommands.get(1);
        Assert.assertEquals("ALTER TABLE `t_topic2` ADD `abc` VARCHAR(255) NULL;", migrationCommand1.toSQL());
    }

    @Test
    public void createTableSQLTest3() {
        MigrationsSQLGenerator migrationsSQLGenerator = easyEntityQuery.getRuntimeContext().getMigrationsSQLGenerator();
        MigrationContext migrationContext = new MigrationContext(Arrays.asList(NewTopic3.class));
        List<MigrationCommand> migrationCommands = migrationsSQLGenerator.generateMigrationSQL(migrationContext);
        for (MigrationCommand migrationCommand : migrationCommands) {
            System.out.println(migrationCommand.toSQL());
        }
        Assert.assertEquals(1, migrationCommands.size());
        MigrationCommand migrationCommand0 = migrationCommands.get(0);
        Assert.assertEquals("ALTER TABLE `t_topic` CHANGE `title` `abc` varchar(200) NULL;", migrationCommand0.toSQL());
    }

    @Test
    public void createTableSQLTest4() {


        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        if (databaseCodeFirst.tableExists(MyMigrationBlog0.class)) {
            System.out.println("存在表:MyMigrationBlog0");
            boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
            CodeFirstExecutable codeFirstExecutable = databaseCodeFirst.dropTables(Arrays.asList(MyMigrationBlog0.class));
            codeFirstExecutable.executeWithTransaction(arg -> {
                System.out.println("执行删除");
                System.out.println(arg.sql);
            });
        } else {
            System.out.println("不存在表:MyMigrationBlog0");
        }

        CodeFirstExecutable codeFirstExecutable = databaseCodeFirst.syncTables(Arrays.asList(MyMigrationBlog0.class));
        codeFirstExecutable.executeWithTransaction(arg -> {
            System.out.println(arg.sql);
            String md5 = MD5Util.getMD5Hash(arg.sql);
            System.out.println("sql-hash:" + md5);
            Assert.assertEquals("ef793f270480e3ebe65328501cc51bd2", md5);
            arg.commit();
        });
        boolean any = easyEntityQuery.queryable(MyMigrationBlog0.class).any();
        Assert.assertFalse(any);
        CodeFirstExecutable codeFirstExecutable1 = databaseCodeFirst.dropTables(Arrays.asList(MyMigrationBlog0.class));
        codeFirstExecutable1.executeWithEnvTransaction(arg -> {
            System.out.println("执行删除");
            System.out.println(arg.sql);
        });
        Exception ex = null;
        try {
            easyEntityQuery.queryable(MyMigrationBlog0.class).any();
        } catch (Exception e) {
            ex = e;
            System.out.println(ex);
        }
        Assert.assertNotNull(ex);
    }

    @Test
    public void test2(){
//        DriverManager.getConnection()
        String jdbcUrl = EasyDatabaseUtil.getJdbcUrl(dataSource);
        System.out.println(jdbcUrl);
        String databaseName = EasyDatabaseUtil.getDatabaseName(dataSource,null);
        System.out.println(databaseName);
        String s = EasyDatabaseUtil.parseDatabaseName(jdbcUrl);
        System.out.println(s);
        String serverBaseUrl = EasyDatabaseUtil.getServerBaseUrl(jdbcUrl);
        System.out.println(serverBaseUrl);
        String serverBaseUrl1 = EasyDatabaseUtil.getServerBaseUrl("jdbc:sqlserver://localhost:1433;databaseName=mydb;encrypt=true");
        System.out.println(serverBaseUrl1);
    }


    @SneakyThrows
    @Test
    public void test3(){
//        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true", "root", "root");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/eq_db2?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);


        EasyQueryClient client = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    //进行一系列可以选择的配置
                    //op.setPrintSql(true);
                })
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build();
        DefaultEasyEntityQuery entityQuery = new DefaultEasyEntityQuery(client);

        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        //自动同步数据库表
        CodeFirstExecutable codeFirstExecutable = databaseCodeFirst.syncTables(Arrays.asList(Topic.class, BlogEntity.class));
        //执行命令
        codeFirstExecutable.executeWithTransaction(arg->{
            System.out.println(arg.sql);
            arg.commit();
        });
    }

}
