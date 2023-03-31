/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.Box;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;
import gg.evlieye.mixinterface.IKeyBinding;
import gg.evlieye.settings.CheckboxSetting;

@SearchTags({"safe walk"})
public final class SafeWalkHack extends Hack
{
	private final CheckboxSetting sneak =
		new CheckboxSetting("Sneak at edges", "Visibly sneak at edges.", false);
	
	private boolean sneaking;
	
	public SafeWalkHack()
	{
		super("SafeWalk");
		setCategory(Category.MOVEMENT);
		addSetting(sneak);
	}
	
	@Override
	protected void onEnable()
	{
		WURST.getHax().parkourHack.setEnabled(false);
		sneaking = false;
	}
	
	@Override
	protected void onDisable()
	{
		if(sneaking)
			setSneaking(false);
	}
	
	public void onClipAtLedge(boolean clipping)
	{
		if(!isEnabled() || !sneak.isChecked() || !MC.player.isOnGround())
		{
			if(sneaking)
				setSneaking(false);
			
			return;
		}
		
		ClientPlayerEntity player = MC.player;
		Box bb = player.getBoundingBox();
		float stepHeight = player.stepHeight;
		
		for(double x = -0.05; x <= 0.05; x += 0.05)
			for(double z = -0.05; z <= 0.05; z += 0.05)
				if(MC.world.isSpaceEmpty(player, bb.offset(x, -stepHeight, z)))
					clipping = true;
				
		setSneaking(clipping);
	}
	
	private void setSneaking(boolean sneaking)
	{
		KeyBinding sneakKey = MC.options.sneakKey;
		
		if(sneaking)
			sneakKey.setPressed(true);
		else
			((IKeyBinding)sneakKey).resetPressedState();
		
		this.sneaking = sneaking;
	}
}
