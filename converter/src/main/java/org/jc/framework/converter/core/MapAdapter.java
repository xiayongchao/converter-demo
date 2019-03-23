package org.jc.framework.converter.core;

import java.util.Map;

/**
 * @author xiayc
 * @date 2019/3/19
 */
public interface MapAdapter<TM extends Map> {
    <K, V> Map<K, V> adapt(Class<K> keyClass, Class<V> valueClass);
}
