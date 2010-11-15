package print.kprint;

/*******************************************************************************
 * Copyright (C) 2004 by Friederich Kupzog Elektronik & Software All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html Contributors: Friederich Kupzog - initial API and
 * implementation fkmk@kupzog.de www.kupzog.de/fkmk
 ******************************************************************************/

class TownExampleModel implements KTableModel
{

	private int[] colWidths;

	private TownExampleContent[] content;

	public TownExampleModel()
	{
		colWidths = new int[getColumnCount()];
		colWidths[0] = 120;
		colWidths[1] = 100;
		colWidths[2] = 180;

		content = new TownExampleContent[3];
		content[0] = new TownExampleContent("Aachen", "Germany");
		content[1] = new TownExampleContent("Cologne", "Germany");
		content[2] = new TownExampleContent("Edinburgh", "Scotland");

	}

	/*
	 * overridden from superclass
	 */
	public Object getContentAt(int col, int row)
	{
		if(row == 0) // Header
		{
			if(col == 0)
				return "Town";
			else if(col == 1)
				return "Country";
			else
				return "Notes";
		}
		else
		{
			return content[row - 1];
		}
	}

	/*
	 * overridden from superclass
	 */
	public KTableCellEditor getCellEditor(int col, int row)
	{
		if(row > 0 && col == 2)
			return new KTableCellEditorMultilineText();
		return null;
	}

	/*
	 * overridden from superclass
	 */
	public void setContentAt(int col, int row, Object value)
	{
		content[row - 1].notes = (String)value;
	}

	/*
	 * overridden from superclass
	 */
	public int getRowCount()
	{
		return 4;
	}

	/*
	 * overridden from superclass
	 */
	public int getFixedRowCount()
	{
		return 1;
	}

	/*
	 * overridden from superclass
	 */
	public int getColumnCount()
	{
		return 3;
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
		return colWidths[col];
	}

	/*
	 * overridden from superclass
	 */
	public boolean isColumnResizable(int col)
	{
		return (col != 0);
	}

	/*
	 * overridden from superclass
	 */
	public void setColumnWidth(int col, int value)
	{
		if(value > 120)
			colWidths[col] = value;
	}

	/*
	 * overridden from superclass
	 */
	public int getRowHeight()
	{
		return 140;
	}

	/*
	 * overridden from superclass
	 */
	public int getFirstRowHeight()
	{
		return 20;
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
		return 20;
	}

	/*
	 * overridden from superclass
	 */
	public void setRowHeight(int value)
	{}

	/*
	 * overridden from superclass
	 */
	public KTableCellRenderer getCellRenderer(int col, int row)
	{
		if(row > 0)
			return new TownExampleRenderer();
		return KTableCellRenderer.defaultRenderer;
	}

}
