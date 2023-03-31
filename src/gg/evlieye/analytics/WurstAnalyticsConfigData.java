/*

 *



 */
package gg.evlieye.analytics;

import gg.evlieye.analytics.dmurph.AnalyticsConfigData;
import gg.evlieye.analytics.dmurph.VisitorData;

public final class EvlieyeAnalyticsConfigData extends AnalyticsConfigData
{
	public EvlieyeAnalyticsConfigData(String argTrackingCode)
	{
		super(argTrackingCode, VisitorData.newVisitor());
	}
}
