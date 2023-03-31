/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.mixinterface.IArmorItem;

@Mixin(ArmorItem.class)
public class ArmorItemMixin extends Item implements IArmorItem
{
	@Shadow
	protected float toughness;
	
	private ArmorItemMixin(EvlieyeClient evlieye, Settings item$Settings_1)
	{
		super(item$Settings_1);
	}
	
	@Override
	public float getToughness()
	{
		return toughness;
	}
}
