package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.ReflectionExtension.getNumberValue;
import static java.text.NumberFormat.getPercentInstance;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TableItem;

import cop.swt.extensions.ColorExtension;
import cop.swt.widgets.tmp.ActionTO;

public class PercentColumnDescription<T> extends NumericColumnDescription<T>
{
	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected PercentColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);
	}

	private void drawProgressBar(Event event, TableViewerColumn columnViewer)
	{
		ActionTO data = (ActionTO)((TableItem)event.item).getData();
		double percent = data.getPercent() * 100;

		Color foreground = event.gc.getForeground();
		Color background = event.gc.getBackground();

		event.gc.setForeground(ColorExtension.RED);
		event.gc.setBackground(ColorExtension.YELLOW);

		int width = columnViewer.getColumn().getWidth();
		int len = (int)((width * percent) / 100);

		event.gc.fillGradientRectangle(event.x, event.y, len, event.height, true);
		// event.gc.fillRectangle(event.x, event.y, len, event.height);
		// event.gc.fillRoundRectangle(event.x, event.y, len, event.height, 10, 10);
		// event.gc.drawRectangle(event.x, event.y, width - 1, event.height - 1);
		event.gc.setForeground(background);
		event.gc.setBackground(foreground);
	}

	@Override
	protected NumberFormat getNumberFormat(Locale locale)
	{
		NumberFormat numberFormat = getPercentInstance(locale);

		numberFormat.setMinimumFractionDigits(2);

		return numberFormat;
	}

	/*
	 * NumericColumnDescription
	 */

	@Override
	protected Object parseNumber(String value) throws Exception
	{
		Assert.isNotNull(value);

		try
		{
			return getNumberValue(type, numberFormat.parse(value));
		}
		catch(ParseException e)
		{}

		Number obj = (Number)super.parseNumber(value);

		return obj.doubleValue() / 100;
	}

	/*
	 * AbstractColumnDescription
	 */

	@Override
	protected String getText(Object obj)
	{
		return isNotNull(obj) ? numberFormat.format(obj) : "";
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(isNotNull(locale))
			numberFormat = getNumberFormat(locale);
	}

	/*
	 * ColumnDescription
	 */

	@Override
	public void handleEvent(Event event, TableViewer tableViewer, TableViewerColumn columnViewer)
	{
		if(event.type == SWT.PaintItem)
			drawProgressBar(event, columnViewer);
	}
}
