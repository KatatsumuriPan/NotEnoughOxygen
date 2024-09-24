package kpan.not_enough_oxygen.proxy;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

	@SuppressWarnings("RedundantMethodOverride")
	@Override
	public void registerOnlyClient() {
		//MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
	}

	@Override
	public boolean hasClientSide() { return true; }

}
