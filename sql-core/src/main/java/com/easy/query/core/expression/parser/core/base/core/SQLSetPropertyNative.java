package com.easy.query.core.expression.parser.core.base.core;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;

/**
 * create time 2023/8/5 12:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSetPropertyNative<TChain> extends SQLTableOwner {


    /**
     * set column=val
     *
     * @param property
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    TChain setSQLSegment(String property, String sqlSegment, SQLActionExpression1<SQLNativePropertyExpressionContext> contextConsume);
}
