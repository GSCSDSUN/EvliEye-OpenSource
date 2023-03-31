/*

 *



 */
package gg.evlieye.analytics;

import gg.evlieye.analytics.dmurph.JGoogleAnalyticsTracker;

public final class EvlieyeAnalyticsTracker extends JGoogleAnalyticsTracker
{
	public EvlieyeAnalyticsTracker(String trackingID)
	{
		super(new EvlieyeAnalyticsConfigData(trackingID),
			GoogleAnalyticsVersion.V_4_7_2);
	}
}
