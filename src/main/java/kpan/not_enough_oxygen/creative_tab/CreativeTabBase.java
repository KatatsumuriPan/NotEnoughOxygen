package kpan.not_enough_oxygen.creative_tab;

import java.util.function.Supplier;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabBase extends CreativeTabs {

    private final Supplier<ItemStack> tabIcon;
    public CreativeTabBase(String label, Supplier<ItemStack> tabIcon) {
        super(label);
        this.tabIcon = tabIcon;
    }
    @Override
    public ItemStack createIcon() {
        return tabIcon.get();
    }
}
