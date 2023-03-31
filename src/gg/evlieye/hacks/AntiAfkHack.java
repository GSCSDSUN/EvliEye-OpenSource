/*

 *



 */
package gg.evlieye.hacks;

import java.util.ArrayList;
import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.ai.PathFinder;
import gg.evlieye.ai.PathPos;
import gg.evlieye.ai.PathProcessor;
import gg.evlieye.commands.PathCmd;
import gg.evlieye.events.RenderListener;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.DontSaveState;
import gg.evlieye.hack.Hack;
import gg.evlieye.mixinterface.IKeyBinding;
import gg.evlieye.settings.CheckboxSetting;

@SearchTags({"anti afk", "AFKBot", "afk bot"})
@DontSaveState
public final class AntiAfkHack extends Hack
	implements UpdateListener, RenderListener
{
	private final CheckboxSetting useAi = new CheckboxSetting("Use AI", true);
	
	private int timer;
	private Random random = new Random();
	private BlockPos start;
	private BlockPos nextBlock;
	
	private RandomPathFinder pathFinder;
	private PathProcessor processor;
	private boolean creativeFlying;
	
	public AntiAfkHack()
	{
		super("AntiAFK");
		
		setCategory(Category.OTHER);
		addSetting(useAi);
	}
	
	@Override
	public void onEnable()
	{
		start = BlockPos.ofFloored(MC.player.getPos());
		nextBlock = null;
		pathFinder = new RandomPathFinder(start);
		creativeFlying = MC.player.getAbilities().flying;
		
		EVENTS.add(UpdateListener.class, this);
		EVENTS.add(RenderListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
		EVENTS.remove(RenderListener.class, this);
		
		((IKeyBinding)MC.options.forwardKey).resetPressedState();
		((IKeyBinding)MC.options.jumpKey).resetPressedState();
		
		pathFinder = null;
		processor = null;
		PathProcessor.releaseControls();
	}
	
	@Override
	public void onUpdate()
	{
		// check if player died
		if(MC.player.getHealth() <= 0)
		{
			setEnabled(false);
			return;
		}
		
		MC.player.getAbilities().flying = creativeFlying;
		
		if(useAi.isChecked())
		{
			// update timer
			if(timer > 0)
			{
				timer--;
				if(!evlieye.getHax().jesusHack.isEnabled())
					MC.options.jumpKey.setPressed(MC.player.isTouchingWater());
				return;
			}
			
			// find path
			if(!pathFinder.isDone() && !pathFinder.isFailed())
			{
				PathProcessor.lockControls();
				
				pathFinder.think();
				
				if(!pathFinder.isDone() && !pathFinder.isFailed())
					return;
				
				pathFinder.formatPath();
				
				// set processor
				processor = pathFinder.getProcessor();
			}
			
			// check path
			if(processor != null
				&& !pathFinder.isPathStillValid(processor.getIndex()))
			{
				pathFinder = new RandomPathFinder(pathFinder);
				return;
			}
			
			// process path
			if(!processor.isDone())
				processor.process();
			else
				pathFinder = new RandomPathFinder(start);
			
			// wait 2 - 3 seconds (40 - 60 ticks)
			if(processor.isDone())
			{
				PathProcessor.releaseControls();
				timer = 40 + random.nextInt(21);
			}
		}else
		{
			// set next block
			if(timer <= 0 || nextBlock == null)
			{
				nextBlock =
					start.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
				timer = 40 + random.nextInt(21);
			}
			
			// face block
			evlieye.getRotationFaker()
				.faceVectorClientIgnorePitch(Vec3d.ofCenter(nextBlock));
			
			// walk
			if(MC.player.squaredDistanceTo(Vec3d.ofCenter(nextBlock)) > 0.5)
				MC.options.forwardKey.setPressed(true);
			else
				MC.options.forwardKey.setPressed(false);
			
			// swim up
			MC.options.jumpKey.setPressed(MC.player.isTouchingWater());
			
			// update timer
			if(timer > 0)
				timer--;
		}
	}
	
	@Override
	public void onRender(MatrixStack matrixStack, float partialTicks)
	{
		if(!useAi.isChecked())
			return;
		
		PathCmd pathCmd = evlieye.getCmds().pathCmd;
		RenderSystem.setShader(GameRenderer::getPositionProgram);
		pathFinder.renderPath(matrixStack, pathCmd.isDebugMode(),
			pathCmd.isDepthTest());
	}
	
	private class RandomPathFinder extends PathFinder
	{
		public RandomPathFinder(BlockPos goal)
		{
			super(goal.add(random.nextInt(33) - 16, random.nextInt(33) - 16,
				random.nextInt(33) - 16));
			setThinkTime(10);
			setFallingAllowed(false);
			setDivingAllowed(false);
		}
		
		public RandomPathFinder(PathFinder pathFinder)
		{
			super(pathFinder);
			setFallingAllowed(false);
			setDivingAllowed(false);
		}
		
		@Override
		public ArrayList<PathPos> formatPath()
		{
			failed = true;
			return super.formatPath();
		}
	}
}
