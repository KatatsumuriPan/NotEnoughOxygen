package kpan.not_enough_oxygen.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IntHashMap;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.jetbrains.annotations.Nullable;

public class ModGuiHandler implements IGuiHandler {

    private static final IntHashMap<ContainerFactory> CONTAINER_FACTORIES = new IntHashMap<>();
    private static final IntHashMap<GuiFactory> GUI_FACTORIES = new IntHashMap<>();


    public static void registerContainers() {

    }


    private static void registerContainer(int id, ContainerFactory factory) {
        CONTAINER_FACTORIES.addKey(id, factory);
    }

    private static void registerGui(int id, GuiFactory factory) {
        GUI_FACTORIES.addKey(id, factory);
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
        GuiFactory factory = GUI_FACTORIES.lookup(ID);
        return factory != null ? factory.createGui(player, world, x, y, z) : null;
    }

    public interface ContainerFactory {
        Container createContainer(EntityPlayer player, World world, int x, int y, int z);
    }

    public interface GuiFactory {
        GuiScreen createGui(EntityPlayer player, World world, int x, int y, int z);
    }

}
