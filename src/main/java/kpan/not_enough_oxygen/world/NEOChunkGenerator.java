package kpan.not_enough_oxygen.world;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import kpan.not_enough_oxygen.block.BlockInit;
import kpan.not_enough_oxygen.client.particle.emitter_block.BlockParticleManager;
import kpan.not_enough_oxygen.client.particle.emitter_block.OxyliteParticleEmitter;
import kpan.not_enough_oxygen.neo_world.NEO3DBiomeProvider;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import org.jetbrains.annotations.Nullable;

public class NEOChunkGenerator implements IChunkGenerator {

    private final NEO3DBiomeProvider biomeProvider;

    public static boolean isInGameChunk(int chunkX, int chunkZ) {
        return 0 <= chunkX && chunkX < NEOWorldProvider.WORLD_SIZE && 0 <= chunkZ && chunkZ < NEOWorldProvider.WORLD_SIZE;
    }

    private final World world;

    private final ChunkPrimer chunkPrimer = new ChunkPrimer();

    public NEOChunkGenerator(World world) {
        this.world = world;
        biomeProvider = new NEO3DBiomeProvider(new Vec3i(NEOWorldProvider.WORLD_SIZE * 16, 256, NEOWorldProvider.WORLD_SIZE * 16), new Vec3i(24, 24, 24));
    }

    private void setBlocksInChunk(int chunkX, int chunkZ) {
        if (isInGameChunk(chunkX, chunkZ)) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < 256; y++) {
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
                    for (int y = 0; y < 256; y++) {
                        chunkPrimer.setBlockState(x, y, z, Blocks.BEDROCK.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    public Chunk generateChunk(int chunkX, int chunkZ) {
        setBlocksInChunk(chunkX, chunkZ);
        placeBiomeBlocks(chunkX, chunkZ);

        Chunk chunk = new Chunk(world, chunkPrimer, chunkX, chunkZ);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte) Biome.getIdForBiome(ModBiomes.DUMMY);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void placeBiomeBlocks(int chunkX, int chunkZ) {
        if (!isInGameChunk(chunkX, chunkZ))
            return;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    if (chunkPrimer.getBlockState(x, y, z) == Blocks.BEDROCK.getDefaultState())
                        continue;
                    byte biome = biomeProvider.getBiome(x + chunkX * 16, y, z + chunkZ * 16);
                    IBlockState state;
                    if (biome < 16)
                        state = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.byMetadata(biome % 16));
                    else
                        state = Blocks.WOOL.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.byMetadata(biome % 16));
                    chunkPrimer.setBlockState(x, y, z, state);
                }
            }
        }
    }

    @Override
    public void populate(int chunkX, int chunkZ) {
        if (!isInGameChunk(chunkX, chunkZ))
            return;

        generateSpawn(chunkX, chunkZ);
    }
    private void generateSpawn(int chunkX, int chunkZ) {
        BlockPos spawnPoint = ((NEOWorldProvider) world.provider).getSpawnPoint1();
        int spawnX = spawnPoint.getX();
        int spawnY = spawnPoint.getY();
        int spawnZ = spawnPoint.getZ();


        // 床
        for (int x = -6; x <= 6; x++) {
            for (int z = -6; z <= 6; z++) {
                if (Math.max(Math.abs(x), Math.abs(z)) <= 3)
                    placeBlock(chunkX, chunkZ, spawnX + x, spawnY - 1, spawnZ + z, Blocks.STONEBRICK.getDefaultState());
                else
                    placeBlock(chunkX, chunkZ, spawnX + x, spawnY - 1, spawnZ + z, Blocks.STONE.getDefaultState());
            }
        }
        // 高さ0~2
        for (int x = -6; x <= 6; x++) {
            for (int z = -6; z <= 6; z++) {
                for (int y = 0; y <= 2; y++) {
                    if (Math.max(Math.abs(x), Math.abs(z)) <= 3)
                        placeBlock(chunkX, chunkZ, spawnX + x, spawnY + y, spawnZ + z, Blocks.AIR.getDefaultState());
                    else
                        placeBlock(chunkX, chunkZ, spawnX + x, spawnY + y, spawnZ + z, Blocks.STONE.getDefaultState());
                }
            }
        }
        // 高さ3
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                if (Math.max(Math.abs(x), Math.abs(z)) <= 2)
                    placeBlock(chunkX, chunkZ, spawnX + x, spawnY + 3, spawnZ + z, Blocks.AIR.getDefaultState());
                else
                    placeBlock(chunkX, chunkZ, spawnX + x, spawnY + 3, spawnZ + z, Blocks.STONE.getDefaultState());
            }
        }
        // 高さ4~5
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                placeBlock(chunkX, chunkZ, spawnX + x, spawnY + 4, spawnZ + z, Blocks.STONE.getDefaultState());
                if (Math.max(Math.abs(x), Math.abs(z)) <= 2)
                    placeBlock(chunkX, chunkZ, spawnX + x, spawnY + 5, spawnZ + z, Blocks.STONE.getDefaultState());
            }
        }

        Random random = new Random(world.getSeed());
        // 石の置換
        placeBlockNearSpawn(chunkX, chunkZ, Blocks.DIRT.getDefaultState(), spawnX, spawnY, spawnZ, 1.2, 3, random);
        placeBlockNearSpawn(chunkX, chunkZ, Blocks.DIRT.getDefaultState(), spawnX, spawnY, spawnZ, 1.5, 5, random);
        placeBlockNearSpawn(chunkX, chunkZ, Blocks.DIRT.getDefaultState(), spawnX, spawnY, spawnZ, 2, 3, random);
        placeBlockNearSpawn(chunkX, chunkZ, Blocks.WOOL.getStateFromMeta(13), spawnX, spawnY, spawnZ, 1.2, 3, random);
        placeBlockNearSpawn(chunkX, chunkZ, Blocks.WOOL.getStateFromMeta(13), spawnX, spawnY, spawnZ, 1.2, 3, random);
        placeBlockNearSpawn(chunkX, chunkZ, Blocks.IRON_ORE.getDefaultState(), spawnX, spawnY, spawnZ, 1, 2.5, random);

        // オキシライト
        placeBlock(chunkX, chunkZ, spawnX - 1, spawnY + 3, spawnZ - 3, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 0, spawnY + 3, spawnZ - 3, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 2, spawnY + 4, spawnZ - 3, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 1, spawnY + 4, spawnZ - 3, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 0, spawnY + 4, spawnZ - 3, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 2, spawnY + 4, spawnZ - 4, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 1, spawnY + 4, spawnZ - 4, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 0, spawnY + 4, spawnZ - 4, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 1, spawnY + 5, spawnZ - 3, BlockInit.OXYLITE.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 0, spawnY + 5, spawnZ - 3, BlockInit.OXYLITE.getDefaultState());

        // ゲート
        placeBlock(chunkX, chunkZ, spawnX - 2, spawnY + 0, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX + 2, spawnY + 0, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 2, spawnY + 1, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX + 2, spawnY + 1, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 2, spawnY + 2, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX + 2, spawnY + 2, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX - 1, spawnY + 3, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX + 1, spawnY + 3, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
        placeBlock(chunkX, chunkZ, spawnX, spawnY + 3, spawnZ, Blocks.GLOWSTONE.getDefaultState());

        // チェスト
        placeBlock(chunkX, chunkZ, spawnX - 2, spawnY, spawnZ + 1, Blocks.CHEST.getDefaultState());
        if (placeBlock(chunkX, chunkZ, spawnX - 3, spawnY, spawnZ + 1, Blocks.CHEST.getDefaultState())) {
            if (world.getTileEntity(new BlockPos(spawnX - 3, spawnY, spawnZ + 1)) instanceof TileEntityChest tileentity) {
                tileentity.setInventorySlotContents(0, new ItemStack(Items.BREAD, 10));
            }
        }
    }


    private boolean placeBlock(int chunkX, int chunkZ, int blockPosX, int blockPosY, int blockPosZ, IBlockState state) {
        if (blockPosX >> 4 == chunkX && blockPosZ >> 4 == chunkZ) {
            world.setBlockState(new BlockPos(blockPosX, blockPosY, blockPosZ), state, 2);
            if (state.getBlock() == BlockInit.OXYLITE)
                BlockParticleManager.particles.add(new OxyliteParticleEmitter(new BlockPos(blockPosX, blockPosY, blockPosZ)));
            return true;
        }
        return false;
    }

    private void placeBlockNearSpawn(int chunkX, int chunkZ, IBlockState state, int spawnX, int spawnY, int spawnZ, double rMin, double rMax, Random random) {
        double x = spawnX + random.nextDouble() * 10 - 5;
        double y = spawnY + random.nextDouble() * 10 - 5;
        double z = spawnZ + random.nextDouble() * 10 - 5;
        double r = random.nextDouble() * (rMax - rMin) + rMin;
        for (int ix = (int) -r; ix <= (int) r; ix++) {
            for (int iz = (int) -r; iz <= (int) r; iz++) {
                for (int iy = (int) -r; iy <= (int) r; iy++) {
                    if (new Vec3d(x + ix, y + iy, z + iz).squareDistanceTo(new Vec3d(x, y, z)) - (Math.abs(ix) + Math.abs(iy) + Math.abs(iz)) * 0.3 > r * r)
                        continue;
                    if (world.getBlockState(new BlockPos((int) (x + ix), (int) (y + iy), (int) (z + iz))) == Blocks.STONE.getDefaultState()) {
                        placeBlock(chunkX, chunkZ, (int) (x + ix), (int) (y + iy), (int) (z + iz), state);
                    }
                }
            }
        }
    }

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
