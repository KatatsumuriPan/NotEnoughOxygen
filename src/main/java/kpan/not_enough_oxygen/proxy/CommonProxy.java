package kpan.not_enough_oxygen.proxy;

import kpan.not_enough_oxygen.util.interfaces.block.IHasTileEntityAndRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

@SuppressWarnings("unused")
public class CommonProxy {
    public void registerOnlyClient() { }

    public boolean hasClientSide() { return false; }

    public void registerSingleModel(Item item, int meta, String id) { }

    public void registerMultiItemModel(Item item, int meta, String filename, String id) { }

    public <T extends TileEntity> void registerTESR(IHasTileEntityAndRenderer<T> hasTESR) { }
}
