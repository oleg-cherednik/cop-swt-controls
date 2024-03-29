package cop.swt.widgets.viewers.table;

import static cop.swt.widgets.viewers.model.enums.ModificationTypeEnum.UPDATE;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;

import cop.swt.widgets.interfaces.Editable;
import cop.swt.widgets.interfaces.Enablable;
import cop.swt.widgets.viewers.interfaces.ItemModifyListener;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.interfaces.PModifyProvider;
import cop.swt.widgets.viewers.table.columns.settings.ColumnSettings;

public class PCellEditor<T> extends EditingSupport implements ModifyListenerSupport<T>, Editable, Enablable
{
	private final PTableViewer<T> tableViewer;
	private final Set<ItemModifyListener<T>> modifyListeners = new HashSet<ItemModifyListener<T>>();
	private final ColumnSettings<T> settings;

	private boolean editable;
	private boolean enabled;
	private PModifyProvider<T> modifyProvider;

	public PCellEditor(PTableViewer<T> tableViewer, ColumnSettings<T> settings)
	{
		super(tableViewer.getWidget());

		this.tableViewer = tableViewer;
		this.settings = settings;
	}

	public void setModifyProvider(PModifyProvider<T> provider)
	{
		this.modifyProvider = provider;
	}

	/*
	 * ModifyListenerSupport
	 */

	@Override
	public void addModifyListener(ItemModifyListener<T> listener)
	{
		modifyListeners.add(listener);
	}

	@Override
	public void removeModifyListener(ItemModifyListener<T> listener)
	{
		modifyListeners.remove(listener);
	}

	private void notifyModifyListeners(T item)
	{
		for(ItemModifyListener<T> listener : modifyListeners)
			listener.itemModified(tableViewer, item, UPDATE);
	}

	/*
	 * EditingSupport
	 */

	@Override
	@SuppressWarnings("unchecked")
	protected boolean canEdit(Object element)
	{
		System.out.println("ColumnEditor.canEdit()");

		if(!editable || !enabled || settings.isReadonly())
			return false;

		Table table = ((TableViewer)getViewer()).getTable();
		T item = (T)element;
		String key = settings.getKey();

		if((modifyProvider != null) && !modifyProvider.canModify(table, item, key))
			return false;

		return !settings.isReadonly();
	}

	@Override
	protected CellEditor getCellEditor(Object element)
	{
		return settings.getCellEditor(((TableViewer)getViewer()).getTable());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Object getValue(Object element)
	{
		System.out.println("ColumnEditor.getValue()");

		try
		{
			return settings.getCellEditorValue((T)element);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setValue(Object element, Object value)
	{
		System.out.println("ColumnEditor.setValue()");

		try
		{
			settings.setValue((T)element, value);
			getViewer().update(element, null);
			notifyModifyListeners((T)element);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Editable
	 */

	@Override
	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	@Override
	public boolean isEditable()
	{
		return editable;
	}

	/*
	 * Enablable
	 */

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled()
	{
		return enabled;
	}
}
