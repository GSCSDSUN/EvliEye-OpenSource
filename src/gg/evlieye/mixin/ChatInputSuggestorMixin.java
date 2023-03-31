/*

 *



 */
package gg.evlieye.mixin;

import java.util.concurrent.CompletableFuture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.suggestion.Suggestions;

import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.hacks.AutoCompleteHack;

@Mixin(ChatInputSuggestor.class)
public abstract class ChatInputSuggestorMixin
{
	@Shadow
	private TextFieldWidget textField;
	@Shadow
	private CompletableFuture<Suggestions> pendingSuggestions;
	
	@Inject(at = @At("TAIL"), method = "refresh()V")
	private void onRefresh(CallbackInfo ci)
	{
		AutoCompleteHack autoComplete =
			EvlieyeClient.INSTANCE.getHax().autoCompleteHack;
		if(!autoComplete.isEnabled())
			return;
		
		String draftMessage =
			textField.getText().substring(0, textField.getCursor());
		autoComplete.onRefresh(draftMessage, (builder, suggestion) -> {
			textField.setSuggestion(suggestion);
			pendingSuggestions = builder.buildFuture();
			show(false);
		});
	}
	
	@Shadow
	public abstract void show(boolean narrateFirstSuggestion);
}
