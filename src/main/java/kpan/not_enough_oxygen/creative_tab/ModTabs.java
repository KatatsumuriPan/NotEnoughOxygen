package kpan.not_enough_oxygen.creative_tab;

import kpan.not_enough_oxygen.block.BlockElementSolid;
import kpan.not_enough_oxygen.block.BlockInit;
import kpan.not_enough_oxygen.neo_world.Elements;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ModTabs {
    public static final CreativeTabBase SOLID_BLOCKS = new CreativeTabBase("neo_solid_blocks", () -> new ItemStack(BlockElementSolid.get(Elements.OXYLITE)));
    public static final CreativeTabBase SOLID_BUILDINGS = new CreativeTabBase("neo_buildings", () -> new ItemStack(Blocks.CHEST));
    public static final CreativeTabBase SOLID_POWER = new CreativeTabBase("neo_power", () -> new ItemStack(BlockInit.COAL_GENERATOR));

    public static void init() {
        // 特にやることはないが、クラスロードはしてほしい
    }
}
