package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.extensions.ColorExtension.RED;
import static cop.swt.extensions.ColorExtension.YELLOW;
import static java.text.NumberFormat.getPercentInstance;
import static org.eclipse.swt.SWT.PaintItem;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;

//import cop.swt.tmp.ActionTO;

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
//		double percent = ((T)event.item.getData()).getPercent() * 100;
//		drawProgressBar(event.gc, event.x, event.y, columnViewer.getColumn().getWidth(), event.height, percent);
	}

	private static void drawProgressBar(GC gc, int x, int y, int width, int height, double value)
	{
		Color foreground = gc.getForeground();
		Color background = gc.getBackground();

		gc.setForeground(RED);
		gc.setBackground(YELLOW);

		int len = (int)((width * value) / 100);

		gc.fillGradientRectangle(x, y, len, height, true);
		// gc.fillRectangle(event.x, event.y, len, event.height);
		// gc.fillRoundRectangle(event.x, event.y, len, event.height, 10, 10);
		// gc.drawRectangle(event.x, event.y, width - 1, event.height - 1);
		gc.setForeground(background);
		gc.setBackground(foreground);
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
	protected Number parseNumber(String value) throws ParseException
	{
		Number obj = numberFormat.parse(value.endsWith("%") ? value : (value + "%"));

		return obj.doubleValue();
	}

	/*
	 * ColumnDescription
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
		super.handleEvent(event, tableViewer, columnViewer);

		if(event.type == PaintItem)
			drawProgressBar(event, columnViewer);
	}
}
