package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.crud.read.IAggregation;
import io.github.kiryu1223.expressionTree.delegate.Func3;
import io.github.kiryu1223.expressionTree.expressions.Expr;
import io.github.kiryu1223.expressionTree.expressions.ExprTree;

import java.math.BigDecimal;

public abstract class SqlAggregation3<T1, T2, T3> implements IAggregation
{
    public <R> long count(@Expr Func3<T1, T2, T3, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal sum(@Expr Func3<T1, T2, T3, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal avg(@Expr Func3<T1, T2, T3, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> R max(@Expr Func3<T1, T2, T3, R> expr)
    {
        throw new RuntimeException();
    }

    public <R> R min(@Expr Func3<T1, T2, T3, R> expr)
    {
        throw new RuntimeException();
    }

    public long count()
    {
        throw new RuntimeException();
    }

    public long count(int i)
    {
        throw new RuntimeException();
    }

    public <R> long count(ExprTree<Func3<T1, T2, T3, R>> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal sum(ExprTree<Func3<T1, T2, T3, R>> expr)
    {
        throw new RuntimeException();
    }

    public <R> BigDecimal avg(ExprTree<Func3<T1, T2, T3, R>> expr)
    {
        throw new RuntimeException();
    }

    public <R> R min(ExprTree<Func3<T1, T2, T3, R>> expr)
    {
        throw new RuntimeException();
    }

    public <R> R max(ExprTree<Func3<T1, T2, T3, R>> expr)
    {
        throw new RuntimeException();
    }
}