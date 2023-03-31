/*

 *



 */
package gg.evlieye.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.mixinterface.IScreen;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractParentElement
	implements Drawable, IScreen
{
	@Shadow
	@Final
	private List<Drawable> drawables;
	
	@Inject(at = {@At("HEAD")},
		method = {
			"renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V"},
		cancellable = true)
	public void onRenderBackground(MatrixStack matrices, CallbackInfo ci)
	{
		if(EvlieyeClient.INSTANCE.getHax().noBackgroundHack
			.shouldCancelBackground((Screen)(Object)this))
			ci.cancel();
	}
	
	@Override
	public List<Drawable> getButtons()
	{
		return drawables;
	}
}