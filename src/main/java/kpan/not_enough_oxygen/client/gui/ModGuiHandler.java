package kpan.not_enough_oxygen.client.gui;

import kpan.not_enough_oxygen.block.gui.GuiCoalGenerator;
import kpan.not_enough_oxygen.block.tileentity.container.ContainerCoalGenerator;
import kpan.not_enough_oxygen.block.tileentity.container.ContainerTileInventory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IntHashMap;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

public class ModGuiHandler implements IGuiHandler {

    private static int nextGuiId = 1;
    private static final IntHashMap<ContainerFactory> CONTAINER_FACTORIES = new IntHashMap<>();
    private static final IntHashMap<GuiFactory> GUI_FACTORIES = new IntHashMap<>();

    public static int newId() { return nextGuiId++; }

    @SideOnly(Side.CLIENT)
    public static void registerGuis() {
        registerGui(ContainerCoalGenerator.ID, GuiCoalGenerator::new);
    }

    public ModGuiHandler() {
        registerContainer(ContainerCoalGenerator.ID, ContainerCoalGenerator::new);
    }

    // Containerを取得
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ContainerFactory factory = CONTAINER_FACTORIES.lookup(ID);
        return factory != null ? factory.createContainer(player, world, x, y, z) : null;
    }

    // Guiを取得
    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ContainerFactory containerFactory = CONTAINER_FACTORIES.lookup(ID);
        GuiFactory guiFactory = GUI_FACTORIES.lookup(ID);
        return containerFactory != null && guiFactory != null ? guiFactory.createGui(containerFactory.createContainer(player, world, x, y, z)) : null;
    }

    private static void registerContainer(int id, ContainerFactory factory) {
        CONTAINER_FACTORIES.addKey(id, factory);
    }

    private static void registerGui(int id, GuiFactory factory) {
        GUI_FACTORIES.addKey(id, factory);
    }

    public interface ContainerFactory {
        ContainerTileInventory<?> createContainer(EntityPlayer player, World world, int x, int y, int z);
    }

    public interface GuiFactory {
        GuiScreen createGui(ContainerTileInventory<?> container);
    }

}
