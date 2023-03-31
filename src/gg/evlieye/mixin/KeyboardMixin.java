/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Keyboard;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.KeyPressListener.KeyPressEvent;

@Mixin(Keyboard.class)
public class KeyboardMixin
{
	@Inject(at = @At("HEAD"), method = "onKey(JIIII)V")
	private void onOnKey(long windowHandle, int keyCode, int scanCode,
		int action, int modifiers, CallbackInfo ci)
	{
		KeyPressEvent event =
			new KeyPressEvent(keyCode, scanCode, action, modifiers);
		
		EventManager.fire(event);
	}
}
