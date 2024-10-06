package kpan.not_enough_oxygen.block;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import kpan.not_enough_oxygen.block.BlockMultiBlockFiller.IHasMultiBlockFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BlockMultiBlockFiller<T extends BlockBase & IHasMultiBlockFiller<T> & IWireConnectable> extends BlockBase implements IWireConnectable {

    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public static final PropertyBool SOURCE_LOST = PropertyBool.create("source_lost");

    public final T sourceBlock;

    public BlockMultiBlockFiller(T sourceBlock) {
        super(sourceBlock.getRegistryName().getPath() + "_filler", sourceBlock.getRegisteredMaterial());
        this.sourceBlock = sourceBlock;
        fullBlock = getDefaultState().isOpaqueCube();
        setTickRandomly(true);
        setCreativeTab(null);
    }

    // property
    @Override
    protected ArrayList<IProperty<?>> getProperties() {
        ArrayList<IProperty<?>> properties = super.getProperties();
        properties.add(FACING);
        properties.add(SOURCE_LOST);
        return properties;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = super.getStateFromMeta(meta);
        state = state.withProperty(FACING, EnumFacing.byIndex(meta & 7));
        state = state.withProperty(SOURCE_LOST, (meta & 8) != 0);
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return getFacing(state).getIndex() | (state.getValue(SOURCE_LOST) ? 8 : 0);
    }

    // IWireConnectable
    @Override
    public boolean canConnect(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
        BlockPos sourcePos = getSourcePos(state, world, pos);
        if (sourcePos == null)
            return false;
        return sourceBlock.canConnectToFiller(world, sourcePos, pos, facing);
    }

    // util
    @Nullable
    public BlockPos getSourcePos(IBlockState state, IBlockAccess iBlockAccess, BlockPos pos) {
        if (state.getValue(SOURCE_LOST))
            return null;
        IBlockState s = state;
        BlockPos p = pos;
        do {
            p = p.offset(getFacing(s));
            s = iBlockAccess.getBlockState(p);
            if (s.getBlock() == sourceBlock)
                return p;
            if (s.getBlock() != this)
                return null;
        }
        while (true);
    }

    public <P extends BlockPos> void fillAll(World world, BlockPos sourcePos, Iterable<P> positions) {
        for (BlockPos pos : positions) {
            if (pos.equals(sourcePos))
                continue;
            world.setBlockToAir(pos);
        }
        for (BlockPos pos : positions) {
            if (pos.equals(sourcePos))
                continue;
            EnumFacing facing;
            if (pos.getY() != sourcePos.getY()) {
                if (pos.getY() < sourcePos.getY())
                    facing = EnumFacing.UP;
                else
                    facing = EnumFacing.DOWN;
            } else if (pos.getX() != sourcePos.getX()) {
                if (pos.getX() < sourcePos.getX())
                    facing = EnumFacing.EAST;
                else
                    facing = EnumFacing.WEST;
            } else {
                if (pos.getZ() < sourcePos.getZ())
                    facing = EnumFacing.SOUTH;
                else
                    facing = EnumFacing.NORTH;
            }
            world.setBlockState(pos, getDefaultState().withProperty(FACING, facing).withProperty(SOURCE_LOST, false));
        }
    }
    public <P extends BlockPos> void removeAll(World world, BlockPos sourcePos, Iterable<P> positions) {
        markAllAsSourceLost(world, sourcePos, positions);
        for (BlockPos pos : positions) {
            if (pos.equals(sourcePos))
                continue;
            world.setBlockToAir(pos);
        }
    }
    public <P extends BlockPos> void markAllAsSourceLost(World world, BlockPos sourcePos, Iterable<P> positions) {
        for (BlockPos pos : positions) {
            if (pos.equals(sourcePos))
                continue;
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock() != this)
                continue;
            world.setBlockState(pos, state.withProperty(SOURCE_LOST, true), 2);
        }
    }

    // その他すべて

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        BlockPos sourcePos = getSourcePos(state, worldIn, pos);
        if (sourcePos == null)
            worldIn.setBlockToAir(pos);
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return sourceBlock.isTopSolid(state);
    }
    @Override
    public boolean isFullBlock(IBlockState state) {
        return sourceBlock.isFullBlock(state);
    }
    @Override
    public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
        return sourceBlock.canEntitySpawn(state, entityIn);
    }
    @Override
    public int getLightOpacity(IBlockState state) {
        return sourceBlock.getLightOpacity(state);
    }
    @Override
    public boolean isTranslucent(IBlockState state) {
        return sourceBlock.isTranslucent(state);
    }
    @Override
    public int getLightValue(IBlockState state) {
        return sourceBlock.getLightValue(state);
    }
    @Override
    public boolean getUseNeighborBrightness(IBlockState state) {
        return sourceBlock.getUseNeighborBrightness(state);
    }
    @Override
    public Material getMaterial(IBlockState state) {
        return sourceBlock.getMaterial(state);
    }
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return sourceBlock.getMapColor(state, worldIn, pos, this);
    }
    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return sourceBlock.isBlockNormalCube(state);
    }
    @Override
    public boolean isNormalCube(IBlockState state) {
        return sourceBlock.isNormalCube(state);
    }
    @Override
    public boolean causesSuffocation(IBlockState state) {
        return sourceBlock.causesSuffocation(state);
    }
    @Override
    public boolean isFullCube(IBlockState state) {
        return sourceBlock.isFullCube(state);
    }
    @Override
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return sourceBlock.hasCustomBreakingProgress(state);
    }
    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return sourceBlock.isPassable(worldIn, pos, this);
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).getValue(SOURCE_LOST))
            return true;
        return sourceBlock.isReplaceable(worldIn, pos, this);
    }
    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return sourceBlock.getBlockHardness(blockState, worldIn, pos, this);
    }
    @Override
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        return sourceBlock.getPackedLightmapCoords(state, source, pos, this);
    }
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return sourceBlock.shouldSideBeRendered(blockState, blockAccess, pos, side, this);
    }
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return sourceBlock.getBlockFaceShape(worldIn, state, pos, face, this);
    }
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        sourceBlock.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState, this);
    }
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return sourceBlock.getCollisionBoundingBox(blockState, worldIn, pos, this);
    }
    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return sourceBlock.getSelectedBoundingBox(state, worldIn, pos, this);
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return sourceBlock.collisionRayTrace(blockState, worldIn, pos, start, end, this);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        if (sourceBlock == null)
            return true;
        return sourceBlock.isOpaqueCube(state);
    }
    @Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return sourceBlock.canCollideCheck(state, hitIfLiquid);
    }
    @Override
    public boolean isCollidable() {
        return sourceBlock.isCollidable();
    }
    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
        sourceBlock.onPlayerDestroy(worldIn, pos, state, this);
    }
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        // SOURCE_LOSTがtrueなら確定、25%の確率で探索
        if (state.getValue(SOURCE_LOST) || worldIn.rand.nextInt(100) < 25 && getSourcePos(state, worldIn, pos) == null) {
            worldIn.setBlockToAir(pos);
            return;
        }
        sourceBlock.neighborChanged(state, worldIn, pos, blockIn, fromPos, this);
    }
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    }
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getValue(SOURCE_LOST))
            return;
        sourceBlock.breakBlock(worldIn, pos, state, this);
    }
    @Override
    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
        return sourceBlock.getPlayerRelativeBlockHardness(state, player, worldIn, pos, this);
    }
    @Override
    public float getExplosionResistance(Entity exploder) {
        return sourceBlock.getExplosionResistance(exploder);
    }
    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        sourceBlock.onExplosionDestroy(worldIn, pos, explosionIn, this);
    }
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }
    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return sourceBlock.canPlaceBlockOnSide(worldIn, pos, side, this);
    }
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return sourceBlock.canPlaceBlockAt(worldIn, pos, this);
    }
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return sourceBlock.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ, this);
    }
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        sourceBlock.onEntityWalk(worldIn, pos, entityIn, this);
    }
    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        sourceBlock.onBlockClicked(worldIn, pos, playerIn, this);
    }
    @Override
    public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
        return sourceBlock.modifyAcceleration(worldIn, pos, entityIn, motion, this);
    }
    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return sourceBlock.getWeakPower(blockState, blockAccess, pos, side, this);
    }
    @Override
    public boolean canProvidePower(IBlockState state) {
        return sourceBlock.canProvidePower(state);
    }
    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        sourceBlock.onEntityCollision(worldIn, pos, state, entityIn, this);
    }
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return sourceBlock.getStrongPower(blockState, blockAccess, pos, side, this);
    }
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockToAir(pos);
    }
    @Override
    public boolean canSpawnInBlock() {
        return sourceBlock.canSpawnInBlock();
    }
    @Override
    public String getLocalizedName() {
        return sourceBlock.getLocalizedName();
    }
    @Override
    public String getTranslationKey() {
        return sourceBlock.getTranslationKey();
    }
    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        return sourceBlock.eventReceived(state, worldIn, pos, id, param, this);
    }
    @Override
    public boolean getEnableStats() {
        return false;
    }
    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.IGNORE;
    }
    @Override
    public float getAmbientOcclusionLightValue(IBlockState state) {
        return sourceBlock.getAmbientOcclusionLightValue(state);
    }
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        sourceBlock.onFallenUpon(worldIn, pos, entityIn, fallDistance, this);
    }
    @Override
    public void onLanded(World worldIn, Entity entityIn) {
        sourceBlock.onLanded(worldIn, entityIn);
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        sourceBlock.onBlockHarvested(worldIn, pos, state, player, this);
    }
    @Override
    public void fillWithRain(World worldIn, BlockPos pos) {
        sourceBlock.fillWithRain(worldIn, pos, this);
    }
    @Override
    public boolean requiresUpdates() {
        return false;
    }
    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }
    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return sourceBlock.hasComparatorInputOverride(state);
    }
    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return sourceBlock.getComparatorInputOverride(blockState, worldIn, pos, this);
    }
    @Override
    public SoundType getSoundType() {
        return sourceBlock.getSoundType();
    }
    @Override
    public String toString() {
        return sourceBlock.toString();
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        sourceBlock.addInformation(stack, worldIn, tooltip, flagIn);
    }
    @Override
    public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
        return sourceBlock.getSlipperiness(state, world, pos, entity, this);
    }
    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.getLightValue(state, world, pos, this);
    }
    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return sourceBlock.isLadder(state, world, pos, entity, this);
    }
    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.isNormalCube(state, world, pos, this);
    }
    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return sourceBlock.doesSideBlockRendering(state, world, pos, face, this);
    }
    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return sourceBlock.isSideSolid(base_state, world, pos, side, this);
    }
    @Override
    public boolean isBurning(IBlockAccess world, BlockPos pos) {
        return sourceBlock.isBurning(world, pos, this);
    }
    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return sourceBlock.canHarvestBlock(world, pos, player, this);
    }
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return sourceBlock.removedByPlayer(state, world, pos, player, willHarvest, this);
    }
    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return sourceBlock.getFlammability(world, pos, face, this);
    }
    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return sourceBlock.isFlammable(world, pos, face, this);
    }
    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return sourceBlock.getFireSpreadSpeed(world, pos, face, this);
    }
    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return sourceBlock.isFireSource(world, pos, side, this);
    }
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        sourceBlock.getDrops(drops, world, pos, state, fortune, this);
    }
    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }
    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return sourceBlock.canCreatureSpawn(state, world, pos, type, this);
    }
    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player) {
        return sourceBlock.isBed(state, world, pos, player, this);
    }
    @Nullable
    @Override
    public BlockPos getBedSpawnPosition(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityPlayer player) {
        return sourceBlock.getBedSpawnPosition(state, world, pos, player, this);
    }
    @Override
    public void setBedOccupied(IBlockAccess world, BlockPos pos, EntityPlayer player, boolean occupied) {
        sourceBlock.setBedOccupied(world, pos, player, occupied, this);
    }
    @Override
    public EnumFacing getBedDirection(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.getBedDirection(state, world, pos, this);
    }
    @Override
    public boolean isBedFoot(IBlockAccess world, BlockPos pos) {
        return sourceBlock.isBedFoot(world, pos, this);
    }
    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
        sourceBlock.beginLeavesDecay(state, world, pos, this);
    }
    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.canSustainLeaves(state, world, pos, this);
    }
    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.isLeaves(state, world, pos, this);
    }
    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.canBeReplacedByLeaves(state, world, pos, this);
    }
    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return sourceBlock.isWood(world, pos, this);
    }
    @Override
    public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
        return false;
    }
    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return sourceBlock.getExplosionResistance(world, pos, exploder, explosion, this);
    }
    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        sourceBlock.onBlockExploded(world, pos, explosion, this);
    }
    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return sourceBlock.canConnectRedstone(state, world, pos, side, this);
    }
    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.canPlaceTorchOnTop(state, world, pos, this);
    }
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return sourceBlock.getPickBlock(state, target, world, pos, player, this);
    }
    @Override
    public boolean isFoliage(IBlockAccess world, BlockPos pos) {
        return false;
    }
    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
        return sourceBlock.addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles, this);
    }
    @Override
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
        return sourceBlock.addRunningEffects(state, world, pos, entity, this);
    }
    @Override
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        return sourceBlock.addHitEffects(state, worldObj, target, manager, this);
    }
    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        return sourceBlock.addDestroyEffects(world, pos, manager, this);
    }
    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return sourceBlock.canSustainPlant(state, world, pos, direction, plantable, this);
    }
    @Override
    public void onPlantGrow(IBlockState state, World world, BlockPos pos, BlockPos source) {
        sourceBlock.onPlantGrow(state, world, pos, source, this);
    }
    @Override
    public boolean isFertile(World world, BlockPos pos) {
        return sourceBlock.isFertile(world, pos, this);
    }
    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.getLightOpacity(state, world, pos, this);
    }
    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return sourceBlock.canEntityDestroy(state, world, pos, entity, this);
    }
    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
        return sourceBlock.isBeaconBase(worldObj, pos, beacon, this);
    }
    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return sourceBlock.rotateBlock(world, pos, axis, this);
    }
    @Nullable
    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        return sourceBlock.getValidRotations(world, pos, this);
    }
    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return sourceBlock.getEnchantPowerBonus(world, pos, this);
    }
    @Override
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
        return sourceBlock.recolorBlock(world, pos, side, color, this);
    }
    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        sourceBlock.onNeighborChange(world, pos, neighbor, this);
    }
    @Override
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
        sourceBlock.observedNeighborChange(observerState, world, observerPos, changedBlock, changedBlockPos, this);
    }
    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return sourceBlock.shouldCheckWeakPower(state, world, pos, side, this);
    }
    @Override
    public boolean getWeakChanges(IBlockAccess world, BlockPos pos) {
        return sourceBlock.getWeakChanges(world, pos, this);
    }
    @Nullable
    @Override
    public String getHarvestTool(IBlockState state) {
        return sourceBlock.getHarvestTool(state, this);
    }
    @Override
    public int getHarvestLevel(IBlockState state) {
        return sourceBlock.getHarvestLevel(state, this);
    }
    @Override
    public boolean isToolEffective(String type, IBlockState state) {
        return sourceBlock.isToolEffective(type, state);
    }
    @Nullable
    @Override
    public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity, double yToTest, Material materialIn, boolean testingHead) {
        return sourceBlock.isEntityInsideMaterial(world, blockpos, iblockstate, entity, yToTest, materialIn, testingHead, this);
    }
    @Nullable
    @Override
    public Boolean isAABBInsideMaterial(World world, BlockPos pos, AxisAlignedBB boundingBox, Material materialIn) {
        return sourceBlock.isAABBInsideMaterial(world, pos, boundingBox, materialIn, this);
    }
    @Nullable
    @Override
    public Boolean isAABBInsideLiquid(World world, BlockPos pos, AxisAlignedBB boundingBox) {
        return sourceBlock.isAABBInsideLiquid(world, pos, boundingBox, this);
    }
    @Override
    public float getBlockLiquidHeight(World world, BlockPos pos, IBlockState state, Material material) {
        return sourceBlock.getBlockLiquidHeight(world, pos, state, material, this);
    }
    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return sourceBlock.canRenderInLayer(state, layer);
    }
    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return sourceBlock.getSoundType(state, world, pos, entity, this);
    }
    @Nullable
    @Override
    public float[] getBeaconColorMultiplier(IBlockState state, World world, BlockPos pos, BlockPos beaconPos) {
        return sourceBlock.getBeaconColorMultiplier(state, world, pos, beaconPos, this);
    }
    @Override
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
        return sourceBlock.getFogColor(world, pos, state, entity, originalColor, partialTicks, this);
    }
    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return sourceBlock.canBeConnectedTo(world, pos, facing, this);
    }
    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos) {
        return sourceBlock.getAiPathNodeType(state, world, pos, this);
    }
    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
        return sourceBlock.getAiPathNodeType(state, world, pos, entity, this);
    }
    @Override
    public boolean doesSideBlockChestOpening(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return sourceBlock.doesSideBlockChestOpening(blockState, world, pos, side, this);
    }
    @Override
    public boolean isStickyBlock(IBlockState state) {
        return sourceBlock.isStickyBlock(state);
    }

//

    public static EnumFacing getFacing(IBlockState state) {
        try {
            return state.getValue(FACING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public interface IHasMultiBlockFiller<T extends BlockBase & IHasMultiBlockFiller<T> & IWireConnectable> {

        boolean canConnectToFiller(IBlockAccess world, BlockPos pos, BlockPos fillerPos, EnumFacing facing);

        default MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getMapColor(state, worldIn, pos);
        }

        default boolean isPassable(IBlockAccess worldIn, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isPassable(worldIn, pos);
        }

        default boolean isReplaceable(IBlockAccess worldIn, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isReplaceable(worldIn, pos);
        }

        default float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getBlockHardness(blockState, worldIn, pos);
        }

        default AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getBoundingBox(state, source, pos);
        }

        default int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getPackedLightmapCoords(state, source, pos);
        }

        default boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).shouldSideBeRendered(blockState, blockAccess, pos, side);
        }

        default BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getBlockFaceShape(worldIn, state, pos, face);
        }

        default void addCollisionBoxToList(IBlockState fillerState, World worldIn, BlockPos fillerPos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState, BlockMultiBlockFiller<T> filler) {
            BlockPos sourcePos = filler.getSourcePos(fillerState, worldIn, fillerPos);
            if (sourcePos == null) {
                return;
            }
            IBlockState sourceState = worldIn.getBlockState(sourcePos);
            ((Block) this).addCollisionBoxToList(sourceState, worldIn, sourcePos, entityBox, collidingBoxes, entityIn, isActualState);
        }
        @Nullable

        default AxisAlignedBB getCollisionBoundingBox(IBlockState fillerState, IBlockAccess worldIn, BlockPos fillerPos, BlockMultiBlockFiller<T> filler) {
            BlockPos sourcePos = filler.getSourcePos(fillerState, worldIn, fillerPos);
            if (sourcePos == null) {
                return NULL_AABB;
            }
            IBlockState sourceState = worldIn.getBlockState(sourcePos);
            return ((Block) this).getCollisionBoundingBox(sourceState, worldIn, sourcePos);
        }

        default AxisAlignedBB getSelectedBoundingBox(IBlockState fillerState, World worldIn, BlockPos fillerPos, BlockMultiBlockFiller<T> filler) {
            BlockPos sourcePos = filler.getSourcePos(fillerState, worldIn, fillerPos);
            if (sourcePos == null) {
                return FULL_BLOCK_AABB.offset(fillerPos);
            }
            IBlockState sourceState = worldIn.getBlockState(sourcePos);
            return ((Block) this).getSelectedBoundingBox(sourceState, worldIn, sourcePos);
        }

        default void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random, BlockMultiBlockFiller<T> filler) {
        }

        default void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand, BlockMultiBlockFiller<T> filler) {
        }

        default void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand, BlockMultiBlockFiller<T> filler) {
        }

        default void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state, BlockMultiBlockFiller<T> filler) {
        }

        default void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, BlockMultiBlockFiller<T> filler) {
        }

        default void breakBlock(World worldIn, BlockPos fillerPos, IBlockState fillerState, BlockMultiBlockFiller<T> filler) {
            BlockPos sourcePos = filler.getSourcePos(fillerState, worldIn, fillerPos);
            if (sourcePos == null) {
                return;
            }
            worldIn.setBlockToAir(sourcePos);
        }

        default Item getItemDropped(IBlockState state, Random rand, int fortune, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getItemDropped(state, rand, fortune);
        }

        default float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getPlayerRelativeBlockHardness(state, player, worldIn, pos);
        }

        @Nullable
        default RayTraceResult collisionRayTrace(IBlockState fillerState, World worldIn, BlockPos fillerPos, Vec3d start, Vec3d end, BlockMultiBlockFiller<T> filler) {
            return filler.rayTrace(fillerPos, start, end, fillerState.getBoundingBox(worldIn, fillerPos));
        }

        default void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn, BlockMultiBlockFiller<T> filler) {
        }

        default boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canPlaceBlockOnSide(worldIn, pos, side);
        }

        default boolean canPlaceBlockAt(World worldIn, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canPlaceBlockAt(worldIn, pos);
        }

        default boolean onBlockActivated(World worldIn, BlockPos fillerPos, IBlockState fillerState, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, BlockMultiBlockFiller<T> filler) {
            BlockPos sourcePos = filler.getSourcePos(fillerState, worldIn, fillerPos);
            return ((Block) this).onBlockActivated(worldIn, sourcePos, worldIn.getBlockState(sourcePos), playerIn, hand, facing, hitX, hitY, hitZ);
        }

        default void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn, BlockMultiBlockFiller<T> filler) {
        }

        default void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn, BlockMultiBlockFiller<T> filler) {
        }

        default Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).modifyAcceleration(worldIn, pos, entityIn, motion);
        }

        default int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getWeakPower(blockState, blockAccess, pos, side);
        }

        default void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn, BlockMultiBlockFiller<T> filler) {
        }

        default int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getStrongPower(blockState, blockAccess, pos, side);
        }

        default void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack, BlockMultiBlockFiller<T> filler) {
        }

        default void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, BlockMultiBlockFiller<T> filler) {
        }

        default boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).eventReceived(state, worldIn, pos, id, param);
        }

        default void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance, BlockMultiBlockFiller<T> filler) {
        }

        default ItemStack getItem(World worldIn, BlockPos pos, IBlockState state, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getItem(worldIn, pos, state);
        }

        default void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, BlockMultiBlockFiller<T> filler) {
        }

        default void fillWithRain(World worldIn, BlockPos pos, BlockMultiBlockFiller<T> filler) {
        }

        default int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getComparatorInputOverride(blockState, worldIn, pos);
        }

        default void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn, BlockMultiBlockFiller<T> filler) {
        }

        default float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getSlipperiness(state, world, pos, entity);
        }

        default int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getLightValue(state, world, pos);
        }

        default boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isLadder(state, world, pos, entity);
        }

        default boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isNormalCube(state, world, pos);
        }

        default boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).doesSideBlockRendering(state, world, pos, face);
        }

        default boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isSideSolid(base_state, world, pos, side);
        }

        default boolean isBurning(IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isBurning(world, pos);
        }

        default boolean canHarvestBlock(IBlockAccess world, BlockPos fillerPos, EntityPlayer player, BlockMultiBlockFiller<T> filler) {
            BlockPos sourcePos = filler.getSourcePos(world.getBlockState(fillerPos), world, fillerPos);
            if (sourcePos == null) {
                return false;
            }
            return ((Block) this).canHarvestBlock(world, sourcePos, player);
        }

        default boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).removedByPlayer(state, world, pos, player, willHarvest);
        }

        default int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getFlammability(world, pos, face);
        }

        default boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isFlammable(world, pos, face);
        }

        default int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getFireSpreadSpeed(world, pos, face);
        }

        default boolean isFireSource(World world, BlockPos pos, EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isFireSource(world, pos, side);
        }

        default void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune, BlockMultiBlockFiller<T> filler) {
        }

        default boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canSilkHarvest(world, pos, state, player);
        }

        default boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canCreatureSpawn(state, world, pos, type);
        }

        default boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isBed(state, world, pos, player);
        }
        @Nullable

        default BlockPos getBedSpawnPosition(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityPlayer player, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getBedSpawnPosition(state, world, pos, player);
        }

        default void setBedOccupied(IBlockAccess world, BlockPos pos, EntityPlayer player, boolean occupied, BlockMultiBlockFiller<T> filler) {
        }

        default EnumFacing getBedDirection(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getBedDirection(state, world, pos);
        }

        default boolean isBedFoot(IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isBedFoot(world, pos);
        }

        default void beginLeavesDecay(IBlockState state, World world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
        }

        default boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canSustainLeaves(state, world, pos);
        }

        default boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isLeaves(state, world, pos);
        }

        default boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canBeReplacedByLeaves(state, world, pos);
        }

        default boolean isWood(IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isWood(world, pos);
        }

        default float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getExplosionResistance(world, pos, exploder, explosion);
        }

        default void onBlockExploded(World world, BlockPos pos, Explosion explosion, BlockMultiBlockFiller<T> filler) {
        }

        default boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canConnectRedstone(state, world, pos, side);
        }

        default boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canPlaceTorchOnTop(state, world, pos);
        }

        default ItemStack getPickBlock(IBlockState fillerState, RayTraceResult target, World world, BlockPos fillerPos, EntityPlayer player, BlockMultiBlockFiller<T> filler) {
            BlockPos sourcePos = filler.getSourcePos(fillerState, world, fillerPos);
            if (sourcePos == null) {
                return ItemStack.EMPTY;
            }
            IBlockState sourceState = world.getBlockState(sourcePos);
            return ((Block) this).getPickBlock(sourceState, target, world, sourcePos, player);
        }

        default boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).addLandingEffects(state, worldObj, blockPosition, iblockstate, entity, numberOfParticles);
        }

        default boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).addRunningEffects(state, world, pos, entity);
        }

        default boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).addHitEffects(state, worldObj, target, manager);
        }

        default boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).addDestroyEffects(world, pos, manager);
        }

        default boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canSustainPlant(state, world, pos, direction, plantable);
        }

        default void onPlantGrow(IBlockState state, World world, BlockPos pos, BlockPos source, BlockMultiBlockFiller<T> filler) {
        }

        default boolean isFertile(World world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isFertile(world, pos);
        }

        default int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getLightOpacity(state, world, pos);
        }

        default boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canEntityDestroy(state, world, pos, entity);
        }

        default boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isBeaconBase(worldObj, pos, beacon);
        }

        default boolean rotateBlock(World world, BlockPos pos, EnumFacing axis, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).rotateBlock(world, pos, axis);
        }
        @Nullable

        default EnumFacing[] getValidRotations(World world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getValidRotations(world, pos);
        }

        default float getEnchantPowerBonus(World world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getEnchantPowerBonus(world, pos);
        }

        default boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).recolorBlock(world, pos, side, color);
        }

        default void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor, BlockMultiBlockFiller<T> filler) {
        }

        default void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos, BlockMultiBlockFiller<T> filler) {
        }

        default boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).shouldCheckWeakPower(state, world, pos, side);
        }

        default boolean getWeakChanges(IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getWeakChanges(world, pos);
        }

        default String getHarvestTool(IBlockState state, BlockMultiBlockFiller<T> filler) {
            return null;
        }

        default int getHarvestLevel(IBlockState state, BlockMultiBlockFiller<T> filler) {
            return -1;
        }

        @Nullable
        default Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity, double yToTest, Material materialIn, boolean testingHead, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isEntityInsideMaterial(world, blockpos, iblockstate, entity, yToTest, materialIn, testingHead);
        }

        @Nullable
        default Boolean isAABBInsideMaterial(World world, BlockPos pos, AxisAlignedBB boundingBox, Material materialIn, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isAABBInsideMaterial(world, pos, boundingBox, materialIn);
        }

        @Nullable
        default Boolean isAABBInsideLiquid(World world, BlockPos pos, AxisAlignedBB boundingBox, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).isAABBInsideLiquid(world, pos, boundingBox);
        }

        default float getBlockLiquidHeight(World world, BlockPos pos, IBlockState state, Material material, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getBlockLiquidHeight(world, pos, state, material);
        }

        default SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getSoundType(state, world, pos, entity);
        }
        @Nullable

        default float[] getBeaconColorMultiplier(IBlockState state, World world, BlockPos pos, BlockPos beaconPos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getBeaconColorMultiplier(state, world, pos, beaconPos);
        }

        default Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getFogColor(world, pos, state, entity, originalColor, partialTicks);
        }

        default IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        }

        default boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).canBeConnectedTo(world, pos, facing);
        }
        @Nullable

        default PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getAiPathNodeType(state, world, pos);
        }
        @Nullable

        default PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).getAiPathNodeType(state, world, pos, entity);
        }

        default boolean doesSideBlockChestOpening(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side, BlockMultiBlockFiller<T> filler) {
            return ((Block) this).doesSideBlockChestOpening(blockState, world, pos, side);
        }

    }
}
