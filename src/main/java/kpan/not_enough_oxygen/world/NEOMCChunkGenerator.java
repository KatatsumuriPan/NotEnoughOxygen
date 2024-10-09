package kpan.not_enough_oxygen.world;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import kpan.not_enough_oxygen.neo_world.NEO3DBiomeProvider;
import kpan.not_enough_oxygen.neo_world.NEOBiomes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import org.jetbrains.annotations.Nullable;

public class NEOMCChunkGenerator implements IChunkGenerator {

    public static boolean isInGameChunk(int chunkX, int chunkZ) {
        return 0 <= chunkX && chunkX < NEOWorldProvider.WORLD_SIZE && 0 <= chunkZ && chunkZ < NEOWorldProvider.WORLD_SIZE;
    }

    public static boolean isInGame(int posX, int posY, int posZ) {
        if (posY < 0 || posY >= NEOWorldProvider.WORLD_HEIGHT)
            return false;
        return isInGameChunk(posX >> 4, posZ >> 4);
    }


    private final World world;

    private final ChunkPrimer chunkPrimer = new ChunkPrimer();
    private final NEO3DBiomeProvider biomeProvider = new NEO3DBiomeProvider(new Vec3i(NEOWorldProvider.WORLD_SIZE * 16, NEOWorldProvider.WORLD_HEIGHT, NEOWorldProvider.WORLD_SIZE * 16), new Vec3i(20, 20, 20));

    public NEOMCChunkGenerator(World world) {
        this.world = world;
    }

    public void generateBiome() {
        biomeProvider.generate(new Random(world.getSeed()));
    }

    private void setBlocksInChunk(int chunkX, int chunkZ) {
        if (isInGameChunk(chunkX, chunkZ)) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < NEOWorldProvider.WORLD_HEIGHT; y++) {
                        if (y == 0)
                            chunkPrimer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
                        else
                            chunkPrimer.setBlockState(x, y, z, Blocks.STONE.getDefaultState());
                    }
                }
            }
        } else {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < NEOWorldProvider.WORLD_HEIGHT; y++) {
                        chunkPrimer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    public Chunk generateChunk(int chunkX, int chunkZ) {
        setBlocksInChunk(chunkX, chunkZ);
        if (isInGameChunk(chunkX, chunkZ)) {
            placeBiomeBlocks(chunkX, chunkZ);
        }

        Chunk chunk = new Chunk(world, chunkPrimer, chunkX, chunkZ);
        byte[] abyte = chunk.getBiomeArray();

        Arrays.fill(abyte, 0, abyte.length, (byte) Biome.getIdForBiome(ModBiomes.DUMMY));

        chunk.generateSkylightMap();
        return chunk;
    }

    private void placeBiomeBlocks(int chunkX, int chunkZ) {
        var perlinNoise = new NoiseGeneratorOctaves(new Random(world.getSeed()), 8);
        double[] noise = perlinNoise.generateNoiseOctaves(null, chunkX * 16, 0, chunkZ * 16, 16, NEOWorldProvider.WORLD_HEIGHT, 16, 10, 10, 10);
        OptionalDouble min = Arrays.stream(noise).min();
        OptionalDouble max = Arrays.stream(noise).max();
        OptionalDouble average = Arrays.stream(noise).average();
        int a = 9;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < NEOWorldProvider.WORLD_HEIGHT; y++) {
                    byte biome = biomeProvider.getBiome(x + chunkX * 16, y, z + chunkZ * 16);
                    IBlockState state = NEOBiomes.BIOMES.get(biome).getBiomeBlockList().get((float) noise[x << 12 | z << 8 | y] / 440F + 0.5F);
                    placeBlock(x, y, z, state);
                }
            }
        }
    }

    @Override
    public void populate(int chunkX, int chunkZ) { }


    private boolean placeBlock(int x, int y, int z, IBlockState state) {
        if (y < 0)
            return false;
        if (!NEOMCChunkGenerator.isInGame(x, y, z))
            return false;
        chunkPrimer.setBlockState(x, y, z, state);
        return true;
    }


    // biome

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }
    @Override
    public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return Collections.emptyList();
    }
    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }
    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }

}
