package com.easy.query.dameng.migration;

import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.migration.AbstractDatabaseMigrationProvider;
import com.easy.query.core.migration.ColumnDbTypeResult;
import com.easy.query.core.migration.EntityMigrationMetadata;
import com.easy.query.core.migration.MigrationCommand;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.migration.TableForeignKeyResult;
import com.easy.query.core.migration.TableIndexResult;
import com.easy.query.core.migration.commands.DefaultMigrationCommand;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * create time 2025/1/19 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDatabaseMigrationProvider extends AbstractDatabaseMigrationProvider {
    public DamengDatabaseMigrationProvider(DataSource dataSource, SQLKeyword sqlKeyword, MigrationEntityParser migrationEntityParser) {
        super(dataSource, sqlKeyword, migrationEntityParser);
    }

//    @Override
//    public boolean databaseExists() {
//        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, , Collections.singletonList(getDatabaseName()));
//        return EasyCollectionUtil.isNotEmpty(maps);
//    }
//
//    @Override
//    public MigrationCommand createDatabaseCommand() {
//        log.warn("dameng not support create database command.");
//        return null;


    @Override
    public void createDatabaseIfNotExists() {

    }

    @Override
    public String databaseExistSQL(String databaseName) {
       return String.format("select 1 from sys.dba_users where username='%s'",databaseName);
    }

    @Override
    public String createDatabaseSQL(String databaseName) {
        throw new UnsupportedOperationException("dameng not support create database command.");
    }

    ////        String databaseSQL = "CREATE SCHEMA IF NOT EXISTS " + getQuoteSQLName(databaseName) + ";";
////        return new DefaultMigrationCommand(null, databaseSQL);
//    }


    @Override
    public boolean tableExists(String schema,String tableName) {
        ArrayList<Object> sqlParameters = new ArrayList<>();
        if(EasyStringUtil.isBlank(schema)){
            sqlParameters.add("SYSDBA");
        }else{
            sqlParameters.add(schema);
        }
        sqlParameters.add(tableName);
        List<Map<String, Object>> maps = EasyDatabaseUtil.sqlQuery(dataSource, "select * from all_tab_comments where owner=? and table_name=?", sqlParameters);
        return EasyCollectionUtil.isNotEmpty(maps);
    }

    @Override
    public MigrationCommand renameTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String sql = String.format("execute immediate 'ALTER TABLE %s RENAME TO  %s';", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getOldTableName()), getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()));
        return new DefaultMigrationCommand(entityMetadata, sql);
    }

    @Override
    public MigrationCommand createTable(EntityMigrationMetadata entityMigrationMetadata) {

        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
//        sql.append("BEGIN")
//                .append(newLine);
        StringBuilder columnCommentSQL = new StringBuilder();


        String tableComment = getTableComment(entityMigrationMetadata,"''");
        if (EasyStringUtil.isNotBlank(tableComment)) {
            String format = String.format("execute immediate 'COMMENT ON TABLE %s IS %s';", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()), tableComment);
            columnCommentSQL.append(newLine)
                    .append(format)
                    .append(newLine);
        }

        sql.append("execute immediate 'CREATE TABLE ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName())).append(" ( ");
        for (ColumnMetadata column : entityMetadata.getColumns()) {
            sql.append(newLine)
                    .append(getQuoteSQLName(column.getName()))
                    .append(" ");
            ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
            sql.append(columnDbTypeResult.columnType);
            boolean nullable = migrationEntityParser.isNullable(entityMigrationMetadata, column);
            if (nullable) {
                sql.append(" NULL ");
            } else {
                sql.append(" NOT NULL ");
            }
            if (column.isGeneratedKey()) {
                sql.append(" GENERATED BY DEFAULT AS IDENTITY ");
            }else{
                if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
                    sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
                }
            }
//            if (column.isGeneratedKey()) {
//                sql.append(" GENERATED BY DEFAULT AS IDENTITY");
//            }
            String columnComment = getColumnComment(entityMigrationMetadata, column,"''");
            if (EasyStringUtil.isNotBlank(columnComment)) {
                String format = String.format("execute immediate 'COMMENT ON COLUMN %s.%s IS %s';", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()), getQuoteSQLName(column.getName()), columnComment);
                columnCommentSQL.append(newLine)
                        .append(format);
            }
            sql.append(",");
        }
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        if (EasyCollectionUtil.isNotEmpty(keyProperties)) {
            sql.append(newLine)
                    .append(" CONSTRAINT ").append(getQuoteSQLName(entityMetadata.getTableName()+"_primary_key")).append(" ").append(" PRIMARY KEY (");
            int i = keyProperties.size();
            for (String keyProperty : keyProperties) {
                i--;
                ColumnMetadata keyColumn = entityMetadata.getColumnNotNull(keyProperty);
                sql.append(getQuoteSQLName(keyColumn.getName()));
                if (i > 0) {
                    sql.append(", ");
                } else {
                    sql.append(")");
                }
            }
        }else{
            sql.deleteCharAt(sql.length() - 1);
        }
//        sql.append(newLine).append(")");
        sql.append(newLine).append(") LOGGING ';");
        if(columnCommentSQL.length()>0){
            sql.append(newLine).append(columnCommentSQL);
        }

//        sql.append(newLine).append("END;");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }

    @Override
    protected MigrationCommand renameColumn(EntityMigrationMetadata entityMigrationMetadata, String renameFrom, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        String sql = String.format("execute immediate 'ALTER TABLE %s RENAME COLUMN %s TO  %s';", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()), getQuoteSQLName(renameFrom), getQuoteSQLName(column.getName()));
//
//        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
//        sql.append(columnDbTypeResult.columnType);
//        if (isNullable(entityMigrationMetadata, column)) {
//            sql.append(" NULL");
//        } else {
//            sql.append(" NOT NULL");
//        }

//        String columnComment = getColumnComment(entityMigrationMetadata, column);
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(newLine);
//            sql.append(" COMMENT ON COLUMN ").append(tableName).append(" IS ").append(columnComment);
//            sql.append(";");
//        }
        return new DefaultMigrationCommand(entityMetadata, sql);
    }

    @Override
    protected MigrationCommand addColumn(EntityMigrationMetadata entityMigrationMetadata, ColumnMetadata column) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        String format = String.format("execute immediate 'ALTER TABLE %s ADD (", getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()));
        sql.append(format).append(getQuoteSQLName(column.getName()));

        ColumnDbTypeResult columnDbTypeResult = getColumnDbType(entityMigrationMetadata, column);
        sql.append(columnDbTypeResult.columnType);
        if (migrationEntityParser.isNullable(entityMigrationMetadata, column)) {
            sql.append(" NULL");
        } else {
            sql.append(" NOT NULL");
        }
        if (EasyStringUtil.isNotBlank(columnDbTypeResult.defValue)) {
            sql.append(" DEFAULT ").append(columnDbTypeResult.defValue);
        }
        sql.append(")';");
//
//        String columnComment = getColumnComment(entityMigrationMetadata, column);
//        if (EasyStringUtil.isNotBlank(columnComment)) {
//            sql.append(newLine);
//            sql.append(" COMMENT ON COLUMN ").append(tableName).append(" IS ").append(columnComment);
//            sql.append(";");
//        }
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }


    @Override
    public MigrationCommand dropTable(EntityMigrationMetadata entityMigrationMetadata) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        return new DefaultMigrationCommand(entityMetadata, "execute immediate 'DROP TABLE " + getQuoteSQLName(entityMetadata.getSchemaOrNull(),entityMetadata.getTableName()) + "';");
    }
    @Override
    protected MigrationCommand createIndex(EntityMigrationMetadata entityMigrationMetadata, TableIndexResult tableIndex) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("execute immediate 'CREATE ");
        if (tableIndex.unique) {
            sql.append("UNIQUE INDEX ");
        } else {
            sql.append("INDEX ");
        }
        sql.append(tableIndex.indexName);
        sql.append(" ON ").append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()));
        sql.append(" (");
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < tableIndex.fields.size(); i++) {
            TableIndexResult.EntityField entityField = tableIndex.fields.get(i);
            String column = getQuoteSQLName(entityField.columnName) + " " + (entityField.asc ? "ASC" : "DESC");
            joiner.add(column);
        }
        sql.append(joiner);
        sql.append(")';");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }
    @Override
    protected MigrationCommand createTableForeignKey(EntityMigrationMetadata entityMigrationMetadata, TableForeignKeyResult tableForeignKeyResult) {
        EntityMetadata entityMetadata = entityMigrationMetadata.getEntityMetadata();
        StringBuilder sql = new StringBuilder();
        sql.append("execute immediate 'ALTER TABLE ");
        sql.append(getQuoteSQLName(entityMetadata.getSchemaOrNull(), entityMetadata.getTableName()));
        sql.append(" ADD CONSTRAINT ").append(tableForeignKeyResult.name);
        sql.append(" FOREIGN KEY (");

        String selfColumns = Arrays.stream(tableForeignKeyResult.selfColumn).map(self -> getQuoteSQLName(self)).collect(Collectors.joining(","));
        sql.append(selfColumns);
        sql.append(") REFERENCES ");
        sql.append(getQuoteSQLName(tableForeignKeyResult.targetTable));
        sql.append(" (");
        String targetColumns = Arrays.stream(tableForeignKeyResult.targetColumn).map(target -> getQuoteSQLName(target)).collect(Collectors.joining(","));
        sql.append(targetColumns);
        sql.append(")");

        if (EasyStringUtil.isNotBlank(tableForeignKeyResult.action)) {
            sql.append(" ").append(tableForeignKeyResult.action).append(" ");
        }
        sql.append("';");
        return new DefaultMigrationCommand(entityMetadata, sql.toString());
    }
}
