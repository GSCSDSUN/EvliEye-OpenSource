/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.util.telemetry.TelemetryManager;
import net.minecraft.client.util.telemetry.TelemetrySender;
import gg.evlieye.EvlieyeClient;

@Mixin(TelemetryManager.class)
public class TelemetryManagerMixin
{
	@Inject(at = @At("HEAD"),
		method = "getSender()Lnet/minecraft/client/util/telemetry/TelemetrySender;",
		cancellable = true)
	private void onGetSender(CallbackInfoReturnable<TelemetrySender> cir)
	{
		if(!EvlieyeClient.INSTANCE.getOtfs().noTelemetryOtf.isEnabled())
			return;
		
		// Return a dummy that can't actually send anything. :)
		cir.setReturnValue(TelemetrySender.NOOP);
	}
}
