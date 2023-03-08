package org.easy.query.core.query;

import org.easy.query.core.expression.parser.abstraction.internal.IndexAware;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: TableSegment.java
 * @Description:  [table | (table expression)]  [alias] | [on predicate] [where]
 * @Date: 2023/3/3 22:06
 * @Created by xuejiaming
 */
public interface SqlTableExpressionSegment extends SqlExpressionSegment, IndexAware {
    Class<?> entityClass();
    PredicateSegment getOn();
    boolean hasOn();
    String getAlias();
    int getIndex();
    String getSelectTableSource();
//    String getSqlColumnSegment(String propertyName);
}