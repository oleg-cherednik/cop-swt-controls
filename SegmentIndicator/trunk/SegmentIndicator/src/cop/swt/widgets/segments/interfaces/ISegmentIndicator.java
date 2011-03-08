package cop.swt.widgets.segments.interfaces;

import org.eclipse.swt.graphics.Color;

import cop.swt.widgets.interfaces.Clearable;

public interface ISegmentIndicator<T> extends IControl, Clearable, Scaleable
{
	void setValue(T value);

	T getValue();

	int getScale();

	void setScale(int scale);

	void setTransparent(boolean enabled);

	boolean isTransparent();

	void setOffColor(Color color);

	Color getOffColor();
}
