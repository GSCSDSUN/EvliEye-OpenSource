/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.KnockbackListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

@SearchTags({"anti knockback", "AntiVelocity", "anti velocity", "NoKnockback",
	"no knockback", "AntiKB", "anti kb"})
public final class AntiKnockbackHack extends Hack implements KnockbackListener
{
	private final SliderSetting hStrength =
		new SliderSetting("Horizontal Strength",
			"How far to reduce horizontal knockback.\n" + "100% = no knockback",
			1, 0.01, 1, 0.01, ValueDisplay.PERCENTAGE);
	
	private final SliderSetting vStrength =
		new SliderSetting("Vertical Strength",
			"How far to reduce vertical knockback.\n" + "100% = no knockback",
			1, 0.01, 1, 0.01, ValueDisplay.PERCENTAGE);
	
	public AntiKnockbackHack()
	{
		super("AntiKnockback");
		
		setCategory(Category.COMBAT);
		addSetting(hStrength);
		addSetting(vStrength);
	}
	
	@Override
	protected void onEnable()
	{
		EVENTS.add(KnockbackListener.class, this);
	}
	
	@Override
	protected void onDisable()
	{
		EVENTS.remove(KnockbackListener.class, this);
	}
	
	@Override
	public void onKnockback(KnockbackEvent event)
	{
		double verticalMultiplier = 1 - vStrength.getValue();
		double horizontalMultiplier = 1 - hStrength.getValue();
		
		event.setX(event.getDefaultX() * horizontalMultiplier);
		event.setY(event.getDefaultY() * verticalMultiplier);
		event.setZ(event.getDefaultZ() * horizontalMultiplier);
	}
}
