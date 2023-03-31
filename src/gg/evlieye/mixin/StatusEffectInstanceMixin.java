/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.effect.StatusEffectInstance;
import gg.evlieye.EvlieyeClient;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin
	implements Comparable<StatusEffectInstance>
{
	@Shadow
	private int duration;
	
	@Inject(at = {@At("HEAD")},
		method = {"updateDuration()I"},
		cancellable = true)
	private void onUpdateDuration(CallbackInfoReturnable<Integer> cir)
	{
		if(EvlieyeClient.INSTANCE.getHax().potionSaverHack.isFrozen())
			cir.setReturnValue(duration);
	}
}
