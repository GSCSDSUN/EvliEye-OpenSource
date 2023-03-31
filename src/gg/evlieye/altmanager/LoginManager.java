/*

 *



 */
package gg.evlieye.altmanager;

import java.net.Proxy;
import java.util.Optional;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.util.Session;
import gg.evlieye.EvlieyeClient;

public enum LoginManager
{
	;
	
	public static void login(String email, String password)
		throws LoginException
	{
		YggdrasilUserAuthentication auth =
			(YggdrasilUserAuthentication)new YggdrasilAuthenticationService(
				Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
		
		auth.setUsername(email);
		auth.setPassword(password);
		
		try
		{
			auth.logIn();
			
			GameProfile profile = auth.getSelectedProfile();
			String username = profile.getName();
			String uuid = profile.getId().toString();
			String accessToken = auth.getAuthenticatedToken();
			
			Session session = new Session(username, uuid, accessToken,
				Optional.empty(), Optional.empty(), Session.AccountType.MOJANG);
			
			EvlieyeClient.IMC.setSession(session);
			
		}catch(AuthenticationUnavailableException e)
		{
			throw new LoginException("Cannot contact authentication server!",
				e);
			
		}catch(AuthenticationException e)
		{
			e.printStackTrace();
			String msg = e.getMessage().toLowerCase();
			
			if(msg.contains("invalid username or password."))
				throw new LoginException("Wrong password! (or shadowbanned)",
					e);
			
			if(msg.contains("account migrated"))
				throw new LoginException("Account migrated to Mojang account.",
					e);
			
			if(msg.contains("migrated"))
				throw new LoginException(
					"Account migrated to Microsoft account.", e);
			
			throw new LoginException("Cannot contact authentication server!",
				e);
			
		}catch(NullPointerException e)
		{
			e.printStackTrace();
			
			throw new LoginException("Wrong password! (or shadowbanned)", e);
		}
	}
	
	public static void changeCrackedName(String newName)
	{
		Session session = new Session(newName, "", "", Optional.empty(),
			Optional.empty(), Session.AccountType.MOJANG);
		
		EvlieyeClient.IMC.setSession(session);
	}
}
