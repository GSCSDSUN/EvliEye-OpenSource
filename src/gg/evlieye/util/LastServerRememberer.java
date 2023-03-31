/*

 *



 */
package gg.evlieye.util;

import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.mixinterface.IMultiplayerScreen;

/**
 * Remembers the last server you joined to make the "Reconnect",
 * "AutoReconnect" and "Last Server" buttons work.
 */
public enum LastServerRememberer
{
	;
	
	private static ServerInfo lastServer;
	
	public static ServerInfo getLastServer()
	{
		return lastServer;
	}
	
	public static void setLastServer(ServerInfo server)
	{
		lastServer = server;
	}
	
	public static void joinLastServer(MultiplayerScreen mpScreen)
	{
		if(lastServer == null)
			return;
		
		((IMultiplayerScreen)mpScreen).connectToServer(lastServer);
	}
	
	public static void reconnect(Screen prevScreen)
	{
		if(lastServer == null)
			return;
		
		ConnectScreen.connect(prevScreen, EvlieyeClient.MC,
			ServerAddress.parse(lastServer.address), lastServer);
	}
}
