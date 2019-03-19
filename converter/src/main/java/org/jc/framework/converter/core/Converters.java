package org.jc.framework.converter.core;

import org.jc.framework.converter.support.Converter;
import org.jc.framework.converter.support.FieldMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 转换器工具类
 *
 * @author xiayc
 * @date 2019/3/11
 */
public final class Converters {
    private static final ConverterContext DEFAULT_CONVERTER_CONTEXT = new ConverterContext();

    private Converters() {
    }

    public static ConverterContext newConverterContext() {
        return new ConverterContext();
    }

    public ConverterContext setDeepCopy(boolean deepCopy) {
        return DEFAULT_CONVERTER_CONTEXT.setDeepCopy(deepCopy);
    }

    public static ConverterContext addConverter(Converter<?, ?> converter) {
        return DEFAULT_CONVERTER_CONTEXT.addConverter(converter);
    }

    public static ConverterContext addFieldMatcher(FieldMatcher fieldMatcher) {
        return DEFAULT_CONVERTER_CONTEXT.addFieldMatcher(fieldMatcher);
    }

    public static <S, T> T convert(S source, Class<T> targetClass) {
        return DEFAULT_CONVERTER_CONTEXT.convert(source, targetClass);
    }

    public static <S, T> List<T> convert(List<S> sourceList, Class<T> targetClass) {
        return DEFAULT_CONVERTER_CONTEXT.convert(sourceList, targetClass);
    }

    public static <S, TC extends Collection, T> TC convert(Collection<S> sourceCollection, Class<TC> targetCollectionClass, Class<T> targetClass) {
        return DEFAULT_CONVERTER_CONTEXT.convert(sourceCollection, targetCollectionClass, targetClass);
    }
}
