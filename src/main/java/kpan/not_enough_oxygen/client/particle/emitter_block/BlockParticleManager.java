package kpan.not_enough_oxygen.client.particle.emitter_block;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class BlockParticleManager {
    public static final List<BlockParticleEmitter> particles = new LinkedList<>();

    public static void tick() {
        if (Minecraft.getMinecraft().world == null)
            return;
        for (Iterator<BlockParticleEmitter> iterator = BlockParticleManager.particles.iterator(); iterator.hasNext(); ) {
            BlockParticleEmitter emitter = iterator.next();
            if (!emitter.tick())
                iterator.remove();
        }
    }
}