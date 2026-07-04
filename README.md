<p align="center">
  <img height="340" src="./imgs/logo.png">
</p>

# English | [中文](https://github.com/dromara/easy-query/blob/main/README-zh.md)


<p align="center">
    <a target="_blank" href="https://central.sonatype.com/search?q=easy-query">
        <img src="https://img.shields.io/maven-central/v/com.easy-query/easy-query-all?label=Maven%20Central" alt="Maven" />
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8-green.svg" alt="jdk-8" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-11-green.svg" alt="jdk-11" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="jdk-17" />
	</a>
    <br />
        <img src="https://img.shields.io/badge/SpringBoot-v2.x-blue">
        <img src="https://img.shields.io/badge/SpringBoot-v3.x-blue">
        <a target="_blank" href='https://gitee.com/noear/solon'><img src="https://img.shields.io/badge/Solon-v2.x-blue"></a>
    <br />
    <a target="_blank" href='https://gitee.com/dromara/easy-query'>
		<img src='https://gitee.com/xuejm/easy-query/badge/star.svg' alt='Gitee star'/>
	</a>
    <a target="_blank" href='https://github.com/dromara/easy-query'>
		<img src="https://img.shields.io/github/stars/xuejmnet/easy-query.svg?logo=github" alt="Github star"/>
	</a>
      <a href='https://gitcode.com/dromara/easy-query'><img src='https://gitcode.com/dromara/easy-query/star/badge.svg' alt='fork'></a>
</p>

## Special user

<p>
  <a href="https://yxai.chat/" target="_blank">
   <img alt="xinyi-Logo" src="./imgs/adv/xinyi.png" height="50px">
  </a>
</p>

- [GITHUB](https://github.com/dromara/easy-query)  GITHUB镜像地址
- [GITEE](https://gitee.com/dromara/easy-query)  GITEE镜像地址

## ai skill
https://github.com/wzszsw/easy-query-orm

## 📚 documentation
<div align="center">

[GITHUB Documentation](https://xuejmnet.github.io/easy-query-doc/) | [OFFICAL Documentation](https://www.easy-query.com/easy-query-doc/)

</div>

## 🐧 QQGroup:170029046
<div align="center">

<img src="./imgs/qrcode.jpg" title="JetBrains" width=122 />

</div>

## Five Implicit Features 🔥🔥🔥
- [x] Implicit Join - Automatically implements join queries for OneToOne and ManyToOne relationships, supporting filtering, sorting, and result fetching
- [x] Implicit Subquery - Automatically handles subqueries for OneToMany and ManyToMany relationships, supporting filtering, sorting, and aggregate function results
- [x] Implicit Grouping - Optimizes and merges multiple subqueries into grouped queries for OneToMany and ManyToMany relationships, supporting filtering, sorting, and aggregate functions
- [x] Implicit Partition Grouping - Enables first/Nth element operations for OneToMany and ManyToMany relationships, supporting filtering, sorting, and aggregate function results
- [x] Implicit CASE WHEN Expression - property.aggregate.filter() syntax, e.g., o.age().sum().filter(()->o.name().like("123"))


`Company` OneToMany `SysUser`

### Implicit Join
```java
List<SysUser> userInXXCompany = entityQuery.queryable(SysUser.class)
        .where(user -> {
            user.company().name().like("xx Company");
        })
        .orderBy(user -> {
            user.company().registerMoney().desc();
            user.birthday().asc();
        }).toList();
```
### Implicit Subquery
```java
List<Company> companies = entityQuery.queryable(Company.class)
        .where(company -> {
          company.users().any(u -> u.name().like("Xiao Ming"));
          company.users().where(u -> u.name().like("Xiao Ming"))
                  .max(u -> u.birthday()).gt(LocalDateTime.of(2000,1,1,0,0,0));
        }).toList();
```
### Implicit Grouping
```java
List<Company> companies = entityQuery.queryable(Company.class)
        // Two subqueries in where will be merged
        .subQueryToGroupJoin(company -> company.users())
        .where(company -> {
          company.users().any(u -> u.name().like("Xiao Ming"));
          company.users().where(u -> u.name().like("Xiao Ming"))
                  .max(u -> u.birthday()).gt(LocalDateTime.now());
        }).toList();
```
### Implicit Partition Grouping
```java
List<Company> companies = entityQuery.queryable(Company.class)
        .where(company -> {
          company.users().orderBy(u->u.birthday().desc()).first().name().eq("Xiao Ming");
          company.users().orderBy(u->u.birthday().desc()).element(0)
                  .birthday().lt(LocalDateTime.now());
        }).toList();
```
### Implicit CASE WHEN Expression

```java
import java.math.BigDecimal;

List<Draft2<LocalDateTime, Long>> customVO = entityQuery.queryable(SysUser.class)
        .where(user -> {
          user.birthday().lt(LocalDateTime.now());
        }).groupBy(user -> GroupKeys.of(user.companyId()))
        .select(group -> Select.DRAFT.of(
                group.groupTable().birthday().max().filter(() -> {
                  group.groupTable().name().like("Xiao Ming");
                }),
                group.groupTable().id().count().filter(() -> {
                  group.groupTable().birthday().ge(LocalDateTime.of(2024, 1, 1, 0, 0));
                })
        )).toList();


List<Draft3<Long, Long, BigDecimal>> result = entityQuery.queryable(SysUser.class)
        .where(user -> {
          user.birthday().lt(LocalDateTime.now());
        })
        .select(user -> Select.DRAFT.of(
                user.id().count().filter(() -> {
                  user.address().eq("Hangzhou");
                }),
                user.id().count().filter(() -> {
                  user.address().eq("Beijing");
                }),
                user.age().avg().filter(() -> {
                  user.address().eq("Beijing");
                })
        )).toList();
```


## Complete Single-Table Example
First, let's look at a complete single-table query example involving filtering, aggregation, aggregate filtering, projection, and sorting.
```java
List<Draft3<String, Integer, LocalDateTime>> myBlog = easyEntityQuery.queryable(BlogEntity.class)
        .where(b -> {
          b.content().like("my blog");
        })
        .groupBy(b -> GroupKeys.of(b.title()))
        .having(group -> {
          group.groupTable().star().sum().lt(10);
        })
        // The select statement will wrap previous expressions as an inline view (t1 table)
        // If no subsequent chained operations exist, it will expand directly
        .select(group -> Select.DRAFT.of(
                group.key1(),        // value1
                group.groupTable().star().sum().asAnyType(Integer.class),  // value2
                group.groupTable().createTime().max()  // value3
        ))
        // If no orderBy is added, no inline view (t1 table) SQL will be generated
        // Because orderBy operates on the previous select results
        .orderBy(group -> group.value3().desc())
        .limit(2, 2)  // Apply result pagination
        .toList();

-- 第1条sql数据
SELECT
    t1.`value1` AS `value1`,
    t1.`value2` AS `value2`,
    t1.`value3` AS `value3` 
FROM
    (SELECT
        t.`title` AS `value1`,
        SUM(t.`star`) AS `value2`,
        MAX(t.`create_time`) AS `value3` 
    FROM
        `t_blog` t 
    WHERE
        t.`deleted` = false 
        AND t.`content` LIKE '%my blog%' 
    GROUP BY
        t.`title` 
    HAVING
        SUM(t.`star`) < 10) t1 
ORDER BY
    t1.`value3` DESC LIMIT 2,2
```

## Db Support

| 数据库名称          | 包名            | springboot配置   | solon配置        |
| ------------------- | --------------- | ---------------- | ---------------- |
| MySQL               | sql-mysql       | mysql            | mysql            |
| Oracle              | sql-oracle      | oracle           | oracle           |
| PostgreSQL         | sql-pgsql       | pgsql            | pgsql            |
| SqlServer           | sql-mssql       | mssql            | mssql            |
| SqlServer RowNumber | sql-mssql       | mssql_row_number | mssql_row_number |
| H2                  | sql-h2          | h2               | h2               |
| SQLite              | sql-sqlite      | sqlite           | sqlite           |
| ClickHouse          | sql-clickhouse  | clickhouse       | clickhouse       |
| 达梦dameng          | sql-dameng      | dameng           | dameng           |
| 人大金仓KingbaseES  | sql-kingbase-es | kingbase_es      | kingbase_es      |
| 高斯  | sql-gauss-db | gauss-db      | gauss-db      |
| duckdb  | sql-duckdb | duckdb      | duckdb      |
| DB2  | sql-db2 | db2     | db2     |

## Dependency
entity use `@EntityProxy` or `@EntityFileProxy` annotation then build project apt will auto generate java code for proxy
```xml

<properties>
  <easy-query.version>last-version</easy-query.version>
</properties>
<dependency>
<groupId>com.easy-query</groupId>
<artifactId>sql-api-proxy</artifactId>
<version>${easy-query.version}</version>
</dependency>
<dependency>
<groupId>com.easy-query</groupId>
<artifactId>sql-mysql</artifactId>
<version>${easy-query.version}</version>
</dependency>
```

## 🚀  Introduction

- [Usage Guide](#Usage-Guide)
    - [Overview](#Overview)
    - [How to Get the Latest Version](#Get-Latest-Version)
    - [Installation](#Installation)
- [Getting Started](#GettingStarted)
    - [Single Table Query](#Single-Table-Query)
    - [Multi-Table Query](#Multi-Table-Query)
    - [Complex Query](#Complex-Query)
    - [Dynamic Table Names](#Dynamic-Table-Names)
    - [Insert](#Insert)
    - [Update](#Update)
    - [Delete](#Delete)
    - [Union/ALL Query](#Union-ALL-Query)
    - [Subquery](#Subquery)
- [Sharding](#Sharding)
    - [Table Sharding](#Table-Sharding)
    - [Database Sharding](#Database-Sharding)
- [support](#support)


# Usage-Guide
`easy-query` 🚀 is a high-performance, lightweight, and versatile Java/Kotlin object query ORM framework that supports database sharding and read-write separation.

## Overview

`easy-query` is a dependency-free JAVA/Kotlin ORM framework, extremely lightweight, with high performance. It supports single table queries, multi-table queries, union, subqueries, pagination, dynamic table names, VO object query returns, logical deletion, global interception, database column encryption (supporting high-performance LIKE queries), data tracking for differential updates, optimistic locking, multi-tenancy, automatic database sharding, automatic table sharding, read-write separation, and supports full-featured external extension customization of the framework, with strong-typed expressions.

## Get-Latest-Version

[https://central.sonatype.com/](https://central.sonatype.com/) search `com.easy-query`获取最新Installation包

## single table preview

```java

List<Draft3<String, Integer, LocalDateTime>> myBlog = easyEntityQuery.queryable(BlogEntity.class)
        .where(b -> {
            b.content().like("my blog");
            //other conditions
            //b.id().eq("123");
        })
        .groupBy(b -> GroupKeys.of(b.title()))
        .having(group -> {
            group.groupTable().star().sum().lt(10);
        })
        // The select clause will wrap the select and previous expressions into a nested view (t1 table).
        // If there are no subsequent chained operations, it will expand directly; otherwise, it will be represented as a nested view (t1 table).
        .select(group -> Select.DRAFT.of(
                group.key1(),//value1
                group.groupTable().star().sum().asAnyType(Integer.class),//value2
                group.groupTable().createTime().max()//value3
        ))
        // If orderBy is not added, no nested view (t1 table) SQL will be generated
        // because orderBy operates on the results of the preceding select
        .orderBy(group -> group.value3().desc())
        // Apply result limit restrictions
        limit(2,2)
        .toList();


SELECT
    t1.`value1` AS `value1`,
    t1.`value2` AS `value2`,
    t1.`value3` AS `value3` 
FROM
    (SELECT
        t.`title` AS `value1`,
        SUM(t.`star`) AS `value2`,
        MAX(t.`create_time`) AS `value3` 
    FROM
        `t_blog` t 
    WHERE
        t.`deleted` = false 
        AND t.`content` LIKE '%my blog%' 
    GROUP BY
        t.`title` 
    HAVING
        SUM(t.`star`) < 10) t1 
ORDER BY
    t1.`value3` DESC LIMIT 2,2
```

## Installation
Here is the usage guide for spring-boot environment and console mode.
### spring-boot

```xml

<properties>
  <easy-query.version>last-version</easy-query.version>
</properties>
<dependency>
    <groupId>com.easy-query</groupId>
    <artifactId>sql-springboot-starter</artifactId>
    <version>${easy-query.version}</version>
</dependency>
```
### console
for mysql
```xml

<properties>
  <easy-query.version>last-version</easy-query.version>
</properties>
<dependency>
<groupId>com.easy-query</groupId>
<artifactId>sql-api-proxy</artifactId>
<version>${easy-query.version}</version>
</dependency>
        <!-- required data base dialect -->
<dependency>
<groupId>com.easy-query</groupId>
<artifactId>sql-mysql</artifactId>
<version>${easy-query.version}</version>
</dependency>

```

```java
//init DataSource
 HikariDataSource dataSource=new HikariDataSource();
         dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
         dataSource.setUsername("root");
         dataSource.setPassword("root");
         dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
         dataSource.setMaximumPoolSize(20);
//property api client
         EasyQueryClient easyQueryClient=EasyQueryBootstrapper.defaultBuilderConfiguration()
         .setDataSource(dataSource)
         .useDatabaseConfigure(new MySQLDatabaseConfiguration())
         .build();
//entity query api
         EasyEntityQuery easyEntityQuery=new DefaultEasyEntityQuery(easyQueryClient);
```



# Getting-Started
sql script
```sql
create table t_topic
(
  id varchar(32) not null comment '主键ID'primary key,
  stars int not null comment '点赞数',
  title varchar(50) null comment '标题',
  create_time datetime not null comment '创建时间'
)comment '主题表';

create table t_blog
(
  id varchar(32) not null comment '主键ID'primary key,
  deleted tinyint(1) default 0 not null comment '是否Delete',
  create_by varchar(32) not null comment '创建人',
  create_time datetime not null comment '创建时间',
  update_by varchar(32) not null comment '更新人',
  update_time datetime not null comment '更新时间',
  title varchar(50) not null comment '标题',
  content varchar(256) null comment '内容',
  url varchar(128) null comment '博客链接',
  star int not null comment '点赞数',
  publish_time datetime null comment '发布时间',
  score decimal(18, 2) not null comment '评分',
  status int not null comment '状态',
  `order` decimal(18, 2) not null comment '排序',
  is_top tinyint(1) not null comment '是否置顶',
  top tinyint(1) not null comment '是否置顶'
)comment '博客表';
```
query entity
```java

@Data
public class BaseEntity implements Serializable {
  private static final long serialVersionUID = -4834048418175625051L;

  @Column(primaryKey = true)
  private String id;
  /**
   * 创建时间;创建时间
   */
  private LocalDateTime createTime;
  /**
   * Update时间;Update时间
   */
  private LocalDateTime updateTime;
  /**
   * 创建人;创建人
   */
  private String createBy;
  /**
   * Update人;Update人
   */
  private String updateBy;
  /**
   * 是否Delete;是否Delete
   */
  @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
  private Boolean deleted;
}


@Data
@Table("t_topic")
@EntityProxy //or @EntityFileProxy
@ToString
public class Topic implements ProxyEntityAvailable<Topic , TopicProxy> {

  @Column(primaryKey = true)
  private String id;
  private Integer stars;
  private String title;
  private LocalDateTime createTime;

}

//The ProxyEntityAvailable interface can be quickly generated using the IDEA plugin EasyQueryAssistant.


@Data
@Table("t_blog")
@EntityProxy //or @EntityFileProxy
public class BlogEntity extends BaseEntity implements ProxyEntityAvailable<BlogEntity , BlogEntityProxy>{

  /**
   * 标题
   */
  private String title;
  /**
   * 内容
   */
  private String content;
  /**
   * 博客链接
   */
  private String url;
  /**
   * 点赞数
   */
  private Integer star;
  /**
   * 发布时间
   */
  private LocalDateTime publishTime;
  /**
   * 评分
   */
  private BigDecimal score;
  /**
   * 状态
   */
  private Integer status;
  /**
   * 排序
   */
  private BigDecimal order;
  /**
   * 是否置顶
   */
  private Boolean isTop;
  /**
   * 是否置顶
   */
  private Boolean top;
}

```
## Single-Table-Query
```java
Topic topic = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().eq("3"))
                .firstOrNull();      
```
```sql
==> Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ? LIMIT 1
==> Parameters: 3(String)
<== Time Elapsed: 15(ms)
<== Total: 1     
```

## Multi-Table-Query
```java
Topic topic = entityQuery
               .queryable(Topic.class)
               .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
               .where(o -> {
                    o.id().eq("3");
                    o.title().eq("4");
                })
               .firstOrNull();
```
```sql
==> Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? AND t.`title` = ? LIMIT 1
==> Parameters: false(Boolean),3(String),4(String)
<== Time Elapsed: 2(ms)
<== Total: 1
```

## Complex-Query
join + group +page
```java
EasyPageResult<BlogEntity> page = easyEntityQuery
        .queryable(Topic.class)
        .innerJoin(BlogEntity.class,(t1,t2)->t1.id().eq(t2.id()))
        .where((t1,t2)->t2.title().isNotNull())
        .groupBy((t1,t2)->GroupKeys.TABLE2.of(t2.id()))
        .select(g->{
            BlogEntityProxy r = new BlogEntityProxy();
            r.id().set(g.key1());
            r.score().set(g.sum(g.group().t2.score()));
            return r;
        })
        .toPageResult(1, 20);
```
```sql
==> Preparing: SELECT COUNT(*) FROM (SELECT t1.`id` AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id`) t2
  ==> Parameters: false(Boolean)
<== Time Elapsed: 4(ms)
<== Total: 1
==> Preparing: SELECT t1.`id` AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` LIMIT 20
==> Parameters: false(Boolean)
<== Time Elapsed: 2(ms)
<== Total: 20
```

## Dynamic-Table-Names
```java

easyEntityQuery.queryable(BlogEntity.class)
        .asTable(a->"aa_bb_cc")
        .where(o -> o.id().eq("123"))
        .toList();
     
```
```sql
 SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `aa_bb_cc` t WHERE t.`deleted` = ? AND t.`id` = ?  
```

## Insert
```java

Topic topic = new Topic();
topic.setId(String.valueOf(0));
topic.setStars(100);
topic.setTitle("标题0");
topic.setCreateTime(LocalDateTime.now().plusDays(i));

long rows = easyEntityQuery.insertable(topic).executeRows();
```
```sql

//返回结果rows为1
==> Preparing: INSERT INTO `t_topic` (`id`,`stars`,`title`,`create_time`) VALUES (?,?,?,?) 
==> Parameters: 0(String),100(Integer),标题0(String),2023-03-16T21:34:13.287(LocalDateTime)
<== Total: 1
```

## Update
```java
//实体更新
 Topic topic = easyEntityQuery.queryable(Topic.class)
        .where(o -> o.id().eq("7")).firstNotNull("未找到对应的数据");
        String newTitle = "test123" + new Random().nextInt(100);
        topic.setTitle(newTitle);

long rows=easyEntityQuery.updatable(topic).executeRows();
```
```sql
==> Preparing: UPDATE t_topic SET `stars` = ?,`title` = ?,`create_time` = ? WHERE `id` = ?
==> Parameters: 107(Integer),test12364(String),2023-03-27T22:05:23(LocalDateTime),7(String)
<== Total: 1
```
```java
//表达式更新

long rows = easyEntityQuery.updatable(Topic.class)
                    .setColumns(o->{
                        o.stars().set(12);
                    })
                    .where(o->o.id().eq("2"))
                    .executeRows();
//rows为1
easyEntityQuery.updatable(Topic.class)
        .setColumns(o->{
            o.stars().set(12);
        })
        .where(o->o.id().eq("2"))
                    .executeRows(1,"更新失败");
//判断受影响行数并且进行报错,如果当前操作不在事务内执行那么会自动开启事务!!!会自动开启事务!!!会自动开启事务!!!来实现并发更新控制,异常为:EasyQueryConcurrentException 
//抛错后数据将不会被更新
```
```sql
==> Preparing: UPDATE t_topic SET `stars` = ? WHERE `id` = ?
==> Parameters: 12(Integer),2(String)
<== Total: 1
```

## Delete

```java
long l = easyQuery.deletable(Topic.class)
                    .where(o->o.title().eq("title998"))
                    .executeRows();
```
```sql
==> Preparing: DELETE FROM t_topic WHERE `title` = ?
==> Parameters: title998(String)
<== Total: 1
```
```java
Topic topic = easyQuery.queryable(Topic.class).whereId("997").firstNotNull("未找到当前主题数据");
long l = easyQuery.deletable(topic).executeRows();
```
```sql
==> Preparing: DELETE FROM t_topic WHERE `id` = ?
==> Parameters: 997(String)
<== Total: 1
```

## Union-ALL-Query
```java
Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class);
Queryable<Topic> q2 = easyQuery
        .queryable(Topic.class);
Queryable<Topic> q3 = easyQuery
        .queryable(Topic.class);
List<Topic> list = q1.union(q2, q3).where(o -> o.eq(Topic::getId, "123321")).toList();
```
```sql

==> Preparing: SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t UNION SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t UNION SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t) t1 WHERE t1.`id` = ?
==> Parameters: 123321(String)
<== Time Elapsed: 19(ms)
<== Total: 0
```

## Subquery
### inSubquery
```java
EntityQueryable<StringProxy, String> idQuery = easyEntityQuery.queryable(BlogEntity.class)
        .where(o -> o.id().eq("1" ))
        .select(o -> new StringProxy(o.id()));

        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
        .where(o -> o.id().in(idQuery))
        .toList();
```
```sql
==> Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)
  ==> Parameters: false(Boolean),1(String)
<== Time Elapsed: 6(ms)
<== Total: 1 
```

### existsSubquery
```java

EntityQueryable<BlogEntityProxy, BlogEntity> where = easyEntityQuery.queryable(BlogEntity.class)
        .where(o -> o.id().eq("1" ));

List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
        .where(o -> {
        o.exists(() -> where.where(q -> q.id().eq(o.id())));
        }).toList();
```
```sql
==> Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ? AND t1.`id` = t.`id`)
==> Parameters: false(Boolean),1(String)
<== Time Elapsed: 2(ms)
<== Total: 1
```

# Sharding
`easy-query`支持Table Sharding、Database Sharding、Table Sharding+Database Sharding
## Table-Sharding
```java
//创建Sharding对象
@Data
@Table(value = "t_topic_sharding_time",shardingInitializer = TopicShardingTimeShardingInitializer.class)
@ToString
public class TopicShardingTime {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    @ShardingTableKey
    private LocalDateTime createTime;
}
//Sharding初始化器很简单 假设我们是2020年1月到2023年5月也就是当前时间进行Sharding那么要生成对应的Sharding表每月一张
public class TopicShardingTimeShardingInitializer extends AbstractShardingMonthInitializer<TopicShardingTime> {

    @Override
    protected LocalDateTime getBeginTime() {
        return LocalDateTime.of(2020, 1, 1, 1, 1);
    }

    @Override
    protected LocalDateTime getEndTime() {
        return LocalDateTime.of(2023, 5, 1, 0, 0);
    }


    @Override
    public void configure0(ShardingEntityBuilder<TopicShardingTime> builder) {

////以下条件可以选择配置也可以不配置用于优化Sharding性能
//        builder.paginationReverse(0.5,100)
//                .ascSequenceConfigure(new TableNameStringComparator())
//                .addPropertyDefaultUseDesc(TopicShardingTime::getCreateTime)
//                .defaultAffectedMethod(false, ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.COUNT,ExecuteMethodEnum.FIRST)
//                .useMaxShardingQueryLimit(2,ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.FIRST);

    }
}
//Sharding时间路由规则按月然后beanSharding属性就是LocalDateTime也可以自定义实现
public class TopicShardingTimeTableRoute extends AbstractMonthTableRoute<TopicShardingTime> {

    @Override
    protected LocalDateTime convertLocalDateTime(Object shardingValue) {
        return (LocalDateTime)shardingValue;
    }
}

```
[数据库脚本参考源码](https://github.com/xuejmnet/easy-query/blob/main/sql-test/src/main/resources/mysql-init-sqk-easy-sharding.sql)

其中`shardingInitializer`为Sharding初始化器用来初始化告诉框架有多少Sharding的表名(支持动态添加)

`ShardingTableKey`表示哪个字段作为Sharding键(Sharding键不等于主键)

执行sql
```java
LocalDateTime beginTime = LocalDateTime.of(2021, 1, 1, 1, 1);
LocalDateTime endTime = LocalDateTime.of(2021, 5, 2, 1, 1);
Duration between = Duration.between(beginTime, endTime);
long days = between.toDays();
List<TopicShardingTime> list = easyQuery.queryable(TopicShardingTime.class)
        .where(o->o.rangeClosed(TopicShardingTime::getCreateTime,beginTime,endTime))
        .orderByAsc(o -> o.column(TopicShardingTime::getCreateTime))
        .toList();
```
```sql


==> SHARDING_EXECUTOR_2, name:ds2020, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_time_202101` t WHERE t.`create_time` >= ? AND t.`create_time` <= ? ORDER BY t.`create_time` ASC
==> SHARDING_EXECUTOR_3, name:ds2020, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_time_202102` t WHERE t.`create_time` >= ? AND t.`create_time` <= ? ORDER BY t.`create_time` ASC
==> SHARDING_EXECUTOR_2, name:ds2020, Parameters: 2021-01-01T01:01(LocalDateTime),2021-05-02T01:01(LocalDateTime)
==> SHARDING_EXECUTOR_3, name:ds2020, Parameters: 2021-01-01T01:01(LocalDateTime),2021-05-02T01:01(LocalDateTime)
<== SHARDING_EXECUTOR_3, name:ds2020, Time Elapsed: 3(ms)
<== SHARDING_EXECUTOR_2, name:ds2020, Time Elapsed: 3(ms)
==> SHARDING_EXECUTOR_2, name:ds2020, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_time_202103` t WHERE t.`create_time` >= ? AND t.`create_time` <= ? ORDER BY t.`create_time` ASC
==> SHARDING_EXECUTOR_3, name:ds2020, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_time_202104` t WHERE t.`create_time` >= ? AND t.`create_time` <= ? ORDER BY t.`create_time` ASC
==> SHARDING_EXECUTOR_2, name:ds2020, Parameters: 2021-01-01T01:01(LocalDateTime),2021-05-02T01:01(LocalDateTime)
==> SHARDING_EXECUTOR_3, name:ds2020, Parameters: 2021-01-01T01:01(LocalDateTime),2021-05-02T01:01(LocalDateTime)
<== SHARDING_EXECUTOR_3, name:ds2020, Time Elapsed: 2(ms)
<== SHARDING_EXECUTOR_2, name:ds2020, Time Elapsed: 2(ms)
==> main, name:ds2020, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_time_202105` t WHERE t.`create_time` >= ? AND t.`create_time` <= ? ORDER BY t.`create_time` ASC
==> main, name:ds2020, Parameters: 2021-01-01T01:01(LocalDateTime),2021-05-02T01:01(LocalDateTime)
<== main, name:ds2020, Time Elapsed: 2(ms)
<== Total: 122
```


## Database-Sharding

```java

@Data
@Table(value = "t_topic_sharding_ds",shardingInitializer = DataSourceAndTableShardingInitializer.class)
@ToString
public class TopicShardingDataSource {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    @ShardingDataSourceKey
    private LocalDateTime createTime;
}
public class DataSourceShardingInitializer implements EntityShardingInitializer<TopicShardingDataSource> {
    @Override
    public void configure(ShardingEntityBuilder<TopicShardingDataSource> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        String tableName = entityMetadata.getTableName();
        List<String> tables = Collections.singletonList(tableName);
        LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
            put("ds2020", tables);
            put("ds2021", tables);
            put("ds2022", tables);
            put("ds2023", tables);
        }};
        builder.actualTableNameInit(initTables);


    }
}
//Database Sharding数据源路由规则
public class TopicShardingDataSourceRoute extends AbstractDataSourceRoute<TopicShardingDataSource> {
    @Override
    protected RouteFunction<String> getRouteFilter(TableAvailable table, Object shardingValue, ShardingOperatorEnum shardingOperator, boolean withEntity) {
        LocalDateTime createTime = (LocalDateTime) shardingValue;
        String dataSource = "ds" + createTime.getYear();
        switch (shardingOperator){
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                return ds-> dataSource.compareToIgnoreCase(ds)<=0;
            case LESS_THAN:
            {
                //如果小于月初那么月初的表是不需要被查询的
                LocalDateTime timeYearFirstDay = LocalDateTime.of(createTime.getYear(),1,1,0,0,0);
                if(createTime.isEqual(timeYearFirstDay)){
                    return ds->dataSource.compareToIgnoreCase(ds)>0;
                }
                return ds->dataSource.compareToIgnoreCase(ds)>=0;
            }
            case LESS_THAN_OR_EQUAL:
                return ds->dataSource.compareToIgnoreCase(ds)>=0;

            case EQUAL:
                return ds->dataSource.compareToIgnoreCase(ds)==0;
            default:return t->true;
        }
    }
}

```

```java
LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
Duration between = Duration.between(beginTime, endTime);
long days = between.toDays();
EasyPageResult<TopicShardingDataSource> pageResult = easyQuery.queryable(TopicShardingDataSource.class)
        .orderByAsc(o -> o.column(TopicShardingDataSource::getCreateTime))
        .toPageResult(1, 33);
```
```sql

==> SHARDING_EXECUTOR_23, name:ds2022, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_ds` t ORDER BY t.`create_time` ASC LIMIT 33
==> SHARDING_EXECUTOR_11, name:ds2021, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_ds` t ORDER BY t.`create_time` ASC LIMIT 33
==> SHARDING_EXECUTOR_2, name:ds2020, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_ds` t ORDER BY t.`create_time` ASC LIMIT 33
==> SHARDING_EXECUTOR_4, name:ds2023, Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic_sharding_ds` t ORDER BY t.`create_time` ASC LIMIT 33
<== SHARDING_EXECUTOR_4, name:ds2023, Time Elapsed: 4(ms)
<== SHARDING_EXECUTOR_23, name:ds2022, Time Elapsed: 4(ms)
<== SHARDING_EXECUTOR_2, name:ds2020, Time Elapsed: 4(ms)
<== SHARDING_EXECUTOR_11, name:ds2021, Time Elapsed: 6(ms)
<== Total: 33
```

## support
<img src="./imgs/zfb.jpg" title="JetBrains" width=200 />
<img src="./imgs/wx.jpg" title="JetBrains" width=222 />
[博客](https://www.cnblogs.com/xuejiaming)


个人QQ:326308290(欢迎技术支持提供您宝贵的意见)

个人邮箱:326308290@qq.com