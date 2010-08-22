package plugin.cop.swt.example.examples;

import static cop.common.extensions.StringExtension.isEmpty;
import static cop.common.extensions.StringExtension.isNotEmpty;
import static cop.swt.extensions.ColorExtension.BLACK;
import static cop.swt.extensions.ColorExtension.BLUE;
import static cop.swt.extensions.ColorExtension.CYAN;
import static cop.swt.extensions.ColorExtension.DARK_BLUE;
import static cop.swt.extensions.ColorExtension.DARK_GREEN;
import static cop.swt.extensions.ColorExtension.GRAY;
import static cop.swt.extensions.ColorExtension.GREEN;
import static cop.swt.extensions.ColorExtension.MAGENTA;
import static cop.swt.extensions.ColorExtension.RED;
import static cop.swt.extensions.ColorExtension.WHITE;
import static cop.swt.extensions.ColorExtension.YELLOW;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_COPY;
import static cop.swt.widgets.menus.enums.MenuItemEnum.MI_DELETE;
import static cop.swt.widgets.menus.enums.MenuItemEnum.*;
import static cop.swt.widgets.tmp.CountEnum.EIGHT;
import static cop.swt.widgets.tmp.CountEnum.FIVE;
import static cop.swt.widgets.tmp.CountEnum.FOUR;
import static cop.swt.widgets.tmp.CountEnum.NINE;
import static cop.swt.widgets.tmp.CountEnum.ONE;
import static cop.swt.widgets.tmp.CountEnum.SEVEN;
import static cop.swt.widgets.tmp.CountEnum.SIX;
import static cop.swt.widgets.tmp.CountEnum.TEN;
import static cop.swt.widgets.tmp.CountEnum.THREE;
import static cop.swt.widgets.tmp.CountEnum.TWO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import plugin.cop.swt.Activator;

import cop.swt.extensions.ColorExtension;
import cop.swt.images.ImageProvider;
import cop.swt.widgets.localization.interfaces.LocaleSupport;
import cop.swt.widgets.menus.items.StateMenuItem;
import cop.swt.widgets.tmp.ActionTO;
import cop.swt.widgets.tmp.MarketTO;
import cop.swt.widgets.tmp.localization.Name;
import cop.swt.widgets.tmp.localization.StateBundleEnum;
import cop.swt.widgets.viewers.PViewer;
import cop.swt.widgets.viewers.interfaces.IModifyListener;
import cop.swt.widgets.viewers.interfaces.ISelectionListener;
import cop.swt.widgets.viewers.list.ListViewerConfig;
import cop.swt.widgets.viewers.list.PListViewer;
import cop.swt.widgets.viewers.model.ListModel;
import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;
import cop.swt.widgets.viewers.model.interfaces.ViewerModel;
import cop.swt.widgets.viewers.table.PTableViewer;
import cop.swt.widgets.viewers.table.TableColumnProperty;
import cop.swt.widgets.viewers.table.TableViewerConfig;
import cop.swt.widgets.viewers.table.descriptions.BooleanColumnDescription;
import cop.swt.widgets.viewers.table.interfaces.TableColumnAdapter;
import cop.swt.widgets.viewers.table.interfaces.TableColumnListener;

public class TableViewerExample implements IExample, LocaleSupport
{
	private static Boolean MODEL_A = Boolean.FALSE;
	private static Boolean MODEL_B = Boolean.TRUE;
	private static Boolean MODEL_OWN = null;

	private static Color MODEL_A_COLOR = ColorExtension.CYAN;
	private static Color MODEL_B_COLOR = ColorExtension.GREEN;
	private static Color MODEL_C_TABLE_COLOR = ColorExtension.RED;
	private static Color MODEL_C_LIST_COLOR = ColorExtension.MAGENTA;

	private int style = SWT.BORDER | SWT.MULTI;
	// private List<ActionTO> actions = new ArrayList<ActionTO>();
	public static List<MarketTO> markets = new ArrayList<MarketTO>();
	private PTableViewer<ActionTO> table;
	private PListViewer<ActionTO> list;
	private Text searchText;
	private Combo localesCombo;
	private Locale[] locales = new Locale[] { Locale.US, Locale.UK, Locale.GERMANY, new Locale("ru", "RU") };
	private ListModel<ActionTO> modelA, modelB;
	private List<ActionTO> actions;
	private Button removeItemButton;
	private Boolean currentTableModel = MODEL_A;
	private Boolean currentListModel = MODEL_A;
	private TableViewerConfig tableConfig;
	private ListViewerConfig listConfig;
	private static final ImageProviderImpl IMAGE_PROVIDER = new ImageProviderImpl();

	{
		tableConfig = new TableViewerConfig();
		tableConfig.setImageProvider(IMAGE_PROVIDER);
	}

	{
		listConfig = new ListViewerConfig();
		listConfig.setImageProvider(IMAGE_PROVIDER);
	}

	private List<LocaleSupport> localeObjects = new ArrayList<LocaleSupport>();

	{
		List<ActionTO> actions1 = new ArrayList<ActionTO>();
		List<ActionTO> actions2 = new ArrayList<ActionTO>();
		actions = new ArrayList<ActionTO>();

		Name nameCO = new Name("Cherednik, Oleg_us", "Cherednik, Oleg_uk", "Cherednik, Oleg_de", "Чередник, Олег");
		Name namePN = new Name("Pavlenko, Nikita_us", "Pavlenko, Nikita_uk", "Pavlenko, Nikita_de", "Павленко, Никита");
		Name nameSS = new Name("Savitsky, Sergey_us", "Savitsky, Sergey_uk", "Savitsky, Sergey_de", "Савицкий, Сергей");
		Name nameZO = new Name("Zaslavsky, Oleg_us", "Zaslavsky, Oleg_uk", "Zaslavsky, Oleg_de", "Заславский, Олег");
		Name nameKM = new Name("Kazanzev, Maksim_us", "Kazanzev, Maksim_uk", "Kazanzev, Maksim_de", "Казанцев, Максим");
		Name nameSK = new Name("Khomyakov, Sergey_us", "Khomyakov, Sergey_uk", "Khomyakov, Sergey_de",
		                "Хомяков, Сергей");
		Name nameAM = new Name("Mikhailov, Aleksandr_us", "Mikhailov, Aleksandr_uk", "Mikhailov, Aleksandr_de",
		                "Михайлов, Александр");
		Name nameRS = new Name("Romanov, Aleksandr_us", "Romanov, Aleksandr_uk", "Romanov, Aleksandr_de",
		                "Романов, Александр");
		Name nameMI = new Name("Murashko, Ivan_us", "Murashko, Ivan_uk", "Murashko, Ivan_de", "Мурашко, Иван");
		Name nameRI = new Name("Rodionov, Ivan_us", "Rodionov, Ivan_uk", "Rodionov, Ivan_de", "Родионов, Иван");

		Calendar date = Calendar.getInstance();
		ActionTO action0 = new ActionTO(nameCO, date, 0, 1021.15, 1.35, 12345.678, true, ONE, RED.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);
		ActionTO action1 = new ActionTO(namePN, date, 1, 1021.15, 0.35, 12345.678, true, TWO, BLACK.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);
		ActionTO action2 = new ActionTO(nameSS, date, 2, 1021.15, 0.35, 12345.678, true, THREE, GREEN.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);
		ActionTO action3 = new ActionTO(nameZO, date, 3, 1021.15, 0.35, 12345.678, true, FOUR, BLUE.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);
		ActionTO action4 = new ActionTO(nameKM, date, 4, 1021.15, 0.35, 12345.678, true, FIVE, GRAY.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);

		ActionTO action5 = new ActionTO(nameSK, date, 5, 1021, 0.35, 12345.0, true, SIX, YELLOW.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);
		ActionTO action6 = new ActionTO(nameAM, date, 6, 1021, 0.35, 12345.0, true, SEVEN, MAGENTA.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);
		ActionTO action7 = new ActionTO(nameRS, date, 7, 1021, 0.35, null, true, EIGHT, CYAN.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);
		ActionTO action8 = new ActionTO(nameMI, date, 8, 1021, 0.35, 12345.0, true, NINE, DARK_BLUE.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);
		ActionTO action9 = new ActionTO(nameRI, date, 9, 1021, 0.35, null, true, TEN, DARK_GREEN.getRGB());
		date.add(Calendar.DAY_OF_YEAR, 1);

		actions1.add(action0);
		actions1.add(action1);
		actions1.add(action2);
		actions1.add(action3);
		actions1.add(action4);

		actions2.add(action5);
		actions2.add(action6);
		actions2.add(action7);
		actions2.add(action8);
		actions2.add(action9);

		actions.add(action0);
		actions.add(action1);
		actions.add(action2);
		actions.add(action3);
		actions.add(action4);
		actions.add(action5);
		actions.add(action6);
		actions.add(action7);
		actions.add(action8);
		actions.add(action9);

		markets.add(new MarketTO("userName 0"));
		markets.add(new MarketTO("userName 1"));
		markets.add(new MarketTO("userName 2"));
		markets.add(new MarketTO("userName 3"));
		markets.add(new MarketTO("userName 4"));

		modelA = new ListModel<ActionTO>("model A");
		modelA.add(actions1);

		modelB = new ListModel<ActionTO>("model B");
		modelB.add(actions2);

		Locale.setDefault(Locale.US);
	}

	@Override
	public void run(Composite parent)
	{
		try
		{
			parent = createComposite(parent);

			createSearchLocalizationPart(parent);
			createViewerPart(parent);
			createModelPart(parent);

			localesCombo.select(2);
			setLocale(locales[2]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void createSearchLocalizationPart(Composite parent)
	{
		createSearchText(parent);
		createLocaleCombo(parent);
	}

	private void createViewerPart(Composite parent) throws Exception
	{
		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		sash.setLayout(createLayout(2));
		sash.setBackground(parent.getBackground());

		createTableViewer(sash);
		createListViewer(sash);

		sash.setWeights(new int[] { 7, 2 });
	}

	private void createSearchText(Composite parent)
	{
		Group group = new Group(parent, SWT.NONE);

		group.setText("Search");
		group.setBackground(parent.getBackground());
		group.setLayout(createLayout(1));
		group.setLayoutData(createLayoutData(1));

		searchText = new Text(group, SWT.BORDER);
		searchText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		searchText.addModifyListener(onSearchText);
	}

	private void createLocaleCombo(Composite parent)
	{
		Group group = new Group(parent, SWT.NONE);

		group.setText("Locale");
		group.setBackground(parent.getBackground());
		group.setLayout(createLayout(1));
		group.setLayoutData(new GridData());

		localesCombo = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);

		for(Locale locale : locales)
			if(isNotEmpty(locale.getCountry()))
				localesCombo.add(locale.getDisplayName());

		localesCombo.addSelectionListener(onChangeLocale);
	}

	private void createTableViewer(Composite parent) throws Exception
	{
		Group group = new Group(parent, SWT.NONE);

		group.setText("PTableViewer");
		group.setBackground(parent.getBackground());
		group.setLayout(createLayout(1));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		table = new PTableViewer<ActionTO>(new ActionTO(), group, style, tableConfig);

		// table.setBackground(BLACK);
		// table.setLayout(createLayout());
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// table.setContentProvider(model);
		table.beginListenToModel(modelA);

		// getSite().setSelectionProvider(table);

		// table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		// table.setLinesVisible(true);
		// table.setHeaderVisible(true);
		// table.setReadonlyProvider(ActionTO.checkStrategy);

		// table.setItems(actions);
		//table.addSelectionListener(onItemSelect);
		table.addModifyListener(modifyTableListener);
		table.addTableColumnListener(onTableColumn);
		// table.setPreferencePage(EmployeeListPreferencePage.class.getName());

		localeObjects.add(table);

		Composite client = new Composite(group, SWT.NONE);

		client.setLayout(new GridLayout(4, false));
		client.setLayoutData(new GridData());
		client.setBackground(group.getBackground());

		Button readonly = new Button(client, SWT.CHECK);
		readonly.setText("read only");
		readonly.setBackground(client.getBackground());
		readonly.addSelectionListener(tableReadOnly);

		Button modelA = new Button(client, SWT.RADIO);
		modelA.setText("A");
		modelA.setBackground(MODEL_A_COLOR);
		modelA.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		modelA.setSelection(true);
		modelA.addSelectionListener(changeTableModelA);

		Button modelB = new Button(client, SWT.RADIO);
		modelB.setText("B");
		modelB.setBackground(MODEL_B_COLOR);
		modelB.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		modelB.addSelectionListener(changeTableModelB);

		Button modelC = new Button(client, SWT.RADIO);
		modelC.setText("C");
		modelC.setBackground(MODEL_C_TABLE_COLOR);
		modelC.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		modelC.addSelectionListener(changeTableModelC);
	}

	private void createListViewer(Composite parent) throws Exception
	{
		Group group = new Group(parent, SWT.NONE);

		group.setText("PListViewer");
		group.setBackground(parent.getBackground());
		group.setLayout(createLayout(1));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		list = new PListViewer<ActionTO>(new ActionTO(), group, style, listConfig);
		list.beginListenToModel(modelA);
		// list.a onListModify
		// list.setTableModel(model);
		// list.setItems(arr);
		// list.getList().setBackground(ColorExtension.YELLOW);
		// //list.setDeleteKey(true);
		list.addModifyListener(modifyListListener);
		list.addSelectionListener(onItemSelect);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		localeObjects.add(list);

		Composite client = new Composite(group, SWT.NONE);

		client.setLayout(new GridLayout(5, false));
		client.setLayoutData(new GridData());
		client.setBackground(group.getBackground());

		Button readonly = new Button(client, SWT.CHECK);
		readonly.setText("read only");
		readonly.setBackground(client.getBackground());
		readonly.addSelectionListener(listReadOnly);

		Button modelA = new Button(client, SWT.RADIO);
		modelA.setText("A");
		modelA.setBackground(MODEL_A_COLOR);
		modelA.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		modelA.setSelection(true);
		modelA.addSelectionListener(changeListModelA);

		Button modelB = new Button(client, SWT.RADIO);
		modelB.setText("B");
		modelB.setBackground(MODEL_B_COLOR);
		modelB.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		modelB.addSelectionListener(changeListModelB);

		Button modelC = new Button(client, SWT.RADIO);
		modelC.setText("D");
		modelC.setBackground(MODEL_C_LIST_COLOR);
		modelC.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		modelC.addSelectionListener(changeListModelC);
	}

	private void createModelPart(Composite parent) throws Exception
	{
		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		sash.setLayoutData(createLayoutData(2));
		sash.setLayout(createLayout(2));
		sash.setBackground(parent.getBackground());

		createModelAPart(sash);
		createModelBPart(sash);
	}

	private void createModelAPart(Composite parent) throws Exception
	{
		Group group = new Group(parent, SWT.READ_ONLY);

		group.setText("Model A");
		group.setBackground(parent.getBackground());
		group.setLayout(createLayout(1));
		group.setLayoutData(createLayoutData(1));

		PListViewer<ActionTO> list = new PListViewer<ActionTO>(new ActionTO(), group, SWT.BORDER, listConfig);
		list.beginListenToModel(modelA);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		list.widget.getControl().setBackground(MODEL_A_COLOR);
		list.setReadonly(true);
		localeObjects.add(list);
		list.setLabelName("model");
		//list.setEnabled(false);
	}

	private void createModelBPart(Composite parent) throws Exception
	{
		Group group = new Group(parent, SWT.READ_ONLY);

		group.setText("Model B");
		group.setBackground(parent.getBackground());
		group.setLayout(createLayout(1));
		group.setLayoutData(createLayoutData(1));

		PListViewer<ActionTO> list = new PListViewer<ActionTO>(new ActionTO(), group, style, listConfig);
		list.beginListenToModel(modelB);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		list.widget.getControl().setBackground(MODEL_B_COLOR);
		list.setReadonly(true);
		localeObjects.add(list);
		list.setLabelName("model");
		//list.setEnabled(false);
	}

	@SuppressWarnings("unchecked")
	private <T> void setModel(PViewer<T> viewer, boolean isModelA, boolean isModelB)
	{
		if(isModelA)
			viewer.beginListenToModel((ViewerModel<T>)modelA);
		else if(isModelB)
			viewer.beginListenToModel((ViewerModel<T>)modelB);
		else
		{
			viewer.stopListenToModel((ViewerModel<T>)modelA);
			viewer.stopListenToModel((ViewerModel<T>)modelB);
		}
	}

	private ISelectionListener<ActionTO> onItemSelect = new ISelectionListener<ActionTO>()
	{
		@Override
		public void itemSelected(Widget widget, List<ActionTO> items)
		{
			System.out.println("Selection changed:");

			for(ActionTO item : items)
				System.out.println(item);
		}
	};

	private SelectionListener changeTableModelA = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			boolean res = ((Button)e.widget).getSelection();

			if(!res)
				return;

			setModel(table, res, false);
			currentTableModel = MODEL_A;
		}
	};

	private SelectionListener changeTableModelB = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			boolean res = ((Button)e.widget).getSelection();

			if(!res)
				return;

			setModel(table, false, res);
			currentTableModel = MODEL_B;
		}
	};

	private SelectionListener changeTableModelC = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			boolean res = ((Button)e.widget).getSelection();

			if(!res)
				return;

			setModel(table, false, false);
			currentTableModel = MODEL_OWN;
			table.setItems(new ArrayList<ActionTO>(actions));
		}
	};

	private SelectionListener changeListModelA = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			boolean res = ((Button)e.widget).getSelection();

			if(!res)
				return;

			setModel(list, res, false);
			currentListModel = MODEL_A;
		}
	};

	private SelectionListener changeListModelB = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			boolean res = ((Button)e.widget).getSelection();

			if(!res)
				return;

			setModel(list, false, res);
			currentListModel = MODEL_B;
		}
	};

	private SelectionListener changeListModelC = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			boolean res = ((Button)e.widget).getSelection();

			if(!res)
				return;

			setModel(list, false, false);
			currentListModel = MODEL_OWN;
			list.setItems(new ArrayList<ActionTO>(actions));
		}
	};

	SelectionListener printItem = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			// table.print();
			for(ActionTO action : table.getItems())
				System.out.println(action);

			table.pack();
		}
	};

	SelectionListener removeItem = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			Collection<ActionTO> items = table.getSelectedItems();

			for(ActionTO item : items)
				modelA.remove(item);

			// if(model)
		}
	};

	private SelectionListener tableReadOnly = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			table.setReadonly(((Button)e.widget).getSelection());
		}
	};

	private SelectionListener listReadOnly = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent e)
		{
			list.setReadonly(((Button)e.widget).getSelection());
		}
	};

	ModifyListener onSearchText = new ModifyListener()
	{
		@Override
		public void modifyText(ModifyEvent e)
		{
			table.setSearchText(searchText.getText());
			// System.out.println("key char: " + ke.character + ", int: " + (int)ke.character);
			// filter1.setSearchText(searchText.getText());
			// labelProvider.setSearchText(searchText.getText());
			// viewer.refresh();

		}
	};

	private TableColumnListener onTableColumn = new TableColumnAdapter()
	{
		@Override
		public void columnMoved(TableColumnProperty movedColumn, TableColumnProperty[] columns)
		{
			System.out.print("Column '" + movedColumn.getKey() + "' was moved. Current order:");

			for(TableColumnProperty column : columns)
				System.out.print(" " + column.getKey());

			System.out.println();
		}

		@Override
		public void columnResized(TableColumnProperty resizeColumn, TableColumnProperty[] columns)
		{
			System.out.print("Column '" + resizeColumn.getKey() + "' was resized. Current order:");

			for(TableColumnProperty column : columns)
				System.out.print(" " + column.getKey());

			System.out.println();
		}
	};

	private IModifyListener<ActionTO> modifyTableListener = new IModifyListener<ActionTO>()
	{
		@Override
		public void itemModified(Widget widget, ActionTO item, ModificationTypeEnum type)
		{
			if(currentTableModel == MODEL_OWN)
				return;

			if(type == ModificationTypeEnum.REMOVE)
			{
				if(currentTableModel == MODEL_A)
					modelA.remove(item);
				else if(currentTableModel == MODEL_B)
					modelB.remove(item);
			}
			else
			{
				if(currentTableModel == MODEL_A)
					modelA.updateItem(item);
				else if(currentTableModel == MODEL_B)
					modelB.updateItem(item);
			}
		}
	};

	private IModifyListener<ActionTO> modifyListListener = new IModifyListener<ActionTO>()
	{
		@Override
		public void itemModified(Widget widget, ActionTO item, ModificationTypeEnum type)
		{
			if(currentListModel == MODEL_OWN)
				return;

			if(type == ModificationTypeEnum.REMOVE)
			{
				if(currentListModel == MODEL_A)
					modelA.remove(item);
				else if(currentListModel == MODEL_B)
					modelB.remove(item);
			}
			else
			{
				if(currentListModel == MODEL_A)
					modelA.updateItem(item);
				else if(currentListModel == MODEL_B)
					modelB.updateItem(item);
			}
		}
	};

	private IModifyListener<ActionTO> onListModify = new IModifyListener<ActionTO>()
	{
		// @Override
		// public void itemModified(Widget widget, ActionTO item, ModificationTypeEnum type)
		// {
		// if(currentTableModel == MODEL_OWN)
		// return;
		//
		// if(type == ModificationTypeEnum.REMOVE)
		// {
		// if(currentTableModel == MODEL_A)
		// modelA.remove(item);
		// else if(currentTableModel == MODEL_B)
		// modelB.remove(item);
		// }
		// else
		// {
		// if(currentTableModel == MODEL_A)
		// modelA.updateItem(item);
		// else if(currentTableModel == MODEL_B)
		// modelB.updateItem(item);
		// }
		// }

		@Override
		public void itemModified(Widget widget, ActionTO item, ModificationTypeEnum type)
		{
		// TODO Auto-generated method stub

		}
	};

	private Composite createComposite(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);

		composite.setBackground(WHITE);
		composite.setLayout(createLayout(2));
		composite.setLayoutData(createLayoutData(2));

		return composite;
	}

	private static Layout createLayout(int numColumns)
	{
		GridLayout layout = new GridLayout(numColumns, false);

		return layout;
	}

	private static Object createLayoutData(int horizontalSpan)
	{
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);

		layoutData.horizontalSpan = horizontalSpan;

		return layoutData;
	}

	private SelectionListener onChangeLocale = new SelectionAdapter()
	{
		@Override
		public void widgetSelected(SelectionEvent event)
		{
			setLocale(locales[localesCombo.getSelectionIndex()]);
		}
	};

	@Override
	public void setLocale(Locale locale)
	{
		for(LocaleSupport item : localeObjects)
			item.setLocale(locales[localesCombo.getSelectionIndex()]);
	}
}

class ImageProviderImpl implements ImageProvider
{
	private static Properties paths;

	static
	{
		paths = new Properties();

		paths.setProperty(MI_COPY.name(), "icons//copy//copy16.png");
		paths.setProperty(MI_DELETE.name(), "icons//delete//delete16.png");
		// paths.setProperty(MI_PRINT.name(), "icons//print//print16.png");
		paths.setProperty(MI_PROPERTIES.name(), "icons//properties//properties16.png");

		paths.setProperty(StateMenuItem.getKey() + "_" + StateBundleEnum.STATE0.name(), "icons//num0.png");
		paths.setProperty(StateMenuItem.getKey() + "_" + StateBundleEnum.STATE1.name(), "icons//num1.png");
		paths.setProperty(StateMenuItem.getKey() + "_" + StateBundleEnum.STATE2.name(), "icons//num2.png");
		paths.setProperty(StateMenuItem.getKey() + "_" + StateBundleEnum.STATE3.name(), "icons//num3.png");
		// paths.setProperty("userName", "icons//num3.png");

		paths.setProperty(BooleanColumnDescription.CHECKED_MARKER, "icons//checked.gif");
		paths.setProperty(BooleanColumnDescription.UNCHECKED_MARKER, "icons//unchecked.gif");
	}

	@Override
	public Image getImage(String key)
	{
		if(isEmpty(key))
			return null;

		String path = paths.getProperty(key);
		return isNotEmpty(path) ? Activator.getImageDescriptor(path).createImage() : null;
	}
}
