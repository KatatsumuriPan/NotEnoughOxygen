package kpan.not_enough_oxygen.block.tileentity;

import kpan.not_enough_oxygen.ModTagsGenerated;
import kpan.not_enough_oxygen.block.BlockCoalGenerator;
import kpan.not_enough_oxygen.block.tileentity.container.ContainerCoalGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TileEntityCoalGenerator extends TileEntityContainer implements ITickable, IInteractionObject {

    // protected static final AnimationBuilder DEPLOY = new AnimationBuilder().addAnimation("animation.coalGenerator.stopped", true).addAnimation("misc.idle", true);

    private final AnimationFactory factory = new AnimationFactory(this);

    private int burnTime = 0;
    private int currentItemBurnTime = 0;

    public TileEntityCoalGenerator() {
        super(1);
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getCurrentItemBurnTime() {
        return currentItemBurnTime;
    }
    @Override
    public void update() {
        boolean isBurning = isBurning();
        boolean dirty = false;

        if (isBurning()) {
            --burnTime;
        }

        if (!world.isRemote) {
            ItemStack fuelStack = itemStackList.get(0);

            if (isBurning() || !fuelStack.isEmpty()) {
                if (!isBurning()) {
                    burnTime = getItemBurnTime(fuelStack);
                    currentItemBurnTime = burnTime;

                    if (isBurning()) {
                        dirty = true;
                        if (!fuelStack.isEmpty())
                            fuelStack.shrink(1);
                    }
                }
            }

            if (isBurning != isBurning()) {
                dirty = true;
            }
        }

        if (dirty) {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockCoalGenerator.RUNNING, isBurning()));
            markDirty();
        }
    }


    public boolean isBurning() {
        return burnTime > 0;
    }

    // IAnimatable
    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 0;
        boolean isRunning = false;
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockCoalGenerator)
            isRunning = state.getValue(BlockCoalGenerator.RUNNING);
        if (isRunning)
            event.getController().setAnimation(new AnimationBuilder().loop("animation.coalGenerator.running"));
        else
            event.getController().setAnimation(new AnimationBuilder().loop("animation.coalGenerator.stopped"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(
                new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public String getName() {
        return "tile.coal_generator.name";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerCoalGenerator(playerInventory, this);
    }

    @Override
    public String getGuiID() {
        return new ResourceLocation(ModTagsGenerated.MODID, "coal_generator").toString();
    }
    @Override
    public int getField(int id) {
        if (id == 0)
            return burnTime;
        else
            return currentItemBurnTime;
    }
    @Override
    public void setField(int id, int value) {
        if (id == 0)
            burnTime = value;
        else
            currentItemBurnTime = value;
    }
    @Override
    public int getFieldCount() {
        return 2;
    }


    public static int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty())
            return 0;
        if (stack.getItem() == Items.COAL)
            return 1600;
        else
            return 0;
    }
    public static boolean isValidFuel(ItemStack stack) {
        return getItemBurnTime(stack) > 0;
    }

}
