package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author xuejiaming
 * @Description: 查询projects
 * create time 2023/2/13 22:39
 */
public class ProjectSQLBuilderSegmentImpl extends AbstractSQLBuilderSegment implements ProjectSQLBuilderSegment {

    private boolean projectHasAggregate = false;

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        StringBuilder sb = new StringBuilder();
        List<SQLSegment> sqlSegments = getSQLSegments();
        if (!sqlSegments.isEmpty()) {
            Iterator<SQLSegment> iterator = sqlSegments.iterator();
            SQLSegment first = iterator.next();
            sb.append(first.toSQL(toSQLContext));
            while (iterator.hasNext()) {
                SQLSegment sqlSegment = iterator.next();
                sb.append(SQLKeywordEnum.DOT.toSQL()).append(sqlSegment.toSQL(toSQLContext));
            }
        }
        return sb.toString();
    }

    @Override
    public SQLBuilderSegment cloneSQLBuilder() {
        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();
        projectSQLBuilderSegment.projectHasAggregate = projectHasAggregate;
        copyTo(projectSQLBuilderSegment);
        return projectSQLBuilderSegment;
    }

    @Override
    public void append(SQLSegment sqlSegment) {
        super.append(sqlSegment);
        if (!projectHasAggregate) {

            boolean aggregateColumn = EasySQLSegmentUtil.isAggregateColumn(sqlSegment);
            if (aggregateColumn) {
                projectHasAggregate = true;
            }
        }

    }

    @Override
    public boolean hasAggregateColumns() {
        return projectHasAggregate;
    }
}
