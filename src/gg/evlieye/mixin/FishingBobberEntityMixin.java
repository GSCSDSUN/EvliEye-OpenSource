/*

 *



 */
package gg.evlieye.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.math.BlockPos;
import gg.evlieye.mixinterface.IFishingBobberEntity;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin implements IFishingBobberEntity
{
	@Override
	public boolean checkOpenWaterAround(BlockPos pos)
	{
		return isOpenOrWaterAround(pos);
	}
	
	@Shadow
	private boolean isOpenOrWaterAround(BlockPos pos)
	{
		return false;
	}
}
