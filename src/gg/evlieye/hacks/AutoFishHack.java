/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.PacketInputListener;
import gg.evlieye.events.RenderListener;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.hacks.autofish.AutoFishDebugDraw;
import gg.evlieye.hacks.autofish.AutoFishRodSelector;
import gg.evlieye.mixinterface.IFishingBobberEntity;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;
import gg.evlieye.util.ChatUtils;

@SearchTags({"FishBot", "auto fish", "fish bot", "fishing"})
public final class AutoFishHack extends Hack
	implements UpdateListener, PacketInputListener, RenderListener
{
	private final SliderSetting validRange = new SliderSetting("Valid range",
		"Any bites that occur outside of this range will be ignored.\n\n"
			+ "Increase your range if bites are not being detected, decrease it if other people's bites are being detected as yours.",
		1.5, 0.25, 8, 0.25, ValueDisplay.DECIMAL);
	
	private int castRodTimer;
	private int reelInTimer;
	
	private final AutoFishDebugDraw debugDraw = new AutoFishDebugDraw();
	private final AutoFishRodSelector rodSelector = new AutoFishRodSelector();
	
	private boolean wasOpenWater;
	
	public AutoFishHack()
	{
		super("AutoFish");
		
		setCategory(Category.OTHER);
		addSetting(validRange);
		debugDraw.getSettings().forEach(this::addSetting);
	}
	
	@Override
	public void onEnable()
	{
		WURST.getHax().airPlaceHack.setEnabled(false);
		
		castRodTimer = 0;
		reelInTimer = -1;
		rodSelector.reset();
		debugDraw.reset();
		wasOpenWater = true;
		
		EVENTS.add(UpdateListener.class, this);
		EVENTS.add(PacketInputListener.class, this);
		EVENTS.add(RenderListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
		EVENTS.remove(PacketInputListener.class, this);
		EVENTS.remove(RenderListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		debugDraw.updateValidRange(validRange.getValue());
		
		if(reelInTimer > 0)
			reelInTimer--;
		
		ClientPlayerEntity player = MC.player;
		
		if(rodSelector.hasScheduledClick())
		{
			rodSelector.doScheduledClick();
			castRodTimer = 15;
			return;
		}
		
		rodSelector.updateBestRod();
		
		if(!rodSelector.hasARod())
		{
			ChatUtils.message("AutoFish has run out of fishing rods.");
			setEnabled(false);
			return;
		}
		
		if(!rodSelector.isBestRodAlreadySelected())
		{
			rodSelector.selectBestRod();
			return;
		}
		
		// wait for timer
		if(castRodTimer > 0)
		{
			castRodTimer--;
			return;
		}
		
		// cast rod
		if(player.fishHook == null || player.fishHook.isRemoved())
		{
			rightClick();
			castRodTimer = 15;
			reelInTimer = 1200;
		}
		
		// reel in after 60s
		if(reelInTimer == 0)
		{
			reelInTimer--;
			rightClick();
			castRodTimer = 15;
		}
	}
	
	@Override
	public void onReceivedPacket(PacketInputEvent event)
	{
		ClientPlayerEntity player = MC.player;
		if(player == null || player.fishHook == null)
			return;
		
		if(!(event.getPacket() instanceof PlaySoundS2CPacket))
			return;
		
		// check sound type
		PlaySoundS2CPacket sound = (PlaySoundS2CPacket)event.getPacket();
		if(!SoundEvents.ENTITY_FISHING_BOBBER_SPLASH
			.equals(sound.getSound().value()))
			return;
		
		debugDraw.updateSoundPos(sound);
		
		// check position
		FishingBobberEntity bobber = player.fishHook;
		if(Math.abs(sound.getX() - bobber.getX()) > validRange.getValue()
			|| Math.abs(sound.getZ() - bobber.getZ()) > validRange.getValue())
			return;
		
		// check open water
		boolean isOpenWater = isInOpenWater(bobber);
		if(!isOpenWater && wasOpenWater)
		{
			ChatUtils.warning("You are currently fishing in shallow water.");
			ChatUtils.message(
				"You can't get any treasure items while fishing like this.");
			
			if(!WURST.getHax().openWaterEspHack.isEnabled())
				ChatUtils.message("Use OpenWaterESP to find open water.");
		}
		
		// catch fish
		rightClick();
		castRodTimer = 15;
		wasOpenWater = isOpenWater;
	}
	
	private boolean isInOpenWater(FishingBobberEntity bobber)
	{
		return ((IFishingBobberEntity)bobber)
			.checkOpenWaterAround(bobber.getBlockPos());
	}
	
	private void rightClick()
	{
		// check held item
		ItemStack stack = MC.player.getInventory().getMainHandStack();
		if(stack.isEmpty() || !(stack.getItem() instanceof FishingRodItem))
			return;
		
		// right click
		IMC.rightClick();
	}
	
	@Override
	public void onRender(MatrixStack matrixStack, float partialTicks)
	{
		debugDraw.render(matrixStack, partialTicks);
	}
}
