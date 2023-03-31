/*

 *



 */
package gg.evlieye;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.events.PostMotionListener;
import gg.evlieye.events.PreMotionListener;
import gg.evlieye.util.RotationUtils;

public final class RotationFaker
	implements PreMotionListener, PostMotionListener
{
	private boolean fakeRotation;
	private float serverYaw;
	private float serverPitch;
	private float realYaw;
	private float realPitch;
	
	@Override
	public void onPreMotion()
	{
		if(!fakeRotation)
			return;
		
		ClientPlayerEntity player = EvlieyeClient.MC.player;
		realYaw = player.getYaw();
		realPitch = player.getPitch();
		player.setYaw(serverYaw);
		player.setPitch(serverPitch);
	}
	
	@Override
	public void onPostMotion()
	{
		if(!fakeRotation)
			return;
		
		ClientPlayerEntity player = EvlieyeClient.MC.player;
		player.setYaw(realYaw);
		player.setPitch(realPitch);
		fakeRotation = false;
	}
	
	public void faceVectorPacket(Vec3d vec)
	{
		RotationUtils.Rotation needed = RotationUtils.getNeededRotations(vec);
		ClientPlayerEntity player = EvlieyeClient.MC.player;
		
		fakeRotation = true;
		serverYaw =
			RotationUtils.limitAngleChange(player.getYaw(), needed.getYaw());
		serverPitch = needed.getPitch();
	}
	
	public void faceVectorClient(Vec3d vec)
	{
		RotationUtils.Rotation needed = RotationUtils.getNeededRotations(vec);
		
		ClientPlayerEntity player = EvlieyeClient.MC.player;
		player.setYaw(
			RotationUtils.limitAngleChange(player.getYaw(), needed.getYaw()));
		player.setPitch(needed.getPitch());
	}
	
	public void faceVectorClientIgnorePitch(Vec3d vec)
	{
		RotationUtils.Rotation needed = RotationUtils.getNeededRotations(vec);
		
		ClientPlayerEntity player = EvlieyeClient.MC.player;
		EvlieyeClient.MC.player.setYaw(
			RotationUtils.limitAngleChange(player.getYaw(), needed.getYaw()));
		EvlieyeClient.MC.player.setPitch(0);
	}
	
	public float getServerYaw()
	{
		return fakeRotation ? serverYaw : EvlieyeClient.MC.player.getYaw();
	}
	
	public float getServerPitch()
	{
		return fakeRotation ? serverPitch : EvlieyeClient.MC.player.getPitch();
	}
}
