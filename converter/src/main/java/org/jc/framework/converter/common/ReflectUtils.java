package org.jc.framework.converter.common;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * 反射工具类
 */
public final class ReflectUtils {
    /**
     * 根据Type获取Class
     *
     * @param type
     * @return
     */
    public static Class<?> getTypeClass(Type type) {
        if (type == null) {
            return null;
        }
        Class<?> typeClass;
        if (type instanceof ParameterizedType) {
            typeClass = ((ParameterizedTypeImpl) type).getRawType();
        } else {
            typeClass = (Class<?>) type;
        }
        return typeClass;
    }

    /**
     * 判断是否是Collection
     *
     * @param type
     * @return
     */
    public static boolean isCollection(Type type) {
        Class<?> typeClass = getTypeClass(type);
        if (typeClass == null) {
            return false;
        }
        if (typeClass.equals(Collection.class)) {
            return true;
        }
        Type[] genericInterfaces = typeClass.getGenericInterfaces();
        if (genericInterfaces.length > 0) {
            for (Type genericInterface : genericInterfaces) {
                if (isCollection(genericInterface)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是Map
     *
     * @param type
     * @return
     */
    public static boolean isMap(Type type) {
        Class<?> typeClass = getTypeClass(type);
        if (typeClass == null) {
            return false;
        }
        if (typeClass.equals(Map.class)) {
            return true;
        }
        Type[] genericInterfaces = typeClass.getGenericInterfaces();
        if (genericInterfaces.length > 0) {
            for (Type genericInterface : genericInterfaces) {
                if (isMap(genericInterface)) {
                    return true;
                }
            }
        }
        return false;
    }
}
