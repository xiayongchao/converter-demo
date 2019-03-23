package org.jc.framework.converter.core;

import java.util.Collection;

/**
 * @author xiayc
 * @date 2019/3/18
 */
public interface CollectionAdapter<TC extends Collection> {
    <T> Collection<T> adapt(Class<T> targetClass);
}
