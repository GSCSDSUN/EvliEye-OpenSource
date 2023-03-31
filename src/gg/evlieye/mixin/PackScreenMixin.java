/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;

@Mixin(PackScreen.class)
public class PackScreenMixin extends Screen
{
	private PackScreenMixin(EvlieyeClient evlieye, Text title)
	{
		super(title);
	}
	
	/**
	 * Scans for problematic resource packs (currently just VanillaTweaks
	 * Twinkling Stars) whenever the resource pack screen is closed.
	 */
	@Inject(at = @At("HEAD"), method = "close()V")
	public void onClose(CallbackInfo ci)
	{
		EvlieyeClient.INSTANCE.getProblematicPackDetector().start();
	}
}
