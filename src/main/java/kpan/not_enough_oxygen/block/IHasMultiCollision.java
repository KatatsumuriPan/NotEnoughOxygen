package kpan.not_enough_oxygen.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface IHasMultiCollision {
    List<AxisAlignedBB> getCollisionBBList(IBlockState blockState, World worldIn, BlockPos pos);

    // 以下の2つを実装する
    // @Override
    // public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    //     for (AxisAlignedBB bb : getCollisionBBList(state, worldIn, pos)) {
    //         addCollisionBoxToList(pos, entityBox, collidingBoxes, bb);
    //     }
    // }

    // @Nullable
    // @Override
    // public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
    //     return collisionRayTrace(pos, blockState, worldIn, pos, start, end);
    // }

    @Nullable
    default RayTraceResult collisionRayTrace(BlockPos sourcePos, IBlockState sourceState, World worldIn, BlockPos targetPos, Vec3d start, Vec3d end) {
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
    default RayTraceResult rayTrace(BlockPos targetPos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox, BlockPos sourcePos) {
        Vec3d vec3d = start.subtract(sourcePos.getX(), sourcePos.getY(), sourcePos.getZ());
        Vec3d vec3d1 = end.subtract(sourcePos.getX(), sourcePos.getY(), sourcePos.getZ());
        RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
        return raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec.add(sourcePos.getX(), sourcePos.getY(), sourcePos.getZ()), raytraceresult.sideHit, targetPos);
    }

}
