package com.easy.query.core.configuration.column2mapkey;

/**
 * create time 2024/8/5 15:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultColumn2MapKeyConversion implements Column2MapKeyConversion{
    @Override
    public String convertToMapKey(String columnName) {
        return columnName;
    }
}
