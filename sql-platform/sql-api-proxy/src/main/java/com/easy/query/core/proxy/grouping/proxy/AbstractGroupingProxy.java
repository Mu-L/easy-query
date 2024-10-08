package com.easy.query.core.proxy.grouping.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnObjectFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * create time 2023/12/28 14:57
 * 抽象的group代理对象
 *
 * @author xuejiaming
 */
public abstract class AbstractGroupingProxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TSourceProxy> extends AbstractProxyEntity<TProxy, TEntity> implements SQLGroupByExpression {

    private final TSourceProxy tSourceProxy;

    public AbstractGroupingProxy(TSourceProxy tSourceProxy) {
        this.tSourceProxy = tSourceProxy;
    }

    /**
     * 当仅单表是group就是当前表
     * 如果是多表下比如join下那么groups就是MergeTuple2-10最多10个如有需要可以提交issue或者自行扩展
     * @return
     */
    public TSourceProxy groupTable() {
        return tSourceProxy;
    }

    /**
     * 请使用{@link #expression()}或者{@link Expression#count()}
     * COUNT(*)
     *
     * @return 返回类型为Long
     */
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(getEntitySQLContext(), null, null, f -> {
            return f.count(c -> {
            });
        }, Long.class);
    }

//    public <TProperty> ColumnFunctionComparableNumberChainExpression<Long> count(ColumnObjectFunctionAvailable<TProperty, ?> column) {
//        return column.count();
//    }
    public <TProperty> ColumnFunctionCompareComparableNumberChainExpression<Long> count(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.count();
        }
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, Long.class);
    }
    public <TProperty> ColumnFunctionCompareComparableNumberChainExpression<Long> count(PropTypeColumn<TProperty> column, boolean distinct) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.count(distinct);
        }
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            if(distinct){
                return fx.count(x -> {
                    PropTypeColumn.columnFuncSelector(x, column);
                }).distinct(true);
            }
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, Long.class);
    }

    /**
     * 请使用{@link #expression()}或者{@link Expression#intCount()}
     * COUNT(*)
     *
     * @return 返回类型为Integer
     */
    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(getEntitySQLContext(), null, null, f -> {
            return f.count(c -> {
            });
        }, Integer.class);
    }

//    public <TProperty> ColumnFunctionComparableNumberChainExpression<Integer> intCount(ColumnObjectFunctionAvailable<TProperty, ?> column) {
//        return column.intCount();
//    }
    public <TProperty> ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.intCount();
        }
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, Integer.class);
    }
    public <TProperty> ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount(PropTypeColumn<TProperty> column, boolean distinct) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.intCount(distinct);
        }
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            if(distinct){
                return fx.count(x -> {
                    PropTypeColumn.columnFuncSelector(x, column);
                }).distinct(true);
            }
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, Integer.class);
    }

    protected <TKey extends PropTypeColumn<TKey1>, TKey1> void acceptGroupSelector(TKey key, GroupSelector s) {

        if (key instanceof DSLSQLFunctionAvailable) {
            Function<SQLFunc, SQLFunction> funcCreate = ((DSLSQLFunctionAvailable) key).func();
            SQLFunc fx = s.getRuntimeContext().fx();
            SQLFunction sqlFunction = funcCreate.apply(fx);
            s.columnFunc(key.getTable(), sqlFunction);
        } else {
            key.accept(s);
//            if(key instanceof SQLNativeDraft){
//            }else{
//                s.column(key.getTable(), key.getValue());
//            }
        }
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }


    /**
     * 请使用 id().max()
     * @param column
     * @return
     * @param <TProperty>
     * @param <TChain>
     */
    public <TProperty, TChain extends PropTypeColumn<TProperty>> PropTypeColumn<TProperty> max(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnObjectFunctionAvailable) {
            ColumnObjectFunctionAvailable<TProperty, TChain> column1 = (ColumnObjectFunctionAvailable<TProperty, TChain>) column;
            return column1.max();
        }
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.max(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, column.getPropertyType());
    }

    /**
     * 请使用 id().min()
     * @param column
     * @return
     * @param <TProperty>
     * @param <TChain>
     */
    public <TProperty, TChain extends PropTypeColumn<TProperty>> PropTypeColumn<TProperty> min(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnObjectFunctionAvailable) {
            ColumnObjectFunctionAvailable<TProperty, TChain> column1 = (ColumnObjectFunctionAvailable<TProperty, TChain>) column;
            return column1.min();
        }
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(this.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.min(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, column.getPropertyType());
    }

    /**
     * 请使用 age().sum()
     * @param column
     * @return
     * @param <TProperty>
     */
    public <TProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<TProperty> sum(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.sum();
        }
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, column.getPropertyType());
    }


    /**
     * 请使用 age().sumBigDecimal()
     * @param column
     * @return
     * @param <TProperty>
     */
    public <TProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> sumBigDecimal(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.sumBigDecimal();
        }
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, BigDecimal.class);
    }

    /**
     * 请使用 age().avg()
     * @param column
     * @return
     * @param <TProperty>
     */
    public <TProperty extends Number> ColumnFunctionCompareComparableNumberChainExpression<BigDecimal> avg(PropTypeColumn<TProperty> column) {
        if (column instanceof ColumnNumberFunctionAvailable) {
            ColumnNumberFunctionAvailable<TProperty> funcColumn = (ColumnNumberFunctionAvailable<TProperty>) column;
            return funcColumn.avg();
        }
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.avg(x -> {
                PropTypeColumn.columnFuncSelector(x, column);
            });
        }, BigDecimal.class);
    }

    /**
     * 请使用 age().join(",")
     * @param column
     * @param delimiter
     * @return
     * @param <TProperty>
     */
    public <TProperty> ColumnFunctionCompareComparableStringChainExpression<String> join(ColumnStringFunctionAvailable<TProperty> column, String delimiter) {
        return column.join(delimiter);
    }

    /**
     * 请使用 age().join(",")
     * @param column
     * @param delimiter
     * @param distinct
     * @return
     * @param <TProperty>
     */
    public <TProperty> ColumnFunctionCompareComparableStringChainExpression<String> join(PropTypeColumn<TProperty> column, String delimiter, boolean distinct) {
        if (column instanceof ColumnStringFunctionAvailable) {
            ColumnStringFunctionAvailable<TProperty> funcColumn = (ColumnStringFunctionAvailable<TProperty>) column;
            return funcColumn.join(delimiter);
        }
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(column.getEntitySQLContext(), column.getTable(), column.getValue(), fx -> {
            return fx.join(x -> {
                x.value(delimiter);
                PropTypeColumn.columnFuncSelector(x, column);
            },distinct);
        }, String.class);
    }
    public <TProperty> ColumnFunctionCompareComparableStringChainExpression<String> join(PropTypeColumn<TProperty> column, String delimiter) {
        return join(column,delimiter,false);
    }
}