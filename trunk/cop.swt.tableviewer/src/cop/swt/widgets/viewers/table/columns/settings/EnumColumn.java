/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.table.columns.settings;

import static cop.algorithms.search.LinearSearch.linearSearch;
import static cop.common.extensions.ArrayExtension.convertToStringArray;
import static cop.common.extensions.CompareExtension.compareNumbers;
import static cop.swt.widgets.annotations.services.i18nService.getTranslations;

import java.util.Locale;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.viewers.table.columns.ColumnSettingsContext;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 03.09.2010
 */
public class EnumColumn<T> extends AbstractColumnSettings<T>
{
	private final Object[] constatns;
	private String[] i18n;

	protected EnumColumn(ColumnSettingsContext context)
	{
		super(context);

		this.constatns = type.getEnumConstants();
		buildLocalizedConstants();
	}

	private void buildLocalizedConstants()
	{
		try
		{
			i18n = getTranslations(type, locale);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			i18n = convertToStringArray(constatns);
		}

		if(editor != null)
			editor.dispose();

		editor = null;
	}

	/*
	 * Comparator
	 */

	@Override
	public int compare(T item1, T item2)
	{
		try
		{
			return compareNumbers(Integer.TYPE, getValue(item1), getValue(item2));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * ColumnDescription
	 */

	@Override
	public CellEditor getCellEditor(Composite parent)
	{
		if(editor == null)
			editor = new ComboBoxCellEditor(parent, i18n, SWT.READ_ONLY);
		return editor;
	}

	@Override
	public Object getValue(T item) throws Exception
	{
		Object str = invoke(item);
		Object[] values = type.getEnumConstants();
		int pos = linearSearch(values, str);

		return (pos < 0) ? 0 : pos;
	}

	@Override
	public void setValue(T item, Object value) throws Exception
	{
		Object[] values = type.getEnumConstants();
		Integer index = (Integer)value;

		invoke(item, values[index]);
	}

	@Override
	protected String getText(Object obj)
	{
		if(obj == null)
			return "";

		int pos = linearSearch(constatns, obj);

		return (pos >= 0) ? i18n[pos] : obj.toString();
	}

	@Override
	protected void check()
	{
		if(!type.isEnum())
			throw new IllegalArgumentException("Given object is not Enum");
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);
		buildLocalizedConstants();
	}
}
