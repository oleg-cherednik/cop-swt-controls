package cop.swt.widgets.viewers.list.descriptions;

import cop.swt.widgets.interfaces.LabelSupport;
import cop.swt.widgets.localization.interfaces.LocaleSupport;

public interface ILabelDescription<T> extends LocaleSupport, LabelSupport
{
	String getTextValue(T item) throws Exception;
}
