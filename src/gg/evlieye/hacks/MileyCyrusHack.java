/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.client.option.KeyBinding;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.mixinterface.IKeyBinding;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

@SearchTags({"miley cyrus", "twerk", "wrecking ball"})
public final class MileyCyrusHack extends Hack implements UpdateListener
{
	private final SliderSetting twerkSpeed = new SliderSetting("Twerk speed",
		"I came in like a wreeecking baaall...", 5, 1, 10, 1,
		ValueDisplay.INTEGER);
	
	private int timer;
	
	public MileyCyrusHack()
	{
		super("MileyCyrus");
		setCategory(Category.FUN);
		addSetting(twerkSpeed);
	}
	
	@Override
	public void onEnable()
	{
		timer = 0;
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
		((IKeyBinding)MC.options.sneakKey).resetPressedState();
	}
	
	@Override
	public void onUpdate()
	{
		timer++;
		if(timer < 10 - twerkSpeed.getValueI())
			return;
		
		KeyBinding sneakKey = MC.options.sneakKey;
		sneakKey.setPressed(!sneakKey.isPressed());
		timer = -1;
	}
}
