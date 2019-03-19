package org.jc.framework.converter.support;

import org.jc.framework.converter.core.AbstractConverter;

/**
 * @author xiayc
 * @date 2019/3/12
 */
public class IntegerConverter extends AbstractConverter<Integer, Integer> implements Converter<Integer, Integer> {

    public IntegerConverter(Integer defaultValue) {
        super(defaultValue);
    }

    /**
     * 进行转换
     *
     * @param source
     * @return
     */
    @Override
    public Integer convert(Integer source) {
        if (source == null) {
            return getDefaultValue();
        }
        return source;
    }
}
