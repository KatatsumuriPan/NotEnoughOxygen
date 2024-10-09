package kpan.not_enough_oxygen.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public interface IMyCapability<T extends IMyCapability<T>> {

    NBTBase writeNBT(Capability<T> capability, EnumFacing side);
    void readNBT(Capability<T> capability, EnumFacing side, NBTBase nbt);

}
