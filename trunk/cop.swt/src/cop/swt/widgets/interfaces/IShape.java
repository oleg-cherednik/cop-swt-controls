package cop.swt.widgets.interfaces;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public interface IShape
{
	int[] getShape();
	int[] getShape(Rectangle rect);
	
	void draw(GC gc, Color color);
}
