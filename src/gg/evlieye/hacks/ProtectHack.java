/*

 *



 */
package gg.evlieye.hacks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.Category;
import gg.evlieye.ai.PathFinder;
import gg.evlieye.ai.PathPos;
import gg.evlieye.ai.PathProcessor;
import gg.evlieye.commands.PathCmd;
import gg.evlieye.events.RenderListener;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.DontSaveState;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.AttackSpeedSliderSetting;
import gg.evlieye.settings.CheckboxSetting;
import gg.evlieye.settings.PauseAttackOnContainersSetting;
import gg.evlieye.settings.filterlists.EntityFilterList;
import gg.evlieye.settings.filters.*;
import gg.evlieye.util.EntityUtils;
import gg.evlieye.util.FakePlayerEntity;

@DontSaveState
public final class ProtectHack extends Hack
	implements UpdateListener, RenderListener
{
	private final AttackSpeedSliderSetting speed =
		new AttackSpeedSliderSetting();
	
	private final CheckboxSetting useAi =
		new CheckboxSetting("Use AI (experimental)", false);
	
	private final PauseAttackOnContainersSetting pauseOnContainers =
		new PauseAttackOnContainersSetting(true);
	
	private final EntityFilterList entityFilters =
		new EntityFilterList(FilterPlayersSetting.genericCombat(false),
			FilterSleepingSetting.genericCombat(false),
			FilterFlyingSetting.genericCombat(0),
			FilterMonstersSetting.genericCombat(false),
			FilterPigmenSetting.genericCombat(false),
			FilterEndermenSetting.genericCombat(false),
			FilterAnimalsSetting.genericCombat(false),
			FilterBabiesSetting.genericCombat(false),
			FilterPetsSetting.genericCombat(false),
			FilterTradersSetting.genericCombat(false),
			FilterGolemsSetting.genericCombat(false),
			FilterAllaysSetting.genericCombat(false),
			FilterInvisibleSetting.genericCombat(false),
			FilterNamedSetting.genericCombat(false),
			FilterShulkerBulletSetting.genericCombat(false),
			FilterArmorStandsSetting.genericCombat(false),
			FilterCrystalsSetting.genericCombat(true));
	
	private EntityPathFinder pathFinder;
	private PathProcessor processor;
	private int ticksProcessing;
	
	private Entity friend;
	private Entity enemy;
	
	private double distanceF = 2;
	private double distanceE = 3;
	
	public ProtectHack()
	{
		super("Protect");
		
		setCategory(Category.COMBAT);
		addSetting(speed);
		addSetting(useAi);
		addSetting(pauseOnContainers);
		
		entityFilters.forEach(this::addSetting);
	}
	
	@Override
	public String getRenderName()
	{
		if(friend != null)
			return "Protecting " + friend.getName().getString();
		return "Protect";
	}
	
	@Override
	public void onEnable()
	{
		evlieye.getHax().followHack.setEnabled(false);
		evlieye.getHax().tunnellerHack.setEnabled(false);
		
		// disable other killauras
		evlieye.getHax().aimAssistHack.setEnabled(false);
		evlieye.getHax().clickAuraHack.setEnabled(false);
		evlieye.getHax().crystalAuraHack.setEnabled(false);
		evlieye.getHax().fightBotHack.setEnabled(false);
		evlieye.getHax().killauraLegitHack.setEnabled(false);
		evlieye.getHax().killauraHack.setEnabled(false);
		evlieye.getHax().multiAuraHack.setEnabled(false);
		evlieye.getHax().triggerBotHack.setEnabled(false);
		evlieye.getHax().tpAuraHack.setEnabled(false);
		
		// set friend
		if(friend == null)
		{
			Stream<Entity> stream = StreamSupport
				.stream(MC.world.getEntities().spliterator(), true)
				.filter(e -> e instanceof LivingEntity)
				.filter(
					e -> !e.isRemoved() && ((LivingEntity)e).getHealth() > 0)
				.filter(e -> e != MC.player)
				.filter(e -> !(e instanceof FakePlayerEntity));
			friend = stream
				.min(Comparator
					.comparingDouble(e -> MC.player.squaredDistanceTo(e)))
				.orElse(null);
		}
		
		pathFinder = new EntityPathFinder(friend, distanceF);
		
		speed.resetTimer();
		EVENTS.add(UpdateListener.class, this);
		EVENTS.add(RenderListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(UpdateListener.class, this);
		EVENTS.remove(RenderListener.class, this);
		
		pathFinder = null;
		processor = null;
		ticksProcessing = 0;
		PathProcessor.releaseControls();
		
		enemy = null;
		
		if(friend != null)
		{
			MC.options.forwardKey.setPressed(false);
			friend = null;
		}
	}
	
	@Override
	public void onUpdate()
	{
		speed.updateTimer();
		
		if(pauseOnContainers.shouldPause())
			return;
		
		// check if player died, friend died or disappeared
		if(friend == null || friend.isRemoved()
			|| !(friend instanceof LivingEntity)
			|| ((LivingEntity)friend).getHealth() <= 0
			|| MC.player.getHealth() <= 0)
		{
			friend = null;
			enemy = null;
			setEnabled(false);
			return;
		}
		
		// set enemy
		Stream<Entity> stream = EntityUtils.getAttackableEntities()
			.filter(e -> MC.player.squaredDistanceTo(e) <= 36)
			.filter(e -> e != friend);
		
		stream = entityFilters.applyTo(stream);
		
		enemy = stream
			.min(
				Comparator.comparingDouble(e -> MC.player.squaredDistanceTo(e)))
			.orElse(null);
		
		Entity target =
			enemy == null || MC.player.squaredDistanceTo(friend) >= 24 * 24
				? friend : enemy;
		
		double distance = target == enemy ? distanceE : distanceF;
		
		if(useAi.isChecked())
		{
			// reset pathfinder
			if((processor == null || processor.isDone() || ticksProcessing >= 10
				|| !pathFinder.isPathStillValid(processor.getIndex()))
				&& (pathFinder.isDone() || pathFinder.isFailed()))
			{
				pathFinder = new EntityPathFinder(target, distance);
				processor = null;
				ticksProcessing = 0;
			}
			
			// find path
			if(!pathFinder.isDone() && !pathFinder.isFailed())
			{
				PathProcessor.lockControls();
				evlieye.getRotationFaker()
					.faceVectorClient(target.getBoundingBox().getCenter());
				pathFinder.think();
				pathFinder.formatPath();
				processor = pathFinder.getProcessor();
			}
			
			// process path
			if(!processor.isDone())
			{
				processor.process();
				ticksProcessing++;
			}
		}else
		{
			// jump if necessary
			if(MC.player.horizontalCollision && MC.player.isOnGround())
				MC.player.jump();
			
			// swim up if necessary
			if(MC.player.isTouchingWater() && MC.player.getY() < target.getY())
				MC.player.addVelocity(0, 0.04, 0);
			
			// control height if flying
			if(!MC.player.isOnGround()
				&& (MC.player.getAbilities().flying
					|| evlieye.getHax().flightHack.isEnabled())
				&& MC.player.squaredDistanceTo(target.getX(), MC.player.getY(),
					target.getZ()) <= MC.player.squaredDistanceTo(
						MC.player.getX(), target.getY(), MC.player.getZ()))
			{
				if(MC.player.getY() > target.getY() + 1D)
					MC.options.sneakKey.setPressed(true);
				else if(MC.player.getY() < target.getY() - 1D)
					MC.options.jumpKey.setPressed(true);
			}else
			{
				MC.options.sneakKey.setPressed(false);
				MC.options.jumpKey.setPressed(false);
			}
			
			// follow target
			evlieye.getRotationFaker()
				.faceVectorClient(target.getBoundingBox().getCenter());
			MC.options.forwardKey.setPressed(MC.player.distanceTo(
				target) > (target == friend ? distanceF : distanceE));
		}
		
		if(target == enemy)
		{
			evlieye.getHax().autoSwordHack.setSlot();
			
			// check cooldown
			if(!speed.isTimeToAttack())
				return;
			
			// attack enemy
			evlieye.getHax().criticalsHack.doCritical();
			MC.interactionManager.attackEntity(MC.player, enemy);
			MC.player.swingHand(Hand.MAIN_HAND);
			speed.resetTimer();
		}
	}
	
	@Override
	public void onRender(MatrixStack matrixStack, float partialTicks)
	{
		if(!useAi.isChecked())
			return;
		
		PathCmd pathCmd = evlieye.getCmds().pathCmd;
		pathFinder.renderPath(matrixStack, pathCmd.isDebugMode(),
			pathCmd.isDepthTest());
	}
	
	public void setFriend(Entity friend)
	{
		this.friend = friend;
	}
	
	private class EntityPathFinder extends PathFinder
	{
		private final Entity entity;
		private double distanceSq;
		
		public EntityPathFinder(Entity entity, double distance)
		{
			super(BlockPos.ofFloored(entity.getPos()));
			this.entity = entity;
			distanceSq = distance * distance;
			setThinkTime(1);
		}
		
		@Override
		protected boolean checkDone()
		{
			return done =
				entity.squaredDistanceTo(Vec3d.ofCenter(current)) <= distanceSq;
		}
		
		@Override
		public ArrayList<PathPos> formatPath()
		{
			if(!done)
				failed = true;
			
			return super.formatPath();
		}
	}
}
