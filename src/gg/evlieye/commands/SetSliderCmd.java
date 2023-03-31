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
import gg.evlieye.settings.Setting;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.util.CmdUtils;
import gg.evlieye.util.MathUtils;

@DontBlock
public final class SetSliderCmd extends Command
{
	public SetSliderCmd()
	{
		super("setslider",
			"Changes a slider setting of a feature. Allows you to\n"
				+ "move sliders through keybinds.",
			".setslider <feature> <setting> <value>",
			".setslider <feature> <setting> (more|less)");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(args.length != 3)
			throw new CmdSyntaxError();
		
		Feature feature = CmdUtils.findFeature(args[0]);
		Setting setting = CmdUtils.findSetting(feature, args[1]);
		SliderSetting slider = getAsSlider(feature, setting);
		setValue(args[2], slider);
	}
	
	private SliderSetting getAsSlider(Feature feature, Setting setting)
		throws CmdError
	{
		if(!(setting instanceof SliderSetting))
			throw new CmdError(feature.getName() + " " + setting.getName()
				+ " is not a slider setting.");
		
		return (SliderSetting)setting;
	}
	
	private void setValue(String value, SliderSetting slider)
		throws CmdSyntaxError
	{
		switch(value.toLowerCase())
		{
			case "more":
			slider.increaseValue();
			break;
			
			case "less":
			slider.decreaseValue();
			break;
			
			default:
			if(!MathUtils.isDouble(value))
				throw new CmdSyntaxError("Value must be a number.");
			slider.setValue(Double.parseDouble(value));
			break;
		}
	}
}
