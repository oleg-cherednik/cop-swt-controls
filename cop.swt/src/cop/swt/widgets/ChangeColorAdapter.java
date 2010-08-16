/**
 * $Id: ChangeColorAdapter.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/widgets/ChangeColorAdapter.java $
 */
package cop.swt.widgets;

import static cop.swt.extensions.ColorExtension.getColor;
import static cop.common.extensions.CommonExtension.getValue;

import org.eclipse.swt.graphics.Color;

import cop.swt.widgets.interfaces.IColorChangeable;

public abstract class ChangeColorAdapter implements IColorChangeable
{
	private final Color backgroundColor;
	private final Color foregroundColor;

	protected ChangeColorAdapter()
	{
		this.backgroundColor = null;
		this.foregroundColor = null;
	}

	protected ChangeColorAdapter(Integer backgroundColor)
	{
		this(backgroundColor, null);
	}

	protected ChangeColorAdapter(Integer backgroundColor, Integer foregroundColor)
	{
		this.backgroundColor = (backgroundColor != null) ? getColor(backgroundColor) : null;
		this.foregroundColor = (foregroundColor != null) ? getColor(foregroundColor) : null;
	}

	protected ChangeColorAdapter(Color backgroundColor)
	{
		this(backgroundColor, null);
	}

	protected ChangeColorAdapter(Color backgroundColor, Color foregroundColor)
	{
		this.backgroundColor = getValue(backgroundColor, null);
		this.foregroundColor = getValue(foregroundColor, null);
	}

	abstract protected boolean check(String str);

	@Override
	public final Color getBackgroundColor(String str)
	{
		return (backgroundColor != null && check(str)) ? backgroundColor : null;
	}

	@Override
	public final Color getForegroundColor(String str)
	{
		return (foregroundColor != null && check(str)) ? foregroundColor : null;
	}
}
