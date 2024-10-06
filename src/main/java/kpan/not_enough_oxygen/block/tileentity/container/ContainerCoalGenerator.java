package kpan.not_enough_oxygen.block.tileentity.container;

import kpan.not_enough_oxygen.block.tileentity.TileEntityCoalGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ContainerCoalGenerator extends ContainerTileInventory<TileEntityCoalGenerator> {

    public ContainerCoalGenerator(EntityPlayer player, World world, int x, int y, int z) {
        this(player.inventory, (TileEntityCoalGenerator) world.getTileEntity(new BlockPos(x, y, z)));
    }

    public ContainerCoalGenerator(InventoryPlayer playerInventory, TileEntityCoalGenerator tileInventory) {
        super(tileInventory);
        addSlotToContainer(new SlotFuel(tileInventory, 0, 56, 53));
        addPlayerSlot(playerInventory);
    }

    private static class SlotFuel extends Slot {
        public SlotFuel(TileEntityCoalGenerator inventoryIn, int slotIndex, int xPosition, int yPosition) {
            super(inventoryIn, slotIndex, xPosition, yPosition);
        }
        @Override
        public boolean isItemValid(ItemStack stack) {
            return TileEntityCoalGenerator.isValidFuel(stack);
        }
    }
}
