/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsListener;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.mixinterface.IScreen;

@Mixin(StatsScreen.class)
public abstract class StatsScreenMixin extends Screen implements StatsListener
{
	private StatsScreenMixin(EvlieyeClient wurst, Text text_1)
	{
		super(text_1);
	}
	
	@Inject(at = {@At("TAIL")}, method = {"createButtons()V"})
	private void onCreateButtons(CallbackInfo ci)
	{
		if(EvlieyeClient.INSTANCE.getOtfs().disableOtf.shouldHideEnableButton())
			return;
		
		ButtonWidget toggleEvlieyeButton =
			ButtonWidget.builder(Text.literal(""), this::toggleEvlieye)
				.dimensions(width / 2 - 152, height - 28, 150, 20).build();
		
		updateEvlieyeButtonText(toggleEvlieyeButton);
		addDrawableChild(toggleEvlieyeButton);
		
		for(Drawable d : ((IScreen)this).getButtons())
		{
			if(!(d instanceof ClickableWidget))
				continue;
			
			ClickableWidget button = (ClickableWidget)d;
			
			if(!button.getMessage().getString()
				.equals(I18n.translate("gui.done")))
				continue;
			
			button.setX(width / 2 + 2);
			button.setWidth(150);
		}
	}
	
	private void toggleEvlieye(ButtonWidget button)
	{
		EvlieyeClient wurst = EvlieyeClient.INSTANCE;
		wurst.setEnabled(!wurst.isEnabled());
		
		updateEvlieyeButtonText(button);
	}
	
	private void updateEvlieyeButtonText(ButtonWidget button)
	{
		EvlieyeClient wurst = EvlieyeClient.INSTANCE;
		String text = (wurst.isEnabled() ? "Disable" : "Enable") + " Evlieye";
		button.setMessage(Text.literal(text));
	}
}
