package kpan.not_enough_oxygen.block;

import kpan.not_enough_oxygen.block.tileentity.TileEntityCoalGenerator;
import kpan.not_enough_oxygen.block.tileentity.renderer.CoalGeneratorRenderer;
import kpan.not_enough_oxygen.util.interfaces.block.IHasTileEntityAndRenderer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockCoalGenerator extends BlockHorizontalBase implements IHasTileEntityAndRenderer<TileEntityCoalGenerator> {
    public BlockCoalGenerator() {
        super("coal_generator", Material.ROCK);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    // tileentity
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCoalGenerator();
    }
    @Override
    public Class<TileEntityCoalGenerator> getTileEntityClass() {
        return TileEntityCoalGenerator.class;
    }

    // TESR
    @Override
    public EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TileEntitySpecialRenderer<TileEntityCoalGenerator> getTESR() {
        return new CoalGeneratorRenderer();
    }

}
