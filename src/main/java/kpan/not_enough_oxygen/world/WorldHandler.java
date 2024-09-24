package kpan.not_enough_oxygen.world;


import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class WorldHandler {


    @SubscribeEvent
    public static void onCreate(WorldEvent.CreateSpawnPosition event) {
        if (!(event.getWorld().provider instanceof NEOWorldProvider provider))
            return;
        event.getWorld().getWorldInfo().setSpawn(provider.getSpawnPoint1());
        event.setCanceled(true);
    }

}
