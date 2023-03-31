/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.TesselateBlockListener.TesselateBlockEvent;

@Mixin(TerrainRenderContext.class)
public class TerrainRenderContextMixin
{
	/**
	 * This is a part of what allows X-Ray to make blocks invisible. It's
	 * also the part that keeps breaking whenever Fabric API updates their
	 * rendering code.
	 *
	 * <p>
	 * We could make this optional to stop the game from crashing, but then
	 * X-Ray would silently stop working and it would be much harder to debug.
	 */
	@Inject(at = @At("HEAD"),
		method = "tessellateBlock",
		cancellable = true,
		remap = false)
	private void onTessellateBlock(BlockState blockState, BlockPos blockPos,
		final BakedModel model, MatrixStack matrixStack, CallbackInfo ci)
	{
		TesselateBlockEvent event = new TesselateBlockEvent(blockState);
		EventManager.fire(event);
		
		if(event.isCancelled())
			ci.cancel();
	}
}
