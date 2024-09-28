package kpan.not_enough_oxygen.client;

import kpan.not_enough_oxygen.client.particle.emitter_block.BlockParticleManager;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class ClientTickHandler {

    public static int tick = 0;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderTick(ClientTickEvent event) {
        if (event.phase == Phase.START) {
            tick++;
            BlockParticleManager.tick();
        }
    }
}
