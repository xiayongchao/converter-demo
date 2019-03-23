package org.jc.framework.converter.core;

/**
 * 转换器
 *
 * @author xiayc
 * @date 2019/3/11
 */
public interface Converter<S, T> {

    /**
     * 进行转换
     *
     * @param source
     * @return
     */
    T convert(S source);

    /**
     * 获取默认值(即S为null的情况下应该给T什么值)
     *
     * @return
     */
//    T getDefaultValue();

    /**
     * 拷贝属性
     *
     * @param source
     * @param target
     */
    /*default void copyProperties(S source, T target) {
    }*/
}
