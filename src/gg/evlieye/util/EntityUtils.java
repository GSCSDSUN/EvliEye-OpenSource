/*

 *



 */
package gg.evlieye.util;

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import gg.evlieye.EvlieyeClient;

public enum EntityUtils
{
	;
	
	protected static final EvlieyeClient evlieye = EvlieyeClient.INSTANCE;
	protected static final MinecraftClient MC = EvlieyeClient.MC;
	
	public static Stream<Entity> getAttackableEntities()
	{
		return StreamSupport.stream(MC.world.getEntities().spliterator(), true)
			.filter(IS_ATTACKABLE);
	}
	
	public static Predicate<Entity> IS_ATTACKABLE = e -> e != null
		&& !e.isRemoved()
		&& (e instanceof LivingEntity && ((LivingEntity)e).getHealth() > 0
			|| e instanceof EndCrystalEntity
			|| e instanceof ShulkerBulletEntity)
		&& e != MC.player && !(e instanceof FakePlayerEntity)
		&& !evlieye.getFriends().isFriend(e);
	
	public static Stream<AnimalEntity> getValidAnimals()
	{
		return StreamSupport.stream(MC.world.getEntities().spliterator(), true)
			.filter(e -> e instanceof AnimalEntity).map(e -> (AnimalEntity)e)
			.filter(IS_VALID_ANIMAL);
	}
	
	public static Predicate<AnimalEntity> IS_VALID_ANIMAL =
		a -> a != null && !a.isRemoved() && a.getHealth() > 0;
}
