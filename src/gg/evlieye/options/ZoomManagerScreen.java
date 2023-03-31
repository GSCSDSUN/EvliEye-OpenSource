/*

 *



 */
package gg.evlieye.options;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import gg.evlieye.EvlieyeClient;
import gg.evlieye.other_features.ZoomOtf;
import gg.evlieye.settings.CheckboxSetting;
import gg.evlieye.settings.SliderSetting;

public class ZoomManagerScreen extends Screen implements PressAKeyCallback
{
	private Screen prevScreen;
	private ButtonWidget keyButton;
	private ButtonWidget scrollButton;
	
	public ZoomManagerScreen(Screen par1GuiScreen)
	{
		super(Text.literal(""));
		prevScreen = par1GuiScreen;
	}
	
	@Override
	public void init()
	{
		ZoomOtf zoom = EvlieyeClient.INSTANCE.getOtfs().zoomOtf;
		SliderSetting level = zoom.getLevelSetting();
		CheckboxSetting scroll = zoom.getScrollSetting();
		String zoomKeyName = EvlieyeClient.INSTANCE.getZoomKey()
			.getBoundKeyTranslationKey().replace("key.keyboard.", "");
		
		addDrawableChild(ButtonWidget
			.builder(Text.literal("Back"), b -> client.setScreen(prevScreen))
			.dimensions(width / 2 - 100, height / 4 + 144 - 16, 200, 20)
			.build());
		
		addDrawableChild(keyButton = ButtonWidget
			.builder(Text.literal("Zoom Key: " + zoomKeyName),
				b -> client.setScreen(new PressAKeyScreen(this)))
			.dimensions(width / 2 - 79, height / 4 + 24 - 16, 158, 20).build());
		
		addDrawableChild(ButtonWidget
			.builder(Text.literal("More"), b -> level.increaseValue())
			.dimensions(width / 2 - 79, height / 4 + 72 - 16, 50, 20).build());
		
		addDrawableChild(ButtonWidget
			.builder(Text.literal("Less"), b -> level.decreaseValue())
			.dimensions(width / 2 - 25, height / 4 + 72 - 16, 50, 20).build());
		
		addDrawableChild(ButtonWidget
			.builder(Text.literal("Default"),
				b -> level.setValue(level.getDefaultValue()))
			.dimensions(width / 2 + 29, height / 4 + 72 - 16, 50, 20).build());
		
		addDrawableChild(
			scrollButton = ButtonWidget
				.builder(
					Text.literal(
						"Use Mouse Wheel: " + onOrOff(scroll.isChecked())),
					b -> toggleScroll())
				.dimensions(width / 2 - 79, height / 4 + 96 - 16, 158, 20)
				.build());
	}
	
	private void toggleScroll()
	{
		ZoomOtf zoom = EvlieyeClient.INSTANCE.getOtfs().zoomOtf;
		CheckboxSetting scroll = zoom.getScrollSetting();
		
		scroll.setChecked(!scroll.isChecked());
		scrollButton.setMessage(
			Text.literal("Use Mouse Wheel: " + onOrOff(scroll.isChecked())));
	}
	
	private String onOrOff(boolean on)
	{
		return on ? "ON" : "OFF";
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
		ZoomOtf zoom = EvlieyeClient.INSTANCE.getOtfs().zoomOtf;
		SliderSetting level = zoom.getLevelSetting();
		
		renderBackground(matrixStack);
		drawCenteredTextWithShadow(matrixStack, textRenderer, "Zoom Manager",
			width / 2, 40, 0xffffff);
		drawTextWithShadow(matrixStack, textRenderer,
			"Zoom Level: " + level.getValueString(), width / 2 - 75,
			height / 4 + 44, 0xcccccc);
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void setKey(String key)
	{
		EvlieyeClient.INSTANCE.getZoomKey()
			.setBoundKey(InputUtil.fromTranslationKey(key));
		client.options.write();
		KeyBinding.updateKeysByCode();
		keyButton.setMessage(Text.literal("Zoom Key: " + key));
	}
}
