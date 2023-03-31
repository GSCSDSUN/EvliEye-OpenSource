/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import gg.evlieye.EvlieyeClient;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin
{
	@ModifyConstant(
		method = "renderFireOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V",
		constant = @Constant(floatValue = -0.3F))
	private static float getFireOffset(float orig)
	{
		return orig - EvlieyeClient.INSTANCE.getHax().noFireOverlayHack
			.getOverlayOffset();
	}
	
	@Inject(at = @At("HEAD"),
		method = "renderUnderwaterOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V",
		cancellable = true)
	private static void onRenderUnderwaterOverlay(
		MinecraftClient minecraftClient, MatrixStack matrixStack,
		CallbackInfo ci)
	{
		if(EvlieyeClient.INSTANCE.getHax().noOverlayHack.isEnabled())
			ci.cancel();
	}
}
