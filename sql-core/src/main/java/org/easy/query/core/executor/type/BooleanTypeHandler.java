package org.easy.query.core.executor.type;

import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: BooleanTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:39
 * @Created by xuejiaming
 */
public class BooleanTypeHandler implements JdbcTypeHandler{
    private static final boolean DEFAULT=false;
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        ResultSet rs = resultSet.getRs();
        boolean r = rs.getBoolean(resultSet.getIndex());
        if(rs.wasNull()){//判断当前读取的列是否可以为null，因为基本类型存在默认值而包装类型存在null值
            if(resultSet.isPrimitive()){
                return DEFAULT;
            }else{
                return null;
            }
        }
        return r;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setBoolean(parameter.getIndex(),(Boolean) parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.BOOLEAN;
    }
}
