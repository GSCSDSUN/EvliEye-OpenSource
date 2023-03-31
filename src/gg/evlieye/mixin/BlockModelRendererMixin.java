/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.ShouldDrawSideListener.ShouldDrawSideEvent;
import gg.evlieye.events.TesselateBlockListener.TesselateBlockEvent;

@Mixin(BlockModelRenderer.class)
public abstract class BlockModelRendererMixin
{
	@Inject(at = {@At("HEAD")},
		method = {
			"renderSmooth(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLnet/minecraft/util/math/random/Random;JI)V",
			"renderFlat(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;ZLnet/minecraft/util/math/random/Random;JI)V"},
		cancellable = true)
	private void onRenderSmoothOrFlat(BlockRenderView world, BakedModel model,
		BlockState state, BlockPos pos, MatrixStack matrices,
		VertexConsumer vertexConsumer, boolean cull, Random random, long seed,
		int overlay, CallbackInfo ci)
	{
		TesselateBlockEvent event = new TesselateBlockEvent(state);
		EventManager.fire(event);
		
		if(event.isCancelled())
		{
			ci.cancel();
			return;
		}
		
		if(!cull)
			return;
		
		ShouldDrawSideEvent event2 = new ShouldDrawSideEvent(state);
		EventManager.fire(event2);
		if(!Boolean.TRUE.equals(event2.isRendered()))
			return;
		
		renderSmooth(world, model, state, pos, matrices, vertexConsumer, false,
			random, seed, overlay);
	}
	
	@Shadow
	public void renderSmooth(BlockRenderView world, BakedModel model,
		BlockState state, BlockPos pos, MatrixStack matrices,
		VertexConsumer vertexConsumer, boolean cull, Random random, long seed,
		int overlay)
	{
		
	}
}
