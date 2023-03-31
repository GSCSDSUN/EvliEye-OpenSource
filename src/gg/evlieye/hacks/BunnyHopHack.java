/*

 *



 */
package gg.evlieye.hacks;

import java.util.function.Predicate;

import net.minecraft.client.network.ClientPlayerEntity;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.EnumSetting;

@SearchTags({"AutoJump", "BHop", "bunny hop", "auto jump"})
public final class BunnyHopHack extends Hack implements UpdateListener
{
	private final EnumSetting<JumpIf> jumpIf =
		new EnumSetting<>("Jump if", JumpIf.values(), JumpIf.SPRINTING);
	
	public BunnyHopHack()
	{
		super("BunnyHop");
		setCategory(Category.MOVEMENT);
		addSetting(jumpIf);
	}
	
	@Override
	public String getRenderName()
	{
		return getName() + " [" + jumpIf.getSelected().name + "]";
	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		ClientPlayerEntity player = MC.player;
		if(!player.isOnGround() || player.isSneaking())
			return;
		
		if(jumpIf.getSelected().condition.test(player))
			player.jump();
	}
	
	private enum JumpIf
	{
		SPRINTING("Sprinting",
			p -> p.isSprinting()
				&& (p.forwardSpeed != 0 || p.sidewaysSpeed != 0)),
		
		WALKING("Walking", p -> p.forwardSpeed != 0 || p.sidewaysSpeed != 0),
		
		ALWAYS("Always", p -> true);
		
		private final String name;
		private final Predicate<ClientPlayerEntity> condition;
		
		private JumpIf(String name, Predicate<ClientPlayerEntity> condition)
		{
			this.name = name;
			this.condition = condition;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}
