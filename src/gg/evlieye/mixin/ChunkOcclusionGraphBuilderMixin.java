/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.BlockPos;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.SetOpaqueCubeListener.SetOpaqueCubeEvent;

@Mixin(ChunkOcclusionDataBuilder.class)
public class ChunkOcclusionGraphBuilderMixin
{
	@Inject(at = {@At("HEAD")},
		method = {"markClosed(Lnet/minecraft/util/math/BlockPos;)V"},
		cancellable = true)
	private void onMarkClosed(BlockPos pos, CallbackInfo ci)
	{
		SetOpaqueCubeEvent event = new SetOpaqueCubeEvent();
		EventManager.fire(event);
		
		if(event.isCancelled())
			ci.cancel();
	}
}
