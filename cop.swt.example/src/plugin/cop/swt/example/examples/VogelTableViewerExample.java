package plugin.cop.swt.example.examples;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import plugin.cop.swt.example.Activator;

import cop.swt.widgets.annotations.exceptions.AnnotationDeclarationException;
import cop.swt.widgets.annotations.exceptions.AnnotationMissingException;
import cop.swt.widgets.tmp.vogella.ModelProvider;
import cop.swt.widgets.tmp.vogella.PersonContentProvider;
import cop.swt.widgets.tmp.vogella.PersonEditingSupport;
import cop.swt.widgets.tmp.vogella.PersonFilter;
import cop.swt.widgets.tmp.vogella.PersonLabelProvider;
import cop.swt.widgets.tmp.vogella.TableSorter;

public class VogelTableViewerExample implements IExample
{
	private TableViewer viewer;
	private PersonFilter filter1;
	private PersonLabelProvider labelProvider;
	private TableSorter tableSorter;

	@Override
	public void run(Composite parent)
	{
		try
		{
			GridLayout layout = new GridLayout();
			parent.setLayout(layout);
			Label searchLabel = new Label(parent, SWT.NONE);
			searchLabel.setText("Search: ");
			final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
			searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
			searchText.addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyReleased(KeyEvent ke)
				{
					System.out.println("key char: " + ke.character + ", int: " + (int)ke.character);
					filter1.setSearchText(searchText.getText());
					labelProvider.setSearchText(searchText.getText());
					viewer.refresh();
				}
			});
			createViewer(parent);

			// parent = createMainComposite(parent);
			// checked = new Image(parent.getDisplay(), "C:/checked.gif");
			// unchecked = new Image(parent.getDisplay(), "C:/unchecked.gif");
			//
			// GridLayout layout = new GridLayout(2, false);
			// parent.setLayout(layout);
			// Label searchLabel = new Label(parent, SWT.NONE);
			// searchLabel.setText("Search: ");
			// final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
			// searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
			// searchText.addKeyListener(new KeyAdapter()
			// {
			// @Override
			// public void keyReleased(KeyEvent ke)
			// {
			// filter1.setSearchText(searchText.getText());
			// labelProvider.setSearchText(searchText.getText());
			// viewer.refresh();
			// }
			// });
			//
			// createViewer(parent);
			//
			// // Get the content for the viewer, setInput will call getElements in the
			// // contentProvider
			// viewer.setInput(ModelProvider.getInstance().getPersons());
			// // Make the selection available
			// // getSite().setSelectionProvider(viewer);
			// // Set the sorter for the table
			// tableSorter = new TableSorter();
			// viewer.setSorter(tableSorter);
			// filter1 = new PersonFilter();
			// viewer.addFilter(filter1);
			//
			// // Layout the viewer
			// GridData gridData = new GridData();
			// gridData.verticalAlignment = GridData.FILL;
			// gridData.horizontalSpan = 2;
			// gridData.grabExcessHorizontalSpace = true;
			// gridData.grabExcessVerticalSpace = true;
			// gridData.horizontalAlignment = GridData.FILL;
			// viewer.getControl().setLayoutData(gridData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private Composite createMainComposite(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);

		// composite.setBackground(ColorExtension.WHITE);

		Layout layout = new GridLayout(2, false);
		composite.setLayout(layout);

		GridData gd = new GridData();

		// gd.grabExcessHorizontalSpace = true;
		// gd.grabExcessVerticalSpace = true;
		// gd.horizontalAlignment = GridData.FILL;
		// gd.verticalAlignment
		composite.setLayoutData(gd);

		return composite;

	}

	private void createViewer(Composite parent) throws AnnotationDeclarationException, AnnotationMissingException
	{
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		Image checked = Activator.getImageDescriptor("icons/checked.gif").createImage();
		Image unchecked = Activator.getImageDescriptor("icons/unchecked.gif").createImage();

		createColumns(parent, viewer);
		viewer.setContentProvider(new PersonContentProvider());
		labelProvider = new PersonLabelProvider(checked, unchecked);
		viewer.setLabelProvider(labelProvider);
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(ModelProvider.getInstance().getPersons());
		// Make the selection available
		// Set the sorter for the table
		tableSorter = new TableSorter();
		viewer.setSorter(tableSorter);
		filter1 = new PersonFilter();
		viewer.addFilter(filter1);

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		//gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

	private void createColumns(final Composite parent, final TableViewer viewer)
	{
		final Menu headerMenu = new Menu(parent);
		String[] titles = { "First name", "Last name", "Gender", "Married" };
		int[] bounds = { 100, 100, 100, 100 };

		for(int i = 0; i < titles.length; i++)
		{
			final int index = i;
			final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
			final TableColumn column = viewerColumn.getColumn();
			column.setText(titles[i]);
			column.setWidth(bounds[i]);
			column.setResizable(true);
			createMenuItem(headerMenu, column); // Create the menu item for this
			// column
			column.setMoveable(true);

			// Setting the right sorter
			column.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{
					tableSorter.setColumn(index);
					int dir = viewer.getTable().getSortDirection();
					if(viewer.getTable().getSortColumn() == column)
					{
						dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
					}
					else
					{

						dir = SWT.DOWN;
					}
					viewer.getTable().setSortDirection(dir);
					viewer.getTable().setSortColumn(column);
					viewer.refresh();
				}
			});
			viewerColumn.setEditingSupport(new PersonEditingSupport(viewer, i));
		}
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table.addListener(SWT.MenuDetect, new Listener()
		{
			@Override
            public void handleEvent(Event event)
			{
				table.setMenu(headerMenu);
			}
		});

	}

	private void createMenuItem(Menu parent, final TableColumn column)
	{
		final MenuItem itemName = new MenuItem(parent, SWT.CHECK);
		itemName.setText(column.getText());
		itemName.setSelection(column.getResizable());
		itemName.addListener(SWT.Selection, new Listener()
		{
			@Override
            public void handleEvent(Event event)
			{
				if(itemName.getSelection())
				{
					column.setWidth(150);
					column.setResizable(true);
				}
				else
				{
					column.setWidth(0);
					column.setResizable(false);
				}
			}
		});

	}

	public TableViewer getViewer()
	{
		return viewer;
	}
}
