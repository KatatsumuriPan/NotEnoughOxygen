package kpan.not_enough_oxygen.util.handlers;

import kpan.not_enough_oxygen.item.ItemSaveBook;
import kpan.not_enough_oxygen.world.NEOWorldRegisterer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@EventBusSubscriber
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        if (player.world.getWorldInfo().getTerrainType() != NEOWorldRegisterer.NEO_WORLD_TYPE)
            return;
        ItemSaveBook.goToNEOWorld(player);
    }
}
