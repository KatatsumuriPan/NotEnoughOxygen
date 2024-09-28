package kpan.not_enough_oxygen.util.interfaces.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;

public interface IHasTileEntity<T extends TileEntity> extends ITileEntityProvider {

    Class<T> getTileEntityClass();
}
