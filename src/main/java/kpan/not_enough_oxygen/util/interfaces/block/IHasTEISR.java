package kpan.not_enough_oxygen.util.interfaces.block;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHasTEISR{

    @SideOnly(Side.CLIENT)
    TileEntityItemStackRenderer getTEISR();
}
