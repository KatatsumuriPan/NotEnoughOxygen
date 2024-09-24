package kpan.not_enough_oxygen.config.core.properties;

import java.lang.reflect.Field;
import kpan.not_enough_oxygen.config.core.ConfigAnnotations.RangeLong;
import kpan.not_enough_oxygen.config.core.ConfigSide;
import kpan.not_enough_oxygen.config.core.properties.PropertyValueType.TypeLong;
import org.jetbrains.annotations.Nullable;

public final class ConfigPropertyLong extends ConfigPropertySingle<Long> {

    private boolean hasSlidingControl = false;

    public ConfigPropertyLong(Field field, @Nullable Object fieldOwnerInstance, String id, String commentForFile, int order, ConfigSide side) throws IllegalAccessException {
        super(field, fieldOwnerInstance, id, commentForFile, order, side, createTypeLong(field.getAnnotation(RangeLong.class)));
    }

    private static TypeLong createTypeLong(@Nullable RangeLong range) {
        return range != null ? new TypeLong(range.minValue(), range.maxValue()) : new TypeLong();
    }

    @Override
    public String getAdditionalComment() {
        long minValue = ((TypeLong) valueType).minValue;
        long maxValue = ((TypeLong) valueType).maxValue;
        if (minValue == Long.MIN_VALUE) {
            if (maxValue == Long.MAX_VALUE)
                return "Default: " + defaultValue;
            else
                return "Range: ~ " + maxValue + "\nDefault: " + defaultValue;
        } else {
            if (maxValue == Long.MAX_VALUE)
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
