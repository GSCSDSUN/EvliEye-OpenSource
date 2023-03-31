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

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.hacks.AutoReconnectHack;
import gg.evlieye.nochatreports.ForcedChatReportsScreen;
import gg.evlieye.nochatreports.NcrModRequiredScreen;
import gg.evlieye.util.LastServerRememberer;

@Mixin(DisconnectedScreen.class)
public class DisconnectedScreenMixin extends Screen
{
	private int autoReconnectTimer;
	private ButtonWidget autoReconnectButton;
	
	@Shadow
	@Final
	private Text reason;
	@Shadow
	@Final
	private Screen parent;
	@Shadow
	private int reasonHeight;
	
	private DisconnectedScreenMixin(EvlieyeClient evlieye, Text title)
	{
		super(title);
	}
	
	@Inject(at = @At("TAIL"), method = {"init()V"})
	private void onInit(CallbackInfo ci)
	{
		if(!EvlieyeClient.INSTANCE.isEnabled())
			return;
		
		System.out.println("Disconnected: " + reason);
		
		if(ForcedChatReportsScreen.isCausedByNoChatReports(reason))
		{
			client.setScreen(new ForcedChatReportsScreen(parent));
			return;
		}
		
		if(NcrModRequiredScreen.isCausedByLackOfNCR(reason))
		{
			client.setScreen(new NcrModRequiredScreen(parent));
			return;
		}
		
		addReconnectButtons();
	}
	
	private void addReconnectButtons()
	{
		int backButtonX = width / 2 - 100;
		int backButtonY =
			Math.min(height / 2 + reasonHeight / 2 + 9, height - 30);
		
		addDrawableChild(ButtonWidget
			.builder(Text.literal("Reconnect"),
				b -> LastServerRememberer.reconnect(parent))
			.dimensions(backButtonX, backButtonY + 24, 200, 20).build());
		
		autoReconnectButton = addDrawableChild(ButtonWidget
			.builder(Text.literal("AutoReconnect"), b -> pressAutoReconnect())
			.dimensions(backButtonX, backButtonY + 48, 200, 20).build());
		
		AutoReconnectHack autoReconnect =
			EvlieyeClient.INSTANCE.getHax().autoReconnectHack;
		
		if(autoReconnect.isEnabled())
			autoReconnectTimer = autoReconnect.getWaitTicks();
	}
	
	private void pressAutoReconnect()
	{
		AutoReconnectHack autoReconnect =
			EvlieyeClient.INSTANCE.getHax().autoReconnectHack;
		
		autoReconnect.setEnabled(!autoReconnect.isEnabled());
		
		if(autoReconnect.isEnabled())
			autoReconnectTimer = autoReconnect.getWaitTicks();
	}
	
	@Override
	public void tick()
	{
		if(!EvlieyeClient.INSTANCE.isEnabled() || autoReconnectButton == null)
			return;
		
		AutoReconnectHack autoReconnect =
			EvlieyeClient.INSTANCE.getHax().autoReconnectHack;
		
		if(!autoReconnect.isEnabled())
		{
			autoReconnectButton.setMessage(Text.literal("AutoReconnect"));
			return;
		}
		
		autoReconnectButton.setMessage(Text.literal("AutoReconnect ("
			+ (int)Math.ceil(autoReconnectTimer / 20.0) + ")"));
		
		if(autoReconnectTimer > 0)
		{
			autoReconnectTimer--;
			return;
		}
		
		LastServerRememberer.reconnect(parent);
	}
}
