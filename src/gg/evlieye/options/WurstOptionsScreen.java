/*

 *



 */
package gg.evlieye.options;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.Util.OperatingSystem;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.analytics.EvlieyeAnalytics;
import gg.evlieye.commands.FriendsCmd;
import gg.evlieye.hacks.XRayHack;
import gg.evlieye.mixinterface.IScreen;
import gg.evlieye.other_features.VanillaSpoofOtf;
import gg.evlieye.settings.CheckboxSetting;

public class EvlieyeOptionsScreen extends Screen
{
	private Screen prevScreen;
	
	public EvlieyeOptionsScreen(Screen prevScreen)
	{
		super(Text.literal(""));
		this.prevScreen = prevScreen;
	}
	
	@Override
	public void init()
	{
		addDrawableChild(ButtonWidget
			.builder(Text.literal("Back"), b -> client.setScreen(prevScreen))
			.dimensions(width / 2 - 100, height / 4 + 144 - 16, 200, 20)
			.build());
		
		addSettingButtons();
		addManagerButtons();
		addLinkButtons();
	}
	
	private void addSettingButtons()
	{
		EvlieyeClient wurst = EvlieyeClient.INSTANCE;
		FriendsCmd friendsCmd = wurst.getCmds().friendsCmd;
		CheckboxSetting middleClickFriends = friendsCmd.getMiddleClickFriends();
		EvlieyeAnalytics analytics = wurst.getAnalytics();
		VanillaSpoofOtf vanillaSpoofOtf = wurst.getOtfs().vanillaSpoofOtf;
		CheckboxSetting forceEnglish =
			wurst.getOtfs().translationsOtf.getForceEnglish();
		
		new EvlieyeOptionsButton(-154, 24,
			() -> "Click Friends: "
				+ (middleClickFriends.isChecked() ? "ON" : "OFF"),
			middleClickFriends.getWrappedDescription(200),
			b -> middleClickFriends
				.setChecked(!middleClickFriends.isChecked()));
		
		new EvlieyeOptionsButton(-154, 48,
			() -> "Count Users: " + (analytics.isEnabled() ? "ON" : "OFF"),
			"Counts how many people are using Evlieye\n"
				+ "and which versions are the most popular.\n"
				+ "We use this data to decide when to stop\n"
				+ "supporting old Minecraft versions.\n\n"
				+ "We use a random ID to tell users apart\n"
				+ "so that this data can never be linked to\n"
				+ "your Minecraft account. The random ID is\n"
				+ "changed every 3 days to make extra sure\n"
				+ "that you remain anonymous.",
			b -> analytics.setEnabled(!analytics.isEnabled()));
		
		new EvlieyeOptionsButton(-154, 72,
			() -> "Spoof Vanilla: "
				+ (vanillaSpoofOtf.isEnabled() ? "ON" : "OFF"),
			vanillaSpoofOtf.getWrappedDescription(200),
			b -> vanillaSpoofOtf.doPrimaryAction());
		
		new EvlieyeOptionsButton(-154, 96,
			() -> "Translations: " + (!forceEnglish.isChecked() ? "ON" : "OFF"),
			"§cThis is an experimental feature!\n"
				+ "We don't have many translations yet. If you\n"
				+ "speak both English and some other language,\n"
				+ "please help us by adding more translations.",
			b -> forceEnglish.setChecked(!forceEnglish.isChecked()));
	}
	
	private void addManagerButtons()
	{
		XRayHack xRayHack = EvlieyeClient.INSTANCE.getHax().xRayHack;
		
		new EvlieyeOptionsButton(-50, 24, () -> "Keybinds",
			"Keybinds allow you to toggle any hack\n"
				+ "or command by simply pressing a\n" + "button.",
			b -> client.setScreen(new KeybindManagerScreen(this)));
		
		new EvlieyeOptionsButton(-50, 48, () -> "X-Ray Blocks",
			"Manager for the blocks\n" + "that X-Ray will show.",
			b -> xRayHack.openBlockListEditor(this));
		
		new EvlieyeOptionsButton(-50, 72, () -> "Zoom",
			"The Zoom Manager allows you to\n"
				+ "change the zoom key, how far it\n"
				+ "will zoom in and more.",
			b -> client.setScreen(new ZoomManagerScreen(this)));
	}
	
	private void addLinkButtons()
	{
		OperatingSystem os = Util.getOperatingSystem();
		
		new EvlieyeOptionsButton(54, 24, () -> "Official Website",
			"EvlieyeClient.net", b -> os.open(
				"https://www.wurstclient.net/?utm_source=Evlieye+Client&utm_medium=Evlieye+Options&utm_content=Official+Website"));
		
		new EvlieyeOptionsButton(54, 48, () -> "Evlieye Wiki", "Evlieye.Wiki",
			b -> os.open(
				"https://wurst.wiki/?utm_source=Evlieye+Client&utm_medium=Evlieye+Options&utm_content=Evlieye+Wiki"));
		
		new EvlieyeOptionsButton(54, 72, () -> "Twitter", "@Evlieye_Imperium",
			b -> os.open("https://www.wurstclient.net/twitter/"));
		
		new EvlieyeOptionsButton(54, 96, () -> "Reddit", "r/EvlieyeClient",
			b -> os.open("https://www.wurstclient.net/reddit/"));
		
		new EvlieyeOptionsButton(54, 120, () -> "Donate",
			"EvlieyeClient.net/donate", b -> os.open(
				"https://www.wurstclient.net/donate/?utm_source=Evlieye+Client&utm_medium=Evlieye+Options&utm_content=Donate"));
	}
	
	@Override
	public void close()
	{
		client.setScreen(prevScreen);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY,
		float partialTicks)
	{
		renderBackground(matrixStack);
		renderTitles(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderButtonTooltip(matrixStack, mouseX, mouseY);
	}
	
	private void renderTitles(MatrixStack matrixStack)
	{
		TextRenderer tr = client.textRenderer;
		int middleX = width / 2;
		int y1 = 40;
		int y2 = height / 4 + 24 - 28;
		
		drawCenteredTextWithShadow(matrixStack, tr, "Evlieye Options", middleX,
			y1, 0xffffff);
		
		drawCenteredTextWithShadow(matrixStack, tr, "Settings", middleX - 104,
			y2, 0xcccccc);
		drawCenteredTextWithShadow(matrixStack, tr, "Managers", middleX, y2,
			0xcccccc);
		drawCenteredTextWithShadow(matrixStack, tr, "Links", middleX + 104, y2,
			0xcccccc);
	}
	
	private void renderButtonTooltip(MatrixStack matrixStack, int mouseX,
		int mouseY)
	{
		for(Drawable d : ((IScreen)this).getButtons())
		{
			if(!(d instanceof ClickableWidget))
				continue;
			
			ClickableWidget button = (ClickableWidget)d;
			
			if(!button.isSelected() || !(button instanceof EvlieyeOptionsButton))
				continue;
			
			EvlieyeOptionsButton woButton = (EvlieyeOptionsButton)button;
			
			if(woButton.tooltip.isEmpty())
				continue;
			
			renderTooltip(matrixStack, woButton.tooltip, mouseX, mouseY);
			break;
		}
	}
	
	private final class EvlieyeOptionsButton extends ButtonWidget
	{
		private final Supplier<String> messageSupplier;
		private final List<Text> tooltip;
		
		public EvlieyeOptionsButton(int xOffset, int yOffset,
			Supplier<String> messageSupplier, String tooltip,
			PressAction pressAction)
		{
			super(EvlieyeOptionsScreen.this.width / 2 + xOffset,
				EvlieyeOptionsScreen.this.height / 4 - 16 + yOffset, 100, 20,
				Text.literal(messageSupplier.get()), pressAction,
				ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
			
			this.messageSupplier = messageSupplier;
			
			if(tooltip.isEmpty())
				this.tooltip = Arrays.asList();
			else
			{
				String[] lines = tooltip.split("\n");
				
				Text[] lines2 = new Text[lines.length];
				for(int i = 0; i < lines.length; i++)
					lines2[i] = Text.literal(lines[i]);
				
				this.tooltip = Arrays.asList(lines2);
			}
			
			addDrawableChild(this);
		}
		
		@Override
		public void onPress()
		{
			super.onPress();
			setMessage(Text.literal(messageSupplier.get()));
		}
	}
}
