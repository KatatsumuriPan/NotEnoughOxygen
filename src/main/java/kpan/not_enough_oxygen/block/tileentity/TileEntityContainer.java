package kpan.not_enough_oxygen.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public abstract class TileEntityContainer extends TileEntityMultiBlockBase implements IInventory {

    protected NonNullList<ItemStack> itemStackList;// finalにできないのはreadFromNBTがあるから

    protected TileEntityContainer(int slotNum) {
        itemStackList = NonNullList.withSize(slotNum, ItemStack.EMPTY);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        itemStackList = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, itemStackList);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, itemStackList);
        return compound;
    }

    @Override
    public int getSizeInventory() {
        return itemStackList.size();
    }
    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : itemStackList) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public ItemStack getStackInSlot(int index) {
        return itemStackList.get(index);
    }
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(itemStackList, index, count);
    }
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(itemStackList, index);
    }
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = itemStackList.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        itemStackList.set(index, stack);

        if (stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
    }
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (world.getTileEntity(pos) != this)
            return false;
        return true;
    }
    @Override
    public void openInventory(EntityPlayer player) {
    }
    @Override
    public void closeInventory(EntityPlayer player) {
    }
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }
    @Override
    public void clear() {
        itemStackList.clear();
    }
    @Override
    public boolean hasCustomName() {
        return false;
    }
}
