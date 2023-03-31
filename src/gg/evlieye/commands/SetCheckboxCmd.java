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
import gg.evlieye.settings.CheckboxSetting;
import gg.evlieye.settings.Setting;
import gg.evlieye.util.CmdUtils;

@DontBlock
public final class SetCheckboxCmd extends Command
{
	public SetCheckboxCmd()
	{
		super("setcheckbox",
			"Changes a checkbox setting of a feature. Allows you\n"
				+ "to toggle checkboxes through keybinds.",
			".setcheckbox <feature> <setting> (on|off)",
			".setcheckbox <feature> <setting> toggle");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 3)
			throw new CmdSyntaxError();
		
		Feature feature = CmdUtils.findFeature(args[0]);
		Setting setting = CmdUtils.findSetting(feature, args[1]);
		CheckboxSetting checkbox = getAsCheckbox(feature, setting);
		setChecked(checkbox, args[2]);
	}
	
	private CheckboxSetting getAsCheckbox(Feature feature, Setting setting)
		throws CmdError
	{
		if(!(setting instanceof CheckboxSetting))
			throw new CmdError(feature.getName() + " " + setting.getName()
				+ " is not a checkbox setting.");
		
		return (CheckboxSetting)setting;
	}
	
	private void setChecked(CheckboxSetting checkbox, String value)
		throws CmdSyntaxError
	{
		switch(value.toLowerCase())
		{
			case "on":
			checkbox.setChecked(true);
			break;
			
			case "off":
			checkbox.setChecked(false);
			break;
			
			case "toggle":
			checkbox.setChecked(!checkbox.isChecked());
			break;
			
			default:
			throw new CmdSyntaxError();
		}
	}
}
