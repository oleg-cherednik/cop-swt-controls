package cop.swt.widgets.viewers.table;

import java.util.Locale;

import cop.swt.widgets.viewers.AbstractViewerConfig;
import cop.swt.widgets.viewers.table.columns.ColumnContext;
import cop.swt.widgets.viewers.table.interfaces.PTableColumnProvider;

//import cop.swt.tmp.CurrencyComboWrapper;

public class TableViewerConfig extends AbstractViewerConfig implements ColumnContext
{
	private PTableColumnProvider columnProvider;
	private Locale locale;

	public PTableColumnProvider getColumnProvider()
	{
		return columnProvider;
	}

	public void setColumnProvider(PTableColumnProvider columnProvider)
	{
		this.columnProvider = columnProvider;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}

	/*
	 * ColumnContext
	 */

	@Override
	public Locale getLocale()
	{
		return locale;
	}

	// private CurrencyComboWrapper systemCurrency;
	//
	// public void setSystemCurrency(CurrencyComboWrapper currency)
	// {
	// this.systemCurrency = currency;
	// }
	//
	// public CurrencyComboWrapper getSystemCurrency()
	// {
	// return systemCurrency;
	// }
}
