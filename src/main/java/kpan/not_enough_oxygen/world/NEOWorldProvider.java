package kpan.not_enough_oxygen.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;

public class NEOWorldProvider extends WorldProvider {

    // 24ブロック四方がだいたい1バイオーム
    public static int WORLD_SIZE = 6;// 6x6
    public static int SPAWN_HEIGHT = 150;
    public static int WORLD_HEIGHT = 256;

    public static BlockPos getSpawnPoint1() {
        return new BlockPos(WORLD_SIZE * 16 / 2, SPAWN_HEIGHT, WORLD_SIZE * 16 / 2);
    }


    @Override
    public DimensionType getDimensionType() {
        return NEOWorldRegisterer.DIMENSION_TYPE;
    }

    @Override
    public BlockPos getRandomizedSpawnPoint() {
        return world.getSpawnPoint();
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight) {
        return false;
    }

}
