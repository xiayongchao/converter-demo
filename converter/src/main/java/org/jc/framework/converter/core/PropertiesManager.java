package org.jc.framework.converter.core;

import org.jc.framework.converter.annotation.Properties;
import org.jc.framework.converter.definition.PropertiesDefinition;
import org.jc.framework.converter.exception.ConvertException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesManager {
    private final Map<String, PropertiesDefinition> propertiesDefinitionMap = new ConcurrentHashMap<>();

    public PropertiesDefinition getPropertiesDefinition(Field field) {
        if (field == null) {
            return null;
        }
        return propertiesDefinitionMap.get(field.toGenericString());
    }

    public PropertiesManager addPropertiesDefinition(Field field) {
        if (field != null && getPropertiesDefinition(field) == null) {
            Properties properties = field.getAnnotation(Properties.class);
            if (properties != null) {
                PropertiesDefinition propertiesDefinition = new PropertiesDefinition();
                propertiesDefinition.setImpl(properties.impl());
                propertiesDefinition.setIgnore(properties.ignore());
                String defaultValue = properties.defaultValue().trim();
                if (!(defaultValue.startsWith("${") && defaultValue.endsWith("}"))) {
                    throw new ConvertException("非法的defaultValue值");
                }
                defaultValue = defaultValue.substring(1, defaultValue.length() - 1);
                if (defaultValue.equals("")) {
                    propertiesDefinition.setDefaultValue(null);
                }
                String key = field.toGenericString();
                System.out.println(key);
                propertiesDefinitionMap.put(key, propertiesDefinition);
            }
        }
        return this;
    }
}
