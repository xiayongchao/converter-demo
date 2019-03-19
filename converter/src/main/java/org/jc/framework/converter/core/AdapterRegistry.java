package org.jc.framework.converter.core;

import org.jc.framework.converter.support.CollectionAdapter;
import org.jc.framework.converter.support.ListAdapter;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiayc
 * @date 2019/3/18
 */
public class AdapterRegistry {
    private final Map<String, CollectionAdapter<?>> adapterMap = new ConcurrentHashMap<>();

    public AdapterRegistry() {
        addAdapter(new ListAdapter());
    }

    public AdapterRegistry addAdapter(CollectionAdapter<?> adapter) {
        adapterMap.put(((ParameterizedTypeImpl) adapter.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName(), adapter);
        return this;
    }

    public <T extends Collection> CollectionAdapter<T> getAdapter(Class<T> targetClass) {
        if (targetClass == null) {
            return null;
        }
        return (CollectionAdapter<T>) adapterMap.get(targetClass.getName());
    }
}
