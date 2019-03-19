package org.jc.framework.converter.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xiayc
 * @date 2019/3/18
 */
public class ListAdapter implements CollectionAdapter<List> {
    @Override
    public <T> Collection<T> adapt(Class<T> targetClass) {
        return new ArrayList<T>();
    }
}
