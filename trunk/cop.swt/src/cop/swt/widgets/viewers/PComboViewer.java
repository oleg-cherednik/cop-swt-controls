package cop.swt.widgets.viewers;

import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.NumericExtension.isInRangeMin;
import static cop.swt.widgets.annotations.services.LabelService.getItemName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

import cop.localization.interfaces.LocaleSupport;
import cop.swt.widgets.annotations.exceptions.WrongReturnValueException;
import cop.swt.widgets.interfaces.LabelSupport;
import cop.swt.widgets.interfaces.Refreshable;

public final class PComboViewer<T> implements LabelSupport, LocaleSupport, Refreshable
{
	private Combo combo;

	private java.util.List<T> list = new ArrayList<T>();
	/**
	 * Label name, that is used for <b>Label</b> annotation. If this label <u>not empty</u>, that only annotation with
	 * this name will be worked with.
	 */
	private String labelName = "";

	private boolean emptyValueEnabled;

	private Locale locale;

	private static final String EMPTY_ITEM = "";

	public PComboViewer(Composite parent, int style)
	{
		combo = new Combo(parent, style);
	}

	public void setEmptyValue(boolean enabled)
	{
		if(!(enabled ^ emptyValueEnabled))
			return;

		emptyValueEnabled = enabled;

		if(combo.getItemCount() == 0)
			return;

		if(isFirstItemEmpty())
		{
			if(!enabled)
				combo.remove(0);
		}
		else
		{
			if(enabled)
				combo.add(EMPTY_ITEM, 0);
		}
	}

	public ISelection getISelection()
	{
		final T item = getSelectionItem();

		IStructuredSelection sel = new IStructuredSelection()
		{
			private List<T> list = new ArrayList<T>();

			{
				list.add(item);
			}

			@Override
			public boolean isEmpty()
			{
				int a = 0;
				a++;
				return false;
			}

			@Override
			public List toList()
			{
				int a = 0;
				a++;
				return null;
			}

			@Override
			public Object[] toArray()
			{
				int a = 0;
				a++;
				return null;
			}

			@Override
			public int size()
			{
				int a = 0;
				a++;
				return 0;
			}

			@Override
			public Iterator iterator()
			{
				return list.iterator();
			}

			@Override
			public Object getFirstElement()
			{
				int a = 0;
				a++;
				return null;
			}
		};

		return sel;
	}

	public void setSelection(T item)
	{
		String str = "" + item;

		for(int i = 0, size = list.size(); i < size; i++)
			if(list.get(i).toString().equals(str))
				select(i);
	}

	public boolean isEmptyValueEnabled()
	{
		return emptyValueEnabled;
	}

	private boolean isFirstItemEmpty()
	{
		return combo.getItemCount() != 0 && combo.getItem(0).trim().equals(EMPTY_ITEM);
	}

	private int getSuperIndex(int index)
	{
		return emptyValueEnabled ? (index - 1) : index;
	}

	public T getSelectionItem()
	{
		int index = getSelectionIndex();

		return (index < 0) ? null : list.get(index);
	}

	/**
	 * Add new not null item to combo
	 * 
	 * @param item new item
	 * @see Combo#add(String)
	 */

	public void addItem(T item)
	{
		if(isNull(item))
			return;

		try
		{
			combo.add(getItemName(item, labelName, locale));
			list.add(item);

			if(list.size() == 1)
				combo.select(0);
		}
		catch(WrongReturnValueException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add new item to combo at given position
	 * 
	 * @param item new item
	 * @param index item position
	 * @see Combo#add(String, int)
	 */

	public void addItem(T item, int index)
	{
		try
		{
			combo.add(getItemName(item, labelName), getSuperIndex(index));
			list.add(index, item);
		}
		catch(WrongReturnValueException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Remove item from combo at given position
	 * 
	 * @param index item position
	 * @see Combo#remove(int)
	 */
	public void removeItem(int index)
	{
		remove(getSuperIndex(index));
		list.remove(index);
	}

	/**
	 * Remove given item from combo
	 * 
	 * @param item given item
	 * @see Combo#remove(int)
	 */

	public void removeItem(T item)
	{
		if(isNull(item))
			return;

		try
		{
			combo.remove(getItemName(item, labelName));
			list.remove(item);
		}
		catch(WrongReturnValueException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Returns all items are stored in current combo
	 * 
	 * @return array of all stored items
	 * @see java.util.List#toArray()
	 */
	@SuppressWarnings("unchecked")
	public T[] getAllItems()
	{
		return (T[])list.toArray();
	}

	/**
	 * Returns item at the giving position
	 * 
	 * @param index item position
	 * @return item at the giving position
	 * @see Combo#getItem(int)
	 */
	public T get(int index)
	{
		if(!isInRangeMin(index, 0, list.size()))
			return null;

		return list.get(index);
	}

	/**
	 * Returns a zero-relative position of the giving item.
	 * 
	 * @param item giving item
	 * @return item's zero-relative position
	 * @see Combo#indexOf(String)
	 */
	public int indexOf(T item)
	{
		return indexOf(item, 0);
	}

	/**
	 * Returns a zero-relative position of the giving item starting from giving position.
	 * 
	 * @param item giving item
	 * @param start start position
	 * @return item's zero-relative position
	 * @see Combo#indexOf(String, int)
	 */
	public int indexOf(T item, int start)
	{
		try
		{
			int index = combo.indexOf(getItemName(item, labelName), start);

			return isEmptyValueEnabled() ? (index - 1) : index;
		}
		catch(WrongReturnValueException e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Fill current combo with new items from giving collection.<br>
	 * If giving container is empty or null, current combo will be clear.
	 * 
	 * @param items new item collection
	 * @see Collection
	 */
	public void setItems(Collection<T> items)
	{
		list.clear();
		combo.removeAll();

		if(isEmpty(items))
			return;

		for(T item : items)
			addItem(item);

		select(0);
	}

	/**
	 * Fill current combo with new items from giving array.<br>
	 * If giving array is empty or null, current combo will be clear.
	 * 
	 * @param items new item collection
	 */
	public void setItems(T[] items)
	{
		list.clear();
		combo.removeAll();

		if(isEmpty(items))
			return;

		for(T item : items)
			addItem(item);

		select(0);
	}

	/**
	 * Set given item to given position. After operation combo size will not change.
	 * 
	 * @param item new item
	 * @param index new zero-relative position
	 * @see Combo#setItem(int, String)
	 */
	public void setItem(T item, int index)
	{
		try
		{
			combo.add(getItemName(item, labelName), index);
			combo.remove(index + 1);

			list.set(index, item);
		}
		catch(WrongReturnValueException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Combo
	 */

	public void setVisibleItemCount(int count)
	{
		combo.setVisibleItemCount(count);
	}

	public int getItemCount1()
	{
		return combo.getItemCount();
	}

	public void addSelectionListener(SelectionListener listener)
	{
		combo.addSelectionListener(listener);
	}

	public void removeSelectionListener(SelectionListener listener)
	{
		combo.removeSelectionListener(listener);
	}

	public void select(int index)
	{
		if(!emptyValueEnabled && index < 0)
			return;

		combo.select(emptyValueEnabled ? (index + 1) : index);
	}

	public int getSelectionIndex()
	{
		return getSuperIndex(combo.getSelectionIndex());
	}

	/**
	 * @see Combo#remove(int, int)
	 */

	public void remove(int start, int end)
	{
		if(start > end)
			return;

		combo.remove(start, end);
		list.removeAll(list.subList(start, end));

		if(combo.getItemCount() == 1 && isFirstItemEmpty())
			combo.removeAll();
	}

	/**
	 * @see Combo#remove(int)
	 */

	public void remove(int index)
	{
		list.remove(index);
		combo.remove(getSuperIndex(index));

		if(combo.getItemCount() == 1 && isFirstItemEmpty())
			combo.removeAll();
	}

	/**
	 * @see Combo#removeAll()
	 */

	public void removeAll()
	{
		combo.removeAll();
		list.clear();
	}

	public int getItemCount()
	{
		return list.size();
	}

	/*
	 * LabelSupport
	 */

	/**
	 * @see LabelSupport#getLabelName()
	 */
	@Override
	public String getLabelName()
	{
		return labelName;
	}

	/**
	 * @see LabelSupport#setLabelName(String)
	 */
	@Override
	public void setLabelName(String labelName)
	{
		if(isNull(labelName))
		{
			if(!this.labelName.isEmpty())
				this.labelName = labelName;
		}
		else
		{
			if(!this.labelName.isEmpty())
				this.labelName = "";
		}
	}

	/*
	 * Composite
	 */

	public void setLayout(Layout layout)
	{
		combo.setLayout(layout);
	}

	/*
	 * Control
	 */

	public void setEnabled(boolean enabled)
	{
		combo.setEnabled(enabled);
	}

	public boolean getEnabled()
	{
		return combo.getEnabled();
	}

	public void setLayoutData(Object layoutData)
	{
		combo.setLayoutData(layoutData);
	}

	/*
	 * LocaleSupport
	 */

	@Override
	public void setLocale(Locale locale)
	{
		this.locale = locale;
		refresh();
	}

	/*
	 * Refreshable
	 */

	@Override
	public void refresh()
	{
		if(isEmpty(list))
			return;

		int index = getSelectionIndex();

		combo.removeAll();

		for(T item : list)
		{
			try
			{
				combo.add(getItemName(item, labelName, locale));
			}
			catch(WrongReturnValueException e)
			{
				e.printStackTrace();
			}
		}

		select(index);
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return list.toString();
	}
}
