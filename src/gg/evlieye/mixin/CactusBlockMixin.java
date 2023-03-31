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
import net.minecraft.block.CactusBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.CactusCollisionShapeListener.CactusCollisionShapeEvent;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin extends Block
{
	private CactusBlockMixin(EvlieyeClient wurst, Settings block$Settings_1)
	{
		super(block$Settings_1);
	}
	
	@Inject(at = {@At("HEAD")},
		method = {
			"getCollisionShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;"},
		cancellable = true)
	private void onGetCollisionShape(BlockState blockState_1,
		BlockView blockView_1, BlockPos blockPos_1,
		ShapeContext entityContext_1, CallbackInfoReturnable<VoxelShape> cir)
	{
		CactusCollisionShapeEvent event = new CactusCollisionShapeEvent();
		EventManager.fire(event);
		
		VoxelShape collisionShape = event.getCollisionShape();
		if(collisionShape != null)
			cir.setReturnValue(collisionShape);
	}
}
