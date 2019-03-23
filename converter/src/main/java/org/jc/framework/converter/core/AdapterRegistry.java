package org.jc.framework.converter.core;

import org.jc.framework.converter.support.HashMapAdapter;
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
    private final Map<String, CollectionAdapter<?>> collectionAdapterMap = new ConcurrentHashMap<>();
    private final Map<String, MapAdapter<?>> mapAdapterMap = new ConcurrentHashMap<>();

    public AdapterRegistry() {
        addAdapter(new ListAdapter());
        addAdapter(new HashMapAdapter());
    }

    public AdapterRegistry addAdapter(CollectionAdapter<?> adapter) {
        collectionAdapterMap.put(((ParameterizedTypeImpl) adapter.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName(), adapter);
        return this;
    }

    public AdapterRegistry addAdapter(MapAdapter<?> adapter) {
        mapAdapterMap.put(((ParameterizedTypeImpl) adapter.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName(), adapter);
        return this;
    }

    public <T extends Collection> CollectionAdapter<T> getCollectionAdapter(Class<T> targetClass) {
        if (targetClass == null) {
            return null;
        }
        return (CollectionAdapter<T>) collectionAdapterMap.get(targetClass.getName());
    }

    public <T extends Map> MapAdapter<T> getMapAdapter(Class<T> targetClass) {
        if (targetClass == null) {
            return null;
        }
        return (MapAdapter<T>) mapAdapterMap.get(targetClass.getName());
    }
}
