package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.sharding.compare.ShardingComparer;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.sharding.merge.segment.PropertyOrder;
import com.easy.query.core.sharding.merge.result.OrderStreamMergeResult;
import com.easy.query.core.util.ArrayUtil;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
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
public class EasyOrderStreamMergeResult implements OrderStreamMergeResult {
    private static final List<Comparable<?>> EMPTY_COMPARABLE_VALUES = Collections.emptyList();
    private final StreamMergeContext streamMergeContext;
    private final StreamResult streamResult;
    private List<Comparable<?>> orderValues;

    public EasyOrderStreamMergeResult(StreamMergeContext streamMergeContext, StreamResult streamResult) {

        this.streamMergeContext = streamMergeContext;
        this.streamResult = streamResult;
        setOrderValues();
    }

    private void setOrderValues() {
        orderValues = hasElement() ? getCurrentOrderValues() : EMPTY_COMPARABLE_VALUES;
    }

    private List<Comparable<?>> getCurrentOrderValues() {
        try {
            return doGetCurrentOrderValues();
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
    }

    /**
     * 获取需要排序的字段值
     *
     * @return
     * @throws SQLException
     */
    private List<Comparable<?>> doGetCurrentOrderValues() throws SQLException {
        if (ArrayUtil.isEmpty(streamMergeContext.getOrders())) {
            return EMPTY_COMPARABLE_VALUES;
        }
        ArrayList<Comparable<?>> orders = new ArrayList<>(streamMergeContext.getOrders().size());
        for (PropertyOrder order : streamMergeContext.getOrders()) {
            Object value = this.streamResult.getObject(order.columnIndex());
            if (value instanceof Comparable<?> || Comparable.class.isAssignableFrom(order.propertyType())) {
                orders.add((Comparable<?>) value);
            } else {
                throw new UnsupportedOperationException(" order by value:" + order.propertyName() + "must implements Comparable<?>");
            }
        }
        return orders;
    }

    @Override
    public boolean hasElement() {
        return streamResult.hasElement();
    }

    @Override
    public boolean skipFirst() {
        return streamResult.skipFirst();
    }

    @Override
    public boolean next() throws SQLException {
        boolean next = streamResult.next();
        setOrderValues();
        return next;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return streamResult.getObject(columnIndex);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return streamResult.wasNull();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return streamResult.getMetaData();
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return streamResult.getSQLXML(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return streamResult.getTimestamp(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return streamResult.getTime(columnIndex);
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return streamResult.getString(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return streamResult.getDate(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return streamResult.getShort(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return streamResult.getLong(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return streamResult.getInt(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return streamResult.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return streamResult.getDouble(columnIndex);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return streamResult.getClob(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return streamResult.getByte(columnIndex);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return streamResult.getBytes(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return streamResult.getBoolean(columnIndex);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return streamResult.getBlob(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return streamResult.getBigDecimal(columnIndex);
    }

    @Override
    public List<Comparable<?>> compareValues() {
        if (orderValues == null) {
            return EMPTY_COMPARABLE_VALUES;
        }
        return orderValues;
    }

    @Override
    public void close() throws Exception {
        streamResult.close();
    }

    @Override
    public int compareTo(OrderStreamMergeResult o) {
        if (ArrayUtil.isEmpty(streamMergeContext.getOrders())) {
            return 0;
        }
        ShardingComparer shardingComparer = streamMergeContext.getRuntimeContext().getShardingComparer();
        int i = 0;
        for (PropertyOrder order : streamMergeContext.getOrders()) {
            int compared = shardingComparer.compare(orderValues.get(i), o.compareValues().get(i), order.asc());
            if (compared != 0) {
                return compared;
            }
            i++;
        }
        return 0;
    }
}