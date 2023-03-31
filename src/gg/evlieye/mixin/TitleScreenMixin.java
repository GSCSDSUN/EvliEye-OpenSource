/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.altmanager.screens.AltManagerScreen;
import gg.evlieye.mixinterface.IScreen;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen
{
	private ClickableWidget realmsButton = null;
	private ButtonWidget altsButton;
	
	private TitleScreenMixin(EvlieyeClient wurst, Text text_1)
	{
		super(text_1);
	}
	
	@Inject(at = {@At("RETURN")}, method = {"init()V"})
	private void onInitWidgetsNormal(CallbackInfo ci)
	{
		if(!EvlieyeClient.INSTANCE.isEnabled())
			return;
		
		for(Drawable d : ((IScreen)this).getButtons())
		{
			if(!(d instanceof ClickableWidget))
				continue;
			
			ClickableWidget button = (ClickableWidget)d;
			if(!button.getMessage().getString()
				.equals(I18n.translate("menu.online")))
				continue;
			
			realmsButton = button;
			break;
		}
		
		if(realmsButton == null)
			throw new IllegalStateException("Couldn't find realms button!");
		
		// make Realms button smaller
		realmsButton.setWidth(98);
		
		// add AltManager button
		addDrawableChild(altsButton = ButtonWidget
			.builder(Text.literal("Alt Manager"),
				b -> client.setScreen(new AltManagerScreen(this,
					EvlieyeClient.INSTANCE.getAltManager())))
			.dimensions(width / 2 + 2, realmsButton.getY(), 98, 20).build());
	}
	
	@Inject(at = {@At("RETURN")}, method = {"tick()V"})
	private void onTick(CallbackInfo ci)
	{
		if(realmsButton == null || altsButton == null)
			return;
			
		// adjust AltManager button if Realms button has been moved
		// happens when ModMenu is installed
		altsButton.setY(realmsButton.getY());
	}
	
	/**
	 * Stops the multiplayer button being grayed out if the user's Microsoft
	 * account is parental-control'd or banned from online play.
	 */
	@Inject(at = @At("HEAD"),
		method = "getMultiplayerDisabledText()Lnet/minecraft/text/Text;",
		cancellable = true)
	private void onGetMultiplayerDisabledText(CallbackInfoReturnable<Text> cir)
	{
		cir.setReturnValue(null);
	}
}
