/*

 *



 */
package gg.evlieye.mixinterface;

import net.minecraft.client.util.Session;

public interface IMinecraftClient
{
	public void rightClick();
	
	public void setItemUseCooldown(int itemUseCooldown);
	
	public IClientPlayerInteractionManager getInteractionManager();
	
	public ILanguageManager getLanguageManager();
	
	public int getItemUseCooldown();
	
	public IClientPlayerEntity getPlayer();
	
	public IWorld getWorld();
	
	public void setSession(Session session);
}
