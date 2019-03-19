package org.jc.framework.converter.support;

import org.jc.framework.converter.core.AbstractConverter;

/**
 * @author xiayc
 * @date 2019/3/12
 */
public class DoubleConverter extends AbstractConverter<Double, Double> implements Converter<Double, Double> {

    public DoubleConverter(Double defaultValue) {
        super(defaultValue);
    }

    /**
     * 进行转换
     *
     * @param source
     * @return
     */
    @Override
    public Double convert(Double source) {
        if (source == null) {
            return getDefaultValue();
        }
        return source;
    }
}
