/*

 *



 */
package gg.evlieye.mixinterface;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;

public interface IMultiplayerScreen
{
	public MultiplayerServerListWidget getServerListSelector();
	
	public void connectToServer(ServerInfo server);
}
