/*

 *



 */
package gg.evlieye.ai;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.mixinterface.IKeyBinding;

public abstract class PathProcessor
{
	protected static final EvlieyeClient evlieye = EvlieyeClient.INSTANCE;
	protected static final MinecraftClient MC = EvlieyeClient.MC;
	
	private static final KeyBinding[] CONTROLS =
		{MC.options.forwardKey, MC.options.backKey, MC.options.rightKey,
			MC.options.leftKey, MC.options.jumpKey, MC.options.sneakKey};
	
	protected final ArrayList<PathPos> path;
	protected int index;
	protected boolean done;
	protected int ticksOffPath;
	
	public PathProcessor(ArrayList<PathPos> path)
	{
		if(path.isEmpty())
			throw new IllegalStateException("There is no path!");
		
		this.path = path;
	}
	
	public abstract void process();
	
	public final int getIndex()
	{
		return index;
	}
	
	public final boolean isDone()
	{
		return done;
	}
	
	public final int getTicksOffPath()
	{
		return ticksOffPath;
	}
	
	protected final void facePosition(BlockPos pos)
	{
		evlieye.getRotationFaker()
			.faceVectorClientIgnorePitch(Vec3d.ofCenter(pos));
	}
	
	public static final void lockControls()
	{
		// disable keys
		for(KeyBinding key : CONTROLS)
			key.setPressed(false);
		
		// disable sprinting
		EvlieyeClient.MC.player.setSprinting(false);
	}
	
	public static final void releaseControls()
	{
		// reset keys
		for(KeyBinding key : CONTROLS)
			((IKeyBinding)key).resetPressedState();
	}
}
