package kpan.not_enough_oxygen.config.core.properties;

import java.lang.reflect.Field;
import kpan.not_enough_oxygen.config.core.ConfigAnnotations.RangeFloat;
import kpan.not_enough_oxygen.config.core.ConfigSide;
import kpan.not_enough_oxygen.config.core.properties.PropertyValueType.TypeFloat;
import org.jetbrains.annotations.Nullable;

public class ConfigPropertyFloat extends ConfigPropertySingle<Float> {

    private boolean hasSlidingControl = false;

    public ConfigPropertyFloat(Field field, @Nullable Object fieldOwnerInstance, String id, String commentForFile, int order, ConfigSide side) throws IllegalAccessException {
        super(field, fieldOwnerInstance, id, commentForFile, order, side, createTypeFloat(field.getAnnotation(RangeFloat.class)));
    }

    private static TypeFloat createTypeFloat(@Nullable RangeFloat range) {
        return range != null ? new TypeFloat(range.minValue(), range.maxValue()) : new TypeFloat();
    }

    @Override
    public String getAdditionalComment() {
        float minValue = ((TypeFloat) valueType).minValue;
        float maxValue = ((TypeFloat) valueType).maxValue;
        if (minValue == Float.NEGATIVE_INFINITY) {
            if (maxValue == Float.POSITIVE_INFINITY)
                return "Default: " + defaultValue;
            else
                return "Range: ~ " + maxValue + "\nDefault: " + defaultValue;
        } else {
            if (maxValue == Float.POSITIVE_INFINITY)
                return "Range: " + minValue + " ~" + "\nDefault: " + defaultValue;
            else
                return "Range: " + minValue + " ~ " + maxValue + "\nDefault: " + defaultValue;
        }
    }


    public boolean hasSlidingControl() {
        return hasSlidingControl;
    }

    public void setHasSlidingControl(boolean hasSlidingControl) {
        this.hasSlidingControl = hasSlidingControl;
    }
}
