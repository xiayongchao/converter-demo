package org.jc.framework.converter.support;

/**
 * 字段匹配器
 *
 * @author xiayc
 * @date 2019/3/13
 */
public interface FieldMatcher {
    /**
     * 是否匹配
     *
     * @param sourceFieldName
     * @param targetFieldName
     * @return
     */
    boolean isMatch(String sourceFieldName, String targetFieldName);
}
