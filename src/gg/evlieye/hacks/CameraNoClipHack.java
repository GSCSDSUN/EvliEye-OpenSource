/*

 *



 */
package gg.evlieye.hacks;

import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.hack.Hack;

@SearchTags({"camera noclip", "camera no clip"})
public final class CameraNoClipHack extends Hack
{
	public CameraNoClipHack()
	{
		super("CameraNoClip");
		setCategory(Category.RENDER);
	}
	
	// See CameraMixin.onClipToSpace()
}
