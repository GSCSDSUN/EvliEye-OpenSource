/*

 *



 */
package gg.evlieye.settings;

import gg.evlieye.EvlieyeClient;

public final class AttackSpeedSliderSetting extends SliderSetting
{
	private int tickTimer;
	
	public AttackSpeedSliderSetting()
	{
		this("Speed", "description.evlieye.setting.generic.attack_speed");
	}
	
	public AttackSpeedSliderSetting(String name, String description)
	{
		super(name, description, 0, 0, 20, 0.1,
			ValueDisplay.DECIMAL.withLabel(0, "auto"));
	}
	
	@Override
	public float[] getKnobColor()
	{
		if(getValue() == 0)
			return new float[]{0, 0.5F, 1};
		
		return super.getKnobColor();
	}
	
	public void resetTimer()
	{
		tickTimer = 0;
	}
	
	public void updateTimer()
	{
		tickTimer += 50;
	}
	
	public boolean isTimeToAttack()
	{
		if(getValue() > 0)
			return tickTimer >= 1000 / getValue();
		
		return EvlieyeClient.MC.player.getAttackCooldownProgress(0) >= 1;
	}
}
