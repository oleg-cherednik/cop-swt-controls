package cop.swt.widgets.calendar.viewers.templates;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;

public final class CalendarConfigUnit
{
	private Object parent; // TemplateUnit or Control class
	private Color background;
	private Color foreground;
	private FontData fontData;

	public CalendarConfigUnit(Object parent)
	{
		this(null, null, null, parent);
	}

	public CalendarConfigUnit(Color background, Color foreground)
	{
		this(background, foreground, null, null);
	}

	public CalendarConfigUnit(Color background, Color foreground, Object parent)
	{
		this(background, foreground, null, parent);
	}

	public CalendarConfigUnit(Color background, Color foreground, FontData fontData)
	{
		this(background, foreground, fontData, null);
	}

	public CalendarConfigUnit(Color background, Color foreground, FontData fontData, Object parent)
	{
		this.parent = parent;
		this.background = background;
		this.foreground = foreground;
		this.fontData = fontData;

		if(isNull(parent))
			return;

		if(!(parent instanceof Control) && !(parent instanceof CalendarConfigUnit))
			throw new IllegalArgumentException("parent class must be instance of Control or TemplateUnit");

	}

	public Color getBackground()
	{
		if(isNotNull(background))
			return background;
		if(isNull(parent))
			return null;

		if(parent instanceof Control)
			return ((Control)parent).getBackground();

		return ((CalendarConfigUnit)parent).getBackground();
	}

	public Color getForeground()
	{
		if(isNotNull(foreground))
			return foreground;
		if(isNull(parent))
			return null;

		if(parent instanceof Control)
			return ((Control)parent).getForeground();

		return ((CalendarConfigUnit)parent).getForeground();
	}

	public FontData getFontData()
	{
		if(isNotNull(fontData))
			return fontData;
		if(isNull(parent))
			return null;

		if(parent instanceof Control)
			return ((Control)parent).getFont().getFontData()[0];

		return ((CalendarConfigUnit)parent).getFontData();
	}

	public void setBackground(Color background)
	{
		this.background = background;
	}

	public void setForeground(Color foreground)
	{
		this.foreground = foreground;
	}

	public void setFontData(FontData fontData)
	{
		this.fontData = fontData;
	}
}
