package cop.swt.example.views;

/**
 * —имволы < , > , "  и & , €вл€ющиес€ служебными дл€ HTML, также имеют хорошо запоминающиес€ мнемонические коды:
 *  &lt; , &gt; , &quot; и &amp; .
 * @ - &#64
 * &ltsdsdf&gt
 */
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import cop.swt.example.examples.PViewerExample;



public class View extends ViewPart
{
	public static final String ID = "cop.swt.example.view";

	@Override
	public void setFocus()
	{}

	@Override
	public void createPartControl(Composite _parent)
	{
		new PViewerExample().run(_parent);
		//new NumericExample().run(_parent);
		//new CalendarDialogExample().run(_parent);
		//new WelcomeExample().run(_parent);
		//new CanvasExample().run(_parent);
	}
//
//	private void createChangeable()
//	{
//		Label label = new Label(parent, SWT.NONE);
//		label.setText("dirty status: ");
//		final Text dirtyStatus = new Text(parent, SWT.BORDER | SWT.READ_ONLY | SWT.NO_FOCUS);
//		dirtyStatus.setEditable(false);
//		label = new Label(parent, SWT.NONE);
//		label.setText("original value: ");
//		final Text originalValue = new Text(parent, SWT.BORDER | SWT.READ_ONLY | SWT.NO_FOCUS);
//		originalValue.setEditable(false);
//		label = new Label(parent, SWT.NONE);
//		label.setText("current value: ");
//		final DText txt = DText.createDirtyText(parent, SWT.BORDER);
//		Button button = new Button(parent, SWT.NONE);
//		txt.setFocus();
//
//		button.setText("clear");
//
//		txt.addDirtyListener(new IDirtyListener()
//		{
//			@Override
//			public void setDirty(boolean dirty)
//			{
//				String str = dirty ? "dirty on" : "dirty off";
//				dirtyStatus.setText(str);
//				System.out.println(str);
//			}
//		});
//
//		button.addSelectionListener(new SelectionListener()
//		{
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{
//				originalValue.setText(txt.getText());
//				txt.resetDirty();
//				// dirtyStatus.setText("dirty off");
//
//			}
//
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e)
//			{}
//		});
//
//	}
//
//	private void createDialog()
//	{
//		MyDialog dlg = new MyDialog(parent.getShell());
//		dlg.open();
//	}
//
//	private static GridData createLayoutData()
//	{
//		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		// GridData layoutData = new GridData();
//
//		// layoutData.widthHint = 50;
//
//		return layoutData;
//	}
//
//	private static Layout createLayout()
//	{
//		GridLayout layout = new GridLayout(6, false);
//
//		return layout;
//	}
//
//	/*
//	 * extendedText
//	 */
//
//	private void extendedText()
//	{
//		ColorText text1 = createExtendedText();
//		ColorText text2 = createExtendedText();
//	}
//
//	private ColorText createExtendedText()
//	{
//		ColorText text = new ColorText(parent, SWT.BORDER | SWT.RIGHT);
//
//		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
//
//		text.setLayoutData(layoutData);
//
//		text.setFont(new Font(parent.getDisplay(), new FontData("Georgia", 50, SWT.BOLD)));
//		text.setBackground(ColorExtension.BLUE);
//
//		// text.setColorRule(emptyTextIsNotValid);
//		text.setColorRule(textOneIsRule);
//		text.setColorRuleCheckType(START_CHECK_FROM_INIT);
//		// text.setColorRuleCheckType(START_CHECK_FROM_FIRST_FOCUS_LOST);
//		// text.setColorRuleCheckType(START_CHECK_FROM_FIRST_FOCUS_GAINED);
//		// text.setColorRuleCheckType(START_CHECK_FROM_FIRST_KEY_PRESSED);
//		// text.setColorRuleCheckType(CHECK_ONLY_IN_FOCUS);
//
//		return text;
//	}
//
//	// private <T> void fillTable(ParamTableViewer<T> table1)
//	// {
//	// Table table = table1.getTable();
//	// // Turn off drawing to avoid flicker
//	// table.setRedraw(false);
//	//
//	// // We remove all the table entries, sort our
//	// // rows, then add the entries
//	//
//	// table.removeAll();
//	// // Collections.sort(players, comparator);
//	//
//	// DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
//	//
//	// int i = 0;
//	//
//	// for(ActionTO action : actions)
//	// {
//	// TableItem item = new TableItem(table, SWT.NONE);
//	// int col = 0;
//	//
//	// item.setText(col++, action.getUserName());
//	// item.setText(col++, df.format(action.getDate().getTime()));
//	// item.setText(col++, action.getDescription());
//	// item.setText(col++, action.getTitleName());
//	// item.setText(col, "" + action.isMarker());
//	// //
//	// // if(action.isMarker())
//	// // item.setImage(col, checked);
//	// // else
//	// // item.setImage(col, unchecked);
//	// }
//	//
//	// // Turn drawing back on
//	// table.setRedraw(true);
//	// }
//
//	private void comboViewExample()
//	{
//		List<ActionTO> tmp = new ArrayList<ActionTO>();
//
//		// for(int i = 0; i < 300; i++)
//		// tmp.add(new ActionTO("userName " + i, null, "description " + i, "titleName " + i));
//
//		final ComboViewer<ActionTO> list = new ComboViewer<ActionTO>(parent, SWT.READ_ONLY);
//
//		GridData layoutData = new GridData();
//		layoutData.heightHint = 200;
//		layoutData.widthHint = 100;
//
//		list.setLayoutData(layoutData);
//
//		// list.setLabelName("");
//		// list.setLabelName("name");
//		// list.setLabelName("description");
//
//		for(ActionTO str : tmp)
//			list.addItem(str);
//
//		// list.setItems(tmp);
//
//		list.setVisibleItemCount(10);
//		list.setEmptyValue(true);
//		list.select(0);
//
//		System.out.println(list);
//
//		// list.removeAll(); System.out.println(list);
//		// list.addItem(tmp.get(4), 0); System.out.println(list);
//
//		// System.out.println("0: " + list.get(0));
//		// System.out.println("1: " + list.get(1));
//		// System.out.println("2: " + list.get(2));
//
//		// list.remove(0); System.out.println(list);
//
//		Combo combo = new Combo(parent, SWT.READ_ONLY);
//
//		combo.setLayoutData(layoutData);
//
//		for(ActionTO str : tmp)
//			combo.add("" + str);
//
//		Button button = new Button(parent, SWT.NONE);
//
//		button.setText("empty");
//
//		button.addSelectionListener(new SelectionAdapter()
//		{
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{
//				// int sel = list.getSelectionIndex();
//				System.out.println(list.getSelectionItem());
//				// list.setEmptyValue(true);
//
//				// System.out.println("sel: " + list.getS(list.getSelectionIndex()));
//				// boolean enabled = list.isEmptyValueEnabled();
//
//				// list.setEmptyValue(!enabled);
//			}
//		});
//
//	}
//
//	private IViewerFilter<Food> filter = new AbstractViewerFilter<Food>()
//	{
//		@Override
//		public boolean select(Food item)
//		{
//			return item.isHealthy();
//		}
//	};
//
//	private IContentProvider<Food> contentProvider = new AbstractContentProvider<Food>()
//	{
//		@Override
//		public List<Food> getItems(Food parentItem)
//		{
//			List<Food> tmp = new ArrayList<Food>();
//
//			tmp.add(new Food("Broccoli1", true));
//			tmp.add(new Food("Bundt Cake1", false));
//			tmp.add(new Food("Cabbage1", true));
//			tmp.add(new Food("Candy Canes1", false));
//			tmp.add(new Food("Eggs1", true));
//			tmp.add(new Food("Potato Chips1", false));
//			tmp.add(new Food("Milk1", true));
//			tmp.add(new Food("Soda1", false));
//			tmp.add(new Food("Chicken1", true));
//			tmp.add(new Food("Cinnamon Rolls1", false));
//
//			return tmp;
//		}
//	};
//
//	// class FoodContentProvider implements IStructuredContentProvider
//	// {
//	// /**
//	// * Gets the food items for the list
//	// *
//	// * @param arg0
//	// * the data model
//	// * @return Object[]
//	// */
//	// public Object[] getElements(Object arg0)
//	// {
//	// System.out.println("getElements()");
//	// return ((GroceryList)arg0).getFoods().toArray();
//	// }
//	//
//	// /**
//	// * Disposes any created resources
//	// */
//	// public void dispose()
//	// {
//	// // Do nothing
//	// }
//	//
//	// /**
//	// * Called when the input changes
//	// *
//	// * @param arg0
//	// * the viewer
//	// * @param arg1
//	// * the old input
//	// * @param arg2
//	// * the new input
//	// */
//	// public void inputChanged(Viewer arg0, Object arg1, Object arg2)
//	// {
//	// // Do nothing
//	// }
//	// }
//
//	private void listViewExample()
//	{
//		// FoodList food = new FoodList();
//		//
//		// food.main(new String[0]);
//		//
//		// List<ActionTO> tmp = new ArrayList<ActionTO>();
//		//
//		// for(int i = 0; i < 30; i++)
//		// tmp.add(new ActionTO("userName " + i, null, "description " + i, "titleName " + i));
//
//		final ListViewer<ActionTO> list = new ListViewer<ActionTO>(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL
//		                | SWT.V_SCROLL);
//
//		GridData layoutData = new GridData(GridData.FILL_BOTH);
//		// layoutData.heightHint = 200;
//		// layoutData.widthHint = 100;
//
//		list.setLayoutData(layoutData);
//		// list.addViewerFilter(filter);
//		// list.setContentProvider(contentProvider);
//
//		// list.setLabelName("");
//		// list.setLabelName("name");
//		// list.setLabelName("description");
//
//		// for(ActionTO str : tmp)
//		// list.addItem(str);
//
//		// list.addModifyListener(new ModifyListener()
//		// {
//		// @Override
//		// public void modifyText(ModifyEvent e)
//		// {
//		// System.out.println("modifyText()");
//		// }
//		// });
//
//		list.setItems(actions);
//		// list.addItem(new ActionTO("Oleg1", null, "description", "titleName"));
//		// list.addItem(new ActionTO("Oleg2", null, "description", "titleName"), 0);
//		// list.addItem(new ActionTO("Oleg3", null, "description1", "titleName1"));
//		// list.addItem(new ActionTO("Oleg4", null, "description1", "titleName1"));
//		// list.addItem(new Food("Benzin", false));
//		// list.addItem(new Food("Broccoli", true));
//		// list.addItem(new Food("Bundt Cake", false));
//		// list.addItem(new Food("Cabbage", true));
//		// list.addItem(new Food("Candy Canes", false));
//		// list.addItem(new Food("Eggs", true));
//		// list.addItem(new Food("Potato Chips", false));
//		// list.addItem(new Food("Milk", true));
//		// list.addItem(new Food("Soda", false));
//		// list.addItem(new Food("Chicken", true));
//		// list.addItem(new Food("Cinnamon Rolls", false));
//
//		// list.addSelectionListener(new SelectionAdapter()
//		// {
//		// @SuppressWarnings("unchecked")
//		// @Override
//		// public void widgetSelected(SelectionEvent e)
//		// {
//		// @SuppressWarnings("unused")
//		// ListViewer<ActionTO> obj = (ListViewer<ActionTO>)e.widget;
//		// System.out.println(obj.getSelectedItems());
//		//
//		// }
//		// });
//
//		// list.addMouseWheelListener(new MouseWheelListener()
//		// {
//		// @Override
//		// public void mouseScrolled(MouseEvent e)
//		// {
//		// System.out.println("mouseScrolled2");
//		// }
//		// });
//
//		// list.setSelection(0);
//
//		list.setDeleteKey(true);
//		list.setTopIndex(0);
//
//		// list.moveItemsDown(new int[] { 0});
//
//		// org.eclipse.swt.widgets.List list2 = new org.eclipse.swt.widgets.List(parent, SWT.MULTI | SWT.H_SCROLL
//		// | SWT.V_SCROLL | SWT.READ_ONLY);
//		//
//		// list2.setLayoutData(layoutData);
//
//		// for(Food str : tmp)
//		// list2.add("" + str);
//
//		final org.eclipse.jface.viewers.ListViewer listViewer = new org.eclipse.jface.viewers.ListViewer(parent);
//
//		listViewer.setContentProvider(new FoodContentProvider());
//		listViewer.setLabelProvider(new FoodLabelProvider());
//		listViewer.setInput(new GroceryList());
//
//		listViewer.getList().setLayoutData(layoutData);
//
//		// list2.setSelection(0);
//
//		final Button applyFilter1 = new Button(parent, SWT.TOGGLE);
//		final Button applyFilter2 = new Button(parent, SWT.TOGGLE);
//		final Button changeProvider = new Button(parent, SWT.TOGGLE);
//
//		applyFilter1.setText("filter");
//		applyFilter2.setText("filter");
//		changeProvider.setText("provider");
//
//		applyFilter1.addSelectionListener(new SelectionAdapter()
//		{
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{
//			// if(applyFilter1.getSelection())
//			// list.addFilter(filter);
//			// else
//			// list.removeFilter(filter);
//			}
//		});
//
//		applyFilter2.addSelectionListener(new SelectionAdapter()
//		{
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{
//				if(applyFilter2.getSelection())
//				{
//					listViewer.addFilter(healthyFilter);
//					listViewer.add(new Food("Benzin123", true));
//				}
//				else
//					listViewer.removeFilter(healthyFilter);
//			}
//		});
//
//		changeProvider.addSelectionListener(new SelectionAdapter()
//		{
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{
//			// if(changeProvider.getSelection())
//			// list.setContentProvider(contentProvider);
//			// else
//			// {
//			// list.clearContentProvider();
//			// list.setItems(tmp);
//			// }
//			}
//		});
//	}
//
//	private HealthyFilter healthyFilter = new HealthyFilter();
//
//	private void numericTextExample()
//	{
//		AbstractNumeric<Integer> numText1 = createIntegerNumericText(null, null);
//		// AbstractNumericViewer<Integer> numText2 = createIntegerNumericText(-10, null);
//		// AbstractNumericViewer<Integer> numText3 = createIntegerNumericText(null, -10);
//		// AbstractNumericViewer<Integer> numText4 = createIntegerNumericText(10, null);
//		// AbstractNumericViewer<Integer> numText5 = createIntegerNumericText(-10, 10);
//		//
//		AbstractNumeric<Double> numText6 = createDoubleNumericText(null, null);
//		// AbstractNumericViewer<Double> numText7 = createDoubleNumericText(-10.0, null);
//		// AbstractNumericViewer<Double> numText8 = createDoubleNumericText(null, -10.0);
//		// AbstractNumericViewer<Double> numText9 = createDoubleNumericText(10.0, null);
//		// AbstractNumericViewer<Double> numText10 = createDoubleNumericText(-10.0, 10.0);
//
//		// Text text = new Text(parent, SWT.NONE);
//		//
//		// text.setData(data);
//
//	}
//
//	private AbstractNumeric<Integer> createIntegerNumericText(Integer minimum, Integer maximum)
//	{
//		DecimalFormat df = (DecimalFormat)NumberFormat.getIntegerInstance(locale);
//		df.setGroupingUsed(true);
//
//		AbstractNumeric<Integer> numText = new IntegerNumeric(parent, SWT.CENTER, df);
//
//		numText.setFont(new Font(parent.getDisplay(), new FontData("Georgia", 50, SWT.BOLD)));
//		numText.setBounds(minimum, maximum);
//		numText.setNullValueEnabled(true);
//		// numText.setBackground(ColorExtension.WHITE);
//		// numText.setValue(1234);
//		numText.setWrap(true);
//		// numText.setInc(2);
//		// numText.setColorRule(colorRule1);
//
//		// numText.addBreak(5);
//		// numText.addBreak(10);
//		// numText.addBreak(0);
//
//		numText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//
//		numText.addDirtyListener(new IDirtyListener()
//		{
//			@Override
//			public void setDirty(boolean dirty)
//			{
//				System.out.println("Integer: " + (dirty ? "dirty on" : "dirty off"));
//			}
//		});
//
//		return numText;
//	}
//
//	private AbstractNumeric<Double> createDoubleNumericText(Double minimum, Double maximum)
//	{
//		DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(locale);
//
//		df.setGroupingUsed(true);
//		df.setMinimumFractionDigits(1);
//		df.setMaximumFractionDigits(3);
//
//		AbstractNumeric<Double> numText = new DoubleNumeric(parent, SWT.CENTER, df);
//
//		numText.setFont(new Font(parent.getDisplay(), new FontData("Georgia", 50, SWT.BOLD)));
//		numText.setBounds(minimum, maximum);
//		// numText.setBackground(ColorExtension.BLUE);
//		// numText.setValue(.0);
//		numText.setWrap(true);
//		// numText.setInc(2);
//		// numText.setColorRule(colorRule1);
//
//		// numText.addBreak(5);
//		// numText.addBreak(10);
//		// numText.addBreak(0);
//
//		numText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//
//		numText.addDirtyListener(new IDirtyListener()
//		{
//			@Override
//			public void setDirty(boolean dirty)
//			{
//				System.out.println("Double: " + (dirty ? "dirty on" : "dirty off"));
//			}
//		});
//
//		return numText;
//	}
//
//	private void enumTextEcample()
//	{
//	// EnumText enumText = new EnumText(parent, SWT.NONE, TestEnum.values());
//	// enumText.setFont(new Font(parent.getDisplay(), new FontData("Georgia", 50, SWT.BOLD)));
//	//
//	// enumText.setText("ONE");
//	//
//	// GridData gd = new GridData();
//	//
//	// gd.horizontalAlignment = SWT.FILL;
//	// gd.verticalAlignment = SWT.FILL;
//	// gd.grabExcessVerticalSpace = false;
//	// enumText.setLayoutData(gd);
//	//
//	// enumText.setColorRule(textOneIsRule);
//	//
//	//
//	// DecimalFormat df = (DecimalFormat)DecimalFormat.getIntegerInstance(locale);
//	// df.setGroupingUsed(true);
//	//
//	// AbstractNumericViewer<Integer> numText = new IntegerNumericViewer(parent, SWT.CENTER, df);
//	//
//	// numText.setFont(new Font(parent.getDisplay(), new FontData("Georgia", 50, SWT.BOLD)));
//	// numText.setBounds(minimum, maximum);
//	// numText.setNullValueEnabled(true);
//	// //numText.setBackground(ColorExtension.WHITE);
//	// //numText.setValue(1234);
//	// numText.setWrap(true);
//	// //numText.setInc(2);
//	// // numText.setColorRule(colorRule1);
//	//
//	// // numText.addBreak(5);
//	// // numText.addBreak(10);
//	// // numText.addBreak(0);
//	//
//	// numText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//	//
//	// numText.addDirtyListener(new IDirtyListener()
//	// {
//	// @Override
//	// public void setDirty(boolean dirty)
//	// {
//	// System.out.println("Integer: " + (dirty ? "dirty on" : "dirty off"));
//	// }
//	// });
//
//	}
//
//	private void dateTimeExample()
//	{
//		final TimeControl timePart;
//		// final DateControl datePart;
//		// Composite spinComposite = createSpinComposite(mainComposite);
//
//		Properties props = new Properties();
//		// props.setProperty(keyLayoutHorizontalSpacing, "" + 0);
//		// props.setProperty(keyLayoutVerticalSpacing, "" + 0);
//		// props.setProperty(keyLayoutMarginTop, "" + 5);
//		// props.setProperty(keyLayoutMarginBottom, "" + 5);
//		// props.setProperty(keyLayoutMarginHeight, "" + 5);
//		// props.setProperty(keyLayoutMarginWidth, "" + 5);
//		// props.setProperty(keyLayoutMarginLeft, "" + 5);
//		// props.setProperty(keyLayoutMarginRight, "" + 5);
//
//		// props.setProperty(keyGridHorizontalAlignment, "" + SWT.CENTER);
//		// props.setProperty(keyGridGrabExcessHorizontalSpace, "" + false);
//
//		// props.setProperty(ctrlEnableButton, "" + false);
//
//		// props.setProperty(keyTimeSeparator, ":");
//		// props.setProperty(keyTimeSeparatorSysReplace, "true");
//
//		// props.setProperty(keyTimeFormat, "H:mm t");
//		// props.setProperty(keyTimeAM, "Am");
//		// props.setProperty(keyTimePM, "Pm");
//
//		// numTest = new NumericText(mainComposite, SWT.NONE, -30, 10);
//
//		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
//
//		timePart = new TimeControl(parent, SWT.NONE, props);
//		// datePart = new DateControl(parent, SWT.DROP_DOWN | SWT.CTRL, props);
//
//		timePart.setLayoutData(createLayoutData());
//		// toolkit.paintBordersFor(parent);
//
//		// timePart.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
//		// datePart.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
//		// numTest.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
//		// timePart1.setDate(Calendar.getInstance());
//
//		// timePart1.setMonth(Calendar.JANUARY);
//
//		timePart.setFontData(new FontData("Georgia", 50, SWT.BOLD));
//		// datePart.setFontData(new FontData("Georgia", 15, SWT.BOLD));
//		// numTest.setFont(new Font(mainComposite.getDisplay(), new FontData("Georgia", 20, SWT.BOLD)));
//
//		// 1927 - 2026
//
//		// numTest.setValue(20);
//		// numTest.setValue(27);
//		// numTest.setValue(3);
//		// dt = new DateTime(mainComposite, SWT.DATE | SWT.DROP_DOWN);
//
//		// numTest.addInvisibleValue(0);
//		// numTest.addInvisibleValue(1);
//		// numTest.addInvisibleValue(2);
//		// numTest.addInvisibleValue(4);
//		// numTest.addInvisibleValue(5);
//		// numTest.setValue(26);
//		// numTest.setValue(29);
//
//		// timePart1.setBackground(getColorRGB(SWT.COLOR_BLACK));
//		// timePart1.setForeground(SEPARATOR, getColorRGB(SWT.COLOR_GREEN));
//		// timePart1.setForeground(DIGITS, getColorRGB(SWT.COLOR_RED));
//
//		Button changeBackgroundButton = new Button(parent, SWT.NONE);
//
//		changeBackgroundButton.setText("Change");
//
//		changeBackgroundButton.addSelectionListener(new SelectionListener()
//		{
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{
//				/*
//				 * BACKGROUND_PART, COMPOSITE_PART, ENABLE_BUTTON_PART, CALENDAR_BUTTON_PART, SEPARATOR_PART, AMPM_PART,
//				 * HOUR_PART, MINUTE_PART, SECOND_PART, YEAR_PART, MONTH_PART, DAY_PART
//				 */
//				currentColor = (++currentColor > colorArr.length) ? 0 : currentColor;
//
//				// timePart.setBackgroundRGB(BACKGROUND_PART, colorArr[currentColor]);
//				// datePart.setBackgroundRGB(BACKGROUND_PART, colorArr[currentColor]);
//			}
//
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e)
//			{}
//		});
//
//		timePart.pack();
//	}
//
//	private static RGB getColorRGB(int id)
//	{
//		return Display.getCurrent().getSystemColor(id).getRGB();
//	}
//
//	private Composite createMainComposite(Composite parent)
//	{
//		Composite composite = new Composite(parent, SWT.NONE);
//
//		// composite.setBackground(ColorExtension.WHITE);
//
//		Layout layout = new GridLayout(2, false);
//		composite.setLayout(layout);
//
//		GridData gd = new GridData();
//
//		// gd.grabExcessHorizontalSpace = true;
//		// gd.grabExcessVerticalSpace = true;
//		// gd.horizontalAlignment = GridData.FILL;
//		// gd.verticalAlignment
//		composite.setLayoutData(gd);
//
//		return composite;
//
//	}
//
//	// private String showCommentDialog()
//	// {
//	// BetCommentDialog dlg = new BetCommentDialog(shell, betPresentation.getLookBetItem().getNoNull(
//	// BetTags.BETCOMMENT));
//	//
//	// dlg.setCreateButtons(true, true);
//	// dlg.setBlockOnOpen(true);
//	//
//	// return (dlg.open() == Window.OK) ? dlg.getComment() : null;
//	// }
//
//	// private static IColorChangeable colorRule1 = new ChangeColorAdapter(null, SWT.COLOR_GREEN)
//	// {
//	// @Override
//	// protected boolean check(String str)
//	// {
//	// if(StringExtension.isEmpty(str))
//	// return false;
//	//
//	// try
//	// {
//	// int value = Integer.parseInt(str);
//	//
//	// return NumericExtension.isInRange(value, 8, 12);
//	// }
//	// catch(Exception e)
//	// {
//	// return false;
//	// }
//	// }
//	// };
//
//	private static IColorChangeable textOneIsRule = new ChangeColorAdapter(SWT.COLOR_DARK_MAGENTA, SWT.COLOR_GREEN)
//	{
//		@Override
//		protected boolean check(String str)
//		{
//			if(StringExtension.isEmpty(str))
//				return false;
//
//			return str.equalsIgnoreCase("one");
//		}
//	};
//
//	private static IColorChangeable emptyTextIsNotValid = new ChangeColorAdapter(SWT.COLOR_YELLOW, null)
//	{
//		@Override
//		protected boolean check(String str)
//		{
//			return StringExtension.isEmpty(str);
//		}
//	};
}

//enum ListEnum
//{
//	ITEM_0("item 0"),
//	ITEM_1("item 1"),
//	ITEM_2("item 2"),
//	ITEM_3("item 3"),
//	ITEM_4("item 4"),
//	ITEM_5("item 5");
//
//	private String name;
//
//	private ListEnum(String name)
//	{
//		this.name = name;
//	}
//
//	@com.panbet.swt.widgets.annotations.Label
//	public String getName()
//	{
//		return name;
//	}
//}