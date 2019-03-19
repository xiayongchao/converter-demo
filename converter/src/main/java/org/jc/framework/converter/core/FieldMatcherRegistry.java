package org.jc.framework.converter.core;

import org.jc.framework.converter.support.DefaultFieldMatcher;
import org.jc.framework.converter.support.FieldMatcher;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author xiayc
 * @date 2019/3/14
 */
public class FieldMatcherRegistry {
    private final Set<FieldMatcher> fieldMatcherSet = new LinkedHashSet<>();

    public FieldMatcherRegistry() {
        addFieldMatcher(new DefaultFieldMatcher());
    }

    public FieldMatcherRegistry addFieldMatcher(FieldMatcher fieldMatcher) {
        if (fieldMatcher != null) {
            fieldMatcherSet.add(fieldMatcher);
        }
        return this;
    }

    public boolean isMatch(Field sourceField, Field targetField) {
        if (fieldMatcherSet.size() < 1 || sourceField == null || targetField == null) {
            return false;
        }
        String sourceFieldName = sourceField.getName();
        String targetFieldName = targetField.getName();
        for (FieldMatcher fieldMatcher : fieldMatcherSet) {
            if (fieldMatcher == null) {
                continue;
            }
            if (fieldMatcher.isMatch(sourceFieldName, targetFieldName)) {
                return true;
            }
        }
        return false;
    }
}
