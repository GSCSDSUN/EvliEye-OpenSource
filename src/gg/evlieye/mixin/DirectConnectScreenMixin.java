/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.DirectConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.util.LastServerRememberer;

@Mixin(DirectConnectScreen.class)
public class DirectConnectScreenMixin extends Screen
{
	@Shadow
	@Final
	private ServerInfo serverEntry;
	
	private DirectConnectScreenMixin(EvlieyeClient wurst, Text text_1)
	{
		super(text_1);
	}
	
	@Inject(at = {@At("TAIL")}, method = {"saveAndClose()V"})
	private void onSaveAndClose(CallbackInfo ci)
	{
		LastServerRememberer.setLastServer(serverEntry);
	}
}
