package cop.swt.widgets.segments.interfaces;

import org.eclipse.swt.graphics.Color;

public interface IControl<T>
{
	boolean isVisible();

	void setVisible(boolean visible);

	void setValue(T value);

	T getValue();

	void dispose();

	void setTransparent(boolean enabled);

	void setOffColor(Color color);

	Color getOffColor();

	void setOnColor(Color color);

	Color getOnColor();
	// void setBackgroundColor(Color color);
}
