package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2024/8/4 15:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparablePartitionByChainExpression<T> extends ColumnFunctionCompareComparableAnyChainExpression<T> {
    /**
     * 排序列 ASC
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    default <TProperty> ColumnFunctionCompareComparablePartitionByChainExpression<T> orderBy(PropTypeColumn<TProperty> propTypeColumn) {
        return orderBy(true, propTypeColumn);
    }

    /**
     * 排序列 ASC
     * @param condition 是否生效
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    <TProperty> ColumnFunctionCompareComparablePartitionByChainExpression<T> orderBy(boolean condition, PropTypeColumn<TProperty> propTypeColumn);
    /**
     * 排序列 DESC
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    default <TProperty> ColumnFunctionCompareComparablePartitionByChainExpression<T> orderByDescending(PropTypeColumn<TProperty> propTypeColumn) {
        return orderByDescending(true, propTypeColumn);
    }

    /**
     * 排序列 DESC
     * @param condition 是否生效
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    <TProperty> ColumnFunctionCompareComparablePartitionByChainExpression<T> orderByDescending(boolean condition, PropTypeColumn<TProperty> propTypeColumn);
}