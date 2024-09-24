package kpan.not_enough_oxygen.asm.hook;

import kpan.not_enough_oxygen.asm.acc.ACC_GuiCreateWorld;
import kpan.not_enough_oxygen.world.NEOWorldRegisterer;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.world.WorldType;

@SuppressWarnings("unused")
public class HK_GuiCreateWorld {

    public static void setWorldTypeIndex(GuiCreateWorld self) {
        WorldType[] worldTypes = WorldType.WORLD_TYPES;
        for (int i = 0; i < worldTypes.length; i++) {
            if (worldTypes[i] == NEOWorldRegisterer.NEO_WORLD_TYPE)
                ((ACC_GuiCreateWorld) self).set_selectedIndex(i);
        }
    }

}
