CREATE TABLE IF NOT EXISTS `t_def_table` (
    `id` VARCHAR (100) PRIMARY KEY,
    `user_name` VARCHAR (100),
    `nickname` VARCHAR (128),
    `enable` BOOLEAN,
    `score` DECIMAL (20, 2),
    `mobile` VARCHAR (32),
    `avatar` VARCHAR (256),
    `number` Integer,
    `status` Integer,
    `created` DATETIME,
    `options` VARCHAR (2048)
    );

CREATE TABLE IF NOT EXISTS `t_def_table_left1` (
    `id` VARCHAR (100) PRIMARY KEY,
    `def_id` VARCHAR (100),
    `user_name` VARCHAR (100),
    `nickname` VARCHAR (128),
    `enable` BOOLEAN,
    `score` DECIMAL (20, 2),
    `mobile` VARCHAR (32),
    `avatar` VARCHAR (256),
    `number` Integer,
    `status` Integer,
    `created` DATETIME,
    `options` VARCHAR (2048)
    );

CREATE TABLE IF NOT EXISTS `t_def_table_left2` (
    `id` VARCHAR (100) PRIMARY KEY,
    `def_id` VARCHAR (100),
    `def1_id` VARCHAR (100),
    `user_name` VARCHAR (100),
    `nickname` VARCHAR (128),
    `enable` BOOLEAN,
    `score` DECIMAL (20, 2),
    `mobile` VARCHAR (32),
    `avatar` VARCHAR (256),
    `number` Integer,
    `status` Integer,
    `created` DATETIME,
    `options` VARCHAR (2048)
    );

CREATE TABLE IF NOT EXISTS `t_def_table_left3` (
    `id` VARCHAR (100) PRIMARY KEY,
    `def_id` VARCHAR (100),
    `def1_id` VARCHAR (100),
    `def2_id` VARCHAR (100),
    `user_name` VARCHAR (100),
    `nickname` VARCHAR (128),
    `enable` BOOLEAN,
    `score` DECIMAL (20, 2),
    `mobile` VARCHAR (32),
    `avatar` VARCHAR (256),
    `number` Integer,
    `status` Integer,
    `created` DATETIME,
    `options` VARCHAR (2048)
    );

CREATE TABLE IF NOT EXISTS `t_all_type` (
    `id` VARCHAR (100) PRIMARY KEY,
    `number_decimal` DECIMAL (20, 2),
    `number_float` FLOAT (20),
    `number_double` DOUBLE (20),
    `number_short` SMALLINT,
    `number_integer` INT,
    `number_long` BIGINT,
    `number_byte` TINYINT,
    `number_float_basic` FLOAT (20),
    `number_double_basic` DOUBLE (20),
    `number_short_basic` SMALLINT,
    `number_integer_basic` INT,
    `number_long_basic` BIGINT,
    `number_byte_basic` TINYINT,
    `enable_basic` BOOLEAN,
    `time_local_date_time` DATETIME,
    `time_local_date` DATE,
    `time_local_time` TIME,
    `sql_date` DATETIME,
    `only_date` DATETIME,
    `only_time` TIME,
    `enable` BOOLEAN,
    `j_blob` BLOB,
    `j_clob` CLOB,
    `value` VARCHAR (2048),
    `uid` UUID
    );

CREATE TABLE IF NOT EXISTS `t_all_type_sharding_0` (
    `id` VARCHAR (100) PRIMARY KEY,
    `number_decimal` DECIMAL (20, 2),
    `number_float` FLOAT (20),
    `number_double` DOUBLE (20),
    `number_short` SMALLINT,
    `number_integer` INT,
    `number_long` BIGINT,
    `number_byte` TINYINT,
    `number_float_basic` FLOAT (20),
    `number_double_basic` DOUBLE (20),
    `number_short_basic` SMALLINT,
    `number_integer_basic` INT,
    `number_long_basic` BIGINT,
    `number_byte_basic` TINYINT,
    `enable_basic` BOOLEAN,
    `time_local_date_time` DATETIME,
    `time_local_date` DATE,
    `time_local_time` TIME,
    `sql_date` DATETIME,
    `only_date` DATETIME,
    `only_time` TIME,
    `enable` BOOLEAN,
    `j_blob` BLOB,
    `j_clob` CLOB,
    `value` VARCHAR (2048),
    `uid` UUID
    );

CREATE TABLE IF NOT EXISTS `t_all_type_sharding_1` (
    `id` VARCHAR (100) PRIMARY KEY,
    `number_decimal` DECIMAL (20, 2),
    `number_float` FLOAT (20),
    `number_double` DOUBLE (20),
    `number_short` SMALLINT,
    `number_integer` INT,
    `number_long` BIGINT,
    `number_byte` TINYINT,
    `number_float_basic` FLOAT (20),
    `number_double_basic` DOUBLE (20),
    `number_short_basic` SMALLINT,
    `number_integer_basic` INT,
    `number_long_basic` BIGINT,
    `number_byte_basic` TINYINT,
    `enable_basic` BOOLEAN,
    `time_local_date_time` DATETIME,
    `time_local_date` DATE,
    `time_local_time` TIME,
    `sql_date` DATETIME,
    `only_date` DATETIME,
    `only_time` TIME,
    `enable` BOOLEAN,
    `j_blob` BLOB,
    `j_clob` CLOB,
    `value` VARCHAR (2048),
    `uid` UUID
    );


CREATE TABLE IF NOT EXISTS `t_tb_order` (
    `id` VARCHAR (100) PRIMARY KEY,
    `uid` VARCHAR (100),
    `uname` VARCHAR (128),
    `price` DECIMAL (20, 2)
    );

CREATE TABLE IF NOT EXISTS `t_tb_account` (
    `id` VARCHAR (100) PRIMARY KEY,
    `uid` VARCHAR (100),
    `uname` VARCHAR (128),
    `account` VARCHAR (128)
    );
