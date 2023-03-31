/*

 *



 */
package gg.evlieye.hacks;

import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.PacketOutputListener;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.DontSaveState;
import gg.evlieye.hack.Hack;
import gg.evlieye.settings.filterlists.EntityFilterList;
import gg.evlieye.settings.filterlists.RemoteViewFilterList;
import gg.evlieye.util.ChatUtils;
import gg.evlieye.util.FakePlayerEntity;

@SearchTags({"remote view"})
@DontSaveState
public final class RemoteViewHack extends Hack
	implements UpdateListener, PacketOutputListener
{
	private final EntityFilterList entityFilters =
		RemoteViewFilterList.create();
	
	private Entity entity = null;
	private boolean wasInvisible;
	
	private FakePlayerEntity fakePlayer;
	
	public RemoteViewHack()
	{
		super("RemoteView");
		setCategory(Category.RENDER);
		entityFilters.forEach(this::addSetting);
	}
	
	@Override
	public void onEnable()
	{
		// find entity if not already set
		if(entity == null)
		{
			Stream<Entity> stream = StreamSupport
				.stream(MC.world.getEntities().spliterator(), true)
				.filter(e -> e instanceof LivingEntity)
				.filter(
					e -> !e.isRemoved() && ((LivingEntity)e).getHealth() > 0)
				.filter(e -> e != MC.player)
				.filter(e -> !(e instanceof FakePlayerEntity));
			
			stream = entityFilters.applyTo(stream);
			
			entity = stream
				.min(Comparator
					.comparingDouble(e -> MC.player.squaredDistanceTo(e)))
				.orElse(null);
			
			// check if entity was found
			if(entity == null)
			{
				ChatUtils.error("Could not find a valid entity.");
				setEnabled(false);
				return;
			}
		}
		
		// save old data
		wasInvisible = entity.isInvisibleTo(MC.player);
		
		// enable NoClip
		MC.player.noClip = true;
		
		// spawn fake player
		fakePlayer = new FakePlayerEntity();
		
		// success message
		ChatUtils.message("Now viewing " + entity.getName().getString() + ".");
		
		// add listener
		EVENTS.add(UpdateListener.class, this);
		EVENTS.add(PacketOutputListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		// remove listener
		EVENTS.remove(UpdateListener.class, this);
		EVENTS.remove(PacketOutputListener.class, this);
		
		// reset entity
		if(entity != null)
		{
			ChatUtils.message(
				"No longer viewing " + entity.getName().getString() + ".");
			entity.setInvisible(wasInvisible);
			entity = null;
		}
		
		// disable NoClip
		MC.player.noClip = false;
		
		// remove fake player
		if(fakePlayer != null)
		{
			fakePlayer.resetPlayerPosition();
			fakePlayer.despawn();
		}
	}
	
	public void onToggledByCommand(String viewName)
	{
		// set entity
		if(!isEnabled() && viewName != null && !viewName.isEmpty())
		{
			entity = StreamSupport
				.stream(MC.world.getEntities().spliterator(), false)
				.filter(e -> e instanceof LivingEntity)
				.filter(
					e -> !e.isRemoved() && ((LivingEntity)e).getHealth() > 0)
				.filter(e -> e != MC.player)
				.filter(e -> !(e instanceof FakePlayerEntity))
				.filter(e -> viewName.equalsIgnoreCase(e.getName().getString()))
				.min(Comparator
					.comparingDouble(e -> MC.player.squaredDistanceTo(e)))
				.orElse(null);
			
			if(entity == null)
			{
				ChatUtils
					.error("Entity \"" + viewName + "\" could not be found.");
				return;
			}
		}
		
		// toggle RemoteView
		setEnabled(!isEnabled());
	}
	
	@Override
	public void onUpdate()
	{
		// validate entity
		if(entity.isRemoved() || ((LivingEntity)entity).getHealth() <= 0)
		{
			setEnabled(false);
			return;
		}
		
		// update position, rotation, etc.
		MC.player.copyPositionAndRotation(entity);
		MC.player.setPos(entity.getX(),
			entity.getY() - MC.player.getEyeHeight(MC.player.getPose())
				+ entity.getEyeHeight(entity.getPose()),
			entity.getZ());
		MC.player.resetPosition();
		MC.player.setVelocity(Vec3d.ZERO);
		
		// set entity invisible
		entity.setInvisible(true);
	}
	
	@Override
	public void onSentPacket(PacketOutputEvent event)
	{
		if(event.getPacket() instanceof PlayerMoveC2SPacket)
			event.cancel();
	}
}
