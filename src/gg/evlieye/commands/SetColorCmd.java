/*

 *



 */
package gg.evlieye.commands;

import gg.evlieye.DontBlock;
import gg.evlieye.Feature;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.settings.ColorSetting;
import gg.evlieye.settings.Setting;
import gg.evlieye.util.CmdUtils;
import gg.evlieye.util.ColorUtils;
import gg.evlieye.util.json.JsonException;

@DontBlock
public final class SetColorCmd extends Command
{
	public SetColorCmd()
	{
		super("setcolor",
			"Changes a color setting of a feature. Allows you\n"
				+ "to set RGB values through keybinds.",
			".setcolor <feature> <setting> <RGB>",
			"Example: .setcolor ClickGUI AC #FF0000");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 3)
			throw new CmdSyntaxError();
		
		Feature feature = CmdUtils.findFeature(args[0]);
		Setting setting = CmdUtils.findSetting(feature, args[1]);
		ColorSetting colorSetting = getAsColor(feature, setting);
		setColor(colorSetting, args[2]);
	}
	
	private ColorSetting getAsColor(Feature feature, Setting setting)
		throws CmdError
	{
		if(!(setting instanceof ColorSetting))
			throw new CmdError(feature.getName() + " " + setting.getName()
				+ " is not a color setting.");
		
		return (ColorSetting)setting;
	}
	
	private void setColor(ColorSetting setting, String value)
		throws CmdSyntaxError
	{
		try
		{
			setting.setColor(ColorUtils.parseHex(value));
			
		}catch(JsonException e)
		{
			throw new CmdSyntaxError("Invalid color: " + value);
		}
	}
}
