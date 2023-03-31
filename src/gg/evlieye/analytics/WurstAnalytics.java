/*

 *



 */
package gg.evlieye.analytics;

import java.nio.file.Path;

import gg.evlieye.analytics.dmurph.AnalyticsRequestData;

public final class EvlieyeAnalytics
{
	private final String hostname;
	private final EvlieyeAnalyticsTracker tracker;
	private final AnalyticsConfigFile configFile;
	
	public EvlieyeAnalytics(String trackingID, String hostname, Path configFile)
	{
		tracker = new EvlieyeAnalyticsTracker(trackingID);
		this.hostname = hostname;
		this.configFile = new AnalyticsConfigFile(configFile);
		this.configFile.load(tracker);
	}
	
	public boolean isEnabled()
	{
		return tracker.isEnabled();
	}
	
	public void setEnabled(boolean enabled)
	{
		if(!enabled)
			trackEvent("options", "analytics", "disable");
		
		tracker.setEnabled(enabled);
		configFile.save(tracker);
		
		if(enabled)
			trackEvent("options", "analytics", "enable");
	}
	
	public void trackPageView(String url, String title)
	{
		tracker.trackPageView(url, title, hostname);
	}
	
	public void trackPageViewFromReferrer(String url, String title,
		String referrerSite, String referrerPage)
	{
		tracker.trackPageViewFromReferrer(url, title, hostname, referrerSite,
			referrerPage);
	}
	
	public void trackPageViewFromSearch(String url, String title,
		String searchSource, String keywords)
	{
		tracker.trackPageViewFromSearch(url, title, hostname, searchSource,
			keywords);
	}
	
	public void trackEvent(String category, String action)
	{
		tracker.trackEvent(category, action);
	}
	
	public void trackEvent(String category, String action, String label)
	{
		tracker.trackEvent(category, action, label);
	}
	
	public void trackEvent(String category, String action, String label,
		Integer value)
	{
		tracker.trackEvent(category, action, label, value);
	}
	
	public void makeCustomRequest(AnalyticsRequestData data)
	{
		tracker.makeCustomRequest(data);
	}
}
