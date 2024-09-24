package kpan.not_enough_oxygen.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Consumer;

public class MessageUtil {

	//MessageBaseのdoAction内でMinecraft.getMinecraft().playerを呼ぶとクラッシュするので仕方なく
	@SideOnly(Side.CLIENT)
	public static void withPlayer(Consumer<EntityPlayer> func) {
		func.accept(Minecraft.getMinecraft().player);
	}

	public static void addScheduledTask(MessageBase message, MessageContext ctx) {
		if (ctx.side == Side.SERVER)
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> message.doAction(ctx));
		else
			Minecraft.getMinecraft().addScheduledTask(() -> message.doAction(ctx));
	}
}
