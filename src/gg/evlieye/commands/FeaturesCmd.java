/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.EvlieyeClient;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.hack.Hack;
import gg.evlieye.other_feature.OtherFeature;
import gg.evlieye.util.ChatUtils;

public final class FeaturesCmd extends Command
{
	public FeaturesCmd()
	{
		super("features",
			"Shows the number of features and some other\n" + "statistics.",
			".features");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 0)
			throw new CmdSyntaxError();
		
		if(EvlieyeClient.VERSION.startsWith("7.0pre"))
			ChatUtils.warning(
				"This is just a pre-release! It doesn't (yet) have all of the features of Evlieye 7.0! See download page for details.");
		
		int hax = evlieye.getHax().countHax();
		int cmds = evlieye.getCmds().countCmds();
		int otfs = evlieye.getOtfs().countOtfs();
		int all = hax + cmds + otfs;
		
		ChatUtils.message("All features: " + all);
		ChatUtils.message("Hacks: " + hax);
		ChatUtils.message("Commands: " + cmds);
		ChatUtils.message("Other features: " + otfs);
		
		int settings = 0;
		for(Hack hack : evlieye.getHax().getAllHax())
			settings += hack.getSettings().size();
		for(Command cmd : evlieye.getCmds().getAllCmds())
			settings += cmd.getSettings().size();
		for(OtherFeature otf : evlieye.getOtfs().getAllOtfs())
			settings += otf.getSettings().size();
		
		ChatUtils.message("Settings: " + settings);
	}
	
	@Override
	public String getPrimaryAction()
	{
		return "Show Statistics";
	}
	
	@Override
	public void doPrimaryAction()
	{
		evlieye.getCmdProcessor().process("features");
	}
}
