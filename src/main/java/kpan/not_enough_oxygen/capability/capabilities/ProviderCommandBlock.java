package kpan.not_enough_oxygen.capability.capabilities;

import kpan.not_enough_oxygen.capability.ProviderSerializable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ProviderCommandBlock extends ProviderSerializable<ICapabilityWorld> {

    public ProviderCommandBlock() {
        super("commandblock_options");
    }

    @CapabilityInject(ICapabilityWorld.class)
    public static final Capability<ICapabilityWorld> CAP = null;

    @Override
    protected Capability<ICapabilityWorld> getCap() { return CAP; }

}
