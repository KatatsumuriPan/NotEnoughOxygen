package kpan.not_enough_oxygen.world;

import net.minecraft.init.Biomes;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.IChunkGenerator;

public class NEOWorldType extends WorldType {
    /**
     * Creates a new world type, the ID is hidden and should not be referenced by modders.
     * It will automatically expand the underlying workdType array if there are no IDs left.
     *
     * @param name
     */
    public NEOWorldType() {
        super(NEOWorldRegisterer.DIMENSION_NAME);
    }

    @Override
    public BiomeProvider getBiomeProvider(World world) {
        if (world.provider.getDimensionType() != NEOWorldRegisterer.DIMENSION_TYPE)
            return new BiomeProviderSingle(Biomes.VOID);
        return new BiomeProviderSingle(ModBiomes.cloud);
    }

    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
        if (world.provider.getDimensionType() != NEOWorldRegisterer.DIMENSION_TYPE)
            return new ChunkGeneratorFlat(world, world.getSeed(), false, "");
        return new NEOChunkGenerator(world);
    }

    @Override
    public float getCloudHeight() {
        return 10000f;
    }
}
