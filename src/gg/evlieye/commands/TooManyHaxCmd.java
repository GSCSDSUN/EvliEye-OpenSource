/*

 *



 */
package gg.evlieye.commands;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import gg.evlieye.DontBlock;
import gg.evlieye.Feature;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.hack.Hack;
import gg.evlieye.hacks.TooManyHaxHack;
import gg.evlieye.other_feature.OtherFeature;
import gg.evlieye.util.ChatUtils;
import gg.evlieye.util.MathUtils;
import gg.evlieye.util.json.JsonException;

@DontBlock
public final class TooManyHaxCmd extends Command
{
	public TooManyHaxCmd()
	{
		super("toomanyhax",
			"Allows to manage which hacks should be blocked\n"
				+ "when TooManyHax is enabled.",
			".toomanyhax block <feature>", ".toomanyhax unblock <feature>",
			".toomanyhax block-all", ".toomanyhax unblock-all",
			".toomanyhax list [<page>]", ".toomanyhax load-profile <file>",
			".toomanyhax save-profile <file>",
			".toomanyhax list-profiles [<page>]",
			"Profiles are saved in '.minecraft/evlieye/toomanyhax'.");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length < 1)
			throw new CmdSyntaxError();
		
		switch(args[0].toLowerCase())
		{
			case "block":
			block(args);
			break;
			
			case "unblock":
			unblock(args);
			break;
			
			case "block-all":
			blockAll();
			break;
			
			case "unblock-all":
			unblockAll();
			break;
			
			case "list":
			list(args);
			break;
			
			case "load-profile":
			loadProfile(args);
			break;
			
			case "save-profile":
			saveProfile(args);
			break;
			
			case "list-profiles":
			listProfiles(args);
			break;
			
			default:
			throw new CmdSyntaxError();
		}
	}
	
	private void block(String[] args) throws CmdException
	{
		if(args.length != 2)
			throw new CmdSyntaxError();
		
		String name = args[1];
		Feature feature = parseFeature(name);
		String typeAndName = getType(feature) + " '" + name + "'";
		
		if(!feature.isSafeToBlock())
			throw new CmdError("The " + typeAndName + " is not safe to block.");
		
		TooManyHaxHack tooManyHax = evlieye.getHax().tooManyHaxHack;
		if(tooManyHax.isBlocked(feature))
		{
			ChatUtils.error("The " + typeAndName + " is already blocked.");
			
			if(!tooManyHax.isEnabled())
				ChatUtils.message("Enable TooManyHax to see the effect.");
			
			return;
		}
		
		tooManyHax.setBlocked(feature, true);
		ChatUtils.message("Added " + typeAndName + " to TooManyHax list.");
	}
	
	private void unblock(String[] args) throws CmdException
	{
		if(args.length != 2)
			throw new CmdSyntaxError();
		
		String name = args[1];
		Feature feature = parseFeature(name);
		String typeAndName = getType(feature) + " '" + name + "'";
		
		TooManyHaxHack tooManyHax = evlieye.getHax().tooManyHaxHack;
		if(!tooManyHax.isBlocked(feature))
			throw new CmdError("The " + typeAndName + " is not blocked.");
		
		tooManyHax.setBlocked(feature, false);
		ChatUtils.message("Removed " + typeAndName + " from TooManyHax list.");
	}
	
	private void blockAll()
	{
		evlieye.getHax().tooManyHaxHack.blockAll();
		ChatUtils.message("All* features blocked.");
		ChatUtils
			.message("*Note: A few features cannot be blocked because they");
		ChatUtils.message("are required for Evlieye to work properly.");
	}
	
	private void unblockAll()
	{
		evlieye.getHax().tooManyHaxHack.unblockAll();
		ChatUtils.message("All features unblocked.");
	}
	
	private Feature parseFeature(String name) throws CmdSyntaxError
	{
		Feature feature = evlieye.getFeatureByName(name);
		if(feature == null)
			throw new CmdSyntaxError(
				"A feature named '" + name + "' could not be found");
		
		return feature;
	}
	
	private String getType(Feature feature)
	{
		if(feature instanceof Hack)
			return "hack";
		
		if(feature instanceof Command)
			return "command";
		
		if(feature instanceof OtherFeature)
			return "feature";
		
		throw new IllegalStateException();
	}
	
	private void list(String[] args) throws CmdException
	{
		if(args.length > 2)
			throw new CmdSyntaxError();
		
		TooManyHaxHack tooManyHax = evlieye.getHax().tooManyHaxHack;
		List<Feature> blocked = tooManyHax.getBlockedFeatures();
		int page = parsePage(args);
		int pages = (int)Math.ceil(blocked.size() / 8.0);
		pages = Math.max(pages, 1);
		
		if(page > pages || page < 1)
			throw new CmdSyntaxError("Invalid page: " + page);
		
		String total = "Total: " + blocked.size() + " blocked feature";
		total += blocked.size() != 1 ? "s" : "";
		ChatUtils.message(total);
		
		int start = (page - 1) * 8;
		int end = Math.min(page * 8, blocked.size());
		
		ChatUtils.message("TooManyHax list (page " + page + "/" + pages + ")");
		for(int i = start; i < end; i++)
			ChatUtils.message(blocked.get(i).getName());
	}
	
	private int parsePage(String[] args) throws CmdSyntaxError
	{
		if(args.length < 2)
			return 1;
		
		if(!MathUtils.isInteger(args[1]))
			throw new CmdSyntaxError("Not a number: " + args[1]);
		
		return Integer.parseInt(args[1]);
	}
	
	private void loadProfile(String[] args) throws CmdException
	{
		if(args.length != 2)
			throw new CmdSyntaxError();
		
		String name = parseFileName(args[1]);
		
		try
		{
			evlieye.getHax().tooManyHaxHack.loadProfile(name);
			ChatUtils.message("TooManyHax profile loaded: " + name);
			
		}catch(NoSuchFileException e)
		{
			throw new CmdError("Profile '" + name + "' doesn't exist.");
			
		}catch(JsonException e)
		{
			e.printStackTrace();
			throw new CmdError(
				"Profile '" + name + "' is corrupted: " + e.getMessage());
			
		}catch(IOException e)
		{
			e.printStackTrace();
			throw new CmdError("Couldn't load profile: " + e.getMessage());
		}
	}
	
	private void saveProfile(String[] args) throws CmdException
	{
		if(args.length != 2)
			throw new CmdSyntaxError();
		
		String name = parseFileName(args[1]);
		
		try
		{
			evlieye.getHax().tooManyHaxHack.saveProfile(name);
			ChatUtils.message("TooManyHax profile saved: " + name);
			
		}catch(IOException | JsonException e)
		{
			e.printStackTrace();
			throw new CmdError("Couldn't save profile: " + e.getMessage());
		}
	}
	
	private String parseFileName(String input)
	{
		String fileName = input;
		if(!fileName.endsWith(".json"))
			fileName += ".json";
		
		return fileName;
	}
	
	private void listProfiles(String[] args) throws CmdException
	{
		if(args.length > 2)
			throw new CmdSyntaxError();
		
		ArrayList<Path> files = evlieye.getKeybinds().listProfiles();
		int page = parsePage(args);
		int pages = (int)Math.ceil(files.size() / 8.0);
		pages = Math.max(pages, 1);
		
		if(page > pages || page < 1)
			throw new CmdSyntaxError("Invalid page: " + page);
		
		String total = "Total: " + files.size() + " profile";
		total += files.size() != 1 ? "s" : "";
		ChatUtils.message(total);
		
		int start = (page - 1) * 8;
		int end = Math.min(page * 8, files.size());
		
		ChatUtils.message(
			"TooManyHax profile list (page " + page + "/" + pages + ")");
		for(int i = start; i < end; i++)
			ChatUtils.message(files.get(i).getFileName().toString());
	}
}
