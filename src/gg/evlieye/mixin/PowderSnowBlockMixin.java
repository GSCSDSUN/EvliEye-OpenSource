/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import gg.evlieye.EvlieyeClient;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin extends Block
	implements FluidDrainable
{
	private PowderSnowBlockMixin(EvlieyeClient evlieye, Settings settings)
	{
		super(settings);
	}
	
	@Inject(at = {@At("HEAD")},
		method = {"canWalkOnPowderSnow(Lnet/minecraft/entity/Entity;)Z"},
		cancellable = true)
	private static void onCanWalkOnPowderSnow(Entity entity,
		CallbackInfoReturnable<Boolean> cir)
	{
		if(!EvlieyeClient.INSTANCE.getHax().snowShoeHack.isEnabled())
			return;
		
		if(entity != EvlieyeClient.MC.player)
			return;
		
		cir.setReturnValue(true);
	}
}
