package kpan.not_enough_oxygen.config;

import java.util.Set;
import kpan.not_enough_oxygen.ModMain;
import kpan.not_enough_oxygen.ModTagsGenerated;
import kpan.not_enough_oxygen.config.core.gui.ModGuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class ModGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ModGuiConfig(parentScreen, ModMain.defaultConfig.getRootCategory().getOrderedElements(), null, false, false, ModTagsGenerated.MODNAME, "");
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

}
