package kpan.not_enough_oxygen.capability.capabilities;

import kpan.not_enough_oxygen.neo_world.Simulation;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class CapabilityWorld implements ICapabilityWorld {

    private Simulation simulation = new Simulation();

    public CapabilityWorld() { }

    @Override
    public Simulation getSimulation() {
        return simulation;
    }


    @Override
    public NBTBase writeNBT(Capability<ICapabilityWorld> capability, EnumFacing side) {
        return simulation.toNBT();
    }
    @Override
    public void readNBT(Capability<ICapabilityWorld> capability, EnumFacing side, NBTBase nbt) {
        if (!(nbt instanceof NBTTagCompound tagCompound))
            throw new RuntimeException("nbt is not NBTTagCompound!");
        simulation = Simulation.fromNBT(tagCompound);
    }

    // syncする場合の例
    // @Override
    // public void sync(EntityPlayerMP player) {
    //     MyPacketHandler.sendToPlayer(new MessageSyncCommandBlockOptionMap(optionMap), player);
    // }
    // interfaceに宣言だけ持たせておく
    // 同期するための通信は独自に実装する

}
