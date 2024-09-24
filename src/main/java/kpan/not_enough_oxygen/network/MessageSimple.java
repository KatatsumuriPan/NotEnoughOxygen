package kpan.not_enough_oxygen.network;

import io.netty.buffer.ByteBuf;

public abstract class MessageSimple extends MessageBase {
	//デフォルトコンストラクタは必須
	public MessageSimple() { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

}
