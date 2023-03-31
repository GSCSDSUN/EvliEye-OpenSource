/*

 *



 */
package gg.evlieye.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.RenderTickCounter;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.hacks.TimerHack;

@Mixin(RenderTickCounter.class)
public abstract class RenderTickCounterMixin
{
	@Shadow
	private float lastFrameDuration;
	
	@Inject(at = {@At(value = "FIELD",
		target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J",
		opcode = Opcodes.PUTFIELD,
		ordinal = 0)}, method = {"beginRenderTick(J)I"})
	public void onBeginRenderTick(long long_1,
		CallbackInfoReturnable<Integer> cir)
	{
		TimerHack timerHack = EvlieyeClient.INSTANCE.getHax().timerHack;
		lastFrameDuration *= timerHack.getTimerSpeed();
	}
}
