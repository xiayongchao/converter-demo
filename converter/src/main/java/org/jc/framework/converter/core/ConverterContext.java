package org.jc.framework.converter.core;

import org.jc.framework.converter.exception.ConvertException;
import org.jc.framework.converter.support.CollectionAdapter;
import org.jc.framework.converter.support.Converter;
import org.jc.framework.converter.support.FieldMatcher;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author xiayc
 * @date 2019/3/18
 */
public class ConverterContext {
    private boolean deepCopy = false;
    private final ConverterRegistry converterRegistry = new ConverterRegistry();
    private final FieldMatcherRegistry fieldMatcherRegistry = new FieldMatcherRegistry();
    private final AdapterRegistry adapterRegistry = new AdapterRegistry();

    ConverterContext() {
    }

    public ConverterContext setDeepCopy(boolean deepCopy) {
        this.deepCopy = deepCopy;
        return this;
    }

    public ConverterContext addConverter(Converter<?, ?> converter) {
        converterRegistry.addConverter(converter);
        return this;
    }

    public ConverterContext addFieldMatcher(FieldMatcher fieldMatcher) {
        fieldMatcherRegistry.addFieldMatcher(fieldMatcher);
        return this;
    }

    public boolean isMatch(Field sourceField, Field targetField) {
        return fieldMatcherRegistry.isMatch(sourceField, targetField);
    }

    private <S, T> Object convert(Object source, Type sourceType, Type targetType) {
        //第一步：看是否有对应的转换器
        Converter<S, T> converter = converterRegistry.getConverter(sourceType, targetType);
        if (converter != null) {
            //有转换器则直接转换返回
            return converter.convert((S) source);
        }
        if (sourceType == null || targetType == null) {
            throw new ConvertException("sourceType&targetType的值请不要为空");
        }
        //第二步：如果类型一直则直接返回
        if (sourceType.equals(targetType) && !deepCopy) {
            return source;
        }
        if (sourceType instanceof ParameterizedType) {
            //如果需要转换的类型是泛型类型
            if (isCollection(sourceType) && isCollection(targetType)) {
                //Collection 转换
                return convert((Collection<S>) source, (Class<? extends Collection>) ((ParameterizedTypeImpl) targetType).getRawType(), (Class<T>) ((ParameterizedTypeImpl) targetType).getActualTypeArguments()[0]);
            }

            return null;
        } else {
            return convert(source, (Class<S>) sourceType, (Class<T>) targetType);
        }
    }

    private static boolean isCollection(Type type) {
        if (type == null) {
            return false;
        }
        Class<?> typeClass;
        if (type instanceof ParameterizedType) {
            typeClass = ((ParameterizedTypeImpl) type).getRawType();
        } else {
            typeClass = (Class<?>) type;
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

    private <S, T> T convert(Object source, Class<S> sourceClass, Class<T> targetClass) {
        if (source == null || sourceClass == null || targetClass == null) {
            throw new ConvertException("source&sourceClass&targetClass的值请不要为空");
        }

        Field[] sourceFields = sourceClass.getDeclaredFields();
        if (sourceFields.length < 1) {
            return null;
        }
        Field[] targetFields = targetClass.getDeclaredFields();
        if (targetFields.length < 1) {
            return null;
        }
        T target;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConvertException(String.format("创建[%s]对象异常", targetClass.getName()), e);
        }
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (isMatch(sourceField, targetField)) {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                    try {
                        targetField.set(target, convert(sourceField.get(source), sourceField.getGenericType(), targetField.getGenericType()));
                    } catch (IllegalAccessException e) {
                        throw new ConvertException(String.format("set[%s]值到[%s]异常", sourceField.getName(), targetField.getName()), e);
                    }
                }
            }
        }
        return target;
    }

    public <S, T> T convert(S source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            throw new ConvertException("source和targetClass的值请不要为空");
        }
        Class<S> sourceClass = (Class<S>) source.getClass();
        return (T) convert(source, (Type) sourceClass, (Type) targetClass);
    }

    public <S, TC extends Collection, T> TC convert(Collection<S> sourceCollection, Class<TC> targetCollectionClass, Class<T> targetClass) {
        if (targetCollectionClass == null || targetClass == null) {
            throw new ConvertException("targetCollectionClass或targetClass的值请不要为空");
        }
        if (sourceCollection == null) {
            return null;
        }
        CollectionAdapter<TC> adapter = adapterRegistry.getAdapter(targetCollectionClass);
        if (adapter == null) {
            return null;
        }
        Collection<T> targetCollection = adapter.adapt((Class<T>) targetClass);
        if (sourceCollection.isEmpty()) {
            return (TC) targetCollection;
        }
        Class<S> sourceClass = null;
        for (S source : sourceCollection) {
            if (source == null) {
                targetCollection.add(null);
            } else {
                if (sourceClass == null) {
                    sourceClass = (Class<S>) source.getClass();
                }
                targetCollection.add((T) convert(source, (Type) sourceClass, (Type) targetClass));
            }
        }
        return (TC) targetCollection;
    }
}
