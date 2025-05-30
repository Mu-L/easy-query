package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.segment.ReverseOrderBySegment;
import com.easy.query.core.expression.segment.SQLSegment;

import java.util.List;

/**
 * @Description: 文件说明
 * create time 2023/2/13 22:39
 * @author xuejiaming
 */
public class OrderBySQLBuilderSegmentImpl extends AbstractSQLBuilderSegment implements OrderBySQLBuilderSegment {

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        StringBuilder sb = new StringBuilder();
        List<SQLSegment> sqlSegments = getSQLSegments();
        //从后往前追加
        for (int i = sqlSegments.size() - 1; i >= 0; i--) {
            SQLSegment sqlSegment = sqlSegments.get(i);

            if (sb.length() != 0) {
                sb.insert(0, SQLKeywordEnum.DOT.toSQL());
            }
            String orderSQL = sqlSegment.toSQL(toSQLContext);
            if (sb.length() == 0) {
                sb.insert(0, orderSQL);

            } else {
                sb.insert(0, orderSQL);
            }
        }
        return sb.toString();
    }

    @Override
    public SQLBuilderSegment cloneSQLBuilder() {
        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        copyTo(orderBySQLBuilderSegment);
        return orderBySQLBuilderSegment;
    }

    @Override
    public boolean reverseOrder() {
        if(getSQLSegments().stream().allMatch(o->o instanceof ReverseOrderBySegment)){
            for (SQLSegment sqlSegment : getSQLSegments()) {
                ReverseOrderBySegment reverseOrderBySegment = (ReverseOrderBySegment) sqlSegment;
                reverseOrderBySegment.reverseOrder();
            }
            return true;
        }
        return false;
    }
}
