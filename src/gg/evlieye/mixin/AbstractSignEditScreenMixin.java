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

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.hacks.AutoSignHack;

@Mixin(AbstractSignEditScreen.class)
public abstract class AbstractSignEditScreenMixin extends Screen
{
	@Shadow
	@Final
	private String[] text;
	
	private AbstractSignEditScreenMixin(EvlieyeClient evlieye, Text text_1)
	{
		super(text_1);
	}
	
	@Inject(at = {@At("HEAD")}, method = {"init()V"})
	private void onInit(CallbackInfo ci)
	{
		AutoSignHack autoSignHack = EvlieyeClient.INSTANCE.getHax().autoSignHack;
		
		String[] autoSignText = autoSignHack.getSignText();
		if(autoSignText == null)
			return;
		
		for(int i = 0; i < 4; i++)
			text[i] = autoSignText[i];
		
		finishEditing();
	}
	
	@Inject(at = {@At("HEAD")}, method = {"finishEditing()V"})
	private void onFinishEditing(CallbackInfo ci)
	{
		EvlieyeClient.INSTANCE.getHax().autoSignHack.setSignText(text);
	}
	
	@Shadow
	private void finishEditing()
	{
		
	}
}
