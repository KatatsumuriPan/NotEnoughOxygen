package kpan.not_enough_oxygen.util.handlers;

import kpan.not_enough_oxygen.ModMain;
import kpan.not_enough_oxygen.block.BlockBase;
import kpan.not_enough_oxygen.block.BlockInit;
import kpan.not_enough_oxygen.item.ItemInit;
import kpan.not_enough_oxygen.network.MyPacketHandler;
import kpan.not_enough_oxygen.util.interfaces.IHasModel;
import kpan.not_enough_oxygen.world.ModBiomes;
import kpan.not_enough_oxygen.world.NEOWorldRegisterer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {

    public static void preInitRegistries(@SuppressWarnings("unused") FMLPreInitializationEvent event) {
        ModMain.proxy.registerOnlyClient();
        NEOWorldRegisterer.registerDimensions();
    }

    public static void initRegistries() {
        MyPacketHandler.registerMessages();
        ModBiomes.initBiomeManagerAndDictionary();
    }

    public static void postInitRegistries() {
    }

    public static void serverRegistries(@SuppressWarnings("unused") FMLServerStartingEvent event) {
    }

//	@SubscribeEvent
//	public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event) {
//	}

//	@SubscribeEvent
//	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
//	}

//	@SubscribeEvent
//	public static void onDataSerializerRegister(RegistryEvent.Register<DataSerializerEntry> event) {
//	}


    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        BlockBase.prepareRegistering();
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item : ItemInit.ITEMS) {
            if (item instanceof IHasModel)
                ((IHasModel) item).registerItemModels();
        }

        for (Block block : BlockInit.BLOCKS) {
            if (block instanceof IHasModel)
                ((IHasModel) block).registerItemModels();
        }
    }
}
