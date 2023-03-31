/*

 *



 */
package gg.evlieye;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lwjgl.glfw.GLFW;

import net.minecraft.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import gg.evlieye.altmanager.AltManager;
import gg.evlieye.analytics.EvlieyeAnalytics;
import gg.evlieye.clickgui.ClickGui;
import gg.evlieye.command.CmdList;
import gg.evlieye.command.CmdProcessor;
import gg.evlieye.command.Command;
import gg.evlieye.event.EventManager;
import gg.evlieye.events.ChatOutputListener;
import gg.evlieye.events.GUIRenderListener;
import gg.evlieye.events.KeyPressListener;
import gg.evlieye.events.PostMotionListener;
import gg.evlieye.events.PreMotionListener;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.hack.HackList;
import gg.evlieye.hud.IngameHUD;
import gg.evlieye.keybinds.KeybindList;
import gg.evlieye.keybinds.KeybindProcessor;
import gg.evlieye.mixinterface.IMinecraftClient;
import gg.evlieye.navigator.Navigator;
import gg.evlieye.other_feature.OtfList;
import gg.evlieye.other_feature.OtherFeature;
import gg.evlieye.settings.SettingsFile;
import gg.evlieye.update.ProblematicResourcePackDetector;
import gg.evlieye.update.EvlieyeUpdater;
import gg.evlieye.util.json.JsonException;

public enum EvlieyeClient
{
	INSTANCE;
	
	public static MinecraftClient MC;
	public static IMinecraftClient IMC;
	
	public static final String VERSION = "7.33";
	public static final String MC_VERSION = "1.19.4";
	
	private EvlieyeAnalytics analytics;
	private EventManager eventManager;
	private AltManager altManager;
	private HackList hax;
	private CmdList cmds;
	private OtfList otfs;
	private SettingsFile settingsFile;
	private Path settingsProfileFolder;
	private KeybindList keybinds;
	private ClickGui gui;
	private Navigator navigator;
	private CmdProcessor cmdProcessor;
	private IngameHUD hud;
	private RotationFaker rotationFaker;
	private FriendsList friends;
	
	private boolean enabled = true;
	private static boolean guiInitialized;
	private EvlieyeUpdater updater;
	private ProblematicResourcePackDetector problematicPackDetector;
	private Path evlieyeFolder;
	
	private KeyBinding zoomKey;
	
	public void initialize()
	{
		System.out.println("Starting Evlieye Client...");
		
		MC = MinecraftClient.getInstance();
		IMC = (IMinecraftClient)MC;
		evlieyeFolder = createEvlieyeFolder();
		
		String trackingID = "UA-52838431-5";
		String hostname = "client.evlieye.gg";
		Path analyticsFile = evlieyeFolder.resolve("analytics.json");
		analytics = new EvlieyeAnalytics(trackingID, hostname, analyticsFile);
		
		eventManager = new EventManager(this);
		
		Path enabledHacksFile = evlieyeFolder.resolve("enabled-hacks.json");
		hax = new HackList(enabledHacksFile);
		
		cmds = new CmdList();
		
		otfs = new OtfList();
		
		Path settingsFile = evlieyeFolder.resolve("settings.json");
		settingsProfileFolder = evlieyeFolder.resolve("settings");
		this.settingsFile = new SettingsFile(settingsFile, hax, cmds, otfs);
		this.settingsFile.load();
		hax.tooManyHaxHack.loadBlockedHacksFile();
		
		Path keybindsFile = evlieyeFolder.resolve("keybinds.json");
		keybinds = new KeybindList(keybindsFile);
		
		Path guiFile = evlieyeFolder.resolve("windows.json");
		gui = new ClickGui(guiFile);
		
		Path preferencesFile = evlieyeFolder.resolve("preferences.json");
		navigator = new Navigator(preferencesFile, hax, cmds, otfs);
		
		Path friendsFile = evlieyeFolder.resolve("friends.json");
		friends = new FriendsList(friendsFile);
		friends.load();
		
		cmdProcessor = new CmdProcessor(cmds);
		eventManager.add(ChatOutputListener.class, cmdProcessor);
		
		KeybindProcessor keybindProcessor =
			new KeybindProcessor(hax, keybinds, cmdProcessor);
		eventManager.add(KeyPressListener.class, keybindProcessor);
		
		hud = new IngameHUD();
		eventManager.add(GUIRenderListener.class, hud);
		
		rotationFaker = new RotationFaker();
		eventManager.add(PreMotionListener.class, rotationFaker);
		eventManager.add(PostMotionListener.class, rotationFaker);
		
		updater = new EvlieyeUpdater();
		eventManager.add(UpdateListener.class, updater);
		
		problematicPackDetector = new ProblematicResourcePackDetector();
		problematicPackDetector.start();
		
		Path altsFile = evlieyeFolder.resolve("alts.encrypted_json");
		Path encFolder =
			Paths.get(System.getProperty("user.home"), ".Evlieye encryption")
				.normalize();
		altManager = new AltManager(altsFile, encFolder);
		
		zoomKey = new KeyBinding("key.evlieye.zoom", InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_V, "Zoom");
		KeyBindingHelper.registerKeyBinding(zoomKey);
		
		analytics.trackPageView("/mc" + MC_VERSION + "/v" + VERSION,
			"Evlieye " + VERSION + " MC" + MC_VERSION);
	}
	
	private Path createEvlieyeFolder()
	{
		Path dotMinecraftFolder = MC.runDirectory.toPath().normalize();
		Path evlieyeFolder = dotMinecraftFolder.resolve("evlieye");
		
		try
		{
			Files.createDirectories(evlieyeFolder);
			
		}catch(IOException e)
		{
			throw new RuntimeException(
				"Couldn't create .minecraft/evlieye folder.", e);
		}
		
		return evlieyeFolder;
	}
	
	public String translate(String key)
	{
		if(otfs.translationsOtf.getForceEnglish().isChecked())
			return IMC.getLanguageManager().getEnglish().get(key);
			
		// This extra check is necessary because I18n.translate() doesn't
		// always return the key when the translation is missing. If the key
		// contains a '%', it will return "Format Error: key" instead.
		if(!I18n.hasTranslation(key))
			return key;
		
		return I18n.translate(key);
	}
	
	public EvlieyeAnalytics getAnalytics()
	{
		return analytics;
	}
	
	public EventManager getEventManager()
	{
		return eventManager;
	}
	
	public void saveSettings()
	{
		settingsFile.save();
	}
	
	public ArrayList<Path> listSettingsProfiles()
	{
		if(!Files.isDirectory(settingsProfileFolder))
			return new ArrayList<>();
		
		try(Stream<Path> files = Files.list(settingsProfileFolder))
		{
			return files.filter(Files::isRegularFile)
				.collect(Collectors.toCollection(ArrayList::new));
			
		}catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void loadSettingsProfile(String fileName)
		throws IOException, JsonException
	{
		settingsFile.loadProfile(settingsProfileFolder.resolve(fileName));
	}
	
	public void saveSettingsProfile(String fileName)
		throws IOException, JsonException
	{
		settingsFile.saveProfile(settingsProfileFolder.resolve(fileName));
	}
	
	public HackList getHax()
	{
		return hax;
	}
	
	public CmdList getCmds()
	{
		return cmds;
	}
	
	public OtfList getOtfs()
	{
		return otfs;
	}
	
	public Feature getFeatureByName(String name)
	{
		Hack hack = getHax().getHackByName(name);
		if(hack != null)
			return hack;
		
		Command cmd = getCmds().getCmdByName(name.substring(1));
		if(cmd != null)
			return cmd;
		
		OtherFeature otf = getOtfs().getOtfByName(name);
		return otf;
	}
	
	public KeybindList getKeybinds()
	{
		return keybinds;
	}
	
	public ClickGui getGui()
	{
		if(!guiInitialized)
		{
			guiInitialized = true;
			gui.init();
		}
		
		return gui;
	}
	
	public Navigator getNavigator()
	{
		return navigator;
	}
	
	public CmdProcessor getCmdProcessor()
	{
		return cmdProcessor;
	}
	
	public IngameHUD getHud()
	{
		return hud;
	}
	
	public RotationFaker getRotationFaker()
	{
		return rotationFaker;
	}
	
	public FriendsList getFriends()
	{
		return friends;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		
		if(!enabled)
		{
			hax.panicHack.setEnabled(true);
			hax.panicHack.onUpdate();
		}
	}
	
	public EvlieyeUpdater getUpdater()
	{
		return updater;
	}
	
	public ProblematicResourcePackDetector getProblematicPackDetector()
	{
		return problematicPackDetector;
	}
	
	public Path getEvlieyeFolder()
	{
		return evlieyeFolder;
	}
	
	public KeyBinding getZoomKey()
	{
		return zoomKey;
	}
	
	public AltManager getAltManager()
	{
		return altManager;
	}
}
