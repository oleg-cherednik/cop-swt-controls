/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
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
import org.eclipse.swt.widgets.Spinner;

import cop.swt.widgets.annotations.contents.RangeContent;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 20.12.2010
 */
public class SpinnerCellEditor extends CellEditor
{
	private final NumberFormat nf;
	private final int multiplier;

	public SpinnerCellEditor(Composite parent, NumberFormat nf, int style)
	{
		this(parent, nf, null, style);
	}

	public SpinnerCellEditor(Composite parent, NumberFormat nf, RangeContent range, int style)
	{
		super(parent, style);

		postConstruct(nf, range);

		this.nf = nf;
		this.multiplier = (int)Math.pow(10, getControl().getDigits());
	}

	private void postConstruct(NumberFormat nf, RangeContent range)
	{
		Spinner spinner = getControl();

		spinner.setDigits(nf.getMaximumFractionDigits());

		if(range != null)
		{
			spinner.setMinimum(range.getMinimum());
			spinner.setMaximum(range.getMaximum());
			spinner.setIncrement(range.getIncrement());
		}
	}

	/*
	 * CellEditor
	 */

	@Override
	protected Spinner createControl(Composite parent)
	{
		Spinner spinner = new Spinner(parent, SWT.NONE);

		spinner.addTraverseListener(allowEscape);
		spinner.addTraverseListener(allowReturn);

		return spinner;
	}

	@Override
	public Spinner getControl()
	{
		return (Spinner)super.getControl();
	}

	@Override
	protected Object doGetValue()
	{
		return (double)((Spinner)getControl()).getSelection() / multiplier;
	}

	@Override
	protected void doSetFocus()
	{
		getControl().setFocus();
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
