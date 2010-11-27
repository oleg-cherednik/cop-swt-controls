import static java.lang.Math.ceil;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class BlockListDemo
{
	private final int ITEMS_PER_THREAD = 300;
	private static final List<ActionTO> actions = new ArrayList<ActionTO>();
	private static int currentIndex = 0;

	static
	{
		for(int i = 0; i < 100000; i++)
			actions.add(new ActionTO("userName " + i, null, "description " + i, "titleName " + i));
	}

	public static void main(String[] args)
	{
		DateFormat df = DateFormat.getDateInstance();
		Calendar date = Calendar.getInstance();

		date.set(2009, Calendar.DECEMBER, 28);

		date.setMinimalDaysInFirstWeek(7);
		System.out.println(df.format(date.getTime()));
		System.out.println(date.get(Calendar.WEEK_OF_YEAR));

		date.set(2010, Calendar.JANUARY, 1);
		System.out.println(df.format(date.getTime()));
		System.out.println(date.get(Calendar.WEEK_OF_YEAR));

		// ThreadFactory factory = new ThreadFactory()
		// {
		// @Override
		// public Thread newThread(Runnable r)
		// {
		// return null;
		// }
		// };

		// ExecutorService pool = Executors.newSingleThreadExecutor();
		//
		// for(ActionTO action : actions)
		// {
		// pool.execute(getAction1);
		// }

		// BlockListDemo obj = new BlockListDemo();
		// List<List<ActionTO>> data = obj.createBlocks(obj.actions);

		// int a = 0;
		// a++;

		// obj.tmp.s

	}

	private List<List<ActionTO>> createBlocks(List<ActionTO> data)
	{
		int maxIndex = data.size();
		int blocksNumber = (int)ceil((double)maxIndex / ITEMS_PER_THREAD);
		List<List<ActionTO>> result = new ArrayList<List<ActionTO>>(blocksNumber);

		for(int i = 0, fromIndex = 0; i < blocksNumber; i++, fromIndex += ITEMS_PER_THREAD)
		{
			int toIndex = fromIndex + ITEMS_PER_THREAD;

			if(maxIndex < toIndex)
				toIndex = maxIndex;

			result.add(data.subList(fromIndex, toIndex));
		}

		return result;
	}

	private Callable<ActionTO> getAction = new Callable<ActionTO>()
	{
		@Override
		public ActionTO call() throws Exception
		{
			return actions.get(currentIndex++);
		}

	};

	private static Runnable getAction1 = new Runnable()
	{
		@Override
		public void run()
		{
			ActionTO action = actions.get(currentIndex++);
			System.out.println(action);
		}
	};
}

class ActionTO
{
	private String userName;
	private Calendar date;
	private String description;
	private String titleName;

	public ActionTO(String userName, Calendar date, String description, String titleName)
	{
		super();
		this.userName = userName;
		this.date = date;
		this.description = description;
		this.titleName = titleName;
	}

	public String getUserName()
	{
		return userName;
	}

	public Calendar getDate()
	{
		return date;
	}

	public String getDescription()
	{
		return description;
	}

	public String getTitleName()
	{
		return titleName;
	}

	@Override
	public String toString()
	{
		return "[" + titleName + "]";
	}
}
