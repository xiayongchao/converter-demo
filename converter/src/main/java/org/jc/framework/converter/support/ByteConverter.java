package org.jc.framework.converter.support;

import org.jc.framework.converter.core.AbstractConverter;

/**
 * @author xiayc
 * @date 2019/3/12
 */
public class ByteConverter extends AbstractConverter<Byte, Byte> implements Converter<Byte, Byte> {

    public ByteConverter(Byte defaultValue) {
        super(defaultValue);
    }

    /**
     * 进行转换
     *
     * @param source
     * @return
     */
    @Override
    public Byte convert(Byte source) {
        if (source == null) {
            return getDefaultValue();
        }
        return source;
    }
}
