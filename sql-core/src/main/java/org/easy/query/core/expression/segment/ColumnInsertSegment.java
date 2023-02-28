package org.easy.query.core.expression.segment;

import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class ColumnInsertSegment implements SqlSegment {


    protected final SqlTableInfo table;
    protected final String propertyName;
    protected final SqlContext sqlContext;
    protected String alias;

    public ColumnInsertSegment(SqlTableInfo table, String propertyName, SqlContext sqlContext){
        this(table,propertyName,sqlContext,null);
    }
    public ColumnInsertSegment(SqlTableInfo table, String propertyName, SqlContext sqlContext, String alias){
        this.table = table;

        this.propertyName = propertyName;
        this.sqlContext = sqlContext;
        this.alias = alias;
    }

    @Override
    public String getSql() {
        sqlContext.addParameter(new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(sqlContext.getQuoteName(alias));
        }
        return sql.toString();
    }
}