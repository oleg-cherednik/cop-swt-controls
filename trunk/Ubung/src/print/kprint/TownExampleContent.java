package print.kprint;

import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/*******************************************************************************
 * Copyright (C) 2004 by Friederich Kupzog Elektronik & Software All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is avûailable at http://www.eclipse.org/legal/epl-v10.html Contributors: Friederich Kupzog - initial API and
 * implementation fkmk@kupzog.de www.kupzog.de/fkmk
 ******************************************************************************/

class TownExampleContent
{
	public String name;

	public Image image;

	public String country;

	public String notes;

	public TownExampleContent(String name, String country)
	{
		this.name = name;
		this.country = country;
		image = loadImageResource(Display.getCurrent(), "/gfx/" + name + ".gif");
		System.out.println(image);
		notes = "Double click to edit and use \n" + "Shift+Enter to start a new line...";
	}

	public Image loadImageResource(Display d, String name)
	{
		try
		{

			Image ret = null;
			Class clazz = this.getClass();
			InputStream is = clazz.getResourceAsStream(name);
			if(is != null)
			{
				ret = new Image(d, is);
				is.close();
			}
			return ret;
		}
		catch(Exception e1)
		{
			return null;
		}
	}

	/*
	 * overridden from superclass
	 */
	@Override
	public String toString()
	{
		return notes;
	}

}
