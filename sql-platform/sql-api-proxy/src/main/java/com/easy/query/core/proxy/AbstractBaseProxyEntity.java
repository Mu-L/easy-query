package com.easy.query.core.proxy;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.context.EmptyQueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.SQLBooleanColumn;
import com.easy.query.core.proxy.columns.SQLDateTimeColumn;
import com.easy.query.core.proxy.columns.SQLManyQueryable;
import com.easy.query.core.proxy.columns.SQLNumberColumn;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.columns.impl.PropertySQLManyQueryable;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLBooleanColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLDateTimeColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNumberColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLStringColumnImpl;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBigDecimalTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBooleanTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLByteTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLDateTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLDoubleTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLFloatTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLIntegerTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLongTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLShortTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLTimestampTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLUUIDTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLUtilDateTypeColumn;
import com.easy.query.core.proxy.columns.types.impl.SQLAnyTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLBigDecimalTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLBooleanTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLByteTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLDateTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLDoubleTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLFloatTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLIntegerTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalDateTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalDateTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLongTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLShortTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLStringTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLTimestampTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLUUIDTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLUtilDateTypeColumnImpl;
import com.easy.query.core.proxy.core.ColumnSelectSQLContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;

import java.util.Objects;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBaseProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy, TEntity>, EntitySQLContextAvailable {

    protected TableAvailable table;
    protected EntitySQLContext entitySQLContext = new ColumnSelectSQLContext();

    private String propValue;

    @Override
    public String getNavValue() {
        return propValue;
    }

    @Override
    public void setNavValue(String val) {
        this.propValue = val;
    }

    @Override
    public @Nullable TableAvailable getTable() {
        return table;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        Objects.requireNonNull(entitySQLContext, "cant found entitySQLContext in sql context");
        return entitySQLContext;
    }


    @Override
    public TProxy create(TableAvailable table, EntitySQLContext entitySQLContext) {
        this.table = table;
        this.entitySQLContext = entitySQLContext;
        return EasyObjectUtil.typeCastNullable(this);
    }


    protected <TProperty> SQLDateTimeColumn<TProxy, TProperty> getDateTimeColumn(String property, @Nullable Class<TProperty> propType) {
        SQLDateTimeColumn<TProxy, TProperty> column = new SQLDateTimeColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLNumberColumn<TProxy, TProperty> getNumberColumn(String property, @Nullable Class<TProperty> propType) {
        SQLNumberColumn<TProxy, TProperty> column = new SQLNumberColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLStringColumn<TProxy, TProperty> getStringColumn(String property, @Nullable Class<TProperty> propType) {
        SQLStringColumn<TProxy, TProperty> column = new SQLStringColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLBooleanColumn<TProxy, TProperty> getBooleanColumn(String property, @Nullable Class<TProperty> propType) {
        SQLBooleanColumn<TProxy, TProperty> column = new SQLBooleanColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProperty> SQLAnyColumn<TProxy, TProperty> getAnyColumn(String property, @Nullable Class<TProperty> propType) {
        SQLAnyColumn<TProxy, TProperty> column = new SQLAnyColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }


    protected <TProperty> SQLAnyTypeColumn<TProxy, TProperty> getAnyTypeColumn(String property, @Nullable Class<TProperty> propType) {
        SQLAnyTypeColumn<TProxy, TProperty> column = new SQLAnyTypeColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLBigDecimalTypeColumn<TProxy> getBigDecimalTypeColumn(String property) {
        SQLBigDecimalTypeColumn<TProxy> column = new SQLBigDecimalTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLBooleanTypeColumn<TProxy> getBooleanTypeColumn(String property) {
        SQLBooleanTypeColumn<TProxy> column = new SQLBooleanTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLByteTypeColumn<TProxy> getByteTypeColumn(String property) {
        SQLByteTypeColumn<TProxy> column = new SQLByteTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLDateTypeColumn<TProxy> getSQLDateTypeColumn(String property) {
        SQLDateTypeColumn<TProxy> column = new SQLDateTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLDoubleTypeColumn<TProxy> getDoubleTypeColumn(String property) {
        SQLDoubleTypeColumn<TProxy> column = new SQLDoubleTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLFloatTypeColumn<TProxy> getFloatTypeColumn(String property) {
        SQLFloatTypeColumn<TProxy> column = new SQLFloatTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLIntegerTypeColumn<TProxy> getIntegerTypeColumn(String property) {
        SQLIntegerTypeColumn<TProxy> column = new SQLIntegerTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLLocalDateTimeTypeColumn<TProxy> getLocalDateTimeTypeColumn(String property) {
        SQLLocalDateTimeTypeColumn<TProxy> column = new SQLLocalDateTimeTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLLocalDateTypeColumn<TProxy> getLocalDateTypeColumn(String property) {
        SQLLocalDateTypeColumn<TProxy> column = new SQLLocalDateTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLLocalTimeTypeColumn<TProxy> getLocalTimeTypeColumn(String property) {
        SQLLocalTimeTypeColumn<TProxy> column = new SQLLocalTimeTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLLongTypeColumn<TProxy> getLongTypeColumn(String property) {
        SQLLongTypeColumn<TProxy> column = new SQLLongTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLShortTypeColumn<TProxy> getShortTypeColumn(String property) {
        SQLShortTypeColumn<TProxy> column = new SQLShortTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLStringTypeColumn<TProxy> getStringTypeColumn(String property) {
        SQLStringTypeColumn<TProxy> column = new SQLStringTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLTimestampTypeColumn<TProxy> getTimestampTypeColumn(String property) {
        SQLTimestampTypeColumn<TProxy> column = new SQLTimestampTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLTimeTypeColumn<TProxy> getTimeTypeColumn(String property) {
        SQLTimeTypeColumn<TProxy> column = new SQLTimeTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLUtilDateTypeColumn<TProxy> getUtilDateTypeColumn(String property) {
        SQLUtilDateTypeColumn<TProxy> column = new SQLUtilDateTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }

    protected SQLUUIDTypeColumn<TProxy> getUUIDTypeColumn(String property) {
        SQLUUIDTypeColumn<TProxy> column = new SQLUUIDTypeColumnImpl<>(entitySQLContext, table, property);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }


    protected <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TPropertyProxy getNavigate(String property, TPropertyProxy propertyProxy) {
        Objects.requireNonNull(this.entitySQLContext, "entitySQLContext is null");
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        //vo
        if (entityExpressionBuilder == null || entitySQLContext.methodIsInclude() || entityExpressionBuilder.getRuntimeContext() instanceof EmptyQueryRuntimeContext) {
            TPropertyProxy tPropertyProxy = propertyProxy.create(getTable(), this.getEntitySQLContext());
            tPropertyProxy.setNavValue(getFullNavValue(property));
            return tPropertyProxy;
        } else {
            TableAvailable leftTable = getTable();
            if (leftTable == null) {
                throw new EasyQueryInvalidOperationException(String.format("getNavigate %s cant not found table", property));
            }
            NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
            if (navigateMetadata.getLimit() == 0) {
                EntityRelationPropertyProvider entityRelationToImplicitProvider = navigateMetadata.getEntityRelationPropertyProvider();
                if (entityRelationToImplicitProvider == null) {
                    throw new EasyQueryInvalidOperationException("entityRelationToImplicitProvider is null,Navigate property in non entity plz set supportNonEntity = true.");
                }
                TableAvailable relationTable = entityRelationToImplicitProvider.toImplicitJoin(entityExpressionBuilder, leftTable, property);
                TPropertyProxy tPropertyProxy = propertyProxy.create(relationTable, this.getEntitySQLContext());
                String fullName = getFullNavValue(property);
                tPropertyProxy.setNavValue(fullName);
                return tPropertyProxy;
            } else {
                return getNavigateMany(property, propertyProxy).first();
            }
        }
    }

    protected <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> SQLManyQueryable<TProxy, TPropertyProxy, TProperty> getNavigateMany(String property, TPropertyProxy propertyProxy) {
        Objects.requireNonNull(this.entitySQLContext, "entitySQLContext is null");
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
//        QueryRuntimeContext runtimeContext = this.entitySQLContext.getRuntimeContext();
        String fullName = getFullNavValue(property);
        return new PropertySQLManyQueryable<>(new SubQueryContext<TPropertyProxy, TProperty>(entityExpressionBuilder, this.getEntitySQLContext(), getTable(), property, fullName, propertyProxy, this));
    }

    private String getFullNavValue(String navValue) {
        String parentNavValue = getNavValue();
        if (parentNavValue == null) {
            return navValue;
        }
        return parentNavValue + "." + navValue;
    }

    protected TProxy castChain() {
        return (TProxy) this;
    }


    public SQLAnyTypeColumn<TProxy, Object> anyColumn(String property) {
        return anyColumn(property, Object.class);
    }

    public <TProperty> SQLAnyTypeColumn<TProxy, TProperty> anyColumn(String property, @Nullable Class<TProperty> propType) {
        EasyRelationalUtil.TableOrRelationTable tableOrRelationalTable = EasyRelationalUtil.getTableOrRelationalTable(this.getEntitySQLContext().getEntityExpressionBuilder(), this.getTable(), property);
        SQLAnyTypeColumn<TProxy, TProperty> column = new SQLAnyTypeColumnImpl<>(this.getEntitySQLContext(), tableOrRelationalTable.table, tableOrRelationalTable.property, propType);
        column._setProxy(castChain());
        column.setNavValue(getFullNavValue(property));
        return column;
    }
}
