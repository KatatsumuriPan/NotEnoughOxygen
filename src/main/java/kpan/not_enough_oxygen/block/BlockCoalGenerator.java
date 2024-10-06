package kpan.not_enough_oxygen.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kpan.not_enough_oxygen.ModTagsGenerated;
import kpan.not_enough_oxygen.block.tileentity.TileEntityCoalGenerator;
import kpan.not_enough_oxygen.block.tileentity.TileEntityMultiBlockBase;
import kpan.not_enough_oxygen.block.tileentity.renderer.CoalGeneratorRenderer;
import kpan.not_enough_oxygen.client.gui.ModGuis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public class BlockCoalGenerator extends BlockMultiBlockBase<TileEntityCoalGenerator> {
    public static final PropertyBool RUNNING = PropertyBool.create("running");
    public static final AxisAlignedBB COMPONENTS_BB = new AxisAlignedBB(-1, 0, 0, 1, 2, 1);
    public static final AxisAlignedBB COLLISION_BB = new AxisAlignedBB(-11 / 16D, 11 / 16D, 2 / 16D, 25 / 16D, 39 / 16D, 28 / 16D);
    public static final AxisAlignedBB COLLISION_LEG_L_BB = new AxisAlignedBB(25 / 16D, 0 / 16D, 13 / 16D, 31 / 16D, 15 / 16D, 19 / 16D);
    public static final AxisAlignedBB COLLISION_LEG_R_BB = new AxisAlignedBB(-15 / 16D, 0 / 16D, 13 / 16D, -9 / 16D, 12 / 16D, 19 / 16D);
    public static final AxisAlignedBB COLLISION_INDICATOR_BB = new AxisAlignedBB(25 / 16D, 24 / 16D, 12 / 16D, 31 / 16D, 48 / 16D, 17 / 16D);
    public static final AxisAlignedBB COLLISION_PIPE_BB = new AxisAlignedBB(-15 / 16D, 30 / 16D, 13 / 16D, -9 / 16D, 42 / 16D, 19 / 16D);
    public static final AxisAlignedBB COLLISION_OUT_PANEL_BB = new AxisAlignedBB(1 / 16D, 1 / 16D, 31 / 16D, 15 / 16D, 15 / 16D, 32 / 16D);

    public BlockCoalGenerator() {
        super("coal_generator", Material.ROCK);
        setDefaultState(getDefaultState().withProperty(RUNNING, false));
    }
    @Override
    public boolean canConnect(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return false;
    }
    @Override
    public boolean canConnectToFiller(IBlockAccess world, BlockPos pos, BlockPos fillerPos, EnumFacing facing) {

        IBlockState state = world.getBlockState(pos);
        EnumFacing opposite = state.getValue(HORIZONTAL_FACING).getOpposite();
        if (facing != opposite)
            return false;
        if (!pos.offset(opposite).equals(fillerPos))
            return false;
        return true;
    }

    // property


    @Override
    protected ArrayList<IProperty<?>> getProperties() {
        ArrayList<IProperty<?>> properties = super.getProperties();
        properties.add(RUNNING);
        return properties;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return super.getMetaFromState(state) | (state.getValue(RUNNING) ? 8 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta).withProperty(RUNNING, (meta & 8) != 0);
    }

    // IHasMultiBlockFiller
    @Override
    protected AxisAlignedBB getComponentsBB() {
        return COMPONENTS_BB;
    }
    @Override
    protected AxisAlignedBB getCollisionBB() {
        return COLLISION_BB;
    }
    @Override
    protected List<AxisAlignedBB> getCollisionBBList(IBlockState blockState, World worldIn, BlockPos pos) {
        return Arrays.asList(COLLISION_BB, COLLISION_LEG_L_BB, COLLISION_LEG_R_BB, COLLISION_INDICATOR_BB, COLLISION_PIPE_BB, COLLISION_OUT_PANEL_BB);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos fillerPos, IBlockState fillerState, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, BlockMultiBlockFiller<BlockMultiBlockBase<TileEntityCoalGenerator>> filler) {
        BlockPos sourcePos = filler.getSourcePos(fillerState, worldIn, fillerPos);
        EnumFacing sourceFacing = worldIn.getBlockState(sourcePos).getValue(HORIZONTAL_FACING);
        if (facing == sourceFacing.getOpposite() && fillerPos.equals(sourcePos.offset(sourceFacing.getOpposite()))) {
            // 電力出力パネル
            // if (((ItemBlock) playerIn.getHeldItem(hand).getItem()).getBlock() == BlockInit.WIRE)
            return false;
        }
        return super.onBlockActivated(worldIn, fillerPos, fillerState, playerIn, hand, facing, hitX, hitY, hitZ, filler);
    }

    // tileentity
    @Nullable
    @Override
    public TileEntityMultiBlockBase createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCoalGenerator();
    }
    @Override
    public Class<TileEntityCoalGenerator> getTileEntityClass() {
        return TileEntityCoalGenerator.class;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TileEntitySpecialRenderer<TileEntityCoalGenerator> getTESR() {
        return new CoalGeneratorRenderer();
    }

    // 右クリ

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(ModTagsGenerated.MODID, ModGuis.GUI_ID_COAL_GENERATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
