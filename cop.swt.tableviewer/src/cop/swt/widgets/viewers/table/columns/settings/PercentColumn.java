/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.common.extensions.ReflectionExtension.getNumberValue;
import static cop.swt.extensions.ColorExtension.RED;
import static cop.swt.extensions.ColorExtension.YELLOW;

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
import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class PercentColumn<T> extends NumericColumn<T>
{
	private NumberFormat percentFormat;
	private final RangeContent range;

	protected PercentColumn(AccessibleObject obj, ColumnContext context)
	{
		super(obj, context);

		this.percentFormat = configNumberFormat(NumberFormat.getPercentInstance(locale));
		this.range = PercentService.getContent(obj, percentFormat.getMaximumFractionDigits());
	}

	@SuppressWarnings("unchecked")
	private void drawProgressBar(Event event, TableViewerColumn columnViewer)
	{
		try
		{
			double percent = ((Number)invoke((T)event.item.getData())).doubleValue() * 100;
			drawProgressBar(event.gc, event.x, event.y, columnViewer.getColumn().getWidth() - 2, event.height - 1,
			                percent);
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
		// gc.fillRectangle(x, y, len, height);
		// gc.fillRoundRectangle(x, y, len, height, 10, 10);
		// gc.drawRectangle(x, y, width, height);
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
	public Number getCellEditorValue(T item) throws Exception
	{
		return super.getCellEditorValue(item).doubleValue() * 100;
	}

	@Override
	protected String getCellText(Object obj)
	{
		return (obj instanceof Number) ? percentFormat.format(((Number)obj).doubleValue()) : "";
	}

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		if(editor == null)
		{
			NumberFormat nf = configNumberFormat(NumberFormat.getNumberInstance(locale));
			editor = new SpinnerCellEditor(parent, nf, range, SWT.NONE);
		}

		return editor;
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		if(locale != null)
			percentFormat = configNumberFormat(NumberFormat.getPercentInstance(locale));
	}

	/*
	 * ColumnDescription
	 */

	@Override
	public void handleEvent(Event event, TableViewer tableViewer, TableViewerColumn columnViewer)
	{
		super.handleEvent(event, tableViewer, columnViewer);

		if(event.type == SWT.PaintItem)
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
