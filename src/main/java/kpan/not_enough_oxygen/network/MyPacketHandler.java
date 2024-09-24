package kpan.not_enough_oxygen.network;

import kpan.not_enough_oxygen.ModTagsGenerated;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.ArrayList;
import java.util.List;

public class MyPacketHandler {
	private static final List<IMessage> BUFFER_CLIENT = new ArrayList<>();
	private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModTagsGenerated.MODID);
	private static int messageId = 0;

	public static void registerMessages() {
		messageId = new MyMessageHander().register(INSTANCE, messageId);
	}

	public static void sendToServer(IMessage message) {
		INSTANCE.sendToServer(message);
	}
	public static void sendToServerBufferd(IMessage message) {
		BUFFER_CLIENT.add(message);
	}

	public static void sendToPlayer(IMessage message, EntityPlayerMP player) {
		INSTANCE.sendTo(message, player);
	}
	public static void sendToAllPlayers(IMessage message) {
		INSTANCE.sendToAll(message);
	}

	public static void tickClient() {
		for (IMessage message : BUFFER_CLIENT) {
			sendToServer(message);
		}
		BUFFER_CLIENT.clear();
	}
}
