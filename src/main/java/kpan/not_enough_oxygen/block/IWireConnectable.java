package kpan.not_enough_oxygen.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IWireConnectable {

    boolean canConnect(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing);
}
