package print.kprint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

/*******************************************************************************
 * Copyright (C) 2004 by Friederich Kupzog Elektronik & Software All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html Contributors: Friederich Kupzog - initial API and
 * implementation fkmk@kupzog.de www.kupzog.de/fkmk
 ******************************************************************************/

class PaletteExampleRenderer extends KTableCellRenderer
{

	/**
	 * 
	 */
	public PaletteExampleRenderer()
	{}

	/*
	 * overridden from superclass
	 */
	@Override
	public int getOptimalWidth(GC gc, int col, int row, Object content, boolean fixed)
	{
		return 16;
	}

	/*
	 * overridden from superclass
	 */
	@Override
	public void drawCell(GC gc, Rectangle rect, int col, int row, Object content, boolean focus, boolean fixed,
	                boolean clicked)
	{
		// Performance test:
		/*
		 * gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED)); gc.fillRectangle(rect); int j=1; for
		 * (int i = 0; i < 10000000; i++) { j++; }
		 */
		Color color = new Color(m_Display, (RGB)content);
		gc.setBackground(m_Display.getSystemColor(SWT.COLOR_WHITE));
		rect.height++;
		rect.width++;
		gc.fillRectangle(rect);

		gc.setBackground(color);
		if(!focus)
		{
			rect.x += 1;
			rect.y += 1;
			rect.height -= 2;
			rect.width -= 2;
		}
		gc.fillRectangle(rect);
		color.dispose();
	}

}
