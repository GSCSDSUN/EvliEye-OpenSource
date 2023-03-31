/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import gg.evlieye.EvlieyeClient;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin
{
	@Inject(at = @At("HEAD"),
		method = "hasBlindnessOrDarkness(Lnet/minecraft/client/render/Camera;)Z",
		cancellable = true)
	private void onHasBlindnessOrDarknessEffect(Camera camera,
		CallbackInfoReturnable<Boolean> ci)
	{
		if(EvlieyeClient.INSTANCE.getHax().antiBlindHack.isEnabled())
			ci.setReturnValue(false);
	}
}
