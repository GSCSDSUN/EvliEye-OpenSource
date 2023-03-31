/*

 *



 */
package gg.evlieye.commands;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import gg.evlieye.DontBlock;
import gg.evlieye.Feature;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.settings.BlockSetting;
import gg.evlieye.settings.Setting;
import gg.evlieye.util.CmdUtils;
import gg.evlieye.util.MathUtils;

@DontBlock
public final class SetBlockCmd extends Command
{
	public SetBlockCmd()
	{
		super("setblock",
			"Changes a block setting of a feature. Allows you\n"
				+ "to change these settings through keybinds.",
			".setblock <feature> <setting> <block>",
			".setblock <feature> <setting> reset",
			"Example: .setblock Nuker ID dirt");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 3)
			throw new CmdSyntaxError();
		
		Feature feature = CmdUtils.findFeature(args[0]);
		Setting setting = CmdUtils.findSetting(feature, args[1]);
		BlockSetting blockSetting = getAsBlockSetting(feature, setting);
		setBlock(blockSetting, args[2]);
	}
	
	private BlockSetting getAsBlockSetting(Feature feature, Setting setting)
		throws CmdError
	{
		if(!(setting instanceof BlockSetting))
			throw new CmdError(feature.getName() + " " + setting.getName()
				+ " is not a block setting.");
		
		return (BlockSetting)setting;
	}
	
	private void setBlock(BlockSetting setting, String value)
		throws CmdSyntaxError
	{
		if(value.toLowerCase().equals("reset"))
		{
			setting.resetToDefault();
			return;
		}
		
		Block block = getBlockFromNameOrID(value);
		if(block == null)
			throw new CmdSyntaxError("\"" + value + "\" is not a valid block.");
		
		setting.setBlock(block);
	}
	
	private Block getBlockFromNameOrID(String nameOrId)
	{
		if(MathUtils.isInteger(nameOrId))
		{
			BlockState state = Block.STATE_IDS.get(Integer.parseInt(nameOrId));
			if(state == null)
				return null;
			
			return state.getBlock();
		}
		
		try
		{
			return Registries.BLOCK.getOrEmpty(new Identifier(nameOrId))
				.orElse(null);
			
		}catch(InvalidIdentifierException e)
		{
			return null;
		}
	}
}
