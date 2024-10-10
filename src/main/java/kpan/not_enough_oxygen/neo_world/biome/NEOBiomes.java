package kpan.not_enough_oxygen.neo_world.biome;

import java.util.ArrayList;
import java.util.List;
import kpan.not_enough_oxygen.neo_world.WeightedList;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

public class NEOBiomes {

    public static final List<NEOBiome> BIOMES = new ArrayList<>();
    public static final List<NEOBiome> BIOMES_NORMAL = new ArrayList<>();
    public static final List<NEOBiome> BIOMES_NEAR = new ArrayList<>();
    public static final List<NEOBiome> BIOMES_FAR = new ArrayList<>();

    public static NEOBiome TEMPERATE = new NEOBiomeTemperate();

    public static NEOBiome MAGMA = new NEOBiome(true, true) {
        @Override
        public WeightedList<IBlockState> getBiomeBlockList() {
            WeightedList<IBlockState> res = new WeightedList<>();
            res.add(1, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.RED));
            return res;
        }
    };
    public static NEOBiome OIL = new NEOBiome(true, true) {
        @Override
        public WeightedList<IBlockState> getBiomeBlockList() {
            WeightedList<IBlockState> res = new WeightedList<>();
            res.add(1, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.ORANGE));
            return res;
        }
    };
    public static NEOBiome SPACE = new NEOBiome(true, true) {
        @Override
        public WeightedList<IBlockState> getBiomeBlockList() {
            WeightedList<IBlockState> res = new WeightedList<>();
            res.add(1, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.GRAY));
            return res;
        }
    };
    public static NEOBiome MARSH = new NEOBiome(false, false) {
        @Override
        public WeightedList<IBlockState> getBiomeBlockList() {
            WeightedList<IBlockState> res = new WeightedList<>();
            res.add(1, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.GREEN));
            return res;
        }
    };
    public static NEOBiome JUNGLE = new NEOBiome(false, false) {
        @Override
        public WeightedList<IBlockState> getBiomeBlockList() {
            WeightedList<IBlockState> res = new WeightedList<>();
            res.add(0.3F, Blocks.JUNGLE_FENCE.getDefaultState());
            res.add(0.5F, Blocks.COAL_BLOCK.getDefaultState());
            res.add(0.6F, Blocks.GLOWSTONE.getDefaultState());
            res.add(0.6F, Blocks.WOOL.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.LIME));
            res.add(0.6F, Blocks.COAL_BLOCK.getDefaultState());
            res.add(0.6F, Blocks.GLOWSTONE.getDefaultState());
            res.add(0.6F, Blocks.WOOL.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.LIME));
            res.add(0.5F, Blocks.COAL_BLOCK.getDefaultState());
            res.add(0.2F, Blocks.WEB.getDefaultState());
            return res;
        }
    };
    public static NEOBiome FROZEN = new NEOBiome(false, true) {
        @Override
        public WeightedList<IBlockState> getBiomeBlockList() {
            WeightedList<IBlockState> res = new WeightedList<>();
            res.add(1, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.LIGHT_BLUE));
            return res;
        }
    };
    public static NEOBiome TIDE_POOL = new NEOBiome(false, true) {
        @Override
        public WeightedList<IBlockState> getBiomeBlockList() {
            WeightedList<IBlockState> res = new WeightedList<>();
            res.add(1, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.PINK));
            return res;
        }
    };
    public static NEOBiome WHITE = new NEOBiome(false, true) {
        @Override
        public WeightedList<IBlockState> getBiomeBlockList() {
            WeightedList<IBlockState> res = new WeightedList<>();
            res.add(1, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.WHITE));
            return res;
        }
    };


    static {
        for (NEOBiome biome : BIOMES) {
            if (!biome.isSpecial) {
                BIOMES_NORMAL.add(biome);
                if (biome.farFromSpawn)
                    BIOMES_FAR.add(biome);
                else
                    BIOMES_NEAR.add(biome);
            }
        }
    }
}
