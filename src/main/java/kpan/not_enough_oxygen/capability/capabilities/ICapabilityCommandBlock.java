package kpan.not_enough_oxygen.capability.capabilities;

import java.util.HashMap;
import kpan.not_enough_oxygen.capability.IMyCapability;

public interface ICapabilityCommandBlock extends IMyCapability<ICapabilityCommandBlock> {

    int getTick();
    void setTick(int tick);
    int getOption(String key);
    void setOption(String key, int value);
    HashMap<String, Integer> getOptionMap();

    default boolean getOptionAsBool(String key) {
        return getOption(key) != 0;
    }
    default void setOption(String key, boolean value) {
        setOption(key, value ? 1 : 0);
    }
}
