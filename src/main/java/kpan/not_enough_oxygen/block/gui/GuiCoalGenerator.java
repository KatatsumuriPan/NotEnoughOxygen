package kpan.not_enough_oxygen.block.gui;

import kpan.not_enough_oxygen.block.tileentity.TileEntityCoalGenerator;
import kpan.not_enough_oxygen.block.tileentity.container.ContainerCoalGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiCoalGenerator extends GuiContainer {

    private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");

    public GuiCoalGenerator(EntityPlayer player, World world, int x, int y, int z) {
        this(player.inventory, (TileEntityCoalGenerator) world.getTileEntity(new BlockPos(x, y, z)));
    }

    public GuiCoalGenerator(InventoryPlayer playerInventory, TileEntityCoalGenerator tileInventory) {
        super(new ContainerCoalGenerator(playerInventory, tileInventory));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        if (getTileEntity().isBurning()) {
            int h = getBurnLeftScaled(13);
            drawTexturedModalRect(x + 56, y + 36 + 12 - h, 176, 12 - h, 14, h + 1);
        }
    }

    private int getBurnLeftScaled(int pixels) {
        int total = getTileEntity().getCurrentItemBurnTime();

        if (total == 0) {
            total = 200;
        }

        return getTileEntity().getBurnTime() * pixels / total;
    }

    private TileEntityCoalGenerator getTileEntity() {
        return ((ContainerCoalGenerator) inventorySlots).tileInventory;
    }
}
