package kpan.not_enough_oxygen.network.client;

import io.netty.buffer.ByteBuf;
import kpan.not_enough_oxygen.network.MessageBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageToServer extends MessageBase {
	//デフォルトコンストラクタは必須
	public MessageToServer() { }

	private String name;

	public MessageToServer(String name) {
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
		EntityPlayerMP sender = ctx.getServerHandler().player;
	}

}
