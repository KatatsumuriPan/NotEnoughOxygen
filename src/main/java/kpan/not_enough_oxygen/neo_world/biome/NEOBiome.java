package kpan.not_enough_oxygen.neo_world.biome;

import java.util.Random;
import kpan.not_enough_oxygen.neo_world.WeightedList;
import kpan.not_enough_oxygen.world.NEOChunkGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.ChunkPrimer;

public abstract class NEOBiome {

    protected static int range = 4;
    public final byte biomeId;
    public final boolean isSpecial;
    public final boolean farFromSpawn;

    public NEOBiome(boolean isSpecial, boolean farFromSpawn) {
        biomeId = (byte) NEOBiomes.BIOMES.size();
        this.isSpecial = isSpecial;
        this.farFromSpawn = farFromSpawn;
        NEOBiomes.BIOMES.add(this);
    }

    public void addTunnel(long seed, ChunkPrimer chunkPrimer, double digCenterX, double digCenterY, double digCenterZ, float size, float tunnelYaw, float tunnelPitch, int tunnelProgress, int tunnelLength, double whRatio) {
        float dYaw = 0.0F;
        float dPitch = 0.0F;
        Random random = new Random(seed);

        if (tunnelLength <= 0) {
            int i = (range - 1) * 16;
            tunnelLength = i - random.nextInt(i / 4);
        }

        boolean isRoom = false;

        if (tunnelProgress == -1) {
            tunnelProgress = tunnelLength / 2;
            isRoom = true;
        }

        int branchPoint = random.nextInt(tunnelLength / 2) + tunnelLength / 4;

        boolean isVertical = random.nextInt(6) == 0;
        for (; tunnelProgress < tunnelLength; ++tunnelProgress) {
            double r = 1.5D + (double) (MathHelper.sin((float) tunnelProgress * (float) Math.PI / (float) tunnelLength) * size);
            double h = r * whRatio;
            float pCos = MathHelper.cos(tunnelPitch);
            float pSin = MathHelper.sin(tunnelPitch);
            digCenterX += MathHelper.cos(tunnelYaw) * pCos;
            digCenterY += pSin;
            digCenterZ += MathHelper.sin(tunnelYaw) * pCos;

            if (isVertical) {
                tunnelPitch = tunnelPitch * 0.92F;
            } else {
                tunnelPitch = tunnelPitch * 0.7F;
            }

            tunnelPitch += dPitch * 0.1F;
            tunnelYaw += dYaw * 0.1F;
            dPitch *= 0.9F;
            dYaw *= 0.75F;
            dPitch += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            dYaw += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (!isRoom && tunnelProgress == branchPoint && size > 1.0F && tunnelLength > 0) {
                addTunnel(random.nextLong(), chunkPrimer, digCenterX, digCenterY, digCenterZ, random.nextFloat() * 0.5F + 0.5F, tunnelYaw - ((float) Math.PI / 2F), tunnelPitch / 3.0F, tunnelProgress, tunnelLength, 1.0D);
                addTunnel(random.nextLong(), chunkPrimer, digCenterX, digCenterY, digCenterZ, random.nextFloat() * 0.5F + 0.5F, tunnelYaw + ((float) Math.PI / 2F), tunnelPitch / 3.0F, tunnelProgress, tunnelLength, 1.0D);
                return;
            }

            if (isRoom || random.nextInt(4) != 0) {
                int sx = MathHelper.floor(digCenterX - r) - 1;
                int ex = MathHelper.floor(digCenterX + r) + 1;
                int sy = MathHelper.floor(digCenterY - h) - 1;
                int ey = MathHelper.floor(digCenterY + h) + 1;
                int sz = MathHelper.floor(digCenterZ - r) - 1;
                int ez = MathHelper.floor(digCenterZ + r) + 1;

                for (int x = sx; x < ex; ++x) {
                    double dx = (x + 0.5D - digCenterX) / r;
                    for (int z = sz; z < ez; ++z) {
                        double dz = (z + 0.5D - digCenterZ) / r;
                        if (dx * dx + dz * dz < 1.0D) {
                            for (int y = ey; y > sy; --y) {
                                if (!NEOChunkGenerator.isInGame(x, y, z))
                                    continue;
                                double dy = ((y - 1) + 0.5D - digCenterY) / h;
                                if (dy > -0.7D && dx * dx + dy * dy + dz * dz < 1.0D) {
                                    digBlock(chunkPrimer, x, y, z);
                                }
                            }
                        }
                    }
                }

                if (isRoom) {
                    break;
                }
            }
        }
    }

    protected void digBlock(ChunkPrimer data, int x, int y, int z) {
        data.setBlockState(x, y, z, Blocks.AIR.getDefaultState());
    }

    public abstract WeightedList<IBlockState> getBiomeBlockList();
}
