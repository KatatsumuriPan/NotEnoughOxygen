package kpan.not_enough_oxygen.block;

import com.google.common.collect.AbstractIterator;
import java.util.ArrayList;
import java.util.List;
import kpan.not_enough_oxygen.block.tileentity.TileEntityMultiBlockBase;
import kpan.not_enough_oxygen.util.interfaces.block.IHasTileEntityAndRenderer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BlockMultiBlockBase<T extends TileEntityMultiBlockBase> extends BlockHorizontalBase implements IHasTileEntityAndRenderer<T>, BlockMultiBlockFiller.IHasMultiBlockFiller<BlockMultiBlockBase<T>> {

    public final BlockMultiBlockFiller<BlockMultiBlockBase<T>> filler;

    public BlockMultiBlockBase(String name, Material material) {
        super(name, material);
        filler = new BlockMultiBlockFiller<>(this);
    }

    protected abstract AxisAlignedBB getComponentsBB();
    protected abstract AxisAlignedBB getCollisionBB();
    protected abstract List<AxisAlignedBB> getCollisionBBList(IBlockState blockState, World worldIn, BlockPos pos);

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

    @Override
    protected AxisAlignedBB baseBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(BlockHorizontalBase.HORIZONTAL_FACING);
        return rotate(getComponentsBB(), facing).expand(1, 1, 1);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        // EnumFacing facing = state.getValue(BlockHorizontalBase.HORIZONTAL_FACING);
        // return rotate(getCollisionBB().contract(1, 1, 1), facing).expand(1, 1, 1).offset(pos);
        return super.getSelectedBoundingBox(state, worldIn, pos);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        EnumFacing facing = blockState.getValue(BlockHorizontalBase.HORIZONTAL_FACING);
        return rotate(getCollisionBB().contract(1, 1, 1), facing).expand(1, 1, 1);
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

    @Override
    public @Nullable RayTraceResult collisionRayTrace(IBlockState fillerState, World worldIn, BlockPos fillerPos, Vec3d start, Vec3d end, BlockMultiBlockFiller<BlockMultiBlockBase<T>> filler) {
        BlockPos sourcePos = filler.getSourcePos(fillerState, worldIn, fillerPos);
        if (sourcePos == null) {
            return null;
        }
        IBlockState sourceState = worldIn.getBlockState(sourcePos);
        return collisionRayTrace(sourcePos, sourceState, worldIn, fillerPos, start, end);
    }

    @Nullable
    public RayTraceResult collisionRayTrace(BlockPos sourcePos, IBlockState sourceState, World worldIn, BlockPos targetPos, Vec3d start, Vec3d end) {
        List<RayTraceResult> list = new ArrayList<>();

        for (AxisAlignedBB axisalignedbb : getCollisionBBList(sourceState, worldIn, sourcePos)) {
            list.add(rayTrace(targetPos, start, end, axisalignedbb, sourcePos));
        }

        RayTraceResult res = null;
        double maxDistanceSq = 0.0D;

        for (RayTraceResult rtr : list) {
            if (rtr != null) {
                double distSq = rtr.hitVec.squareDistanceTo(end);

                if (distSq > maxDistanceSq) {
                    res = rtr;
                    maxDistanceSq = distSq;
                }
            }
        }

        return res;
    }

    @javax.annotation.Nullable
    protected RayTraceResult rayTrace(BlockPos targetPos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox, BlockPos sourcePos) {
        Vec3d vec3d = start.subtract(sourcePos.getX(), sourcePos.getY(), sourcePos.getZ());
        Vec3d vec3d1 = end.subtract(sourcePos.getX(), sourcePos.getY(), sourcePos.getZ());
        RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
        return raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec.add(sourcePos.getX(), sourcePos.getY(), sourcePos.getZ()), raytraceresult.sideHit, targetPos);
    }


    public static Iterable<? extends BlockPos> getComponentsPos(BlockPos basePos, AxisAlignedBB aabb) {
        return (Iterable<PooledMutableBlockPos>) () -> new AbstractIterator<>() {

            private boolean px = true;
            private boolean py = true;
            private boolean pz = true;
            private int x = 0;
            private int y = 0;
            private int z = 0;
            private PooledMutableBlockPos pos;
            @Override
            protected PooledMutableBlockPos computeNext() {
                if (pos == null) {
                    if (!pz)
                        return endOfData();
                    x = basePos.getX();
                    y = basePos.getY();
                    z = basePos.getZ();
                    pos = PooledMutableBlockPos.retain(x, y, z);
                    return pos;
                }

                if (py) {
                    y++;
                    if (y <= basePos.getY() + aabb.maxY) {
                        pos.setY(y);
                        return pos;
                    }
                    y = basePos.getY();
                    py = false;
                }
                // py=false
                y--;
                if (y >= basePos.getY() + aabb.minY) {
                    pos.setY(y);
                    return pos;
                }
                y = basePos.getY();
                py = true;

                if (px) {
                    x++;
                    if (x <= basePos.getX() + aabb.maxX) {
                        return pos.setPos(x, y, z);
                    }
                    x = basePos.getX();
                    px = false;
                }
                // px=false
                x--;
                if (x >= basePos.getX() + aabb.minX) {
                    return pos.setPos(x, y, z);
                }
                x = basePos.getX();
                px = true;

                if (pz) {
                    z++;
                    if (z <= basePos.getZ() + aabb.maxZ) {
                        return pos.setPos(x, y, z);
                    }
                    z = basePos.getZ();
                    pz = false;
                }
                // pz=false
                z--;
                if (z >= basePos.getZ() + aabb.minZ) {
                    return pos.setPos(x, y, z);
                }

                pos.release();
                pos = null;
                return endOfData();
            }

        };
    }

    // tileentity
    @Override
    @Nullable
    public abstract TileEntityMultiBlockBase createNewTileEntity(World worldIn, int meta);

    // TESR
    @Override
    public EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    // Filler

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        filler.fillAll(worldIn, pos, getComponentsPos(pos, rotate(getComponentsBB(), state.getValue(HORIZONTAL_FACING))));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        filler.removeAll(worldIn, pos, getComponentsPos(pos, rotate(getComponentsBB(), state.getValue(HORIZONTAL_FACING))));
    }

    private static AxisAlignedBB rotate(AxisAlignedBB northBB, EnumFacing facing) {
        switch (facing) {
            case DOWN -> {
                return new AxisAlignedBB(northBB.minX, northBB.minZ, -northBB.maxY, northBB.maxX, northBB.maxZ, -northBB.minY);
            }
            case UP -> {
                return new AxisAlignedBB(northBB.minX, -northBB.maxZ, northBB.minY, northBB.maxX, -northBB.minZ, northBB.maxY);
            }
            case NORTH -> {
                return northBB;
            }
            case SOUTH -> {
                return new AxisAlignedBB(-northBB.maxX, northBB.minY, -northBB.maxZ, -northBB.minX, northBB.maxY, -northBB.minZ);
            }
            case WEST -> {
                return new AxisAlignedBB(northBB.minZ, northBB.minY, -northBB.maxX, northBB.maxZ, northBB.maxY, -northBB.minX);
            }
            case EAST -> {
                return new AxisAlignedBB(-northBB.maxZ, northBB.minY, northBB.minX, -northBB.minZ, northBB.maxY, northBB.maxX);
            }
            default -> throw new IllegalStateException("Unexpected value: " + facing);
        }
    }

}
