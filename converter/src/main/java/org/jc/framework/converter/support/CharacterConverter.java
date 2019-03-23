package org.jc.framework.converter.support;

import org.jc.framework.converter.core.AbstractConverter;
import org.jc.framework.converter.core.Converter;

/**
 * @author xiayc
 * @date 2019/3/12
 */
public class CharacterConverter extends AbstractConverter<Character, Character> implements Converter<Character, Character> {

    public CharacterConverter(Character defaultValue) {
        super(defaultValue);
    }

    /**
     * 进行转换
     *
     * @param source
     * @return
     */
    @Override
    public Character convert(Character source) {
        if (source == null) {
            return getDefaultValue();
        }
        return source;
    }
}
