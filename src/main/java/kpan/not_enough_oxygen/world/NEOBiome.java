package kpan.not_enough_oxygen.world;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;

public class NEOBiome extends Biome {

    public NEOBiome() {
        super(new BiomeProperties(NEOWorldRegisterer.DIMENSION_NAME)
                .setBaseHeight(1.0F)
                .setHeightVariation(0.2F)
                .setRainDisabled()
                .setTemperature(0.2F)
        );
    }

    @Override
    public BiomeDecorator createBiomeDecorator() {
        return super.createBiomeDecorator();
    }

    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
    }
}
