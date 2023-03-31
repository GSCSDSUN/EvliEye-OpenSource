/*

 *



 */
package gg.evlieye.hacks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"health tags"})
public final class HealthTagsHack extends Hack
{
	public HealthTagsHack()
	{
		super("HealthTags");
		setCategory(Category.RENDER);
	}
	
	public Text addHealth(LivingEntity entity, Text nametag)
	{
		if(!isEnabled())
			return nametag;
		
		int health = (int)entity.getHealth();
		
		MutableText formattedHealth = Text.literal(" ")
			.append(Integer.toString(health)).formatted(getColor(health));
		return ((MutableText)nametag).append(formattedHealth);
	}
	
	private Formatting getColor(int health)
	{
		if(health <= 5)
			return Formatting.DARK_RED;
		
		if(health <= 10)
			return Formatting.GOLD;
		
		if(health <= 15)
			return Formatting.YELLOW;
		
		return Formatting.GREEN;
	}
	
	// See EntityRendererMixin.onRenderLabelIfPresent()
}
