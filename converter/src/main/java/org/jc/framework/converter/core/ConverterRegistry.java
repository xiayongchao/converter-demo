package org.jc.framework.converter.core;

import org.jc.framework.converter.definition.ClassWrapper;
import org.jc.framework.converter.support.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiayc
 * @date 2019/3/13
 */
public class ConverterRegistry {
    private final Map<String, Converter<?, ?>> converterMap = new ConcurrentHashMap<>();

    public ConverterRegistry() {
        //注册默认转换器
        addConverter(new BooleanConverter(false));
        addConverter(new ByteConverter((byte) 0));
        addConverter(new CharacterConverter(' '));
        addConverter(new DoubleConverter(0D));
        addConverter(new FloatConverter(0F));
        addConverter(new IntegerConverter(0));
        addConverter(new LongConverter(0L));
        addConverter(new ShortConverter((short) 0));
        addConverter(new StringConverter(null));
    }

    /**
     * 注册转换器
     *
     * @param converter
     */
    public ConverterRegistry addConverter(Converter<?, ?> converter) {
        if (converter != null) {
            String typeName = converter.getClass().getGenericInterfaces()[0].getTypeName();
            String converterKey = typeName.substring(typeName.indexOf('<') + 1, typeName.lastIndexOf('>')).replaceAll(" ", "");
            String[] keys = converterKey.split(",");
            if (keys[0].equals(keys[1]) && isBaseType(keys[0])) {
                String baseTypeOppositeName = getBaseTypeOppositeName(keys[0]);
                System.out.println("注册转换器：" + String.format("%s,%s", keys[0], baseTypeOppositeName));
                converterMap.put(String.format("%s,%s", keys[0], baseTypeOppositeName), converter);
                System.out.println("注册转换器：" + String.format("%s,%s", baseTypeOppositeName, keys[0]));
                converterMap.put(String.format("%s,%s", baseTypeOppositeName, keys[0]), converter);
            } else {
                System.out.println("注册转换器：" + converterKey);
                converterMap.put(converterKey, converter);
            }
        }
        return this;
    }

    private String getBaseTypeOppositeName(String className) {
        if (className == null) {
            return null;
        }
        if (className.equals(Boolean.class.getName())) {
            return boolean.class.getName();
        } else if (className.equals(Character.class.getName())) {
            return char.class.getName();
        } else if (className.equals(Byte.class.getName())) {
            return byte.class.getName();
        } else if (className.equals(Short.class.getName())) {
            return short.class.getName();
        } else if (className.equals(Integer.class.getName())) {
            return int.class.getName();
        } else if (className.equals(Long.class.getName())) {
            return long.class.getName();
        } else if (className.equals(Float.class.getName())) {
            return float.class.getName();
        } else if (className.equals(Double.class.getName())) {
            return double.class.getName();
        } else if (className.equals(boolean.class.getName())) {
            return Boolean.class.getName();
        } else if (className.equals(char.class.getName())) {
            return Character.class.getName();
        } else if (className.equals(byte.class.getName())) {
            return Byte.class.getName();
        } else if (className.equals(short.class.getName())) {
            return Short.class.getName();
        } else if (className.equals(int.class.getName())) {
            return Integer.class.getName();
        } else if (className.equals(long.class.getName())) {
            return Long.class.getName();
        } else if (className.equals(float.class.getName())) {
            return Float.class.getName();
        } else if (className.equals(double.class.getName())) {
            return Double.class.getName();
        } else {
            return null;
        }
    }

    private boolean isBaseType(String className) {
        if (className == null) {
            return false;
        }
        if (className.equals(Boolean.class.getName())) {
            return true;
        } else if (className.equals(Character.class.getName())) {
            return true;
        } else if (className.equals(Byte.class.getName())) {
            return true;
        } else if (className.equals(Short.class.getName())) {
            return true;
        } else if (className.equals(Integer.class.getName())) {
            return true;
        } else if (className.equals(Long.class.getName())) {
            return true;
        } else if (className.equals(Float.class.getName())) {
            return true;
        } else if (className.equals(Double.class.getName())) {
            return true;
        } else if (className.equals(boolean.class.getName())) {
            return true;
        } else if (className.equals(char.class.getName())) {
            return true;
        } else if (className.equals(byte.class.getName())) {
            return true;
        } else if (className.equals(short.class.getName())) {
            return true;
        } else if (className.equals(int.class.getName())) {
            return true;
        } else if (className.equals(long.class.getName())) {
            return true;
        } else if (className.equals(float.class.getName())) {
            return true;
        } else if (className.equals(double.class.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public <S, T> Converter<S, T> getConverter(ClassWrapper sourceClassWrapper, ClassWrapper targetClassWrapper) {
        return getConverter((Type) sourceClassWrapper.getRawType(), (Type) targetClassWrapper.getRawType());
    }

    public <S, T> Converter<S, T> getConverter(Class<S> sourceClass, Class<T> targetClass) {
        if (sourceClass == null || targetClass == null) {
            return null;
        }
        String key = String.format("%s,%s", sourceClass.getName(), targetClass.getName());
        System.out.println("获取key=" + key);
        return (Converter<S, T>) converterMap.get(key);
    }

    public <S, T> Converter<S, T> getConverter(Type sourceType, Type targetType) {
        if (sourceType == null || targetType == null) {
            return null;
        }
        if (!(sourceType instanceof ParameterizedType)) {
            return getConverter((Class<S>) sourceType, (Class<T>) targetType);
        }
        //如果是泛型
        String key = String.format("%s,%s", sourceType.getTypeName(), targetType.getTypeName());
        System.out.println("获取key=" + key);
        return (Converter<S, T>) converterMap.get(key);
    }
}
