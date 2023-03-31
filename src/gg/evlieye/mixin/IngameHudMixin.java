/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.GUIRenderListener.GUIRenderEvent;

@Mixin(InGameHud.class)
public class IngameHudMixin extends DrawableHelper
{
	@Inject(
		at = @At(value = "INVOKE",
			target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
			remap = false,
			ordinal = 3),
		method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V")
	private void onRender(MatrixStack matrixStack, float partialTicks,
		CallbackInfo ci)
	{
		if(EvlieyeClient.MC.options.debugEnabled)
			return;
		
		GUIRenderEvent event = new GUIRenderEvent(matrixStack, partialTicks);
		EventManager.fire(event);
	}
	
	@Inject(at = @At("HEAD"),
		method = "renderOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Identifier;F)V",
		cancellable = true)
	private void onRenderOverlay(MatrixStack matrixStack, Identifier identifier,
		float f, CallbackInfo ci)
	{
		if(identifier == null || identifier.getPath() == null)
			return;
		
		if(!identifier.getPath().equals("textures/misc/pumpkinblur.png"))
			return;
		
		if(!EvlieyeClient.INSTANCE.getHax().noPumpkinHack.isEnabled())
			return;
		
		ci.cancel();
	}
}
