/**
 * @licence GNU Leser General Public License
 * @author cop
 * 
 * $Id: ColorExtension.java 51 2010-08-16 12:25:56Z oleg.cherednik $
 * $HeadURL: https://cop-swt-controls.googlecode.com/svn/trunk/cop.swt/cop.swt/src/cop/swt/extensions/ColorExtension.java $
 */
package cop.swt.extensions;

import static cop.common.extensions.CommonExtension.*;
import static org.eclipse.swt.SWT.COLOR_BLACK;
import static org.eclipse.swt.SWT.COLOR_BLUE;
import static org.eclipse.swt.SWT.COLOR_CYAN;
import static org.eclipse.swt.SWT.COLOR_DARK_BLUE;
import static org.eclipse.swt.SWT.COLOR_DARK_CYAN;
import static org.eclipse.swt.SWT.COLOR_DARK_GRAY;
import static org.eclipse.swt.SWT.COLOR_DARK_GREEN;
import static org.eclipse.swt.SWT.COLOR_DARK_MAGENTA;
import static org.eclipse.swt.SWT.COLOR_DARK_RED;
import static org.eclipse.swt.SWT.COLOR_DARK_YELLOW;
import static org.eclipse.swt.SWT.COLOR_GRAY;
import static org.eclipse.swt.SWT.COLOR_GREEN;
import static org.eclipse.swt.SWT.COLOR_INFO_BACKGROUND;
import static org.eclipse.swt.SWT.COLOR_INFO_FOREGROUND;
import static org.eclipse.swt.SWT.COLOR_LIST_BACKGROUND;
import static org.eclipse.swt.SWT.COLOR_LIST_FOREGROUND;
import static org.eclipse.swt.SWT.COLOR_LIST_SELECTION;
import static org.eclipse.swt.SWT.COLOR_LIST_SELECTION_TEXT;
import static org.eclipse.swt.SWT.COLOR_MAGENTA;
import static org.eclipse.swt.SWT.COLOR_RED;
import static org.eclipse.swt.SWT.COLOR_TITLE_BACKGROUND;
import static org.eclipse.swt.SWT.COLOR_TITLE_BACKGROUND_GRADIENT;
import static org.eclipse.swt.SWT.COLOR_TITLE_FOREGROUND;
import static org.eclipse.swt.SWT.COLOR_TITLE_INACTIVE_BACKGROUND;
import static org.eclipse.swt.SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT;
import static org.eclipse.swt.SWT.COLOR_TITLE_INACTIVE_FOREGROUND;
import static org.eclipse.swt.SWT.COLOR_WHITE;
import static org.eclipse.swt.SWT.COLOR_WIDGET_BACKGROUND;
import static org.eclipse.swt.SWT.COLOR_WIDGET_BORDER;
import static org.eclipse.swt.SWT.COLOR_WIDGET_DARK_SHADOW;
import static org.eclipse.swt.SWT.COLOR_WIDGET_FOREGROUND;
import static org.eclipse.swt.SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW;
import static org.eclipse.swt.SWT.COLOR_WIDGET_LIGHT_SHADOW;
import static org.eclipse.swt.SWT.COLOR_WIDGET_NORMAL_SHADOW;
import static org.eclipse.swt.SWT.COLOR_YELLOW;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public final class ColorExtension
{
	private static final Map<RGB, Color> rgbMap = new HashMap<RGB, Color>();

	public static final Color WHITE = getColor(COLOR_WHITE);
	public static final Color BLACK = getColor(COLOR_BLACK);
	public static final Color RED = getColor(COLOR_RED);
	public static final Color GREEN = getColor(COLOR_GREEN);
	public static final Color YELLOW = getColor(COLOR_YELLOW);
	public static final Color BLUE = getColor(COLOR_BLUE);
	public static final Color MAGENTA = getColor(COLOR_MAGENTA);
	public static final Color CYAN = getColor(COLOR_CYAN);
	public static final Color GRAY = getColor(COLOR_GRAY);

	public static final Color DARK_RED = getColor(COLOR_DARK_RED);
	public static final Color DARK_GREEN = getColor(COLOR_DARK_GREEN);
	public static final Color DARK_YELLOW = getColor(COLOR_DARK_YELLOW);
	public static final Color DARK_BLUE = getColor(COLOR_DARK_BLUE);
	public static final Color DARK_MAGENTA = getColor(COLOR_DARK_MAGENTA);
	public static final Color DARK_CYAN = getColor(COLOR_DARK_CYAN);
	public static final Color DARK_GRAY = getColor(COLOR_DARK_GRAY);

	public static final Color SELECTION_BACKGROUND = getColor(COLOR_LIST_SELECTION);
	public static final Color SELECTION_FOREGROUND = getColor(COLOR_LIST_SELECTION_TEXT);

	public static final Color INFO_BACKGROUND = getColor(COLOR_INFO_BACKGROUND);
	public static final Color INFO_FOREGROUND = getColor(COLOR_INFO_FOREGROUND);

	public static final Color LIST_BACKGROUND = getColor(COLOR_LIST_BACKGROUND);
	public static final Color LIST_FOREGROUND = getColor(COLOR_LIST_FOREGROUND);
	public static final Color LIST_SELECTION = getColor(COLOR_LIST_SELECTION);
	public static final Color LIST_SELECTION_TEXT = getColor(COLOR_LIST_SELECTION_TEXT);

	public static final Color WIDGET_BACKGROUND = getColor(COLOR_WIDGET_BACKGROUND);
	public static final Color WIDGET_FOREGROUND = getColor(COLOR_WIDGET_FOREGROUND);
	public static final Color WIDGET_BORDER = getColor(COLOR_WIDGET_BORDER);
	public static final Color WIDGET_DARK_SHADOW = getColor(COLOR_WIDGET_DARK_SHADOW);
	public static final Color WIDGET_NORMAL_SHADOW = getColor(COLOR_WIDGET_NORMAL_SHADOW);
	public static final Color WIDGET_LIGHT_SHADOW = getColor(COLOR_WIDGET_LIGHT_SHADOW);
	public static final Color WIDGET_HIGHLIGHT_SHADOW = getColor(COLOR_WIDGET_HIGHLIGHT_SHADOW);

	public static final Color TITLE_BACKGROUND = getColor(COLOR_TITLE_BACKGROUND);
	public static final Color TITLE_FOREGROUND = getColor(COLOR_TITLE_FOREGROUND);
	public static final Color TITLE_INACTIVE_BACKGROUND = getColor(COLOR_TITLE_INACTIVE_BACKGROUND);
	public static final Color TITLE_INACTIVE_FOREGROUND = getColor(COLOR_TITLE_INACTIVE_FOREGROUND);
	public static final Color TITLE_BACKGROUND_GRADIENT = getColor(COLOR_TITLE_BACKGROUND_GRADIENT);
	public static final Color TITLE_INACTIVE_BACKGROUND_GRADIENT = getColor(COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT);

	static
	{
		rgbMap.put(WHITE.getRGB(), WHITE);
		rgbMap.put(BLACK.getRGB(), BLACK);
		rgbMap.put(RED.getRGB(), RED);
		rgbMap.put(GREEN.getRGB(), GREEN);
		rgbMap.put(YELLOW.getRGB(), YELLOW);
		rgbMap.put(BLUE.getRGB(), BLUE);
		rgbMap.put(MAGENTA.getRGB(), MAGENTA);
		rgbMap.put(CYAN.getRGB(), CYAN);
		rgbMap.put(GRAY.getRGB(), GRAY);

		rgbMap.put(DARK_RED.getRGB(), DARK_RED);
		rgbMap.put(DARK_GREEN.getRGB(), DARK_GREEN);
		rgbMap.put(DARK_YELLOW.getRGB(), DARK_YELLOW);
		rgbMap.put(DARK_BLUE.getRGB(), DARK_BLUE);
		rgbMap.put(DARK_MAGENTA.getRGB(), DARK_MAGENTA);
		rgbMap.put(DARK_CYAN.getRGB(), DARK_CYAN);
		rgbMap.put(DARK_GRAY.getRGB(), DARK_GRAY);

		rgbMap.put(SELECTION_BACKGROUND.getRGB(), WHITE);
		rgbMap.put(SELECTION_FOREGROUND.getRGB(), WHITE);

		rgbMap.put(INFO_BACKGROUND.getRGB(), INFO_BACKGROUND);
		rgbMap.put(INFO_FOREGROUND.getRGB(), INFO_FOREGROUND);

		rgbMap.put(LIST_BACKGROUND.getRGB(), LIST_BACKGROUND);
		rgbMap.put(LIST_FOREGROUND.getRGB(), LIST_FOREGROUND);
		rgbMap.put(LIST_SELECTION.getRGB(), LIST_SELECTION);
		rgbMap.put(LIST_SELECTION_TEXT.getRGB(), LIST_SELECTION_TEXT);

		rgbMap.put(WIDGET_BACKGROUND.getRGB(), WIDGET_BACKGROUND);
		rgbMap.put(WIDGET_FOREGROUND.getRGB(), WIDGET_FOREGROUND);
		rgbMap.put(WIDGET_BORDER.getRGB(), WIDGET_BORDER);
		rgbMap.put(WIDGET_DARK_SHADOW.getRGB(), WIDGET_DARK_SHADOW);
		rgbMap.put(WIDGET_NORMAL_SHADOW.getRGB(), WIDGET_NORMAL_SHADOW);
		rgbMap.put(WIDGET_LIGHT_SHADOW.getRGB(), WIDGET_LIGHT_SHADOW);
		rgbMap.put(WIDGET_HIGHLIGHT_SHADOW.getRGB(), WIDGET_HIGHLIGHT_SHADOW);

		rgbMap.put(TITLE_BACKGROUND.getRGB(), TITLE_BACKGROUND);
		rgbMap.put(TITLE_FOREGROUND.getRGB(), TITLE_FOREGROUND);
		rgbMap.put(TITLE_INACTIVE_BACKGROUND.getRGB(), TITLE_INACTIVE_BACKGROUND);
		rgbMap.put(TITLE_INACTIVE_FOREGROUND.getRGB(), TITLE_INACTIVE_FOREGROUND);
		rgbMap.put(TITLE_BACKGROUND_GRADIENT.getRGB(), TITLE_BACKGROUND_GRADIENT);
		rgbMap.put(TITLE_INACTIVE_BACKGROUND_GRADIENT.getRGB(), TITLE_INACTIVE_BACKGROUND_GRADIENT);
	}

	private ColorExtension()
	{}

	public static Color getColor(int color)
	{
		return Display.getCurrent().getSystemColor(color);
	}

	public static Color getColor(RGB rgb)
	{
		if(isNull(rgb))
			return null;

		Color color = rgbMap.get(rgb);

		if(isNull(color))
		{
			color = new Color(Display.getCurrent(), rgb);
			rgbMap.put(rgb, color);
		}

		return color;
	}

	public static RGB getInvertedColor(RGB rgb)
	{
		return isNotNull(rgb) ? new RGB(255 - rgb.red, 255 - rgb.green, 255 - rgb.blue) : null;
	}

	public static Color getInvertedColor(Color color)
	{
		return isNotNull(color) ? getColor(getInvertedColor(color.getRGB())) : null;
	}

	public static void dispose()
	{
		for(Color color : rgbMap.values())
			color.dispose();

		rgbMap.clear();
	}
}
