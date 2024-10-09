package kpan.not_enough_oxygen.util.handlers;

import java.util.concurrent.Callable;
import kpan.not_enough_oxygen.capability.IMyCapability;
import kpan.not_enough_oxygen.capability.ProviderBase;
import kpan.not_enough_oxygen.capability.StorageBase;
import kpan.not_enough_oxygen.capability.capabilities.CapabilityCommandBlock;
import kpan.not_enough_oxygen.capability.capabilities.ICapabilityCommandBlock;
import kpan.not_enough_oxygen.capability.capabilities.ProviderCommandBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class CapabilityHandler {

    // call in preInit
    public static void register() {
        registerCapability(ICapabilityCommandBlock.class, CapabilityCommandBlock::new);
    }

    // AttachCapabilitiesEventの種類
    // TileEntity
    // Entity
    // Village
    // ItemStack
    // World
    // Chunk

    @SubscribeEvent
    public static void attachTileEntityCapability(AttachCapabilitiesEvent<TileEntity> event) {
        if (event.getObject() instanceof TileEntityCommandBlock) {
            addCapability(event, new ProviderCommandBlock());
        }
    }

    private static <T extends IMyCapability<T>> void registerCapability(Class<T> classType, Callable<? extends T> factory) {
        registerCapability(classType, new StorageBase<>(), factory);
    }

    private static <T extends IMyCapability<T>> void registerCapability(Class<T> classType, IStorage<T> storage, Callable<? extends T> factory) {
        CapabilityManager.INSTANCE.register(classType, storage, factory);
    }

    private static void addCapability(AttachCapabilitiesEvent<?> event, ProviderBase<?> provider) {
        event.addCapability(provider.getName(), provider);
    }
}
