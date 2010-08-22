package cop.swt.widgets.viewers;

import static cop.common.extensions.CollectionExtension.isEmpty;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_SORT;
import static org.eclipse.swt.SWT.H_SCROLL;
import static org.eclipse.swt.SWT.V_SCROLL;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.annotations.exceptions.AnnotationMissingException;
import cop.swt.widgets.annotations.services.LabelService;
import cop.swt.widgets.interfaces.LabelSupport;
import cop.swt.widgets.menus.MenuBuilder;
import cop.swt.widgets.menus.items.PushMenuItem;
import cop.swt.widgets.menus.items.SeparatorMenuItem;
import cop.swt.widgets.viewers.list.ListViewerConfig;
import cop.swt.widgets.viewers.list.PListLabelProvider;
import cop.swt.widgets.viewers.list.descriptions.ILabelDescription;
import cop.swt.widgets.viewers.table.descriptions.IColumnDescription;

public class PListViewer<T> extends PViewer<T> implements LabelSupport
{
	private ILabelDescription<T> description;
	private PListLabelProvider<T> labelProvider;
	//private PListSorter<T> sorter;
	private static final String PREFERENCE_PAGE = null;// EmployeeListPreferencePage.class.getName();

	public PListViewer(T obj, Composite parent, int style, ListViewerConfig config) throws Exception
	{
		super(obj, new ListViewer(parent, style | V_SCROLL | H_SCROLL), PREFERENCE_PAGE, config);

		setLabelName(isNotNull(config) ? config.getLabelName() : "");

		// setReadonly(isBitSet(style, READ_ONLY));
		createLabelProvider();
		//createSorter();

		// viewer.setSorter(new PListSorter<T>());

		// createFilter();
		addListeners();
		postConstruct();
	}

	private void createSorter()
	{
		Assert.isNotNull(labelProvider);

		//		sorter = new PListSorter<T>(LabelComparator.createLabelComparator(obj, labelProvider.getLabelName()));
		//		AbstractLabelDescription<T> cd = AbstractLabelDescription.createColumnDescription(obj, labelProvider.getLabelName(), Locale.getDefault());
		//		sorter = new PListSorter<T>(cd);
		//		sorter.setDirection(SortDirectionEnum.SORT_ASC);
		//		widget.setSorter(sorter);

		// sorter.setAccessibleObject(description);
		// columnViewer.getColumn().addSelectionListener(setSorter);
	}

	@Override
	public List<T> getSelectedItems()
	{
		return labelProvider.getItems(((ListViewer)widget).getList().getSelection());
	}

	@Override
	public int getItemCount()
	{
		return labelProvider.getItemCount();
	}

	private void createLabelProvider()
	{
		labelProvider = new PListLabelProvider<T>(description);
		widget.setLabelProvider(labelProvider);
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
		IColumnDescription<T> description;

		menuBuilder.addMenuItem(new PushMenuItem(MI_SORT, isSortable, null, setSorter));
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

	private Listener setSorter = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			System.out.println("---------");
			try
			{
				//				if(!description.isSortable())
				//					return;
				//
				//				TableColumn column = columnViewer.getColumn();
				//				Table table = tableViewer.getTable();
				//				int dir = table.getSortDirection();
				//
				//				if(table.getSortColumn() == column)
				//					setSorterDirection(parseSwtDirection((dir == UP) ? DOWN : UP));
				//				else
				//					setSorterDirection(parseSwtDirection(DEFAULT_SORT_DIRECTION.getSwtDirection()));
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	};

	@Override
	protected List<String[]> toStringArrayList(List<T> items)
	{
		if(isEmpty(items))
			return new ArrayList<String[]>(0);

		List<String[]> data = new ArrayList<String[]>(items.size() + 1);

		for(String str : labelProvider.getLabels(items))
			data.add(new String[] { str });

		return data;
	}

	@Override
	public void selectAll()
	{
		if(getSelectedItems().size() == getItemCount())
			return;

		((ListViewer)widget).getList().selectAll();
		super.selectAll();
	}

	@Override
	public void deselectAll()
	{
		if(getSelectedItems().size() == 0)
			return;

		((ListViewer)widget).getList().deselectAll();
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
		Assert.isNotNull(widget);

		return ((ListViewer)widget).getList().getTopIndex();
	}

	@Override
	protected int[] getSelectionIndices()
	{
		Assert.isNotNull(widget);

		return ((ListViewer)widget).getList().getSelectionIndices();
	}

	@Override
	protected void setTopIndex(int index)
	{
		Assert.isNotNull(widget);

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

	@Override
	protected void modelChanged()
	{
		labelProvider.updateItems(getItems());
		super.modelChanged();
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