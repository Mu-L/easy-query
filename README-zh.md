<p align="center">
  <img height="340" src="./imgs/logo.png">
</p>

# [English](https://github.com/dromara/easy-query/blob/main/README.md) | 中文

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
    <a target="_blank" href='https://gitee.com/xuejm/easy-query'>
		<img src='https://gitee.com/xuejm/easy-query/badge/star.svg' alt='Gitee star'/>
	</a>
    <a target="_blank" href='https://github.com/xuejmnet/easy-query'>
		<img src="https://img.shields.io/github/stars/xuejmnet/easy-query.svg?logo=github" alt="Github star"/>
	</a>
      <a href='https://gitcode.com/dromara/easy-query'>
        <img src='https://gitcode.com/dromara/easy-query/star/badge.svg' alt='fork'>
      </a>
</p>


- [GITHUB](https://github.com/dromara/easy-query)  GITHUB镜像地址
- [GITEE](https://gitee.com/dromara/easy-query)  GITEE镜像地址

## 📚 文档
<div align="center">

[GITHUB文档地址](https://xuejmnet.github.io/easy-query-doc/) | [官方 文档地址](https://www.easy-query.com/easy-query-doc/)

</div>

## 🐧 QQ群:170029046
<div align="center">

<img src="./imgs/qrcode.jpg" title="JetBrains" width=122 />

</div>

## 五大隐式 🔥🔥🔥
- [x] 隐式join `OneToOne`、`ManyToOne` 自动实现join查询筛选、排序和结果获取
- [x] 隐式子查询 `OneToMany`、`ManyToMany` 自动实现子查询查询筛选、排序和聚合函数结果获取
- [x] 隐式分组 `OneToMany`、`ManyToMany` 自动实现子查询优化合并将多个子查询合并成一个分组查询支持筛选、排序和聚合函数结果获取
- [x] 隐式分区分组 `OneToMany`、`ManyToMany` 自动实现第一个、第N个数据的筛选、排序和聚合函数结果获取
- [x] 隐式CASE WHEN表达式 `属性.聚合函数.筛选`，`o.age().sum().filter(()->o.name().like("123"))`

`Company` OneToMany `SysUser`

### 隐式join
查询用户条件是用户所属企业是xx公司
```java

List<SysUser> userInXXCompany = entityQuery.queryable(SysUser.class)
        .where(user -> {
            user.company().name().like("xx公司");
        })
        .orderBy(user -> {
            user.company().registerMoney().desc();
            user.birthday().asc();
        }).toList();
```


### 隐式子查询
查询企业条件是企业必须有小明这个用户并且小明这个用户是2000年1月1日之后出生
```java

List<Company> companies = entityQuery.queryable(Company.class)
        .where(company -> {
          company.users().any(u -> u.name().like("小明"));
          company.users().where(u -> u.name().like("小明")).max(u -> u.birthday()).gt(LocalDateTime.of(2000,1,1,0,0,0));
        }).toList();
```
### 隐式分组
查询企业条件是企业必须有小明这个用户并且小明这个用户是2000年1月1日之后出生

合并两个子查询使用join group合并
```java

List<Company> companies = entityQuery.queryable(Company.class)
        //在where中的两个子查询会进行合并
        .subQueryToGroupJoin(company -> company.users())
        .where(company -> {
          company.users().any(u -> u.name().like("小明"));
          company.users().where(u -> u.name().like("小明")).max(u -> u.birthday()).gt(LocalDateTime.of(2000,1,1,0,0,0));
        }).toList();
```

### 隐式分区分组
查询企业要求企业的所有员工年龄最小的那个是小明并且生日是2000年1月1日之前

```java

List<Company> companies = entityQuery.queryable(Company.class)
        .where(company -> {
          company.users().orderBy(u->u.birthday().desc()).first().name().eq("小明");
          company.users().orderBy(u->u.birthday().desc()).element(0).birthday().lt(LocalDateTime.of(2000,1,1,0,0,0));
        }).toList();
```

### 隐式CASE WHEN表达式
```java

List<Draft2<LocalDateTime, Long>> customVO = entityQuery.queryable(SysUser.class)
        .where(user -> {
            user.birthday().lt(LocalDateTime.now());
        }).groupBy(user -> GroupKeys.of(user.companyId()))
        .select(group -> Select.DRAFT.of(
                group.groupTable().birthday().max().filter(() -> {
                    group.groupTable().name().like("小明");
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


## 单表完整案例
首先我们来看一下完整版本的单表查询,涉及到筛选、聚合、聚合筛选、映射查询、排序
```java

List<Draft3<String, Integer, LocalDateTime>> myBlog = easyEntityQuery.queryable(BlogEntity.class)
        .where(b -> {
            b.content().like("my blog");
        })
        .groupBy(b -> GroupKeys.of(b.title()))
        .having(group -> {
            group.groupTable().star().sum().lt(10);
        })
        //select那么会将select和之前的表达式作为一个内嵌视图(t1表)进行包裹如果后续没有链式配置则会展开否则以内嵌视图(t1表)表示
        .select(group -> Select.DRAFT.of(
                group.key1(),//value1
                group.groupTable().star().sum().asAnyType(Integer.class),//value2
                group.groupTable().createTime().max()//value3
        ))
        //如果不添加orderBy则不会生成内嵌视图(t1表)sql
        //因为orderBy是对前面的select结果进行orderBy
        .orderBy(group -> group.value3().desc())
        limit(2,2)//对结果进行限制返回
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


## 数据库支持

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


## 依赖
### 使用代理
entity对象添加注解 `@EntityProxy` 然后build project apt 将会自动生成代理对象的java代码
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
## 🚀 介绍

- [使用介绍](#使用介绍)
    - [简介](#简介)
    - [如何获取最新版本](#如何获取最新版本)
    - [安装](#安装)
- [开始](#开始)
    - [单表查询](#单表查询)
    - [多表查询](#多表查询)
    - [复杂查询](#复杂查询)
    - [动态表名](#动态表名)
    - [新增](#新增)
    - [修改](#修改)
    - [删除](#删除)
    - [联合查询](#联合查询)
    - [子查询](#子查询)
- [分片](#分片)
    - [分表](#分表)
    - [分库](#分库)
- [捐赠](#捐赠)


# 使用介绍
`easy-query` 🚀 一款高性能、轻量级、多功能的Java/Kotlin对象查询ORM框架支持分库分表读写分离

## 简介

`easy-query`是一款无任何依赖的JAVA/Kotlin ORM
框架，十分轻量，拥有非常高的性能，支持单表查询、多表查询、union、子查询、分页、动态表名、VO对象查询返回、逻辑删、全局拦截、数据库列加密(
支持高性能like查询)、数据追踪差异更新、乐观锁、多租户、自动分库、自动分表、读写分离，支持框架全功能外部扩展定制，拥有强类型表达式。


## 如何获取最新版本

[https://central.sonatype.com/](https://central.sonatype.com/) 搜索`com.easy-query`获取最新安装包

## 安装
以下是`spring-boot`环境和控制台模式的安装
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
以mysql为例
```xml

<properties>
  <easy-query.version>last-version</easy-query.version>
</properties>
        <!--  not required support proxy 非必须  提供了代理模式支持apt模式以非lambda形式的强类型sql语法 -->
<dependency>
<groupId>com.easy-query</groupId>
<artifactId>sql-api-proxy</artifactId>
<version>${easy-query.version}</version>
</dependency>
        <!--  not required support labda  提供了以java语法强类型,如果不引用也可以使用只是无法使用lambda表达式来表示属性只能用字符串 -->
<dependency>
<groupId>com.easy-query</groupId>
<artifactId>sql-api4j</artifactId>
<version>${easy-query.version}</version>
</dependency>
        <!-- required databaase sql  这边以mysql为例 其实不需要添加下面的包也可以运行,指示默认的个别数据库行为语句没办法生成 -->
<dependency>
<groupId>com.easy-query</groupId>
<artifactId>sql-mysql</artifactId>
<version>${easy-query.version}</version>
</dependency>

```

```java
//初始化连接池
 HikariDataSource dataSource=new HikariDataSource();
         dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
         dataSource.setUsername("root");
         dataSource.setPassword("root");
         dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
         dataSource.setMaximumPoolSize(20);
//非强类型api
         EasyQueryClient easyQueryClient=EasyQueryBootstrapper.defaultBuilderConfiguration()
         .setDataSource(dataSource)
         .useDatabaseConfigure(new MySQLDatabaseConfiguration())
         .build();
//强类型api
         EasyQuery easyQuery=new DefaultEasyQuery(easyQueryClient);
```



# 开始
sql脚本
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
  deleted tinyint(1) default 0 not null comment '是否删除',
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
查询对象
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
## 单表查询
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

## 多表查询
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
==> Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? LIMIT 1
==> Parameters: false(Boolean),3(String)
<== Time Elapsed: 2(ms)
<== Total: 1
```

## 复杂查询
join + group +分页
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

## 动态表名
```java
easyEntityQuery.queryable(BlogEntity.class)
        .asTable(a->"aa_bb_cc")
        .where(o -> o.id().eq("123"))
        .toList();
     
```
```sql
 SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `aa_bb_cc` t WHERE t.`deleted` = ? AND t.`id` = ?  
```

## 新增
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

## 修改
```java
//实体更新
 Topic topic = easyEntityQuery.queryable(Topic.class)
        .where(o -> o.id().eq("7")).firstNotNull("未找到对应的数据");
        String newTitle = "test123" + new Random().nextInt(100);
        topic.setTitle(newTitle);

long rows=easyQuery.updatable(topic).executeRows();
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

## 删除

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

## 联合查询
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

## 子查询
### in子查询
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

### exists子查询
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

# 分片
`easy-query`支持分表、分库、分表+分库
## 分表
```java
//创建分片对象
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
//分片初始化器很简单 假设我们是2020年1月到2023年5月也就是当前时间进行分片那么要生成对应的分片表每月一张
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

////以下条件可以选择配置也可以不配置用于优化分片性能
//        builder.paginationReverse(0.5,100)
//                .ascSequenceConfigure(new TableNameStringComparator())
//                .addPropertyDefaultUseDesc(TopicShardingTime::getCreateTime)
//                .defaultAffectedMethod(false, ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.COUNT,ExecuteMethodEnum.FIRST)
//                .useMaxShardingQueryLimit(2,ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.FIRST);

    }
}
//分片时间路由规则按月然后bean分片属性就是LocalDateTime也可以自定义实现
public class TopicShardingTimeTableRoute extends AbstractMonthTableRoute<TopicShardingTime> {

    @Override
    protected LocalDateTime convertLocalDateTime(Object shardingValue) {
        return (LocalDateTime)shardingValue;
    }
}

```
[数据库脚本参考源码](https://github.com/xuejmnet/easy-query/blob/main/sql-test/src/main/resources/mysql-init-sqk-easy-sharding.sql)

其中`shardingInitializer`为分片初始化器用来初始化告诉框架有多少分片的表名(支持动态添加)

`ShardingTableKey`表示哪个字段作为分片键(分片键不等于主键)

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


## 分库

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
//分库数据源路由规则
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

## 捐赠
<img src="./imgs/zfb.jpg" title="JetBrains" width=200 />
<img src="./imgs/wx.jpg" title="JetBrains" width=222 />
[博客](https://www.cnblogs.com/xuejiaming)


个人QQ:326308290(欢迎技术支持提供您宝贵的意见)

个人邮箱:326308290@qq.com