package cop.swt.widgets.viewers.interfaces;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Listener;

import cop.i18.LocaleSupport;
import cop.swt.widgets.interfaces.Clearable;
import cop.swt.widgets.interfaces.Editable;
import cop.swt.widgets.interfaces.Enablable;
import cop.swt.widgets.interfaces.Refreshable;
import cop.swt.widgets.model.interfaces.ModelChangedListener;
import cop.swt.widgets.viewers.model.interfaces.ModelSupport;

public interface IViewer<T> extends ModelSupport<T>, LocaleSupport, ModifyListenerSupport<T>, SelectionListenerSupport,
                Clearable, Refreshable, Listener, ModelChangedListener<T>, Editable, Enablable
{
	StructuredViewer getWidget();
}
