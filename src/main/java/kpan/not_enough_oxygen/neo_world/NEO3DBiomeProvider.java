package kpan.not_enough_oxygen.neo_world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import kpan.not_enough_oxygen.neo_world.biome.NEOBiomes;
import kpan.not_enough_oxygen.world.NEOWorldProvider;
import kpan.not_enough_oxygen.world.biome.MVec2f;
import kpan.not_enough_oxygen.world.biome.MVec3f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public final class NEO3DBiomeProvider {

    public final Vec3i areaSize;
    private final Vec3i biomeSize;
    private final byte[] biomeIndices;

    public NEO3DBiomeProvider(Vec3i areaSize, Vec3i biomeSize) {
        this.areaSize = areaSize;
        this.biomeSize = biomeSize;
        biomeIndices = new byte[areaSize.getX() * NEOWorldProvider.WORLD_HEIGHT * areaSize.getZ()];
    }

    public void generate(Random rand) {
        // バイオーム中心点生成
        int biomeNum = getVolume(areaSize) / getVolume(biomeSize);
        List<MVec3f> biomeCenterList = new ArrayList<>(biomeNum);
        List<MVec3f> biomeCenterVecList = new ArrayList<>(biomeNum);
        for (int i = 0; i < biomeNum; i++) {
            biomeCenterList.add(new MVec3f(rand.nextInt(areaSize.getX()), rand.nextInt(NEOWorldProvider.WORLD_HEIGHT), rand.nextInt(areaSize.getZ())));
            biomeCenterVecList.add(new MVec3f(0, 0, 0));
        }

        int magmaHeight = 13;
        int spaceHeight = 30;

        // 演算
        int iterates = 80;
        for (int i = 0; i < iterates; i++) {
            float border = 2;
            for (int j = 0; j < biomeCenterList.size(); j++) {
                MVec3f p1 = biomeCenterList.get(j);
                MVec3f v1 = biomeCenterVecList.get(j);
                // 壁
                if (p1.x < 0 + biomeSize.getX() / 2 - border)
                    v1.x += border * 2;
                if (p1.x > areaSize.getX() - biomeSize.getX() / 2 + border)
                    v1.x -= border * 2;
                if (p1.y < 0 + biomeSize.getY() / 2 - border)
                    v1.y += border * 2;
                if (p1.y > NEOWorldProvider.WORLD_HEIGHT - biomeSize.getY() / 2 + border)
                    v1.y -= border * 2;
                if (p1.z < 0 + biomeSize.getZ() / 2 - border)
                    v1.z += border * 2;
                if (p1.z > areaSize.getZ() - biomeSize.getZ() / 2 + border)
                    v1.z -= border * 2;
                // マグマ
                if (p1.y < magmaHeight + 10 + biomeSize.getY() / 2)
                    v1.y += (magmaHeight + 10 + biomeSize.getY() / 2 - p1.y) * 0.5;
                if (p1.y > 255 - spaceHeight - 10 - biomeSize.getY() / 2)
                    v1.y -= (p1.y - (NEOWorldProvider.WORLD_HEIGHT - 1 - spaceHeight - 10 - biomeSize.getY() / 2)) * 0.5;

                for (int k = j + 1; k < biomeCenterList.size(); k++) {
                    MVec3f p2 = biomeCenterList.get(k);
                    float radiusSq = radiusSqAtAngle(new MVec3f(biomeSize.getX() / 2.0F, biomeSize.getY() / 2.0F, biomeSize.getZ() / 2.0F), getAngle(p1, p2));
                    float distSq = p1.distSq(p2);
                    if (distSq < radiusSq * 4) {
                        MVec3f v2 = biomeCenterList.get(k);
                        float dx = p2.x - p1.x;
                        if (dx != 0) {
                            float a = Math.signum(dx) * (biomeSize.getX() * 1.05F - Math.abs(dx)) / 10;
                            v1.x -= a;
                            v2.x += a;
                        }
                        float dy = p2.y - p1.y;
                        if (dy != 0) {
                            float a = Math.signum(dy) * (biomeSize.getY() * 1.05F - Math.abs(dy)) / 10;
                            v1.y -= a;
                            v2.y += a;
                        }
                        float dz = p2.z - p1.z;
                        if (dz != 0) {
                            float a = Math.signum(dz) * (biomeSize.getZ() * 1.05F - Math.abs(dz)) / 10;
                            v1.z -= a;
                            v2.z += a;
                        }
                    }
                }
            }

            for (int j = 0; j < biomeCenterList.size(); j++) {
                MVec3f p1 = biomeCenterList.get(j);
                MVec3f v1 = biomeCenterVecList.get(j);
                p1.add(v1);
                p1.x = MathHelper.clamp(p1.x, 0, areaSize.getX());
                p1.y = MathHelper.clamp(p1.y, magmaHeight + 1, NEOWorldProvider.WORLD_HEIGHT - spaceHeight - 1);
                p1.z = MathHelper.clamp(p1.z, 0, areaSize.getZ());
                v1.scale(0.5F);
            }
        }

        // 各バイオーム中心点にバイオーム付与
        byte[] biomeIdxList = new byte[biomeCenterList.size() + 2];
        for (int i = 0; i < biomeIdxList.length - 2; i++) {
            if (rand.nextInt(NEOBiomes.BIOMES_NEAR.size() + NEOBiomes.BIOMES_FAR.size() * 2) < NEOBiomes.BIOMES_NEAR.size())
                biomeIdxList[i] = NEOBiomes.BIOMES_NEAR.get(rand.nextInt(NEOBiomes.BIOMES_NEAR.size())).biomeId;
            else
                biomeIdxList[i] = NEOBiomes.BIOMES_FAR.get(rand.nextInt(NEOBiomes.BIOMES_FAR.size())).biomeId;
        }
        biomeIdxList[biomeIdxList.length - 2] = NEOBiomes.MAGMA.biomeId;
        biomeIdxList[biomeIdxList.length - 1] = NEOBiomes.SPACE.biomeId;

        // 全てのマスに対して、一番近い点を求める
        int[] nearestCenterIndicies = new int[biomeIndices.length];
        for (int y = 0; y < NEOWorldProvider.WORLD_HEIGHT; y++) {
            for (int z = 0; z < areaSize.getZ(); z++) {
                for (int x = 0; x < areaSize.getX(); x++) {
                    MVec3f point = new MVec3f(x, y, z);
                    float minDisSq = Float.POSITIVE_INFINITY;
                    int minIdx = 0;
                    for (int i = 0; i < biomeCenterList.size(); i++) {
                        float dis = biomeCenterList.get(i).distSq(point);
                        if (dis < minDisSq) {
                            minDisSq = dis;
                            minIdx = i;
                        }
                    }
                    // マグマ
                    float rx = 1 + x % 7 * 0.06F - 0.18F;
                    float rz = 1 + x % 5 * 0.08F - 0.2F;
                    {
                        float dis = y * y + (x % 16) * (16 - x % 16) * rx + (z % 16) * (16 - z % 16) * rz;
                        if (dis < minDisSq) {
                            minDisSq = dis;
                            minIdx = biomeIdxList.length - 2;
                        }
                    }
                    // 宇宙
                    {
                        float dis = (NEOWorldProvider.WORLD_HEIGHT - 1 - y) * (NEOWorldProvider.WORLD_HEIGHT - 1 - y) + (x % 16) * (16 - x % 16) * rx + (z % 16) * (16 - z % 16) * rz;
                        if (dis < minDisSq) {
                            minDisSq = dis;
                            minIdx = biomeIdxList.length - 1;
                        }
                    }
                    nearestCenterIndicies[xyz2Index(x, y, z)] = minIdx;
                }
            }
        }


        // バイオームの設定を追加で行う
        {
            BlockPos spawnPoint = NEOWorldProvider.getSpawnPoint1();
            // スポーン近くに適さないバイオームを置換
            for (int i = 0; i < biomeCenterList.size(); i++) {
                MVec3f center = biomeCenterList.get(i);
                if (center.distSq(new MVec3f(spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ())) < 50 * 50) {
                    if (NEOBiomes.BIOMES.get(biomeIdxList[i]).farFromSpawn) {
                        biomeIdxList[i] = NEOBiomes.BIOMES_NEAR.get(rand.nextInt(NEOBiomes.BIOMES_NEAR.size())).biomeId;
                    }
                }
            }

            // スポーン付近を温帯に
            int size = 12;
            int ysize = 9;
            for (int dy = -ysize; dy <= ysize; dy++) {
                for (int dz = -size; dz <= size; dz++) {
                    for (int dx = -size; dx <= size; dx++) {
                        biomeIdxList[nearestCenterIndicies[xyz2Index(spawnPoint.getX() + dx, spawnPoint.getY() + dy, spawnPoint.getZ() + dz)]] = NEOBiomes.TEMPERATE.biomeId;
                    }
                }
            }

            // 石油
            for (int z = 0; z < areaSize.getZ(); z++) {
                for (int x = 0; x < areaSize.getX(); x++) {
                    for (int y = 0; y < NEOWorldProvider.WORLD_HEIGHT; y++) {
                        if (nearestCenterIndicies[xyz2Index(x, y, z)] == biomeIdxList.length - 2)// マグマ
                            continue;
                        biomeIdxList[nearestCenterIndicies[xyz2Index(x, y, z)]] = NEOBiomes.OIL.biomeId;// 石油
                        break;
                    }
                }
            }
        }


        // 全てのマスにバイオーム振り分け
        for (int y = 0; y < NEOWorldProvider.WORLD_HEIGHT; y++) {
            for (int z = 0; z < areaSize.getZ(); z++) {
                for (int x = 0; x < areaSize.getX(); x++) {
                    biomeIndices[xyz2Index(x, y, z)] = biomeIdxList[nearestCenterIndicies[xyz2Index(x, y, z)]];
                }
            }
        }

    }

    public byte getBiome(int x, int y, int z) {
        return (byte) (biomeIndices[xyz2Index(x, y, z)] & 0x7f);
    }

    public boolean isEdge(int x, int y, int z) {
        return (biomeIndices[xyz2Index(x, y, z)] & 0x80) != 0;
    }

    private int xyz2Index(int x, int y, int z) {
        return (y * areaSize.getZ() + z) * areaSize.getX() + x;
    }

    private static float radiusSqAtAngle(MVec3f egg, MVec2f radian) {
        float x = (float) (egg.x * Math.cos(radian.y) * Math.cos(radian.x));
        float y = (float) (egg.y * Math.cos(radian.y) * Math.sin(radian.x));
        float z = (float) (egg.z * Math.sin(radian.y));
        return x * x + y * y + z * z;
    }

    private static MVec2f getAngle(MVec3f p1, MVec3f p2) {
        MVec2f p1xz = new MVec2f(p1.x, p1.z);
        MVec2f p2xz = new MVec2f(p2.x, p2.z);
        float yaw = getAngle(p1xz, p2xz);
        float dxz = (float) Math.sqrt(p1xz.distanceSq(p2xz));
        float pitch = (float) Math.atan2(p2.y - p1.y, dxz);
        return new MVec2f(pitch, yaw);
    }

    private static float getAngle(MVec2f p1, MVec2f p2) {
        float dx = p2.x - p1.x;
        float dy = p2.y - p1.y;
        return (float) Math.atan2(dy, dx);
    }

    private static int getVolume(Vec3i v) {
        return v.getX() * v.getY() * v.getZ();
    }
}