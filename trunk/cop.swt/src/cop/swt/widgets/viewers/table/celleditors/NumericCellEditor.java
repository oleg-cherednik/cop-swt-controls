package cop.swt.widgets.viewers.table.celleditors;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.listeners.TraverseListenerSet.allowEscape;
import static cop.swt.widgets.listeners.TraverseListenerSet.allowReturn;
import static java.text.DateFormat.SHORT;

import java.text.DateFormat;
import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.numeric.DoubleNumeric;
import cop.swt.widgets.numeric.IntegerNumeric;

public class NumericCellEditor extends CellEditor implements LocaleSupport
{
	private DoubleNumeric num;
	private Locale locale;

	public static DateFormat getDateFormat()
	{
		return getDateFormat(Locale.getDefault());
	}

	public static DateFormat getDateFormat(Locale locale)
	{
		return DateFormat.getDateTimeInstance(SHORT, SHORT, locale);
	}

	public NumericCellEditor(Composite parent, int style, Locale locale)
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
		num = new DoubleNumeric(parent, SWT.NONE);

		num.addTraverseListener(allowEscape);
		num.addTraverseListener(allowReturn);
		//num.addKeyListener(keyReleaseOccured);

		return num;
	}

	@Override
	protected Object doGetValue()
	{
		return num.getText();
	}

	@Override
	protected void doSetFocus()
	{
		num.setFocus();
	}

	@Override
	protected void doSetValue(Object value)
	{
		num.setText("" + value);
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
		//this.num.setLocale(locale);
	}

	/*
	 * Listener
	 */

	private KeyListener keyReleaseOccured = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			keyReleaseOccured(e);
		}
	};
}
