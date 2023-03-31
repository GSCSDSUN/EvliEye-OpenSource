/*

 *



 */
package gg.evlieye.other_features;

import gg.evlieye.DontBlock;
import gg.evlieye.SearchTags;
import gg.evlieye.other_feature.OtherFeature;
import gg.evlieye.settings.CheckboxSetting;

@SearchTags({"languages", "localizations", "localisations",
	"internationalization", "internationalisation", "i18n", "sprachen",
	"übersetzungen", "force english"})
@DontBlock
public final class TranslationsOtf extends OtherFeature
{
	private final CheckboxSetting forceEnglish = new CheckboxSetting(
		"Force English",
		"Displays the Evlieye Client in English, even if Minecraft is set to a different language.",
		true);
	
	public TranslationsOtf()
	{
		super("Translations", "Localization settings.\n\n"
			+ "\u00a7cThis is an experimental feature!\u00a7r\n"
			+ "We don't have many translations yet. If you speak both English and some other language, please help us by adding more translations.");
		addSetting(forceEnglish);
	}
	
	public CheckboxSetting getForceEnglish()
	{
		return forceEnglish;
	}
}
