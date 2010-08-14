package cop.swt.widgets.viewers.table;

import cop.swt.widgets.tmp.CurrencyComboWrapper;

public class TableViewerConfig extends AbstractViewerConfig
{
	private CurrencyComboWrapper systemCurrency;

	public void setSystemCurrency(CurrencyComboWrapper currency)
	{
		this.systemCurrency = currency;
	}

	public CurrencyComboWrapper getSystemCurrency()
	{
		return systemCurrency;
	}
}
