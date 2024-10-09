package kpan.not_enough_oxygen.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public abstract class ProviderSerializable<T extends IMyCapability<T>> extends ProviderBase<T> implements ICapabilitySerializable<NBTBase> {

    public ProviderSerializable(String name) { super(name); }

    @Override
    public NBTBase serializeNBT() {
        return getCap().getStorage().writeNBT(getCap(), instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        getCap().getStorage().readNBT(getCap(), instance, null, nbt);
    }
}
