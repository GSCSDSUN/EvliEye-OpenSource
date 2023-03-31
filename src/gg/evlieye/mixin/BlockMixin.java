/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.ShouldDrawSideListener.ShouldDrawSideEvent;
import gg.evlieye.hack.HackList;

@Mixin(Block.class)
public abstract class BlockMixin implements ItemConvertible
{
	@Inject(at = {@At("HEAD")},
		method = {
			"shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/BlockPos;)Z"},
		cancellable = true)
	private static void onShouldDrawSide(BlockState state, BlockView world,
		BlockPos pos, Direction direction, BlockPos blockPos,
		CallbackInfoReturnable<Boolean> cir)
	{
		ShouldDrawSideEvent event = new ShouldDrawSideEvent(state);
		EventManager.fire(event);
		
		if(event.isRendered() != null)
			cir.setReturnValue(event.isRendered());
	}
	
	@Inject(at = {@At("HEAD")},
		method = {"getVelocityMultiplier()F"},
		cancellable = true)
	private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir)
	{
		HackList hax = EvlieyeClient.INSTANCE.getHax();
		if(hax == null || !hax.noSlowdownHack.isEnabled())
			return;
		
		if(cir.getReturnValueF() < 1)
			cir.setReturnValue(1F);
	}
}
