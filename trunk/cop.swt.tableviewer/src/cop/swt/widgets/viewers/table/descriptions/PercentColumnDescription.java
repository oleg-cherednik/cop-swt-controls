package cop.swt.widgets.viewers.table.descriptions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.ReflectionExtension.getNumberValue;
import static cop.swt.extensions.ColorExtension.RED;
import static cop.swt.extensions.ColorExtension.YELLOW;
import static org.eclipse.swt.SWT.PaintItem;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

import cop.swt.widgets.annotations.contents.RangeContent;
import cop.swt.widgets.annotations.services.PercentService;
import cop.swt.widgets.viewers.table.celleditors.SpinnerCellEditor;

public class PercentColumnDescription<T> extends NumericColumnDescription<T>
{
	private NumberFormat percentFormat;
	private final RangeContent range;

	/**
	 * Closed constructor
	 * 
	 * @param obj
	 * @param locale
	 */
	protected PercentColumnDescription(AccessibleObject obj, Locale locale)
	{
		super(obj, locale);

		this.percentFormat = configNumberFormat(NumberFormat.getPercentInstance(locale));
		this.range = PercentService.getContent(obj, percentFormat.getMaximumFractionDigits());
	}

	@SuppressWarnings("unchecked")
	private void drawProgressBar(Event event, TableViewerColumn columnViewer)
	{
		try
		{
			double percent = ((Number)invoke((T)event.item.getData())).doubleValue() * 100;
			drawProgressBar(event.gc, event.x, event.y, columnViewer.getColumn().getWidth(), event.height, percent);
		}
		catch(Exception e)
		{}
	}

	private static void drawProgressBar(GC gc, int x, int y, int width, int height, double value)
	{
		Color foreground = gc.getForeground();
		Color background = gc.getBackground();

		gc.setForeground(RED);
		gc.setBackground(YELLOW);

		int len = 0;

		if(value <= 0)
			len = 0;
		else if(value >= 100)
			len = width;
		else
			len = (int)((width * value) / 100);

		gc.fillGradientRectangle(x, y, len, height, true);
		// gc.fillRectangle(event.x, event.y, len, event.height);
		// gc.fillRoundRectangle(event.x, event.y, len, event.height, 10, 10);
		// gc.drawRectangle(event.x, event.y, width - 1, event.height - 1);
		gc.setForeground(foreground);
		gc.setBackground(background);
	}

	/*
	 * NumericColumnDescription
	 */

	@Override
	protected NumberFormat getNumberFormat(Locale locale)
	{
		return configNumberFormat(NumberFormat.getNumberInstance(locale));
	}

	@Override
	protected Number parseNumber(String value) throws ParseException
	{
		return super.parseNumber(value).doubleValue() / 100;
	}

	/*
	 * ColumnDescription
	 */

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		invoke(item, getNumberValue(type, ((Number)value).doubleValue() / 100));
	}

	@Override
	protected String getText(Object obj)
	{
		if(obj instanceof Number)
			return super.getText(((Number)obj).doubleValue() * 100);

		return isEmptyable() ? "" : super.getText(0);
	}

	@Override
	protected String getCellText(Object obj)
	{
		return (obj instanceof Number) ? percentFormat.format(((Number)obj).doubleValue()) : "";
	}

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		NumberFormat nf = configNumberFormat(NumberFormat.getNumberInstance(locale));
		return new SpinnerCellEditor(parent, nf, range, SWT.NONE);
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(isNotNull(locale))
			percentFormat = configNumberFormat(NumberFormat.getPercentInstance(locale));
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

	/*
	 * static
	 */

	private static NumberFormat configNumberFormat(NumberFormat numberFormat)
	{
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);

		return numberFormat;
	}
}
