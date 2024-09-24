package kpan.not_enough_oxygen.world.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.storage.ISaveHandler;

public class CustomWorldServer extends WorldServerMulti {
    public CustomWorldServer(MinecraftServer server, ISaveHandler saveHandlerIn, int dimensionId, WorldServer delegate, Profiler profilerIn) {
        super(server, saveHandlerIn, dimensionId, delegate, profilerIn);
    }
}
