/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.extensions.ReflectionExt.getNumberValue;
import static cop.swt.extensions.ColorExtension.RED;
import static cop.swt.extensions.ColorExtension.YELLOW;

import java.lang.reflect.AccessibleObject;
import java.text.NumberFormat;
import java.text.ParseException;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;

import cop.common.RangeContent;
import cop.swt.widgets.annotations.services.PercentService;
import cop.swt.widgets.viewers.table.columns.ColumnContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class PercentColumn<T> extends NumericColumn<T>
{
	protected PercentColumn(AccessibleObject obj, ColumnContext context)
	{
		super(obj, context);
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
	 * NumericColumn
	 */

	@Override
	protected NumberFormat getNumberFormat()
	{
		NumberFormat numberFormat = NumberFormat.getPercentInstance(locale);

		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);

		return numberFormat;
	}

	@Override
	protected RangeContent getRange()
	{
		return PercentService.getContent(obj, numberFormat.getMaximumFractionDigits());
	}

	@Override
    protected Number parseNumber(String value) throws ParseException
	{
		return super.parseNumber(value).doubleValue() / 100;
	}

	/*
	 * ColumnSettings
	 */

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		invoke(item, getNumberValue(type, ((Number)value).doubleValue() / 100));
	}

	@Override
	protected String getText(Object obj)
	{
		String str;

		if(obj instanceof Number)
			str = super.getText(((Number)obj).doubleValue() * 100);
		else
			str = isEmptyable() ? "" : super.getText(0);

		return str;
	}

	@Override
	public Number getCellEditorValue(T item) throws Exception
	{
		return super.getCellEditorValue(item).doubleValue() * 100;
	}

	@Override
	public void handleEvent(Event event, TableViewer tableViewer, TableViewerColumn columnViewer)
	{
		super.handleEvent(event, tableViewer, columnViewer);

		if(event.type == SWT.PaintItem)
			drawProgressBar(event, columnViewer);
	}
}
