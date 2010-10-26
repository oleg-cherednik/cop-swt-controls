package Lists;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PopupListExample
{
	Text text;
	String[] states =
	                { "Andra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat",
	                                "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka",
	                                "Kerala", "Madya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram",
	                                "Nagaland", "Orissa", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Tripura",
	                                "Uttaranchal", "Uttar Pradesh", "West Bengal" };

	public void init()
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("PopupList");
		createList(shell);
		shell.pack();
		shell.setSize(230, 50);
		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}

	private void createList(final Shell shell)
	{
		shell.setLayout(new RowLayout());
		Button button = new Button(shell, SWT.PUSH);
		button.setText("click");
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				PopupList list = new PopupList(shell);
				list.setItems(states);
				String selected = list.open(shell.getBounds());
				if(selected == null)
				{
					System.out.println("You haven't selected any state");
				}
				else
				{
					text.setText("Selected:" + selected);
					text.setSize(180, 20);
				}
			}
		});
		text = new Text(shell, SWT.BORDER | SWT.SINGLE);
		text.setEditable(true);
	}

	public static void main(String[] args)
	{
		new PopupListExample().init();
	}
}
