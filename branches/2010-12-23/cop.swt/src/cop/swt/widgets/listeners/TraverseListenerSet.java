package cop.swt.widgets.listeners;

import static org.eclipse.swt.SWT.TRAVERSE_ESCAPE;
import static org.eclipse.swt.SWT.TRAVERSE_RETURN;
import static org.eclipse.swt.SWT.TRAVERSE_TAB_NEXT;
import static org.eclipse.swt.SWT.TRAVERSE_TAB_PREVIOUS;

import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;

public final class TraverseListenerSet
{
	private TraverseListenerSet()
	{}

	public static TraverseListener allowTabKey = new TraverseListener()
	{
		@Override
		public void keyTraversed(TraverseEvent e)
		{
			e.doit |= (e.detail == TRAVERSE_TAB_NEXT) || (e.detail == TRAVERSE_TAB_PREVIOUS);
		}
	};

	public static TraverseListener allowReturn = new TraverseListener()
	{
		@Override
        public void keyTraversed(TraverseEvent e)
		{
			e.doit |= e.detail == TRAVERSE_RETURN;
		}
	};

	public static TraverseListener allowEscape = new TraverseListener()
	{
		@Override
        public void keyTraversed(TraverseEvent e)
		{
			e.doit |= e.detail == TRAVERSE_ESCAPE;
		}
	};
}
