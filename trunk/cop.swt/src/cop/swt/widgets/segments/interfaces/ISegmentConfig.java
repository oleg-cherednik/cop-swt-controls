package cop.swt.widgets.segments.interfaces;

import cop.swt.widgets.interfaces.WidgetConfig;
import cop.swt.widgets.segments.tmp.SignPositionEnum;
import cop.swt.widgets.segments.tmp.SignTypeEnum;

public interface ISegmentConfig extends WidgetConfig
{
	SignTypeEnum getSignType();

	SignPositionEnum getSignPosition();

	int getOrientation();

	int getTotalSegments();
	
	boolean isLeadingZero();
}
