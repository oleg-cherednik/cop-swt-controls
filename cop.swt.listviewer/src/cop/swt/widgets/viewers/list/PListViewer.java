package cop.swt.widgets.viewers.list;

import static cop.common.extensions.ArrayExtension.ONE_ITEM_STR_ARR;
import static cop.common.extensions.ArrayExtension.isEmpty;
import static cop.common.extensions.CollectionExtension.EMPTY_STR_ARR_LIST;
import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_ASC;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_DESC;
import static cop.swt.widgets.enums.SortDirectionEnum.SORT_OFF;
import static cop.common.extensions.BitExtension.clearBits;
import static cop.common.extensions.BitExtension.isBitSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import cop.swt.widgets.annotations.exceptions.AnnotationMissingException;
import cop.swt.widgets.annotations.services.LabelService;
import cop.swt.widgets.enums.SortDirectionEnum;
import cop.swt.widgets.interfaces.LabelSupport;
import cop.swt.widgets.menu.MenuBuilder;
import cop.swt.widgets.menu.interfaces.PropertyProvider;
import cop.swt.widgets.menu.items.PushMenuItem;
import cop.swt.widgets.menu.items.SeparatorMenuItem;
import cop.swt.widgets.viewers.AbstractViewer;
import cop.swt.widgets.viewers.PViewerSorter;
import cop.swt.widgets.viewers.descriptions.LabelDescription;

public class PListViewer<T> extends AbstractViewer<T, ListViewer, ListViewerConfig> implements LabelSupport
{
	private LabelDescription<T> description;
	private PListLabelProvider<T> labelProvider;
	private PViewerSorter<T> sorter;

	public PListViewer(Class<T> cls, Composite parent, int style, ListViewerConfig config) throws Exception
	{
		super(cls, new ListViewer(parent, clearBits(style, SWT.READ_ONLY) | SWT.V_SCROLL | SWT.H_SCROLL), config);

		((ListViewer)widget).setUseHashlookup(true);

		setLabelName(isNotNull(config) ? config.getLabelName() : "");

		setEditable(!isBitSet(style, SWT.READ_ONLY));
		createLabelProvider();
		createSorter();

		// createFilter();
		postConstruct();
	}

	private void createSorter()
	{
		sorter = new PViewerSorter<T>(this, description);
	}

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

		menuBuilder.addMenuItem(new PushMenuItem(SORT_OFF, null, isSorterOnProvider, this));
		menuBuilder.addMenuItem(new SeparatorMenuItem());
		menuBuilder.addMenuItem(new PushMenuItem(SORT_ASC, null, getSorterSelectionProvider(SORT_ASC),
		                setSorterAscListener));
		menuBuilder.addMenuItem(new PushMenuItem(SORT_DESC, null, getSorterSelectionProvider(SORT_DESC),
		                setSorterDescListener));

		return menuBuilder;
	}

	// private Listener onSort = new Listener()
	// {
	// @Override
	// public void handleEvent(Event event)
	// {
	// int a = 0;
	// a++;
	//
	// }
	// };

	private PropertyProvider<Boolean> isSorterOnProvider = new PropertyProvider<Boolean>()
	{
		@Override
		public Boolean getProperty()
		{
			return isSorterOn();
		}
	};

	private Listener setSorterOffListener = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			boolean res = false;

			Menu menu1 = getRootMenu((MenuItem)event.widget);
			//
			// MenuItem item1 = (MenuItem)event.widget;
			// Menu menu1 = item1.getParent();
			// MenuItem item2 = menu1.getParentItem();
			// Menu menu3 = menu1.getParentMenu();
			// Menu menu4 = menu3.getParentMenu();

			Menu menu2 = widget.getControl().getMenu();

			if(event.widget != widget.getControl())
				res = menu1 == menu2;

			setSorterOff();
		}
	};

	private Listener setSorterAscListener = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			sorter.setDirection(SORT_ASC);
			refresh();
		}
	};

	private Listener setSorterDescListener = new Listener()
	{
		@Override
		public void handleEvent(Event event)
		{
			sorter.setDirection(SORT_DESC);
			refresh();
		}
	};

	private PropertyProvider<Boolean> getSorterSelectionProvider(final SortDirectionEnum dir)
	{
		PropertyProvider<Boolean> provider = new PropertyProvider<Boolean>()
		{
			@Override
			public Boolean getProperty()
			{
				return sorter.getDirection() != dir;
			}
		};

		return provider;
	}

	@Override
	public void setSorterOff()
	{
		sorter.setDirection(SORT_OFF);
		refresh();
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
		if(getSelectionSize() != getItemCount())
			((ListViewer)widget).getList().selectAll();
	}

	@Override
	public void deselectAll()
	{
		if(getSelectionSize() != 0)
			((ListViewer)widget).getList().deselectAll();
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
			LabelDescription<T> description = LabelService.getDescription(cls, labelName);

			if(description == null)
			{
				// if it's first time - throw exception
				if(this.description == null)
					throw new AnnotationMissingException("No item found. Use @Label annotation.");

				return;
			}

			this.description = description;

			if(labelProvider != null)
				labelProvider.setDescription(description);

			if(sorter != null)
				sorter.setComparator(description);

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

		// if(locale != null)
		// this.locale = locale;

		refresh();
	}
}
