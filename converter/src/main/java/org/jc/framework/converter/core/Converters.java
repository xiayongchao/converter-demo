package org.jc.framework.converter.core;

import java.util.Collection;

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

    public static ConverterContext setDeepCopy(boolean deepCopy) {
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

    public static <S, TC extends Collection, T> Collection<T> convert(Collection<S> sourceCollection, Class<TC> targetCollectionClass, Class<T> targetClass) {
        return DEFAULT_CONVERTER_CONTEXT.convert(sourceCollection, targetCollectionClass, targetClass);
    }
}
