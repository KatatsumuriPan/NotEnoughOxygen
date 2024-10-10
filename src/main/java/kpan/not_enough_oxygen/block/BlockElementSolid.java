package kpan.not_enough_oxygen.block;

import java.util.HashMap;
import java.util.Map;
import kpan.not_enough_oxygen.creative_tab.ModTabs;
import kpan.not_enough_oxygen.neo_world.ElementData;
import kpan.not_enough_oxygen.neo_world.ElementData.ElementState;
import kpan.not_enough_oxygen.neo_world.ElementSolid;
import kpan.not_enough_oxygen.neo_world.Elements;
import net.minecraft.block.material.Material;

public class BlockElementSolid extends BlockElement<ElementSolid> {

    private static final Map<ElementSolid, BlockElementSolid> ELEMENT2SOLID = new HashMap<>();

    public static void registerBlocks() {
        for (ElementData<?> element : Elements.ELEMENTS.values()) {
            if (element.state != ElementState.SOLID)
                continue;
            BlockElementSolid block = new BlockElementSolid((ElementSolid) element);
            ELEMENT2SOLID.put((ElementSolid) element, block);
        }
    }

    public static BlockElementSolid get(ElementSolid element) {
        BlockElementSolid block = ELEMENT2SOLID.get(element);
        if (block == null)
            throw new IllegalArgumentException("Unknown element:" + element);
        return block;
    }

    private BlockElementSolid(ElementSolid element) {
        super(element.name, Material.ROCK, element);
        setCreativeTab(ModTabs.SOLID_BLOCKS);
    }
}
