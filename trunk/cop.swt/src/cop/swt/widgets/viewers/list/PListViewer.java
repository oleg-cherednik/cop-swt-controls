package cop.swt.widgets.viewers.list;

import static cop.common.extensions.ArrayExtension.ONE_ITEM_STR_ARR;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.EMPTY_STR_ARR_LIST;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_SORT;
import static org.eclipse.swt.SWT.H_SCROLL;
import static org.eclipse.swt.SWT.V_SCROLL;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;

import cop.swt.widgets.annotations.exceptions.AnnotationMissingException;
import cop.swt.widgets.annotations.services.LabelService;
import cop.swt.widgets.interfaces.LabelSupport;
import cop.swt.widgets.menus.MenuBuilder;
import cop.swt.widgets.menus.items.PushMenuItem;
import cop.swt.widgets.menus.items.SeparatorMenuItem;
import cop.swt.widgets.viewers.PViewer;
import cop.swt.widgets.viewers.list.descriptions.ILabelDescription;

public class PListViewer<T> extends PViewer<T> implements LabelSupport
{
	private ILabelDescription<T> description;
	private PListLabelProvider<T> labelProvider;
	// private PListSorter<T> sorter;
	private static final String PREFERENCE_PAGE = null;// EmployeeListPreferencePage.class.getName();

	public PListViewer(T obj, Composite parent, int style, ListViewerConfig config) throws Exception
	{
		super(obj, new ListViewer(parent, style | V_SCROLL | H_SCROLL), PREFERENCE_PAGE, config);

		setLabelName(isNotNull(config) ? config.getLabelName() : "");

		// setReadonly(isBitSet(style, READ_ONLY));
		createLabelProvider();
		// createSorter();

		// viewer.setSorter(new PListSorter<T>());

		// createFilter();
		postConstruct();
	}

	// private void createSorter()
	// {
	// Assert.isNotNull(labelProvider);
	//
	// // sorter = new PListSorter<T>(LabelComparator.createLabelComparator(obj, labelProvider.getLabelName()));
	// // AbstractLabelDescription<T> cd = AbstractLabelDescription.createColumnDescription(obj,
	// // labelProvider.getLabelName(), Locale.getDefault());
	// // sorter = new PListSorter<T>(cd);
	// // sorter.setDirection(SortDirectionEnum.SORT_ASC);
	// // widget.setSorter(sorter);
	//
	// // sorter.setAccessibleObject(description);
	// // columnViewer.getColumn().addSelectionListener(setSorter);
	// }

	@Override
	public int getItemCount()
	{
		return ((ListViewer)widget).getList().getItemCount();
	}

	private void createLabelProvider()
	{
		widget.setLabelProvider(labelProvider = new PListLabelProvider<T>(description));
	}

	// public void setItems(Collection<String> items)
	// {
	// if(isEmpty(items))
	// return;
	//
	// viewer.setInput(items);
	// //_pack();
	// }

	/*
	 * PViewer
	 */

	@Override
	protected MenuBuilder createSortMenuBuilder()
	{
		MenuBuilder menuBuilder = new MenuBuilder(getImageProvider());
		// ColumnDescription<T> description;

		menuBuilder.addMenuItem(new PushMenuItem(MI_SORT, isSortable, null, this));
		menuBuilder.addMenuItem(new SeparatorMenuItem());

		// for(PTableColumnInfo<T> column : columns)
		// {
		// description = column.getDescription();
		//
		// if(!description.isSortable())
		// continue;
		//
		// menuBuilder.addMenuItem(new RadioDescriptionMenuItem<T>(obj, column.getDescription(), null, null,
		// getColumnStateSelectionProvider(column), getSortColumnMenuListener(column)));
		// }

		return menuBuilder;
	}

	@Override
	protected String[] getProperties()
	{
		return ONE_ITEM_STR_ARR;
	}

	@Override
	protected List<String[]> toStringArrayList(T[] items)
	{
		if(isEmpty(items))
			return EMPTY_STR_ARR_LIST;

		List<String[]> data = new ArrayList<String[]>(items.length);

		for(String str : labelProvider.getLabels(items))
			data.add(new String[] { str });

		return data;
	}

	@Override
	public void selectAll()
	{
		if(getSelectionSize() == getItemCount())
			return;

		((ListViewer)widget).getList().selectAll();
		super.selectAll();
	}

	@Override
	public void deselectAll()
	{
		if(getSelectionSize() != 0)
			super.deselectAll();
	}

	@Override
	public void setSorterOff()
	{
		// if(!isSorterOn())
		// return;
		//
		// for(TableColumnInfo<T> column : columns)
		// column.setSorterOff();

		refresh();
	}

	@Override
	protected int getTopIndex()
	{
		return ((ListViewer)widget).getList().getTopIndex();
	}

	@Override
	protected int[] getSelectionIndices()
	{
		return ((ListViewer)widget).getList().getSelectionIndices();
	}

	@Override
	protected void setTopIndex(int index)
	{
		((ListViewer)widget).getList().setTopIndex(index);
	}

	@Override
	protected void setSelected(int index, boolean selected)
	{
		if(selected)
			((ListViewer)widget).getList().select(index);
		else
			((ListViewer)widget).getList().deselect(index);
	}

	/*
	 * Control
	 */

	public void setLayoutData(Object layoutData)
	{
		widget.getControl().setLayoutData(layoutData);
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
		return description.getLabelName();
	}

	/**
	 * @see LabelSupport#setLabelName(String)
	 */
	@Override
	public void setLabelName(String labelName)
	{
		try
		{
			ILabelDescription<T> description = LabelService.getDescription(obj.getClass(), labelName);

			if(isNull(description))
			{
				// if it's first time - throw exception
				if(isNull(this.description))
					throw new AnnotationMissingException("No item found. Use @Label annotation.");

				return;
			}

			this.description = description;

			if(isNotNull(labelProvider))
				labelProvider.setDescription(description);

			refresh();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * LocaleSupport
	 */

	@Override
	public void setLocale(Locale locale)
	{
		super.setLocale(locale);

		description.setLocale(locale);

		refresh();
	}
}
