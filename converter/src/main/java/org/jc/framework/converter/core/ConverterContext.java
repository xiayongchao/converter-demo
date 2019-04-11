package org.jc.framework.converter.core;

import org.jc.framework.converter.common.ReflectUtils;
import org.jc.framework.converter.definition.ClassWrapper;
import org.jc.framework.converter.definition.PropertiesDefinition;
import org.jc.framework.converter.exception.ConvertException;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * @author xiayc
 * @date 2019/3/18
 */
public class ConverterContext {
    /**
     * 深拷贝
     */
    private boolean deepCopy = false;
    private final ConverterRegistry converterRegistry = new ConverterRegistry();
    private final FieldMatcherRegistry fieldMatcherRegistry = new FieldMatcherRegistry();
    private final AdapterRegistry adapterRegistry = new AdapterRegistry();
    private final PropertiesManager propertiesManager = new PropertiesManager();

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

    private <S, T> Object convert(Object source, ClassWrapper sourceClassWrapper, ClassWrapper targetClassWrapper) {
        //第一步：看是否有对应的转换器
        Converter<S, T> converter = converterRegistry.getConverter(sourceClassWrapper, targetClassWrapper);
        if (converter != null) {
            //有转换器则直接转换返回
            return converter.convert((S) source);
        }
        return null;
    }

    private <S, T> Object convert(Object source, Field sourceField, Field targetField) {
        if (source == null) {
            PropertiesDefinition propertiesDefinition = propertiesManager.getPropertiesDefinition(targetField);
            if (propertiesDefinition != null) {
                return propertiesDefinition.getDefaultValue();
            }
        }
        return convert(source, sourceField.getGenericType(), targetField.getGenericType());
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
        //第二步：如果类型一致则直接返回
        if (sourceType.equals(targetType) && !deepCopy) {
            return source;
        }
        if (sourceType instanceof ParameterizedType) {
            //如果需要转换的类型是泛型类型
            if (ReflectUtils.isCollection(sourceType) && ReflectUtils.isCollection(targetType)) {
                //Collection转换
                return convert((Collection<S>) source, (Class<? extends Collection>) ((ParameterizedTypeImpl) targetType).getRawType(),
                        (Class<T>) ((ParameterizedTypeImpl) targetType).getActualTypeArguments()[0]);
            }
            if (ReflectUtils.isMap(sourceType) && ReflectUtils.isMap(targetType)) {
                //Map转换
                return convert((Map<?, ?>) source, (Class<? extends Map>) ((ParameterizedTypeImpl) targetType).getRawType(),
                        (Class<?>) ((ParameterizedTypeImpl) targetType).getActualTypeArguments()[0],
                        (Class<?>) ((ParameterizedTypeImpl) targetType).getActualTypeArguments()[1]);
            }
            return null;
        } else {
            return convert(source, (Class<S>) sourceType, (Class<T>) targetType);
        }
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
        PropertiesDefinition propertiesDefinition;
        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (isMatch(sourceField, targetField)) {
                    try {
//                        propertiesManager.addPropertiesDefinition(targetField);
//                        ClassWrapper.wrap(targetField);
                        sourceField.setAccessible(true);
                        targetField.setAccessible(true);
                        targetField.set(target, convert(sourceField.get(source), sourceField.getGenericType(), targetField.getGenericType()));
                        convert(sourceField.get(source), sourceField, targetField);
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

    public <S, TC extends Collection, T> Collection<T> convert(Collection<S> sourceCollection, Class<TC> targetCollectionClass, Class<T> targetClass) {
        if (targetCollectionClass == null || targetClass == null) {
            throw new ConvertException("targetCollectionClass或targetClass的值请不要为空");
        }
        if (sourceCollection == null) {
            return null;
        }

        Collection<T> targetCollection;
        if (targetCollectionClass.isInterface()) {
            CollectionAdapter<TC> adapter = adapterRegistry.getCollectionAdapter(targetCollectionClass);
            if (adapter == null) {
                return null;
            }
            targetCollection = adapter.adapt((Class<T>) targetClass);
        } else {
            try {
                targetCollection = targetCollectionClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ConvertException("创建targetCollection异常", e);
            }
        }

        if (sourceCollection.isEmpty()) {
            return targetCollection;
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
        return targetCollection;
    }

    public <SK, SV, TM extends Map, TK, TV> Map<TK, TV> convert(Map<SK, SV> sourceMap, Class<TM> targetMapClass, Class<TK> targetKeyClass, Class<TV> targetValueClass) {
        if (targetMapClass == null || targetKeyClass == null || targetValueClass == null) {
            throw new ConvertException("targetMapClass、targetKeyClass或targetValueClass的值请不要为空");
        }
        if (sourceMap == null) {
            return null;
        }
        MapAdapter<TM> adapter = adapterRegistry.getMapAdapter(targetMapClass);
        if (adapter == null) {
            return null;
        }
        Map<TK, TV> targetMap = adapter.adapt(targetKeyClass, targetValueClass);
        if (sourceMap.isEmpty()) {
            return targetMap;
        }
        for (Map.Entry<SK, SV> entry : sourceMap.entrySet()) {
            targetMap.put(entry.getKey() == null ? null : convert(entry.getKey(), targetKeyClass),
                    entry.getValue() == null ? null : convert(entry.getValue(), targetValueClass));
        }
        return targetMap;
    }
}
