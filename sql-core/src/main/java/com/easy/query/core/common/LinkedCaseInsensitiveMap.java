package com.easy.query.core.common;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * key为字符串且忽略大小写的有序集合
 *
 * @author xuejiaming
 * @FileName: CaseInsensitiveLinkedHashMap.java
 * @Description: 文件说明
 * create time 2023/2/11 10:20
 */
public class LinkedCaseInsensitiveMap<V> extends LinkedHashMap<String, V> {

    private static final long serialVersionUID = 7171102532087849030L;
    private final Map<String, String> caseInsensitiveKeys;
    private final Locale locale;

    public LinkedCaseInsensitiveMap() {
        this(12, Locale.ENGLISH);
    }

    public LinkedCaseInsensitiveMap(Locale locale) {
        this(12, locale);
    }

    public LinkedCaseInsensitiveMap(int expectedSize, Locale locale) {
        super(expectedSize);
        this.caseInsensitiveKeys = new HashMap<>((int) ((float) expectedSize / 0.75F), 0.75F);
        this.locale = locale;
    }

    @Override
    public boolean containsKey(Object key) {

        if (key instanceof String) {
            String lowerCase = key.toString().toLowerCase(this.locale);
            String realKey = caseInsensitiveKeys.get(lowerCase);
            return realKey != null;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (key instanceof String) {
            String realKey = caseInsensitiveKeys.get(key.toString().toLowerCase(this.locale));
            return super.get(realKey);
        }
        return null;
    }

    @Override
    public V put(String key, V value) {

        /*
         * 保持map和lowerCaseMap同步
         * 在put新值之前remove旧的映射关系
         */
        String oldKey = caseInsensitiveKeys.put(key.toLowerCase(this.locale), key);
        V oldValue = super.remove(oldKey);
        super.put(key, value);
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> m) {
        for (Map.Entry<? extends String, ? extends V> entry : m.entrySet()) {
            String key = entry.getKey();
            V value = entry.getValue();
            this.put(key, value);
        }
    }

    @Override
    public V remove(Object key) {
        String realKey = caseInsensitiveKeys.remove(key.toString().toLowerCase(this.locale));
        return super.remove(realKey);
    }

}
