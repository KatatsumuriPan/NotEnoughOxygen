package kpan.not_enough_oxygen.network.server;

import io.netty.buffer.ByteBuf;
import kpan.not_enough_oxygen.network.MessageBase;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageToClient extends MessageBase {
	//デフォルトコンストラクタは必須
	public MessageToClient() { }

	private String name;

	public MessageToClient(String name) {
		this.name = name;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		name = readString(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		writeString(buf, name);
	}

	@Override
	public void doAction(MessageContext ctx) {

	}

}
