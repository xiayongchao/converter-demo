package org.jc.framework.converter.support;

import org.jc.framework.converter.core.AbstractConverter;

/**
 * @author xiayc
 * @date 2019/3/12
 */
public class BooleanConverter extends AbstractConverter<Boolean, Boolean> implements Converter<Boolean, Boolean> {

    public BooleanConverter(Boolean defaultValue) {
        super(defaultValue);
    }

    /**
     * 进行转换
     *
     * @param source
     * @return
     */
    @Override
    public Boolean convert(Boolean source) {
        if (source == null) {
            return getDefaultValue();
        }
        return source;
    }
}
