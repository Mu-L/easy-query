package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.OrderStreamMergeResultSet;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.sharding.comparer.ShardingComparer;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyOrder;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/27 12:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyOrderStreamMergeResultSet implements OrderStreamMergeResultSet {
    private static final List<Comparable<?>> EMPTY_COMPARABLE_VALUES = Collections.emptyList();
    private final StreamMergeContext streamMergeContext;
    private final StreamResultSet streamResultSet;
    private List<Comparable<?>> orderValues;
    private boolean closed = false;

    public EasyOrderStreamMergeResultSet(StreamMergeContext streamMergeContext, StreamResultSet streamResultSet) throws SQLException {

        this.streamMergeContext = streamMergeContext;
        this.streamResultSet = streamResultSet;
        setOrderValues();
    }

    private void setOrderValues() throws SQLException {
        orderValues = hasElement() ? getCurrentOrderValues() : EMPTY_COMPARABLE_VALUES;
    }

    /**
     * 获取需要排序的字段值
     *
     * @return
     * @throws SQLException
     */
    private List<Comparable<?>> getCurrentOrderValues() throws SQLException {
        if (EasyCollectionUtil.isEmpty(streamMergeContext.getOrders())) {
            return EMPTY_COMPARABLE_VALUES;
        }
        ArrayList<Comparable<?>> orders = new ArrayList<>(streamMergeContext.getOrders().size());
        for (PropertyOrder order : streamMergeContext.getOrders()) {
            // local date time等之类的
            Object value = this.streamResultSet.getObject(order.columnIndex() + 1);
            if (value == null || value instanceof Comparable<?>) {
                orders.add((Comparable<?>) value);
            } else {
                throw new UnsupportedOperationException(" order by value:" + order.propertyName() + "must implements Comparable<?>");
            }
        }
        return orders;
    }

    @Override
    public ResultSet getResultSet() {
        throw new EasyQueryInvalidOperationException("this Stream:["+ EasyClassUtil.getSimpleName(this.getClass()) +"] getResultSet not support");
    }

    @Override
    public boolean hasElement() {
        return streamResultSet.hasElement();
    }

    @Override
    public boolean skipFirst() {
        return streamResultSet.skipFirst();
    }

    @Override
    public boolean next() throws SQLException {
        boolean next = streamResultSet.next();
        setOrderValues();
        return next;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return streamResultSet.getObject(columnIndex);
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return streamResultSet.getObject(columnIndex, type);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return streamResultSet.wasNull();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return streamResultSet.getMetaData();
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return streamResultSet.getSQLXML(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return streamResultSet.getTimestamp(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return streamResultSet.getTime(columnIndex);
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return streamResultSet.getString(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return streamResultSet.getDate(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return streamResultSet.getShort(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return streamResultSet.getLong(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return streamResultSet.getInt(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return streamResultSet.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return streamResultSet.getDouble(columnIndex);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return streamResultSet.getClob(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return streamResultSet.getByte(columnIndex);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return streamResultSet.getBytes(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return streamResultSet.getBoolean(columnIndex);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return streamResultSet.getBlob(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return streamResultSet.getBigDecimal(columnIndex);
    }

    @Override
    public List<Comparable<?>> compareValues() {
        if (orderValues == null) {
            return EMPTY_COMPARABLE_VALUES;
        }
        return orderValues;
    }

    @Override
    public void close() throws SQLException {
        if (closed) {
            return;
        }
        closed = true;
        streamResultSet.close();
    }

    @Override
    public int compareTo(OrderStreamMergeResultSet o) {
        if (EasyCollectionUtil.isEmpty(streamMergeContext.getOrders())) {
            return 0;
        }
        ShardingComparer shardingComparer = streamMergeContext.getRuntimeContext().getShardingComparer();
        int i = 0;
        boolean reverseMerge = streamMergeContext.isReverseMerge();
        for (PropertyOrder order : streamMergeContext.getOrders()) {
            int compared = shardingComparer.compare(orderValues.get(i), o.compareValues().get(i), order.asc() == !reverseMerge);
            if (compared != 0) {
                return compared;
            }
            i++;
        }
        return 0;
    }
}
