/*

 *



 */
package gg.evlieye.nochatreports;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.other_features.NoChatReportsOtf;
import gg.evlieye.util.ChatUtils;
import gg.evlieye.util.LastServerRememberer;

public final class ForcedChatReportsScreen extends Screen
{
	private static final List<String> TRANSLATABLE_DISCONNECT_REASONS =
		Arrays.asList("multiplayer.disconnect.missing_public_key",
			"multiplayer.disconnect.invalid_public_key_signature",
			"multiplayer.disconnect.invalid_public_key",
			"multiplayer.disconnect.unsigned_chat");
	
	private static final List<String> LITERAL_DISCONNECT_REASONS =
		Arrays.asList("An internal error occurred in your connection.",
			"A secure profile is required to join this server.",
			"Secure profile expired.", "Secure profile invalid.");
	
	private final Screen prevScreen;
	private final Text reason;
	private MultilineText reasonFormatted = MultilineText.EMPTY;
	private int reasonHeight;
	
	private ButtonWidget signatureButton;
	private final Supplier<String> sigButtonMsg;
	
	public ForcedChatReportsScreen(Screen prevScreen)
	{
		super(Text.literal(ChatUtils.evlieye_PREFIX).append(
			Text.translatable("gui.evlieye.nochatreports.unsafe_server.title")));
		this.prevScreen = prevScreen;
		
		reason =
			Text.translatable("gui.evlieye.nochatreports.unsafe_server.message");
		
		NoChatReportsOtf ncr = EvlieyeClient.INSTANCE.getOtfs().noChatReportsOtf;
		sigButtonMsg = () -> EvlieyeClient.INSTANCE
			.translate("button.evlieye.nochatreports.signatures_status")
			+ blockedOrAllowed(ncr.isEnabled());
	}
	
	private String blockedOrAllowed(boolean blocked)
	{
		return EvlieyeClient.INSTANCE.translate(
			"gui.evlieye.generic.allcaps_" + (blocked ? "blocked" : "allowed"));
	}
	
	@Override
	protected void init()
	{
		reasonFormatted =
			MultilineText.create(textRenderer, reason, width - 50);
		reasonHeight = reasonFormatted.count() * textRenderer.fontHeight;
		
		int buttonX = width / 2 - 100;
		int belowReasonY =
			(height - 78) / 2 + reasonHeight / 2 + textRenderer.fontHeight * 2;
		int signaturesY = Math.min(belowReasonY, height - 68);
		int reconnectY = signaturesY + 24;
		int backButtonY = reconnectY + 24;
		
		addDrawableChild(signatureButton = ButtonWidget
			.builder(Text.literal(sigButtonMsg.get()), b -> toggleSignatures())
			.dimensions(buttonX, signaturesY, 200, 20).build());
		
		addDrawableChild(ButtonWidget
			.builder(Text.literal("Reconnect"),
				b -> LastServerRememberer.reconnect(prevScreen))
			.dimensions(buttonX, reconnectY, 200, 20).build());
		
		addDrawableChild(ButtonWidget
			.builder(Text.translatable("gui.toMenu"),
				b -> client.setScreen(prevScreen))
			.dimensions(buttonX, backButtonY, 200, 20).build());
	}
	
	private void toggleSignatures()
	{
		EvlieyeClient.INSTANCE.getOtfs().noChatReportsOtf.doPrimaryAction();
		signatureButton.setMessage(Text.literal(sigButtonMsg.get()));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY,
		float delta)
	{
		renderBackground(matrices);
		
		int centerX = width / 2;
		int reasonY = (height - 68) / 2 - reasonHeight / 2;
		int titleY = reasonY - textRenderer.fontHeight * 2;
		
		DrawableHelper.drawCenteredTextWithShadow(matrices, textRenderer, title,
			centerX, titleY, 0xAAAAAA);
		reasonFormatted.drawCenterWithShadow(matrices, centerX, reasonY);
		
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public boolean shouldCloseOnEsc()
	{
		return false;
	}
	
	public static boolean isCausedByNoChatReports(Text disconnectReason)
	{
		if(!EvlieyeClient.INSTANCE.getOtfs().noChatReportsOtf.isActive())
			return false;
		
		if(disconnectReason.getContent() instanceof TranslatableTextContent tr
			&& TRANSLATABLE_DISCONNECT_REASONS.contains(tr.getKey()))
			return true;
		
		if(disconnectReason.getContent() instanceof LiteralTextContent lt
			&& LITERAL_DISCONNECT_REASONS.contains(lt.string()))
			return true;
		
		return false;
	}
}
