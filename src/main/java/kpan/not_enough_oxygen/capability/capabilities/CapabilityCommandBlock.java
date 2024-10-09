package kpan.not_enough_oxygen.capability.capabilities;

import java.util.HashMap;
import kpan.not_enough_oxygen.util.MyNBTUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class CapabilityCommandBlock implements ICapabilityCommandBlock {

    int tick = 0;
    HashMap<String, Integer> optionMap = new HashMap<>();

    public CapabilityCommandBlock() { }

    @Override
    public int getTick() { return tick; }
    @Override
    public void setTick(int tick) { this.tick = tick; }

    @Override
    public int getOption(String key) {
        Integer val = optionMap.get(key);
        return val != null ? val : 0;
    }
    @Override
    public void setOption(String key, int value) {
        optionMap.put(key, value);
    }
    @Override
    public HashMap<String, Integer> getOptionMap() { return optionMap; }

    @Override
    public NBTBase writeNBT(Capability<ICapabilityCommandBlock> capability, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("optionMap", MyNBTUtil.toNBT(optionMap));
        nbt.setInteger("tick", tick);
        return nbt;
    }
    @Override
    public void readNBT(Capability<ICapabilityCommandBlock> capability, EnumFacing side, NBTBase nbt) {
        NBTTagCompound compound = (NBTTagCompound) nbt;
        if (compound.hasKey("optionMap")) {
            MyNBTUtil.readIntMap(compound.getCompoundTag("optionMap"), optionMap);
            tick = compound.getInteger("tick");
        } else {
            MyNBTUtil.readIntMap(compound, optionMap);
        }
    }

    // syncする場合の例
    // @Override
    // public void sync(EntityPlayerMP player) {
    //     MyPacketHandler.sendToPlayer(new MessageSyncCommandBlockOptionMap(optionMap), player);
    // }
    // interfaceに宣言だけ持たせておく
    // 同期するための通信は独自に実装する

}
