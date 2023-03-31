/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.google.common.collect.Multimap;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.mixinterface.ISwordItem;

@Mixin(SwordItem.class)
public class SwordItemMixin extends ToolItem implements ISwordItem
{
	@Shadow
	@Final
	protected float attackDamage;
	
	@Shadow
	@Final
	private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
	
	private SwordItemMixin(EvlieyeClient wurst, ToolMaterial material,
		Settings settings)
	{
		super(material, settings);
	}
	
	@Override
	public float fuckMcAfee()
	{
		return (float)attributeModifiers
			.get(EntityAttributes.GENERIC_ATTACK_SPEED).iterator().next()
			.getValue();
	}
}
