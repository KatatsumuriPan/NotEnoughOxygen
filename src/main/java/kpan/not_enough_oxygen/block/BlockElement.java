package kpan.not_enough_oxygen.block;

import kpan.not_enough_oxygen.neo_world.ElementData;
import kpan.not_enough_oxygen.neo_world.ElementSolid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.apache.commons.lang3.NotImplementedException;

public abstract class BlockElement<E extends ElementData<E>> extends BlockBase {

    public final E element;

    public BlockElement(String name, Material material, E element) {
        super(name, material);
        this.element = element;
    }

    public static IBlockState toBlockState(ElementData<?> element) {
        return switch (element.state) {
            case VACUUM -> Blocks.AIR.getDefaultState();
            case SOLID -> BlockElementSolid.get((ElementSolid) element).getDefaultState();
            case LIQUID -> throw new NotImplementedException();
            case GAS -> throw new NotImplementedException();
        };
    }
}
