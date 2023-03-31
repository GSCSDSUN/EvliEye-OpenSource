/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Mouse;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.MouseScrollListener.MouseScrollEvent;

@Mixin(Mouse.class)
public class MouseMixin
{
	@Inject(at = {@At("RETURN")}, method = {"onMouseScroll(JDD)V"})
	private void onOnMouseScroll(long long_1, double double_1, double double_2,
		CallbackInfo ci)
	{
		MouseScrollEvent event = new MouseScrollEvent(double_2);
		EventManager.fire(event);
	}
}
