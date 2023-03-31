/*

 *



 */
package gg.evlieye.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import gg.evlieye.mixinterface.IServerList;

@Mixin(ServerList.class)
public class ServerListMixin implements IServerList
{
	@Shadow
	private List<ServerInfo> servers;
	
	@Override
	public void clear()
	{
		servers.clear();
	}
}
