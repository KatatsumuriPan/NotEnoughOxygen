package kpan.not_enough_oxygen.network;

import kpan.not_enough_oxygen.network.client.MessageToServer;
import kpan.not_enough_oxygen.network.server.MessageToClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class MyMessageHander implements IMessageHandler<MessageBase, MessageBase> {
	private final ArrayList<Pair<Class<? extends MessageBase>, Side>> messages = new ArrayList<>();

	public MyMessageHander() {
		sendToServer(MessageToServer.class);
		sendToServer(MessageToClient.class);
	}

	public int register(SimpleNetworkWrapper wrapper, int messageid) {
		for (Pair<Class<? extends MessageBase>, Side> pair : messages) {
			wrapper.registerMessage(this, pair.getLeft(), messageid++, pair.getRight());
		}
		return messageid;
	}

	private void addToList(Class<? extends MessageBase> message, Side send_to) {
		messages.add(Pair.of(message, send_to));
	}
	private void sendToServer(Class<? extends MessageBase> message) {
		addToList(message, Side.SERVER);
	}
	private void sendToClient(Class<? extends MessageBase> message) {
		addToList(message, Side.CLIENT);
	}
	@SuppressWarnings("unused")
	private void sendBoth(Class<? extends MessageBase> message) {
		sendToServer(message);
		sendToClient(message);
	}

	@Override
	public MessageBase onMessage(MessageBase message, MessageContext ctx) {
		//メインスレッドで実行してもらう
		MessageUtil.addScheduledTask(message, ctx);
		return null;
	}

}
