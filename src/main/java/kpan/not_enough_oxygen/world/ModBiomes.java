package kpan.not_enough_oxygen.world;

import kpan.not_enough_oxygen.ModTagsGenerated;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

// TODO: Auto-generated Javadoc
@ObjectHolder(ModTagsGenerated.MODID)
public class ModBiomes {
    // instantiate Biomes
    public final static NEODummyBiome DUMMY = null;

    @Mod.EventBusSubscriber(modid = ModTagsGenerated.MODID)
    public static class RegistrationHandler {
        /**
         * Register this mod's {@link Biome}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onEvent(final RegistryEvent.Register<Biome> event) {
            final IForgeRegistry<Biome> registry = event.getRegistry();

            System.out.println("Registering biomes");

            registry.register(new NEODummyBiome().setRegistryName(ModTagsGenerated.MODID, NEOWorldRegisterer.DIMENSION_NAME));
        }
    }

    /**
     * This method should be called during the "init" FML lifecycle
     * because it must happen after object handler injection.
     */
    public static void initBiomeManagerAndDictionary() {
        BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(DUMMY, 10));
    }
}