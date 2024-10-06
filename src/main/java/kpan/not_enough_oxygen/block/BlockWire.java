package kpan.not_enough_oxygen.block;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockWire extends BlockBase implements IHasMultiCollision {

    public static final PropertyEnum<EnumModel> MODEL = PropertyEnum.create("model", EnumModel.class);
    public static final EnumMap<EnumFacing, PropertyEnum<EnumConnection>> connectionMap = new EnumMap<>(EnumFacing.class);

    public static final AxisAlignedBB CORE_BB = new AxisAlignedBB(6 / 16D, 6 / 16D, 6 / 16D, 10 / 16D, 10 / 16D, 10 / 16D);
    public static final AxisAlignedBB DOWN_BB = new AxisAlignedBB(6 / 16D, 0 / 16D, 6 / 16D, 10 / 16D, 6 / 16D, 10 / 16D);
    public static final AxisAlignedBB UP_BB = new AxisAlignedBB(6 / 16D, 10 / 16D, 6 / 16D, 10 / 16D, 16 / 16D, 10 / 16D);
    public static final AxisAlignedBB NORTH_BB = new AxisAlignedBB(6 / 16D, 6 / 16D, 0 / 16D, 10 / 16D, 10 / 16D, 6 / 16D);
    public static final AxisAlignedBB SOUTH_BB = new AxisAlignedBB(6 / 16D, 6 / 16D, 10 / 16D, 10 / 16D, 10 / 16D, 16 / 16D);
    public static final AxisAlignedBB WEST_BB = new AxisAlignedBB(0 / 16D, 6 / 16D, 6 / 16D, 6 / 16D, 10 / 16D, 10 / 16D);
    public static final AxisAlignedBB EAST_BB = new AxisAlignedBB(10 / 16D, 6 / 16D, 6 / 16D, 16 / 16D, 10 / 16D, 10 / 16D);
    public static final AxisAlignedBB[] FACING_BB_LIST = new AxisAlignedBB[6];

    static {
        for (EnumFacing facing : EnumFacing.VALUES) {
            connectionMap.put(facing, PropertyEnum.create(facing.getName().toLowerCase(Locale.ROOT), EnumConnection.class));
        }
        FACING_BB_LIST[EnumFacing.DOWN.getIndex()] = DOWN_BB;
        FACING_BB_LIST[EnumFacing.UP.getIndex()] = UP_BB;
        FACING_BB_LIST[EnumFacing.NORTH.getIndex()] = NORTH_BB;
        FACING_BB_LIST[EnumFacing.SOUTH.getIndex()] = SOUTH_BB;
        FACING_BB_LIST[EnumFacing.WEST.getIndex()] = WEST_BB;
        FACING_BB_LIST[EnumFacing.EAST.getIndex()] = EAST_BB;
    }

    public BlockWire(String name) {
        super(name, Material.IRON);
    }

    @Override
    protected ArrayList<IProperty<?>> getProperties() {
        ArrayList<IProperty<?>> properties = super.getProperties();
        properties.add(MODEL);
        properties.addAll(connectionMap.values());
        return properties;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        int connectionNum = 0;
        EnumFacing connection1 = null;
        EnumFacing connection2 = null;
        for (EnumFacing facing : EnumFacing.VALUES) {
            IBlockState facingState = worldIn.getBlockState(pos.offset(facing));
            if (facingState.getBlock() == BlockInit.WIRE) {
                state = state.withProperty(connectionMap.get(facing), EnumConnection.JOINT);
                connectionNum++;
                connection1 = connection2;
                connection2 = facing;
            } else if (facingState.getBlock() instanceof IWireConnectable block && block.canConnect(facingState, worldIn, pos.offset(facing), facing.getOpposite())) {
                state = state.withProperty(connectionMap.get(facing), EnumConnection.PLUG);
                connectionNum++;
                connection1 = connection2;
                connection2 = facing;
            } else {
                state = state.withProperty(connectionMap.get(facing), EnumConnection.NONE);
            }
        }
        if (connectionNum == 2) {
            if (connection1.getOpposite() == connection2)
                state = state.withProperty(MODEL, EnumModel.STRAIGHT);
            else
                state = state.withProperty(MODEL, EnumModel.CORNER);
        } else {
            state = state.withProperty(MODEL, EnumModel.MULTI);
        }
        return state;
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

    // collision

    @Override
    public List<AxisAlignedBB> getCollisionBBList(IBlockState blockState, World worldIn, BlockPos pos) {
        IBlockState actualState = getActualState(blockState, worldIn, pos);
        List<AxisAlignedBB> res = new ArrayList<>();
        res.add(CORE_BB);
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (actualState.getValue(connectionMap.get(facing)) != EnumConnection.NONE)
                res.add(FACING_BB_LIST[facing.getIndex()]);
        }
        return res;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        for (AxisAlignedBB bb : getCollisionBBList(state, worldIn, pos)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
        }
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return collisionRayTrace(pos, blockState, worldIn, pos, start, end);
    }


    public enum EnumModel implements IStringSerializable {
        STRAIGHT,
        CORNER,
        MULTI,
        ;
        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    public enum EnumConnection implements IStringSerializable {
        NONE,
        JOINT,
        PLUG,
        ;
        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
