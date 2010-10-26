package Tables;

//Send questions, comments, bug reports, etc. to the authors:

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * This program displays a table of baseball players and allows sorting by first name, last name, or lifetime batting
 * average.
 */
public class PlayerTable
{
	private PlayerComparator comparator;

	// Must fully-qualify List to avoid ambiguity with
	// org.eclipse.swt.widgets.List
	private List<Player> players = new ArrayList<Player>();
	
	{
		players.add(new Player("Gil", "Hodges", 0.273f));
		players.add(new Player("Jim", "Gilliam", 0.265f));
		players.add(new Player("Jackie", "Robinson", 0.311f));
		players.add(new Player("Pee Wee", "Reese", 0.269f));
		players.add(new Player("Roy", "Campanella", 0.276f));
		players.add(new Player("Carl", "Furillo", 0.299f));
		players.add(new Player("Sandy", "Amoros", 0.255f));
		players.add(new Player("Duke", "Snider", 0.295f));
	}

	/**
	 * Constructs a PlayerTable
	 */
	public PlayerTable()
	{
		// Create the comparator used for sorting
		comparator = new PlayerComparator();
		comparator.setColumn(PlayerComparator.FIRST_NAME);
		comparator.setDirection(PlayerComparator.ASCENDING);
	}

	/**
	 * Runs the application
	 */
	public void run()
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Player Table");
		createContents(shell);
		shell.pack();
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

	/**
	 * Create the contents of the main window
	 * 
	 * @param composite the parent composite
	 */
	private void createContents(Composite composite)
	{
		composite.setLayout(new FillLayout());

		// Create the table
		final Table table = new Table(composite, SWT.NONE);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Create each of the columns, adding an event
		// listener that will set the appropriate fields
		// into the comparator and then call the fillTable
		// helper method
		TableColumn[] columns = new TableColumn[3];
		columns[0] = new TableColumn(table, SWT.NONE);
		columns[0].setText("First Name");
		columns[0].addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				comparator.setColumn(PlayerComparator.FIRST_NAME);
				comparator.reverseDirection();
				fillTable(table);
			}
		});

		columns[1] = new TableColumn(table, SWT.NONE);
		columns[1].setText("Last Name");
		columns[1].addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				comparator.setColumn(PlayerComparator.LAST_NAME);
				comparator.reverseDirection();
				fillTable(table);
			}
		});

		columns[2] = new TableColumn(table, SWT.RIGHT);
		columns[2].setText("Batting Average");
		columns[2].addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				comparator.setColumn(PlayerComparator.BATTING_AVERAGE);
				comparator.reverseDirection();
				fillTable(table);
			}
		});

		// Do the initial fill of the table
		fillTable(table);

		// Pack each column so inital display is good
		for(int i = 0, n = columns.length; i < n; i++)
		{
			columns[i].pack();
		}
	}

	private void fillTable(Table table)
	{
		// Turn off drawing to avoid flicker
		table.setRedraw(false);

		// We remove all the table entries, sort our
		// rows, then add the entries
		table.removeAll();
		Collections.sort(players, comparator);
		for(Iterator itr = players.iterator(); itr.hasNext();)
		{
			Player player = (Player)itr.next();
			TableItem item = new TableItem(table, SWT.NONE);
			int c = 0;
			item.setText(c++, player.getFirstName());
			item.setText(c++, player.getLastName());
			item.setText(c++, String.valueOf(player.getBattingAverage()));
		}

		// Turn drawing back on
		table.setRedraw(true);
	}

	/**
	 * The application's entry point
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		new PlayerTable().run();
	}
}

// Send questions, comments, bug reports, etc. to the authors:

// Rob Warner (rwarner@interspatial.com)
// Robert Harris (rbrt_harris@yahoo.com)

/**
 * This class represents a player.
 */
class Player
{
	private String firstName;
	private String lastName;
	private float battingAverage;

	/**
	 * Constructs a Player
	 * 
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param battingAverage the batting average
	 */
	public Player(String firstName, String lastName, float battingAverage)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.battingAverage = battingAverage;
	}

	/**
	 * Gets the batting average
	 * 
	 * @return float
	 */
	public float getBattingAverage()
	{
		return battingAverage;
	}

	/**
	 * Gets the first name
	 * 
	 * @return String
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Gets the last name
	 * 
	 * @return String
	 */
	public String getLastName()
	{
		return lastName;
	}
}

// Send questions, comments, bug reports, etc. to the authors:

// Rob Warner (rwarner@interspatial.com)
// Robert Harris (rbrt_harris@yahoo.com)

/**
 * This class does the comparisons for sorting Player objects.
 */
class PlayerComparator implements Comparator
{
	/** Constant for First Name column */
	public static final int FIRST_NAME = 0;

	/** Constant for Last Name column */
	public static final int LAST_NAME = 1;

	/** Constant for Batting Average column */
	public static final int BATTING_AVERAGE = 2;

	/** Constant for ascending */
	public static final int ASCENDING = 0;

	/** Constant for descending */
	public static final int DESCENDING = 1;

	private int column;
	private int direction;

	/**
	 * Compares two Player objects
	 * 
	 * @param obj1 the first Player
	 * @param obj2 the second Player
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object obj1, Object obj2)
	{
		int rc = 0;
		Player p1 = (Player)obj1;
		Player p2 = (Player)obj2;

		// Determine which field to sort on, then sort
		// on that field
		switch(column)
		{
		case FIRST_NAME:
			rc = p1.getFirstName().compareTo(p2.getFirstName());
			break;
		case LAST_NAME:
			rc = p1.getLastName().compareTo(p2.getLastName());
			break;
		case BATTING_AVERAGE:
			rc = (p1.getBattingAverage() < p2.getBattingAverage()) ? -1 : 1;
			break;
		}

		// Check the direction for sort and flip the sign
		// if appropriate
		if(direction == DESCENDING)
		{
			rc = -rc;
		}
		return rc;
	}

	/**
	 * Sets the column for sorting
	 * 
	 * @param column the column
	 */
	public void setColumn(int column)
	{
		this.column = column;
	}

	/**
	 * Sets the direction for sorting
	 * 
	 * @param direction the direction
	 */
	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	/**
	 * Reverses the direction
	 */
	public void reverseDirection()
	{
		direction = 1 - direction;
	}
}
