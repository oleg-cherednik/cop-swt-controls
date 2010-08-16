/*
 * Created on 22.02.2006
 */

package cop.swt.widgets;


/**
 * @author likov
 */
public class EventsTreeDialog// extends DialogEx implements IModelListener
{
//	protected FormToolkit toolkit;
//
//	private /*ScrolledForm*/Form form;
//	
//	private final static String TITLE_CATEGORY = BetSearchBundle.EVENTTREE_TITLE_CATEG.i18n();
//	
//	private final static String TITLE_MULTI = BetSearchBundle.EVENTTREE_TITLE_MULTI.i18n();
//
//	private final static String TITLE = BetSearchBundle.EVENTTREE_TITLE.i18n();
//	
//	protected TreeViewer viewer;
//
//	private List<EventTO> events;
//
//	private List<TreeItemTO> treeItems;
//	
//	private boolean multi;
//
//	private boolean isOnlyCategory;
//	
//	private final static int width = 400;
//
//	private final static int height = 500;
//
//	private Action doubleClickAction;
//	
//	private TreeFilter filter;
//
//	public EventsTreeDialog(Shell parentShell, boolean multi, boolean isOnlyCategory)
//	{
//		super(parentShell);
//		setShellStyle(getShellStyle() & SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.MAX | SWT.MIN);
//		this.multi = multi;
//		this.isOnlyCategory = isOnlyCategory;
//	}
//
//	public EventsTreeDialog(Shell parentShell, boolean multi, boolean isOnlyCategory, TreeFilter filter)
//	{
//		this(parentShell, multi, isOnlyCategory);
//		
//		this.filter = filter;
//	}
//
//	protected void configureShell(Shell shell)
//	{
//		super.configureShell(shell);
//		shell.setText(isOnlyCategory? TITLE_CATEGORY : (multi ? TITLE_MULTI : TITLE));
//		shell.setSize(width, height);
//		GUIHelper.activateSettingSaver(shell, this.getClass().getName());
//	}
//
//	protected Control createDialogArea(Composite parent)
//	{
//		GridLayout layout = new GridLayout();
//
//		toolkit = new FormToolkit(parent.getDisplay());
//		form = toolkit.createForm(parent);
//		form.setText(isOnlyCategory? TITLE_CATEGORY : (multi ? TITLE_MULTI : TITLE));
//
//		layout = new GridLayout();
//		layout.numColumns = 1;
//		layout.horizontalSpacing = 10;
//		layout.verticalSpacing = 15;
//
//		form.getBody().setLayout(layout);
//		form.setLayoutData(new GridData(GridData.FILL_BOTH));
//
//		initTreeViewer(form.getBody());
//
//		createAdditionalPart(form.getBody());
//		
//		ModelManager manager = ClientServiceLocator.getInstance().getModelManager();
//		beginListenToModel(manager.getModelByCode(EventTreeModel.class.getName()));
//
//		GUIHelper.setControlFont(parent, GUIHelper.getDefaultFont(), false);
//		
//		return form.getBody();
//	}
//	
//	public boolean close()
//	{
//		ModelManager manager = ClientServiceLocator.getInstance().getModelManager();
//		endListenToModel(manager.getModelByCode(EventTreeModel.class.getName()));
//
//		return super.close();
//	}
//
//	private void initTreeViewer(Composite parent)
//	{
//		EventTreeContentProvider provider = new EventTreeContentProvider(
//				false, filter == null ? TreeVisibilityHelper.getClientEventFilter() : filter);
//		provider.initialize();
//
//		if (multi) {
//			viewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
//		}
//		else {
//			viewer = new TreeViewer(parent, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
//		}
//
//		GridData gdata = new GridData(GridData.FILL_BOTH);
//
//		viewer.getTree().setLayoutData(gdata);
//		viewer.setSorter(new TreeNumberSorter());
//		viewer.setContentProvider(provider);
//		setLabelProvider();
//
//		EventTreeModel treeModel = (EventTreeModel) ClientServiceLocator.getInstance()
//				.getModelManager().getModelByCode(EventTreeModel.class.getName());
//		viewer.setInput(treeModel);
//
//		makeActions();
//		hookDoubleClickAction();
//		hookSelectionAction();
//	}
//
//	protected void setLabelProvider()
//	{
//		viewer.setLabelProvider(new TreeLabelProvider());
//	}
//	
//	protected void createAdditionalPart(Composite parent)
//	{
//	}
//	
//	protected void makeActions()
//	{
//		makeDoubleClickAction();
//	}
//
//	private void makeDoubleClickAction()
//	{
//		doubleClickAction = new Action() {
//			public void run()
//			{
//				buttonPressed(IDialogConstants.OK_ID);
//			}
//		};
//	}
//
//	private void hookDoubleClickAction()
//	{
//		viewer.addDoubleClickListener(new IDoubleClickListener() {
//			public void doubleClick(DoubleClickEvent event)
//			{
//				doubleClickAction.run();
//			}
//		});
//	}
//	
//	protected void selectAction(TreeItemTO item)
//	{
//		
//	}
//	
//	private void hookSelectionAction()
//	{
//		viewer.addSelectionChangedListener(new ISelectionChangedListener(){
//			public void selectionChanged(SelectionChangedEvent event)
//			{
//				ISelection selection = viewer.getSelection();
//				Object obj = ((IStructuredSelection) selection).getFirstElement();
//				if (obj != null) {
//					if (obj instanceof TreeItemTO) {
//						selectAction((TreeItemTO)obj);
//					}
//				}
//			} 
//		});
//	}
//	
//
//    private void saveSelectedEvents()
//	{
//		IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
//		Object obj = ((IStructuredSelection) selection).getFirstElement();
//		
//		if(obj == null || !(obj instanceof TreeItemTO))
//			return;
//		
//		boolean isNullId = false;
//
//		events = new ArrayList<EventTO>();
//		treeItems = new ArrayList<TreeItemTO>();
//		
//		try
//		{
//			@SuppressWarnings("unchecked")
//			Iterator<TreeItemTO> it = selection.iterator();
//			
//			while(it.hasNext())
//			{
//				TreeItemTO item = it.next();
//				
//				if(isOnlyCategory && item.getLeafId() == null)
//					treeItems.add(item);							
//				else if(!isOnlyCategory && item.getLeafId() != null)
//				{
//					EventTO event = ServiceLocator.getInstance().getCommandProxy()
//							.getEventById(item.getLeafId());
//					
//					if (event != null)
//					{
//						events.add(event);
//						treeItems.add(item);
//					}
//					else
//						isNullId = true;
//				}
//			}
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//			MessageDialogEx.showWarning(BetSearchBundle.EVENTTREE_CANNOT_LOAD.i18n());
//		}
//
//		if (isNullId)
//			MessageDialogEx.showWarning(BetSearchBundle.EVENTTREE_NOTALL_ADDED.i18n());
//	}
//
//	public List<EventTO> getEvents()
//	{
//		return events;
//	}
//
//	public List<TreeItemTO> getTreeItems()
//	{
//		return treeItems;
//	}
//	
//	protected void buttonPressed(int buttonId)
//	{
//		if (buttonId == IDialogConstants.OK_ID) {
//			saveSelectedEvents();
//
//			if (events != null && events.size() > 0 || treeItems != null && treeItems.size() > 0) {
//				super.buttonPressed(buttonId);
//			}
//			else {
//				ISelection selection = viewer.getSelection();
//				Object obj = ((IStructuredSelection) selection).getFirstElement();
//				if (obj != null && obj instanceof TreeItemTO) {
//					GUIHelper.expandElement(viewer, obj);
//				}
//			}
//		}
//		else {
//			super.buttonPressed(buttonId);
//		}
//	}
//
//	public void beginListenToModel(AbstractModel model)
//	{
//		if (model != null)
//			model.addListener(this);
//	}
//
//	public void endListenToModel(AbstractModel model)
//	{
//		if (model != null)
//			model.removeListener(this);
//	}
//
//	public void modelChanged(AbstractModel model, ModelProperty property)
//	{
//		if (viewer == null)
//			return;
//
//		if (model instanceof EventTreeModel && property != null) {
//			viewer.refresh();
//		}
//	}
//
//	public TreeViewer getViewer()
//	{
//		return viewer;
//	}
}
