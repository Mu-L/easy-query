package com.easy.query.core.proxy.extra;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.extra.ExtraFilter;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
  * create time 2025/4/16 10:51
  * 文件说明
  * @author xuejiaming
  */
public class EntityExtraFilter<TProxy> implements ExtraFilter {
    private final SQLExpression1<TProxy> whereExpression;

    public EntityExtraFilter(SQLExpression1<TProxy> whereExpression){
        this.whereExpression = whereExpression;
    }
    @Override
    public void filter(WherePredicate<?> wherePredicate) {
        TableAvailable table = wherePredicate.getTable();

        ProxyEntity tEntityProxy = EntityQueryProxyManager.create(EasyObjectUtil.typeCastNullable(table.getEntityClass()));
        ProxyEntity tableProxy = (ProxyEntity)tEntityProxy.create(table, wherePredicate.getEntityExpressionBuilder(), wherePredicate.getRuntimeContext());
//        wherePredicate.getFilter().getEntityExpressionBuilder()
        tableProxy.getEntitySQLContext()._where(wherePredicate.getFilter(),()->{
            whereExpression.apply((TProxy)tableProxy);
        });

    }
}
