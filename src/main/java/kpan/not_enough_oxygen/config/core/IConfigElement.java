package kpan.not_enough_oxygen.config.core;

import java.io.BufferedWriter;
import java.io.IOException;
import kpan.not_enough_oxygen.config.core.gui.ModGuiConfig;
import kpan.not_enough_oxygen.config.core.gui.ModGuiConfigEntries;
import kpan.not_enough_oxygen.config.core.gui.ModGuiConfigEntries.IGuiConfigEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IConfigElement {
    int getOrder();
    ConfigSide getSide();

    String getNameTranslationKey(String path);

    String getCommentTranslationKey(String path);

    boolean requiresWorldRestart();
    boolean requiresMcRestart();

    void write(BufferedWriter out, int indent, String path) throws IOException;

    @SideOnly(Side.CLIENT)
    boolean showInGui();

    @SideOnly(Side.CLIENT)
    IGuiConfigEntry toEntry(ModGuiConfig screen, ModGuiConfigEntries entryList);
}
