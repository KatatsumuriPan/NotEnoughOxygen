package kpan.not_enough_oxygen.block.tileentity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ContainerTileInventory<T extends TileEntity & IInventory> extends Container {
    public final T tileInventory;
    protected final int[] currentFieldValues;

    public ContainerTileInventory(T tileInventory) {
        this.tileInventory = tileInventory;
        currentFieldValues = new int[tileInventory.getFieldCount()];
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileInventory);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener icontainerlistener : listeners) {
            for (int i = 0; i < currentFieldValues.length; i++) {
                icontainerlistener.sendWindowProperty(this, i, tileInventory.getField(i));
            }
        }
        for (int i = 0; i < currentFieldValues.length; i++) {
            currentFieldValues[i] = tileInventory.getField(i);
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        tileInventory.setField(id, data);
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileInventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            result = itemstack1.copy();

            if (index < tileInventory.getSizeInventory()) {
                // ブロック->プレイヤー
                if (!mergeItemStack(itemstack1, tileInventory.getSizeInventory(), inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // プレイヤー->ブロック
                if (!mergeItemStack(itemstack1, 0, tileInventory.getSizeInventory(), false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return result;
    }


    protected void addPlayerSlot(InventoryPlayer playerInventory) {
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
            addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

}
