package com.easy.query.core.proxy.extension.functions;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastBooleanAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastDateTimeAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastNumberAvailable;
import com.easy.query.core.proxy.extension.functions.cast.ColumnFunctionCastStringAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.DateTimeTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.DateTimeTypeExpressionImpl;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.impl.duration.DurationAnyExpression;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyStringUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnAnyFunctionAvailable<TProperty> extends ColumnObjectFunctionAvailable<TProperty, AnyTypeExpression<TProperty>>, SQLSelectAsExpression, PropTypeColumn<TProperty>,
        ColumnFunctionCastStringAvailable<TProperty>,
        ColumnFunctionCastNumberAvailable<TProperty>,
        ColumnFunctionCastDateTimeAvailable<TProperty>,
        ColumnFunctionCastBooleanAvailable<TProperty>,
        ColumnJsonMapFunctionAvailable<TProperty> {

    @Override
    default AnyTypeExpression<TProperty> createChainExpression(Function<SQLFunc, SQLFunction> func, Class<?> propType) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), func, propType);
    }

    default AnyTypeExpression<TProperty> concat(TablePropColumn... propColumns) {
        return concat(o -> {
            for (TablePropColumn propColumn : propColumns) {
                o.getColumnFuncSelector().column(propColumn.getTable(), propColumn.getValue());
            }
        });
    }

    default AnyTypeExpression<TProperty> concat(SQLActionExpression1<ProxyColumnFuncSelector> selector) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.concat(o -> {
                    o.sqlFunc(sqlFunction);
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            } else {
                return fx.concat(o -> {
                    o.column(this.getTable(), this.getValue());
                    selector.apply(new ProxyColumnFuncSelectorImpl(o));
                });
            }
        }, String.class);
    }

    default AnyTypeExpression<TProperty> nullEmpty() {
        return nullOrDefault(o -> o.value(EasyStringUtil.EMPTY));
    }

    default AnyTypeExpression<String> toLower() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.toLower(sqlFunction);
            } else {
                return fx.toLower(this.getValue());
            }
        }, String.class);
    }

    default AnyTypeExpression<String> toUpper() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.toUpper(sqlFunction);
            } else {
                return fx.toUpper(this.getValue());
            }
        }, String.class);
    }

    /**
     * 字符串截取
     *
     * @param begin  开始索引默认0
     * @param length 截取长度
     * @return
     */
    default AnyTypeExpression<String> subString(int begin, int length) {
        if (begin < 0) {
            throw new IllegalArgumentException("begin must be greater than 0");
        }
        if (length < 0) {
            throw new IllegalArgumentException("length must be greater than 0");
        }
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.subString(sqlFunction, begin, length);
            } else {
                return fx.subString(this.getValue(), begin, length);
            }
        }, String.class);
    }

    /**
     * 去空格
     *
     * @return
     */
    default AnyTypeExpression<String> trim() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trim(sqlFunction);
            } else {
                return fx.trim(this.getValue());
            }
        }, String.class);
    }
    default AnyTypeExpression<String> ltrim() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimStart(sqlFunction);
            } else {
                return fx.trimStart(this.getValue());
            }
        }, String.class);
    }

    /**
     * 去尾部空格
     *
     * @return
     */
    default AnyTypeExpression<String> rtrim() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.trimEnd(sqlFunction);
            } else {
                return fx.trimEnd(this.getValue());
            }
        }, String.class);
    }

    default AnyTypeExpression<String> replace(String oldValue, String newValue) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.replace(sqlFunction, oldValue, newValue);
            } else {
                return fx.replace(this.getValue(), oldValue, newValue);
            }
        }, String.class);
    }

    default AnyTypeExpression<String> leftPad(int totalWidth) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction, totalWidth);
            } else {
                return fx.leftPad(this.getValue(), totalWidth);
            }
        }, String.class);
    }

    default AnyTypeExpression<String> leftPad(int totalWidth, char paddingChar) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.leftPad(sqlFunction, totalWidth, paddingChar);
            } else {
                return fx.leftPad(this.getValue(), totalWidth, paddingChar);
            }
        }, String.class);
    }

    default AnyTypeExpression<String> rightPad(int totalWidth) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction, totalWidth);
            } else {
                return fx.rightPad(this.getValue(), totalWidth);
            }
        }, String.class);
    }

    default AnyTypeExpression<String> rightPad(int totalWidth, char paddingChar) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.rightPad(sqlFunction, totalWidth, paddingChar);
            } else {
                return fx.rightPad(this.getValue(), totalWidth, paddingChar);
            }
        }, String.class);
    }

    default AnyTypeExpression<String> joining(String delimiter) {
        return joining(delimiter,false);
    }

    default AnyTypeExpression<String> joining(String delimiter, boolean distinct) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.joining(x -> {
                x.value(delimiter);
                PropTypeColumn.columnFuncSelector(x, this);
            }, distinct);
        }, String.class);
    }

    default AnyTypeExpression<Integer> length() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.length(sqlFunction);
            } else {
                return fx.length(this.getValue());
            }
        }, Integer.class);
    }

    default <T extends BigDecimal> AnyTypeExpression<T> avg() {
        return avg(false);
    }

    default <T extends BigDecimal> AnyTypeExpression<T> avg(boolean distinct) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.avg(sqlFunction).distinct(distinct);
            } else {
                return fx.avg(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }

    default <T extends Number> AnyTypeExpression<T> sum() {
        return sum(false);
    }

    default <T extends Number> AnyTypeExpression<T> sum(boolean distinct) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.sum(sqlFunction).distinct(distinct);
            } else {
                return fx.sum(this.getValue()).distinct(distinct);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<TProperty> abs() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Abs);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Abs);
            }
        }, getPropertyType());
    }

    default AnyTypeExpression<Integer> sign() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sin);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Sin);
            }
        }, Integer.class);
    }

    default AnyTypeExpression<BigDecimal> floor() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Floor);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Floor);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> ceiling() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Ceiling);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Ceiling);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> round() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Round);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Round);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> round(int decimals) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction).value(decimals);
                }, MathMethodEnum.Ceiling);
            } else {
                return fx.math(o -> o.column(this.getValue()).value(decimals), MathMethodEnum.Ceiling);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> exp() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Exp);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Exp);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> log() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Log);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Log);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> log10() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Log10);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Log10);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> pow() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Pow);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Pow);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> pow(BigDecimal exponent) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction).value(exponent);
                }, MathMethodEnum.Pow);
            } else {
                return fx.math(o -> o.column(this.getValue()).value(exponent), MathMethodEnum.Pow);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> sqrt() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sqrt);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Sqrt);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> cos() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Cos);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Cos);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> sin() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Sin);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Sin);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> tan() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Tan);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Tan);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> acos() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Acos);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Acos);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> asin() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Asin);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Asin);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> atan() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Atan);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Atan);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> atan2() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Atan2);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Atan2);
            }
        }, BigDecimal.class);
    }

    default AnyTypeExpression<BigDecimal> truncate() {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.math(o -> {
                    o.sqlFunc(sqlFunction);
                }, MathMethodEnum.Truncate);
            } else {
                return fx.math(o -> o.column(this.getValue()), MathMethodEnum.Truncate);
            }
        }, BigDecimal.class);
    }


    default AnyTypeExpression<String> dateTimeFormat(String javaFormat) {
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.dateTimeFormat(this.getValue(), javaFormat);
        }, String.class);
    }

    /**
     * 最小精度为秒部分数据库支持秒以下精度
     *
     * @param duration
     * @param timeUnit
     * @return
     */
    default DateTimeTypeExpression<TProperty> plusTimeUnit(long duration, TimeUnit timeUnit) {
        return new DateTimeTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTime(sqlFunction, duration, timeUnit);
            } else {
                return fx.plusDateTime(this.getValue(), duration, timeUnit);
            }
        }, getPropertyType());
    }

    default DateTimeTypeExpression<TProperty> plusMonths(int month) {
        return new DateTimeTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTimeMonths(sqlFunction, month);
            } else {
                return fx.plusDateTimeMonths(this.getValue(), month);
            }
        }, getPropertyType());
    }

    default DateTimeTypeExpression<TProperty> plusYears(int year) {
        return new DateTimeTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            if (this instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) this).func().apply(fx);
                return fx.plusDateTimeYears(sqlFunction, year);
            } else {
                return fx.plusDateTimeYears(this.getValue(), year);
            }
        }, getPropertyType());
    }

    default AnyTypeExpression<Integer> dayOfYear() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.DayOfYear);
    }

    /**
     * 星期1-6为1-6星期日为0
     *
     * @return
     */
    default AnyTypeExpression<Integer> dayOfWeek() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.DayOfWeek);
    }

    default AnyTypeExpression<Integer> year() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Year);
    }

    /**
     * MM 1-12
     *
     * @return
     */
    default AnyTypeExpression<Integer> month() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Month);
    }

    /**
     * 1-31
     *
     * @return
     */
    default AnyTypeExpression<Integer> day() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Day);
    }

    /**
     * HH 24小时制0-23
     *
     * @return
     */
    default AnyTypeExpression<Integer> hour() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Hour);
    }

    /**
     * mm 0-60
     *
     * @return
     */
    default AnyTypeExpression<Integer> minute() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Minute);
    }

    /**
     * ss 0-60
     *
     * @return
     */
    default AnyTypeExpression<Integer> second() {
        return dateTimeProp(this, this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), DateTimeUnitEnum.Second);
    }


    /**
     * a.duration(b).toDays()
     * a比b少多少天,如果a小于b则返回正数,如果a大于b则返回负数
     * 两个日期a,b之间相隔多少天如果不需考虑时间则请使用abs函数保证肯定是正数
     *
     * @param after 被比较的时间
     * @return 后续duration操作
     */
    default DurationAnyExpression duration(ColumnDateTimeFunctionAvailable<TProperty> after) {
        return new DurationAnyExpression(this, after);
    }

    /**
     * a.duration(b).toDays()
     * a比b少多少天,如果a小于b则返回正数,如果a大于b则返回负数
     * 两个日期a,b之间相隔多少天如果不需考虑时间则请使用abs函数保证肯定是正数
     *
     * @param after 被比较的时间
     * @return 后续duration操作
     */
    default DurationAnyExpression duration(LocalDateTime after) {
        return new DurationAnyExpression(this, after);
    }


    static AnyTypeExpression<Integer> dateTimeProp(PropColumn propColumn, EntitySQLContext entitySQLContext, TableAvailable table, String property, DateTimeUnitEnum dateTimeUnitEnum) {
        return new AnyTypeExpressionImpl<>(entitySQLContext, table, property, fx -> {
            if (propColumn instanceof DSLSQLFunctionAvailable) {
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) propColumn).func().apply(fx);
                return fx.dateTimeProperty(sqlFunction, dateTimeUnitEnum);
            } else {
                return fx.dateTimeProperty(propColumn.getValue(), dateTimeUnitEnum);
            }
        }, Integer.class);
    }
}
