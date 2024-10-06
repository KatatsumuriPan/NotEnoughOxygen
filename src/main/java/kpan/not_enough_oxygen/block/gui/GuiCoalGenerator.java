package kpan.not_enough_oxygen.block.gui;

import kpan.not_enough_oxygen.ModTagsGenerated;
import kpan.not_enough_oxygen.block.tileentity.TileEntityCoalGenerator;
import kpan.not_enough_oxygen.block.tileentity.container.ContainerCoalGenerator;
import kpan.not_enough_oxygen.block.tileentity.container.ContainerTileInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiCoalGenerator extends GuiContainer {

    private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(ModTagsGenerated.MODID, "textures/gui/container/coal_generator.png");

    public GuiCoalGenerator(ContainerTileInventory<?> inventorySlotsIn) {
        super((ContainerCoalGenerator) inventorySlotsIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(GUI_TEXTURES);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        if (getTileEntity().isBurning()) {
            int h = getBurnLeftScaled(13);
            drawTexturedModalRect(x + 56, y + 25 + 12 - h, 176, 12 - h, 14, h + 1);
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
