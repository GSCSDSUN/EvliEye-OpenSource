/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.util.shape.VoxelShapes;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.CactusCollisionShapeListener;
import gg.evlieye.hack.Hack;

@SearchTags({"NoCactus", "anti cactus", "no cactus"})
public final class AntiCactusHack extends Hack
	implements CactusCollisionShapeListener
{
	public AntiCactusHack()
	{
		super("AntiCactus");
		setCategory(Category.BLOCKS);
	}
	
	@Override
	protected void onEnable()
	{
		EVENTS.add(CactusCollisionShapeListener.class, this);
	}
	
	@Override
	protected void onDisable()
	{
		EVENTS.remove(CactusCollisionShapeListener.class, this);
	}
	
	@Override
	public void onCactusCollisionShape(CactusCollisionShapeEvent event)
	{
		event.setCollisionShape(VoxelShapes.fullCube());
	}
}
