package kpan.not_enough_oxygen.world;

import java.util.Collections;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import org.jetbrains.annotations.Nullable;

public class NEOChunkGenerator implements IChunkGenerator {

    public static boolean isInGameChunk(int chunkX, int chunkZ) {
        return 0 <= chunkX && chunkX < NEOWorldProvider.WORLD_SIZE && 0 <= chunkZ && chunkZ < NEOWorldProvider.WORLD_SIZE;
    }

    private final World world;

    private final ChunkPrimer chunkPrimer = new ChunkPrimer();

    public NEOChunkGenerator(World world) { this.world = world; }

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

        Chunk chunk = new Chunk(world, chunkPrimer, chunkX, chunkZ);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte) Biome.getIdForBiome(ModBiomes.cloud);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int chunkX, int chunkZ) {
        if (!isInGameChunk(chunkX, chunkZ))
            return;

        // spawn
        {
            BlockPos spawnPoint = ((NEOWorldProvider) world.provider).getSpawnPoint1();
            int spawnX = spawnPoint.getX();
            int spawnY = spawnPoint.getY();
            int spawnZ = spawnPoint.getZ();
            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {
                    placeBlock(chunkX, chunkZ, spawnX + x, spawnY - 1, spawnZ + z, Blocks.STONEBRICK.getDefaultState());
                    for (int y = 0; y <= 4; y++) {
                        if (Math.max(Math.abs(x), Math.abs(z)) + y < 6)
                            placeBlock(chunkX, chunkZ, spawnX + x, spawnY + y, spawnZ + z, Blocks.AIR.getDefaultState());
                    }
                }
            }
            placeBlock(chunkX, chunkZ, spawnX - 2, spawnY + 0, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX + 2, spawnY + 0, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX - 2, spawnY + 1, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX + 2, spawnY + 1, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX - 2, spawnY + 2, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX + 2, spawnY + 2, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX - 1, spawnY + 3, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX + 1, spawnY + 3, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX, spawnY + 3, spawnZ, Blocks.GLOWSTONE.getDefaultState());
            placeBlock(chunkX, chunkZ, spawnX - 2, spawnY, spawnZ + 1, Blocks.CHEST.getDefaultState());
            if (placeBlock(chunkX, chunkZ, spawnX - 3, spawnY, spawnZ + 1, Blocks.CHEST.getDefaultState())) {
                if (world.getTileEntity(new BlockPos(spawnX - 3, spawnY, spawnZ + 1)) instanceof TileEntityChest tileentity) {
                    tileentity.setInventorySlotContents(0, new ItemStack(Items.BREAD, 10));
                }
            }

        }
    }


    private boolean placeBlock(int chunkX, int chunkZ, int blockPosX, int blockPosY, int blockPosZ, IBlockState state) {
        if (blockPosX >> 4 == chunkX && blockPosZ >> 4 == chunkZ) {
            world.setBlockState(new BlockPos(blockPosX, blockPosY, blockPosZ), state, 2);
            return true;
        }
        return false;
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
