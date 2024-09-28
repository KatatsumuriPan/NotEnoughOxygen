package kpan.not_enough_oxygen.util.interfaces.block;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHasTileEntityAndRenderer<T extends TileEntity> extends IHasTileEntity<T> {

    @SideOnly(Side.CLIENT)
    TileEntitySpecialRenderer<T> getTESR();
}
