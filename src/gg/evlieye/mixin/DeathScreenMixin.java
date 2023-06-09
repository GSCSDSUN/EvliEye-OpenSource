/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.DeathListener.DeathEvent;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen
{
	private DeathScreenMixin(EvlieyeClient evlieye, Text text_1)
	{
		super(text_1);
	}
	
	@Inject(at = {@At(value = "TAIL")}, method = {"tick()V"})
	private void onTick(CallbackInfo ci)
	{
		EventManager.fire(DeathEvent.INSTANCE);
	}
}
