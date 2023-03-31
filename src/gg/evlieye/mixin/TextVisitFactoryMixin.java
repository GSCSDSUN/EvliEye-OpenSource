/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.text.TextVisitFactory;
import gg.evlieye.EvlieyeClient;

@Mixin(TextVisitFactory.class)
public abstract class TextVisitFactoryMixin
{
	@ModifyArg(at = @At(value = "INVOKE",
		target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z",
		ordinal = 0),
		method = {
			"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"},
		index = 0)
	private static String adjustText(String text)
	{
		return EvlieyeClient.INSTANCE.getHax().nameProtectHack.protect(text);
	}
}
