/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.DontSaveState;
import gg.evlieye.hack.Hack;

@SearchTags({"auto sign"})
@DontSaveState
public final class AutoSignHack extends Hack
{
	private String[] signText;
	
	public AutoSignHack()
	{
		super("AutoSign");
		setCategory(Category.BLOCKS);
	}
	
	@Override
	public void onDisable()
	{
		signText = null;
	}
	
	public String[] getSignText()
	{
		return signText;
	}
	
	public void setSignText(String[] signText)
	{
		if(isEnabled() && this.signText == null)
			this.signText = signText;
	}
}
