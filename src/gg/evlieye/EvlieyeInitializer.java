/*

 *



 */
package gg.evlieye;

import net.minecraft.api.ModInitializer;

public final class EvlieyeInitializer implements ModInitializer
{
	private static boolean initialized;
	
	@Override
	public void onInitialize()
	{
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		if(initialized)
			throw new RuntimeException(
				"EvlieyeInitializer.onInitialize() ran twice!");
		
		EvlieyeClient.INSTANCE.initialize();
		initialized = true;
	}
}
