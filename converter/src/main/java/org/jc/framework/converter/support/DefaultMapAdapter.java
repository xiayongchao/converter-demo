package org.jc.framework.converter.support;

import org.jc.framework.converter.core.MapAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiayc
 * @date 2019/3/19
 */
public class DefaultMapAdapter implements MapAdapter<Map> {
    @Override
    public <K, V> Map<K, V> adapt(Class<K> keyClass, Class<V> valueClass) {
        return new HashMap<K, V>();
    }
}
