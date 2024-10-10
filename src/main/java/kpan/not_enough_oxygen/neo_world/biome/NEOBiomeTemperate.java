package kpan.not_enough_oxygen.neo_world.biome;

import kpan.not_enough_oxygen.block.BlockElementSolid;
import kpan.not_enough_oxygen.neo_world.Elements;
import kpan.not_enough_oxygen.neo_world.WeightedList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class NEOBiomeTemperate extends NEOBiome {
    public NEOBiomeTemperate() {
        super(true, false);
    }

    @Override
    public WeightedList<IBlockState> getBiomeBlockList() {
        WeightedList<IBlockState> res = new WeightedList<>();
        res.add(0.3F, Blocks.JUNGLE_FENCE.getDefaultState());
        res.add(0.1F, BlockElementSolid.get(Elements.ALGAE).getDefaultState());
        res.add(0.2F, BlockElementSolid.get(Elements.COAL).getDefaultState());
        res.add(0.3F, BlockElementSolid.get(Elements.COPPER_ORE).getDefaultState());
        res.add(0.8F, BlockElementSolid.get(Elements.SANDSTONE).getDefaultState());
        res.add(0.3F, BlockElementSolid.get(Elements.ALGAE).getDefaultState());
        res.add(0.3F, BlockElementSolid.get(Elements.DIRT).getDefaultState());
        res.add(0.65F, BlockElementSolid.get(Elements.SANDSTONE).getDefaultState());
        res.add(0.2F, BlockElementSolid.get(Elements.DIRT).getDefaultState());
        res.add(0.1F, BlockElementSolid.get(Elements.COAL).getDefaultState());
        res.add(0.1F, BlockElementSolid.get(Elements.SAND).getDefaultState());
        res.add(0.1F, BlockElementSolid.get(Elements.DIRT).getDefaultState());
        res.add(0.2F, BlockElementSolid.get(Elements.COPPER_ORE).getDefaultState());
        res.add(0.1F, BlockElementSolid.get(Elements.COAL).getDefaultState());
        res.add(0.1F, BlockElementSolid.get(Elements.ALGAE).getDefaultState());
        res.add(0.05F, BlockElementSolid.get(Elements.OXYLITE).getDefaultState());
        res.add(0.2F, Blocks.WEB.getDefaultState());
        return res;
    }

}
