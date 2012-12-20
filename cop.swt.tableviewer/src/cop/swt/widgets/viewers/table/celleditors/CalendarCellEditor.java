package cop.swt.widgets.viewers.table.celleditors;

import static cop.extensions.CommonExt.isNotNull;
import static cop.extensions.CommonExt.isNull;
import static java.text.DateFormat.SHORT;
import static org.eclipse.swt.SWT.NONE;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import cop.i18n.LocaleSupport;

public class CalendarCellEditor extends CellEditor implements LocaleSupport
{
	private Text stamp;
	private Locale locale;

	public static DateFormat getDateFormat()
	{
		return getDateFormat(Locale.getDefault());
	}

	public static DateFormat getDateFormat(Locale locale)
	{
		return DateFormat.getDateTimeInstance(SHORT, SHORT, locale);
	}

	public CalendarCellEditor(Composite parent, int style, Locale locale)
	{
		super(parent, style);

		setLocale(isNotNull(locale) ? locale : Locale.getDefault());
	}

	/*
	 * CellEditor
	 */

	@Override
	protected Control createControl(Composite parent)
	{
		stamp = new Text(parent, NONE); 

		//stamp.addTraverseListener(allowEscape);
		//stamp.addTraverseListener(allowReturn);
		//stamp.addKeyListener(keyReleaseOccured);

		return stamp;
	}

	@Override
	protected Object doGetValue()
	{
//		Date date = stamp.getDate();
//
//		if(isNull(date))
//			return null;

		Calendar day = Calendar.getInstance(locale);

//		day.setTime(date);

		return day;
	}

	@Override
	protected void doSetFocus()
	{}

	@Override
	protected void doSetValue(Object value)
	{
		//stamp.setDate(((Calendar)value).getTime());
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		if(isNull(locale))
			return;

		this.locale = locale;
		//this.stamp.setLocale(locale);
	}

	/*
	 * Listener
	 */

//	private KeyListener keyReleaseOccured = new KeyAdapter()
//	{
//		@Override
//		public void keyPressed(KeyEvent e)
//		{
//			keyReleaseOccured(e);
//		}
//	};
}
