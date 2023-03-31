/*

 *



 */
package gg.evlieye.hacks;

import java.util.ArrayList;
import java.util.stream.Collectors;

import net.minecraft.block.Material;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.PacketOutputListener;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.CheckboxSetting;
import gg.evlieye.util.BlockUtils;

@SearchTags({"WaterWalking", "water walking"})
public final class JesusHack extends Hack
	implements UpdateListener, PacketOutputListener
{
	private final CheckboxSetting bypass =
		new CheckboxSetting("NoCheat+ bypass",
			"Bypasses NoCheat+ but slows down your movement.", false);
	
	private int tickTimer = 10;
	private int packetTimer = 0;
	
	public JesusHack()
	{
		super("Jesus");
		setCategory(Category.MOVEMENT);
		addSetting(bypass);
	}
	
	@Override
	public void onEnable()
	{
		EVENTS.add(UpdateListener.class, this);
		EVENTS.add(PacketOutputListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
		EVENTS.remove(PacketOutputListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		// check if sneaking
		if(MC.options.sneakKey.isPressed())
			return;
		
		ClientPlayerEntity player = MC.player;
		
		// move up in liquid
		if(player.isTouchingWater() || player.isInLava())
		{
			Vec3d velocity = player.getVelocity();
			player.setVelocity(velocity.x, 0.11, velocity.z);
			tickTimer = 0;
			return;
		}
		
		// simulate jumping out of water
		Vec3d velocity = player.getVelocity();
		if(tickTimer == 0)
			player.setVelocity(velocity.x, 0.30, velocity.z);
		else if(tickTimer == 1)
			player.setVelocity(velocity.x, 0, velocity.z);
		
		// update timer
		tickTimer++;
	}
	
	@Override
	public void onSentPacket(PacketOutputEvent event)
	{
		// check packet type
		if(!(event.getPacket() instanceof PlayerMoveC2SPacket))
			return;
		
		PlayerMoveC2SPacket packet = (PlayerMoveC2SPacket)event.getPacket();
		
		// check if packet contains a position
		if(!(packet instanceof PlayerMoveC2SPacket.PositionAndOnGround
			|| packet instanceof PlayerMoveC2SPacket.Full))
			return;
		
		// check inWater
		if(MC.player.isTouchingWater())
			return;
		
		// check fall distance
		if(MC.player.fallDistance > 3F)
			return;
		
		if(!isOverLiquid())
			return;
		
		// if not actually moving, cancel packet
		if(MC.player.input == null)
		{
			event.cancel();
			return;
		}
		
		// wait for timer
		packetTimer++;
		if(packetTimer < 4)
			return;
		
		// cancel old packet
		event.cancel();
		
		// get position
		double x = packet.getX(0);
		double y = packet.getY(0);
		double z = packet.getZ(0);
		
		// offset y
		if(bypass.isChecked() && MC.player.age % 2 == 0)
			y -= 0.05;
		else
			y += 0.05;
		
		// create new packet
		Packet<?> newPacket;
		if(packet instanceof PlayerMoveC2SPacket.PositionAndOnGround)
			newPacket =
				new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, true);
		else
			newPacket = new PlayerMoveC2SPacket.Full(x, y, z, packet.getYaw(0),
				packet.getPitch(0), true);
		
		// send new packet
		MC.player.networkHandler.getConnection().send(newPacket);
	}
	
	public boolean isOverLiquid()
	{
		boolean foundLiquid = false;
		boolean foundSolid = false;
		
		// check collision boxes below player
		ArrayList<Box> blockCollisions = IMC.getWorld()
			.getBlockCollisionsStream(MC.player,
				MC.player.getBoundingBox().offset(0, -0.5, 0))
			.map(VoxelShape::getBoundingBox)
			.collect(Collectors.toCollection(ArrayList::new));
		
		for(Box bb : blockCollisions)
		{
			BlockPos pos = BlockPos.ofFloored(bb.getCenter());
			Material material = BlockUtils.getState(pos).getMaterial();
			
			if(material == Material.WATER || material == Material.LAVA)
				foundLiquid = true;
			else if(material != Material.AIR)
				foundSolid = true;
		}
		
		return foundLiquid && !foundSolid;
	}
	
	public boolean shouldBeSolid()
	{
		return isEnabled() && MC.player != null && MC.player.fallDistance <= 3
			&& !MC.options.sneakKey.isPressed() && !MC.player.isTouchingWater()
			&& !MC.player.isInLava();
	}
}
