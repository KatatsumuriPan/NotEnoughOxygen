package kpan.not_enough_oxygen.block;

import java.util.ArrayList;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHorizontalBase extends BlockBase {
    public static final PropertyDirection HORIZONTAL_FACING = BlockHorizontal.FACING;

    public BlockHorizontalBase(String name, Material material) {
        super(name, material);
    }


    @Override
    protected ArrayList<IProperty<?>> getProperties() {
        ArrayList<IProperty<?>> properties = super.getProperties();
        properties.add(HORIZONTAL_FACING);
        return properties;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = super.getStateFromMeta(meta);
        state.withProperty(HORIZONTAL_FACING, EnumFacing.byHorizontalIndex(meta));
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HORIZONTAL_FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

}
