package kpan.not_enough_oxygen.capability.capabilities;

import kpan.not_enough_oxygen.capability.ProviderSerializable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ProviderCommandBlock extends ProviderSerializable<ICapabilityCommandBlock> {

    public ProviderCommandBlock() {
        super("commandblock_options");
    }

    @CapabilityInject(ICapabilityCommandBlock.class)
    public static final Capability<ICapabilityCommandBlock> CAP = null;

    @Override
    protected Capability<ICapabilityCommandBlock> getCap() { return CAP; }

}
