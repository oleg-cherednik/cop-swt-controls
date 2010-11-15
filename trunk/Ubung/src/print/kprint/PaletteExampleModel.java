package print.kprint;

import org.eclipse.swt.graphics.RGB;

/*******************************************************************************
 * Copyright (C) 2004 by Friederich Kupzog Elektronik & Software All rights
 * reserved. This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Friederich Kupzog - initial API and implementation
 * fkmk@kupzog.de www.kupzog.de/fkmk
 ******************************************************************************/

class PaletteExampleModel implements KTableModel
{

	/*
	 * overridden from superclass
	 */
	public Object getContentAt(int col, int row)
	{
		return new RGB(col * 16, row * 16, (col + row) * 8);
	}

	/*
	 * overridden from superclass
	 */
	public KTableCellEditor getCellEditor(int col, int row)
	{
		return null;
	}

	/*
	 * overridden from superclass
	 */
	public void setContentAt(int col, int row, Object value)
	{}

	/*
	 * overridden from superclass
	 */
	public int getRowCount()
	{
		return 16;
	}

	/*
	 * overridden from superclass
	 */
	public int getFixedRowCount()
	{
		return 0;
	}

	/*
	 * overridden from superclass
	 */
	public int getColumnCount()
	{
		return 16;
	}

	/*
	 * overridden from superclass
	 */
	public int getFixedColumnCount()
	{
		return 0;
	}

	/*
	 * overridden from superclass
	 */
	public int getColumnWidth(int col)
	{
		return 10;
	}

	/*
	 * overridden from superclass
	 */
	public boolean isColumnResizable(int col)
	{
		return false;
	}

	/*
	 * overridden from superclass
	 */
	public void setColumnWidth(int col, int value)
	{}

	/*
	 * overridden from superclass
	 */
	public int getRowHeight()
	{
		return 10;
	}

	/*
	 * overridden from superclass
	 */
	public int getFirstRowHeight()
	{
		return 10;
	}

	/*
	 * overridden from superclass
	 */
	public boolean isRowResizable()
	{
		return false;
	}

	/*
	 * overridden from superclass
	 */
	public int getRowHeightMinimum()
	{
		return 10;
	}

	/*
	 * overridden from superclass
	 */
	public void setRowHeight(int value)
	{}

	private static KTableCellRenderer myRenderer = new PaletteExampleRenderer();

	/*
	 * overridden from superclass
	 */
	public KTableCellRenderer getCellRenderer(int col, int row)
	{
		return myRenderer;
	}

}