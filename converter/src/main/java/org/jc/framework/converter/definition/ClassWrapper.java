package org.jc.framework.converter.definition;

import org.jc.framework.converter.annotation.Properties;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Class包裹类
 *
 * @author jc
 * @date 2019/3/24 14:09
 */
public class ClassWrapper {
    private Class<?> ownerType;
    private Class<?> rawType;
    private Type[] actualTypeArguments;

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
    private String defaultValue;

    /**
     * 是否忽略当前属性
     *
     * @return
     */
    private boolean ignore;

    public static ClassWrapper wrap(Field field) {
        Properties properties = field.getAnnotation(Properties.class);
        Type genericType = field.getGenericType();
        Type[] actualTypeArguments = null;
        if (genericType instanceof ParameterizedType) {
            actualTypeArguments = ((ParameterizedTypeImpl) genericType).getActualTypeArguments();
            genericType = ((ParameterizedTypeImpl) genericType).getRawType();
        }
        return new ClassWrapper(field.getDeclaringClass(), (Class<?>) genericType, actualTypeArguments, properties);
    }

    /*public static ClassWrapper wrap(Class<?> wrapClass) {
        Type genericType = wrapClass;
        Type[] actualTypeArguments = null;
        if (genericType instanceof ParameterizedType) {
            actualTypeArguments = ((ParameterizedTypeImpl) genericType).getActualTypeArguments();
            genericType = ((ParameterizedTypeImpl) genericType).getRawType();
        }
        return new ClassWrapper(wrapClass, (Class<?>) genericType, actualTypeArguments, null);
    }*/

    private ClassWrapper(Class<?> ownerType, Class<?> rawType, Type[] actualTypeArguments, Properties properties) {
        this.ownerType = ownerType;
        this.rawType = rawType;
        this.actualTypeArguments = actualTypeArguments;
        if (properties != null) {
            this.impl = properties.impl();
            this.defaultValue = properties.defaultValue();
            this.ignore = properties.ignore();
        }
    }

    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    public Class<?> getRawType() {
        return rawType;
    }

    public Class<?> getOwnerType() {
        return ownerType;
    }

    public Class<?> getImpl() {
        return impl;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isIgnore() {
        return ignore;
    }
}
