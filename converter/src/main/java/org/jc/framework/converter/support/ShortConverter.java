package org.jc.framework.converter.support;

import org.jc.framework.converter.core.AbstractConverter;
import org.jc.framework.converter.core.Converter;

/**
 * @author xiayc
 * @date 2019/3/12
 */
public class ShortConverter extends AbstractConverter<Short, Short> implements Converter<Short, Short> {

    public ShortConverter(Short defaultValue) {
        super(defaultValue);
    }

    /**
     * 进行转换
     *
     * @param source
     * @return
     */
    @Override
    public Short convert(Short source) {
        if (source == null) {
            return getDefaultValue();
        }
        return source;
    }
}
