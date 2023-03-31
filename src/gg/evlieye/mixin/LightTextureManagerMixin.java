/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.LightmapTextureManager;
import gg.evlieye.EvlieyeClient;

@Mixin(LightmapTextureManager.class)
public class LightTextureManagerMixin
{
	@Inject(at = {@At("HEAD")},
		method = {"getDarknessFactor(F)F"},
		cancellable = true)
	private void onGetDarknessFactor(float delta,
		CallbackInfoReturnable<Float> ci)
	{
		if(EvlieyeClient.INSTANCE.getHax().antiBlindHack.isEnabled())
			ci.setReturnValue(0F);
	}
}
