package cop.swt.widgets.segments.interfaces;

import org.eclipse.swt.graphics.Rectangle;

import cop.swt.widgets.interfaces.IShape;

public interface ISegment extends IShape
{
	int DEF_SCALE = 1;
	int BASE_LENGTH = 4;

	void setPosition(int x, int y);

	void setBounds(int x, int y, int scale);

	Rectangle getBounds();
}
