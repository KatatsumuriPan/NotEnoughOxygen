package kpan.not_enough_oxygen.block;

import java.util.ArrayList;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
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
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockHorizontalBase(String name, Material material) {
        super(name, material);
    }
    public BlockHorizontalBase(String name, Material material, MapColor mapColor) {
        super(name, material, mapColor);
    }
    public BlockHorizontalBase(String name, Material material, SoundType soundtype) {
        super(name, material, soundtype);
    }
    public BlockHorizontalBase(String name, Material material, MapColor mapColor, SoundType soundtype) {
        super(name, material, mapColor, soundtype);
    }
    public BlockHorizontalBase(String name, Material material, float hardness) {
        super(name, material, hardness);
    }
    public BlockHorizontalBase(String name, Material material, MapColor mapColor, float hardness) {
        super(name, material, mapColor, hardness);
    }
    public BlockHorizontalBase(String name, Material material, SoundType soundtype, float hardness) {
        super(name, material, soundtype, hardness);
    }
    public BlockHorizontalBase(String name, Material material, MapColor mapColor, SoundType soundtype, float hardness) {
        super(name, material, mapColor, soundtype, hardness);
    }


    @Override
    protected ArrayList<IProperty<?>> getProperties() {
        ArrayList<IProperty<?>> properties = super.getProperties();
        properties.add(FACING);
        return properties;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = super.getStateFromMeta(meta);
        state.withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

}
