package cop.swt.widgets.viewers.table;

import static cop.swt.widgets.viewers.model.enums.ModificationTypeEnum.UPDATE;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;

import cop.swt.widgets.interfaces.Editable;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.viewers.interfaces.ItemModifyListener;
import cop.swt.widgets.viewers.interfaces.ModifyListenerSupport;
import cop.swt.widgets.viewers.interfaces.PModifyProvider;
import cop.swt.widgets.viewers.table.descriptions.ColumnDescription;

public class PCellEditor<T> extends EditingSupport implements LocaleSupport, ModifyListenerSupport<T>, Editable
{
	private final PTableViewer<T> tableViewer;
	public CellEditor editor;
	private ColumnDescription<T> description;
	private boolean editable;
	private boolean enabled;
	private PModifyProvider<T> modifyProvider;
	private final Set<ItemModifyListener<T>> modifyListeners = new HashSet<ItemModifyListener<T>>();

	public PCellEditor(PTableViewer<T> tableViewer, ColumnDescription<T> description)
	{
		this(tableViewer, description, null);
	}

	public PCellEditor(PTableViewer<T> tableViewer, ColumnDescription<T> description, PModifyProvider<T> modifyProvider)
	{
		super(tableViewer.getWidget());

		this.tableViewer = tableViewer;
		this.description = description;
		this.modifyProvider = modifyProvider;
	}

	public void setModifyProvider(PModifyProvider<T> provider)
	{
		this.modifyProvider = provider;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
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

		if(!editable || !enabled || description.isReadonly())
			return false;

		Table table = ((TableViewer)getViewer()).getTable();
		T item = (T)element;
		String key = description.getKey();

		if((modifyProvider != null) && !modifyProvider.canModify(table, item, key))
			return false;

		return !description.isReadonly();
	}

	@Override
	protected CellEditor getCellEditor(Object element)
	{
		System.out.println("ColumnEditor.getCellEditor()");

		if(editor == null)
			editor = description.getCellEditor(((TableViewer)getViewer()).getTable());

		return editor;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Object getValue(Object element)
	{
		System.out.println("ColumnEditor.getValue()");

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
		System.out.println("ColumnEditor.setValue()");

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
	 * Localizable
	 */

	@Override
	public void setLocale(Locale locale)
	{
		// editor = description.getCellEditor(((TableViewer)getViewer()).getTable());
	}
}
