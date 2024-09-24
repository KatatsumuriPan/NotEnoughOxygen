package kpan.not_enough_oxygen.config.core;

import kpan.not_enough_oxygen.ModMain;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommentLocalizer {

    public static String tryLocalize(String localizationKey, String defaultValue) {
        if (!ModMain.proxy.hasClientSide())
            return defaultValue;

        String localized = format(localizationKey);
        if (localizationKey.equals(localized))
            return defaultValue;
        else
            return localized;
    }

    @SideOnly(Side.CLIENT)
    private static String format(String localizationKey) {
        return I18n.format(localizationKey);
    }
}
