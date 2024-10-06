package kpan.not_enough_oxygen.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlock {
    public ItemBlockBase(Block block) {
        super(block);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return getTranslationKey();
    }

}
