/*

 *



 */
package gg.evlieye.commands;

import net.minecraft.client.network.ServerInfo;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.util.ChatUtils;
import gg.evlieye.util.LastServerRememberer;

public final class IpCmd extends Command
{
	public IpCmd()
	{
		super("ip",
			"Shows the IP of the server you are currently\n"
				+ "connected to or copies it to the clipboard.",
			".ip", "Copy to clipboard: .ip copy");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		String ip = getIP();
		
		switch(String.join(" ", args).toLowerCase())
		{
			case "":
			ChatUtils.message("IP: " + ip);
			break;
			
			case "copy":
			MC.keyboard.setClipboard(ip);
			ChatUtils.message("IP copied to clipboard.");
			break;
			
			default:
			throw new CmdSyntaxError();
		}
	}
	
	private String getIP()
	{
		ServerInfo lastServer = LastServerRememberer.getLastServer();
		if(lastServer == null || MC.isIntegratedServerRunning())
			return "127.0.0.1:25565";
		
		String ip = lastServer.address;
		if(!ip.contains(":"))
			ip += ":25565";
		
		return ip;
	}
	
	@Override
	public String getPrimaryAction()
	{
		return "Get IP";
	}
	
	@Override
	public void doPrimaryAction()
	{
		WURST.getCmdProcessor().process("ip");
	}
}
