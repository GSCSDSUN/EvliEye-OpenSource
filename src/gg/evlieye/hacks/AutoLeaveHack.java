/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.EnumSetting;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

@SearchTags({"auto leave", "AutoDisconnect", "auto disconnect", "AutoQuit",
	"auto quit"})
public final class AutoLeaveHack extends Hack implements UpdateListener
{
	private final SliderSetting health = new SliderSetting("Health",
		"Leaves the server when your health reaches this value or falls below it.",
		4, 0.5, 9.5, 0.5, ValueDisplay.DECIMAL.withSuffix(" hearts"));
	
	public final EnumSetting<Mode> mode = new EnumSetting<>("Mode",
		"\u00a7lQuit\u00a7r mode just quits the game normally.\n"
			+ "Bypasses NoCheat+ but not CombatLog.\n\n"
			+ "\u00a7lChars\u00a7r mode sends a special chat message that causes the server to kick you.\n"
			+ "Bypasses NoCheat+ and some versions of CombatLog.\n\n"
			+ "\u00a7lTP\u00a7r mode teleports you to an invalid location, causing the server to kick you.\n"
			+ "Bypasses CombatLog, but not NoCheat+.\n\n"
			+ "\u00a7lSelfHurt\u00a7r mode sends the packet for attacking another player, but with yourself as both the attacker and the target. This causes the server to kick you.\n"
			+ "Bypasses both CombatLog and NoCheat+.",
		Mode.values(), Mode.QUIT);
	
	public AutoLeaveHack()
	{
		super("AutoLeave");
		
		setCategory(Category.COMBAT);
		addSetting(health);
		addSetting(mode);
	}
	
	@Override
	public String getRenderName()
	{
		return getName() + " [" + mode.getSelected() + "]";
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
		// check gamemode
		if(MC.player.getAbilities().creativeMode)
			return;
		
		// check for other players
		if(MC.isInSingleplayer()
			&& MC.player.networkHandler.getPlayerList().size() == 1)
			return;
		
		// check health
		if(MC.player.getHealth() > health.getValueF() * 2F)
			return;
		
		// leave server
		switch(mode.getSelected())
		{
			case QUIT:
			MC.world.disconnect();
			break;
			
			case CHARS:
			MC.getNetworkHandler().sendChatMessage("\u00a7");
			break;
			
			case TELEPORT:
			MC.player.networkHandler
				.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(3.1e7,
					100, 3.1e7, false));
			break;
			
			case SELFHURT:
			MC.player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket
				.attack(MC.player, MC.player.isSneaking()));
			break;
		}
		
		// disable
		setEnabled(false);
	}
	
	public static enum Mode
	{
		QUIT("Quit"),
		
		CHARS("Chars"),
		
		TELEPORT("TP"),
		
		SELFHURT("SelfHurt");
		
		private final String name;
		
		private Mode(String name)
		{
			this.name = name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}
