/*

 *



 */
package gg.evlieye.hacks;

import java.util.function.BiConsumer;

import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.client.gui.screen.ChatScreen;
import gg.evlieye.Category;
import gg.evlieye.SearchTags;
import gg.evlieye.events.ChatOutputListener;
import gg.evlieye.events.UpdateListener;
import gg.evlieye.hack.Hack;
import gg.evlieye.hacks.autocomplete.MessageCompleter;
import gg.evlieye.hacks.autocomplete.ModelSettings;
import gg.evlieye.hacks.autocomplete.OobaboogaMessageCompleter;
import gg.evlieye.hacks.autocomplete.OpenAiMessageCompleter;
import gg.evlieye.hacks.autocomplete.SuggestionHandler;
import gg.evlieye.settings.EnumSetting;
import gg.evlieye.util.ChatUtils;

@SearchTags({"auto complete", "Copilot", "ChatGPT", "chat GPT", "GPT-3", "GPT3",
	"GPT 3", "OpenAI", "open ai", "ChatAI", "chat AI", "ChatBot", "chat bot"})
public final class AutoCompleteHack extends Hack
	implements ChatOutputListener, UpdateListener
{
	private final ModelSettings modelSettings = new ModelSettings();
	private final SuggestionHandler suggestionHandler = new SuggestionHandler();
	
	private final EnumSetting<ApiProvider> apiProvider = new EnumSetting<>(
		"API provider",
		"\u00a7lOpenAI\u00a7r lets you use models like GPT-3, but requires an"
			+ " account with API access, costs money to use and sends your chat"
			+ " history to their servers. The name is a lie - it's closed"
			+ " source.\n\n"
			+ "\u00a7loobabooga\u00a7r lets you use models like LLaMA and many"
			+ " others. It's a true open source alternative to OpenAI that you"
			+ " can run locally on your own computer. It's free to use and does"
			+ " not send your chat history to any servers.",
		ApiProvider.values(), ApiProvider.OOBABOOGA);
	
	private enum ApiProvider
	{
		OPENAI("OpenAI"),
		OOBABOOGA("oobabooga");
		
		private final String name;
		
		private ApiProvider(String name)
		{
			this.name = name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
	
	private MessageCompleter completer;
	private String draftMessage;
	private BiConsumer<SuggestionsBuilder, String> suggestionsUpdater;
	
	private Thread apiCallThread;
	private long lastApiCallTime;
	private long lastRefreshTime;
	
	public AutoCompleteHack()
	{
		super("AutoComplete");
		setCategory(Category.CHAT);
		
		addSetting(apiProvider);
		modelSettings.forEach(this::addSetting);
		suggestionHandler.getSettings().forEach(this::addSetting);
	}
	
	@Override
	protected void onEnable()
	{
		switch(apiProvider.getSelected())
		{
			case OPENAI:
			String apiKey = System.getenv("WURST_OPENAI_KEY");
			if(apiKey == null)
			{
				ChatUtils.error("API key not found. Please set the"
					+ " WURST_OPENAI_KEY environment variable and reboot.");
				setEnabled(false);
				return;
			}
			completer = new OpenAiMessageCompleter(modelSettings);
			break;
			
			case OOBABOOGA:
			completer = new OobaboogaMessageCompleter(modelSettings);
			break;
		}
		
		EVENTS.add(ChatOutputListener.class, this);
		EVENTS.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		EVENTS.remove(ChatOutputListener.class, this);
		EVENTS.remove(UpdateListener.class, this);
		
		suggestionHandler.clearSuggestions();
	}
	
	@Override
	public void onSentMessage(ChatOutputEvent event)
	{
		suggestionHandler.clearSuggestions();
	}
	
	@Override
	public void onUpdate()
	{
		// check if 300ms have passed since the last refresh
		long timeSinceLastRefresh =
			System.currentTimeMillis() - lastRefreshTime;
		if(timeSinceLastRefresh < 300)
			return;
		
		// check if 3s have passed since the last API call
		long timeSinceLastApiCall =
			System.currentTimeMillis() - lastApiCallTime;
		if(timeSinceLastApiCall < 3000)
			return;
		
		// check if the chat is open
		if(!(MC.currentScreen instanceof ChatScreen))
			return;
		
		// check if we have a draft message and suggestions updater
		if(draftMessage == null || suggestionsUpdater == null)
			return;
		
		// don't start a new thread if the old one is still running
		if(apiCallThread != null && apiCallThread.isAlive())
			return;
		
		// check if we already have a suggestion for the current draft message
		if(suggestionHandler.hasEnoughSuggestionFor(draftMessage))
			return;
			
		// copy fields to local variables, in case they change
		// while the thread is running
		String draftMessage2 = draftMessage;
		BiConsumer<SuggestionsBuilder, String> suggestionsUpdater2 =
			suggestionsUpdater;
		
		// build thread
		apiCallThread = new Thread(() -> {
			
			// get suggestion
			String suggestion = completer.completeChatMessage(draftMessage2);
			if(suggestion.isEmpty())
				return;
			
			// apply suggestion
			suggestionHandler.addSuggestion(suggestion, draftMessage2,
				suggestionsUpdater2);
		});
		apiCallThread.setName("AutoComplete API Call");
		apiCallThread.setPriority(Thread.MIN_PRIORITY);
		apiCallThread.setDaemon(true);
		
		// start thread
		lastApiCallTime = System.currentTimeMillis();
		apiCallThread.start();
	}
	
	public void onRefresh(String draftMessage,
		BiConsumer<SuggestionsBuilder, String> suggestionsUpdater)
	{
		suggestionHandler.showSuggestions(draftMessage, suggestionsUpdater);
		
		this.draftMessage = draftMessage;
		this.suggestionsUpdater = suggestionsUpdater;
		lastRefreshTime = System.currentTimeMillis();
	}
	
	// See ChatInputSuggestorMixin
}
