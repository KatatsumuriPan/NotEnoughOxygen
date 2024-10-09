package kpan.not_enough_oxygen.capability;

import kpan.not_enough_oxygen.ModTagsGenerated;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public abstract class ProviderBase<T extends IMyCapability<T>> implements ICapabilityProvider {

    protected final ResourceLocation name;
    protected T instance = getCap().getDefaultInstance();

    protected abstract Capability<T> getCap();

    public ProviderBase(String name) {
        this.name = new ResourceLocation(ModTagsGenerated.MODID, name);
    }

    public ResourceLocation getName() { return name; }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == getCap();
    }

    @Override
    public <S> S getCapability(Capability<S> capability, EnumFacing facing) {
        return capability == getCap() ? getCap().<S>cast(instance) : null;
    }

}
