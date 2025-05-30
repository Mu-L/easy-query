package com.easy.query.core.basic.extension.navigate;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2024/3/1 15:44
 * 导航属性额外过滤器用于筛选固定数据
 * 比如属性books和historyBooks,其中historyBooks则为某个时间前的书籍
 *
 * @author xuejiaming
 */
public interface NavigateExtraFilterStrategy {
   /**
    * 非中间表的过滤通过当前方法设置返回
    * @param builder navigate相关信息
    * @return 返回null则表示不进行过滤
    */
   @Nullable
   SQLActionExpression1<WherePredicate<?>> getPredicateFilterExpression(NavigateBuilder builder);

   /**
    * 多对多的中间表额外过滤由原先的{@link #getPredicateFilterExpression(NavigateBuilder)}迁移到了{@link #getPredicateMappingClassFilterExpression(NavigateBuilder)}
    *
    * 当前方法仅针对多对多的中间表额外过滤
    * @param builder navigate相关信息
    * @return 返回null则表示不进行过滤
    */
   @Nullable
   SQLActionExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression(NavigateBuilder builder);
}
