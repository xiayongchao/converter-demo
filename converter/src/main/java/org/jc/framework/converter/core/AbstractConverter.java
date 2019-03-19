package org.jc.framework.converter.core;

/**
 * @author xiayc
 * @date 2019/3/14
 */
public abstract class AbstractConverter<S, T> {
    private T defaultValue;

    public AbstractConverter(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    protected T getDefaultValue() {
        return defaultValue;
    }
}
