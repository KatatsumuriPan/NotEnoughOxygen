package kpan.not_enough_oxygen.config.core.properties;

import java.lang.reflect.Field;
import kpan.not_enough_oxygen.config.core.ConfigSide;
import kpan.not_enough_oxygen.config.core.properties.PropertyValueType.TypeChar;
import org.jetbrains.annotations.Nullable;

public class ConfigPropertyChar extends ConfigPropertySingle<Character> {

    public ConfigPropertyChar(Field field, @Nullable Object fieldOwnerInstance, String id, String commentForFile, int order, ConfigSide side) throws IllegalAccessException {
        super(field, fieldOwnerInstance, id, commentForFile, order, side, new TypeChar());
    }

}
