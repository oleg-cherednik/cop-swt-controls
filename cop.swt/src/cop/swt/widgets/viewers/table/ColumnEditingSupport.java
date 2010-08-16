package cop.swt.widgets.viewers.table;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.widgets.viewers.model.enums.ModificationTypeEnum.UPDATE;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;

import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.viewers.interfaces.IModifyListener;
import cop.swt.widgets.viewers.interfaces.IModifyProvider;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.table.descriptions.IColumnDescription;

public class ColumnEditingSupport<T> extends EditingSupport implements LocaleSupport, ModifyListenerSupport<T>
{
	public CellEditor editor;
	private IColumnDescription<T> description;
	private boolean readonly;
	private boolean enabled;
	private IModifyProvider<T> modifyProvider;
	private Set<IModifyListener<T>> modifyListeners = new HashSet<IModifyListener<T>>();

	public ColumnEditingSupport(TableViewer viewer, IColumnDescription<T> description)
	{
		this(viewer, description, null);
	}

	public ColumnEditingSupport(TableViewer viewer, IColumnDescription<T> description, IModifyProvider<T> modifyProvider)
	{
		super(viewer);

		this.description = description;
		this.modifyProvider = modifyProvider;

		editor = description.getCellEditor(viewer.getTable());
	}

	public void setReadonlyProvider(IModifyProvider<T> provider)
	{
		this.modifyProvider = provider;
	}

	public void setReadonly(boolean readonly)
	{
		this.readonly = readonly;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	/*
	 * ModifyListenerSupport
	 */

	@Override
	public void addModifyListener(IModifyListener<T> listener)
	{
		modifyListeners.add(listener);

	}

	@Override
	public void removeModifyListener(IModifyListener<T> listener)
	{
		modifyListeners.remove(listener);
	}

	private void notifyModifyListeners(T item)
	{
		Table table = ((TableViewer)getViewer()).getTable();

		for(IModifyListener<T> listener : modifyListeners)
			listener.itemModified(table, item, UPDATE);
	}

	/*
	 * EditingSupport
	 */

	@Override
	@SuppressWarnings("unchecked")
	protected boolean canEdit(Object element)
	{
		System.out.println("ColumnEditingSupport.canEdit()");
		
		if(readonly || !enabled || description.isReadonly())
			return false;

		TableViewer viewer = (TableViewer)getViewer();
		Table table = viewer.getTable();
		T item = (T)element;
		String key = description.getKey();

		if(isNotNull(modifyProvider) && !modifyProvider.canModify(table, item, key))
			return false;

		return !description.isReadonly();
	}

	@Override
	protected CellEditor getCellEditor(Object element)
	{
		System.out.println("ColumnEditingSupport.getCellEditor()");
		return editor;
	}

    @Override
    @SuppressWarnings("unchecked")
	protected Object getValue(Object element)
	{
		System.out.println("ColumnEditingSupport.getValue()");

		try
		{
			return description.getEditValue((T)element);
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
		System.out.println("ColumnEditingSupport.setValue()");

		try
		{
			description.setValue((T)element, value);
			getViewer().update(element, null);
			notifyModifyListeners((T)element);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		editor = description.getCellEditor(((TableViewer)getViewer()).getTable());
	}
}
