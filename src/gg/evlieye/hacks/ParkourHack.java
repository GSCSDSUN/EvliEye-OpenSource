/*

 *



 */
package gg.evlieye.hacks;

import java.util.stream.Stream;

import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import gg.evlieye.Category;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.SliderSetting;
import gg.evlieye.settings.SliderSetting.ValueDisplay;

public final class ParkourHack extends Hack implements UpdateListener
{
	private final SliderSetting minDepth = new SliderSetting("Min depth",
		"Won't jump over a pit if it isn't at least this deep.\n"
			+ "Increase to stop Parkour from jumping down stairs.\n"
			+ "Decrease to make Parkour jump at the edge of carpets.",
		0.5, 0.05, 10, 0.05, ValueDisplay.DECIMAL.withSuffix("m"));
	
	private final SliderSetting edgeDistance =
		new SliderSetting("Edge distance",
			"How close Parkour will let you get to the edge before jumping.",
			0.001, 0.001, 0.25, 0.001, ValueDisplay.DECIMAL.withSuffix("m"));
	
	public ParkourHack()
	{
		super("Parkour");
		setCategory(Category.MOVEMENT);
		addSetting(minDepth);
		addSetting(edgeDistance);
	}
	
	@Override
	public void onEnable()
	{
		evlieye.getHax().safeWalkHack.setEnabled(false);
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
		if(!MC.player.isOnGround() || MC.options.jumpKey.isPressed())
			return;
		
		if(MC.player.isSneaking() || MC.options.sneakKey.isPressed())
			return;
		
		Box box = MC.player.getBoundingBox();
		Box adjustedBox = box.stretch(0, -minDepth.getValue(), 0)
			.expand(-edgeDistance.getValue(), 0, -edgeDistance.getValue());
		
		Stream<VoxelShape> blockCollisions =
			IMC.getWorld().getBlockCollisionsStream(MC.player, adjustedBox);
		
		if(blockCollisions.findAny().isPresent())
			return;
		
		MC.player.jump();
	}
}
