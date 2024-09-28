package kpan.not_enough_oxygen.proxy;

import kpan.not_enough_oxygen.ModTagsGenerated;
import kpan.not_enough_oxygen.util.interfaces.block.IHasTileEntityAndRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public void registerOnlyClient() {
        // MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
    }

    @Override
    public boolean hasClientSide() { return true; }


    @Override
    public void registerSingleModel(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void registerMultiItemModel(Item item, int meta, String filename, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(ModTagsGenerated.MODID, filename), id));
    }

    @Override
    public <T extends TileEntity> void registerTESR(IHasTileEntityAndRenderer<T> hasTESR) {
        ClientRegistry.bindTileEntitySpecialRenderer(hasTESR.getTileEntityClass(), hasTESR.getTESR());
    }
}
