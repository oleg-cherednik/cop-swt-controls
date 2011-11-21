/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.celleditors;

import static cop.swt.widgets.listeners.TraverseListenerSet.allowEscape;
import static cop.swt.widgets.listeners.TraverseListenerSet.allowReturn;

import java.text.NumberFormat;
import java.text.ParseException;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Spinner;

import cop.swt.widgets.annotations.contents.RangeContent;

public class SpinnerCellEditor extends CellEditor
{
	private final NumberFormat nf;
	private final int multiplier;

	public SpinnerCellEditor(Composite parent, NumberFormat nf, RangeContent range, int style)
	{
		super(parent, style);

		Spinner spinner = (Spinner)getControl();

		spinner.setDigits(nf.getMaximumFractionDigits());
		spinner.setMinimum(range.getMinimum());
		spinner.setMaximum(range.getMaximum());
		spinner.setIncrement(range.getIncrement());

		this.nf = nf;
		this.multiplier = (int)Math.pow(10, spinner.getDigits());
	}

	/*
	 * CellEditor
	 */

	@Override
	protected Control createControl(Composite parent)
	{
		Spinner spinner = new Spinner(parent, SWT.NONE);

		spinner.addTraverseListener(allowEscape);
		spinner.addTraverseListener(allowReturn);

		return spinner;
	}

	@Override
	protected Object doGetValue()
	{
		return (double)((Spinner)getControl()).getSelection() / multiplier;
	}

	@Override
	protected void doSetFocus()
	{
		((Spinner)getControl()).setFocus();
	}

	@Override
	protected void doSetValue(Object value)
	{
		try
		{
			double num = nf.parse((String)value).doubleValue();
			((Spinner)getControl()).setSelection((int)(num * multiplier));
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
	}
}
