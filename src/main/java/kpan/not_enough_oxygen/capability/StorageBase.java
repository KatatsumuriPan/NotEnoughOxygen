package kpan.not_enough_oxygen.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageBase<T extends IMyCapability<T>> implements IStorage<T> {

    @Override
    public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
        return instance.writeNBT(capability, side);
    }

    @Override
    public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
        instance.readNBT(capability, side, nbt);
    }

}
