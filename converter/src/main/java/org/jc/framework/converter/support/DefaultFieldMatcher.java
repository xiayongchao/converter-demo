package org.jc.framework.converter.support;

/**
 * @author xiayc
 * @date 2019/3/13
 */
public class DefaultFieldMatcher implements FieldMatcher {
    /**
     * 是否匹配
     *
     * @param sourceFieldName
     * @param targetFieldName
     * @return
     */
    @Override
    public boolean isMatch(String sourceFieldName, String targetFieldName) {
        return sourceFieldName != null && sourceFieldName.equals(targetFieldName);
    }
}
