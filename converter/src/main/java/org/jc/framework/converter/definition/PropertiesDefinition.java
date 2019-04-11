package org.jc.framework.converter.definition;

public class PropertiesDefinition {
    /**
     * 实现类
     *
     * @return
     */
    private Class<?> impl;

    /**
     * 默认值
     *
     * @return
     */
    private Object defaultValue;

    /**
     * 是否忽略当前属性
     *
     * @return
     */
    private boolean ignore;

    public Class<?> getImpl() {
        return impl;
    }

    public void setImpl(Class<?> impl) {
        this.impl = impl;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
}
