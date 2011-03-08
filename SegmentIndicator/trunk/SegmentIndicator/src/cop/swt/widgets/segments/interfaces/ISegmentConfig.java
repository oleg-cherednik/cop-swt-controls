package cop.swt.widgets.segments.interfaces;

import cop.swt.widgets.segments.tmp.SignPositionEnum;
import cop.swt.widgets.segments.tmp.SignTypeEnum;

public interface ISegmentConfig
{
	SignTypeEnum getSignType();

	SignPositionEnum getSignPosition();

	int getOrientation();

	int getTotalSegments();

	boolean isLeadingZero();
}
