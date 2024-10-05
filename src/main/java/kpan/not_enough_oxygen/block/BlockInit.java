package kpan.not_enough_oxygen.block;

import java.util.ArrayList;
import net.minecraft.block.material.Material;

@SuppressWarnings("unused")
public class BlockInit {

    public static final ArrayList<BlockBase> BLOCKS = new ArrayList<>();

    public static final BlockBase OXYLITE = new BlockBase("oxylite", Material.ROCK);
    public static final BlockCoalGenerator COAL_GENERATOR = new BlockCoalGenerator();

}
