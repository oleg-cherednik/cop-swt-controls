/**
 * @licence GNU Leser General Public License
 * @author cop
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.extensions;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
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
	private static final Map<RGB, Color> RGB_MAP = new HashMap<RGB, Color>();

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
		RGB_MAP.put(WHITE.getRGB(), WHITE);
		RGB_MAP.put(BLACK.getRGB(), BLACK);
		RGB_MAP.put(RED.getRGB(), RED);
		RGB_MAP.put(GREEN.getRGB(), GREEN);
		RGB_MAP.put(YELLOW.getRGB(), YELLOW);
		RGB_MAP.put(BLUE.getRGB(), BLUE);
		RGB_MAP.put(MAGENTA.getRGB(), MAGENTA);
		RGB_MAP.put(CYAN.getRGB(), CYAN);
		RGB_MAP.put(GRAY.getRGB(), GRAY);

		RGB_MAP.put(DARK_RED.getRGB(), DARK_RED);
		RGB_MAP.put(DARK_GREEN.getRGB(), DARK_GREEN);
		RGB_MAP.put(DARK_YELLOW.getRGB(), DARK_YELLOW);
		RGB_MAP.put(DARK_BLUE.getRGB(), DARK_BLUE);
		RGB_MAP.put(DARK_MAGENTA.getRGB(), DARK_MAGENTA);
		RGB_MAP.put(DARK_CYAN.getRGB(), DARK_CYAN);
		RGB_MAP.put(DARK_GRAY.getRGB(), DARK_GRAY);

		RGB_MAP.put(SELECTION_BACKGROUND.getRGB(), WHITE);
		RGB_MAP.put(SELECTION_FOREGROUND.getRGB(), WHITE);

		RGB_MAP.put(INFO_BACKGROUND.getRGB(), INFO_BACKGROUND);
		RGB_MAP.put(INFO_FOREGROUND.getRGB(), INFO_FOREGROUND);

		RGB_MAP.put(LIST_BACKGROUND.getRGB(), LIST_BACKGROUND);
		RGB_MAP.put(LIST_FOREGROUND.getRGB(), LIST_FOREGROUND);
		RGB_MAP.put(LIST_SELECTION.getRGB(), LIST_SELECTION);
		RGB_MAP.put(LIST_SELECTION_TEXT.getRGB(), LIST_SELECTION_TEXT);

		RGB_MAP.put(WIDGET_BACKGROUND.getRGB(), WIDGET_BACKGROUND);
		RGB_MAP.put(WIDGET_FOREGROUND.getRGB(), WIDGET_FOREGROUND);
		RGB_MAP.put(WIDGET_BORDER.getRGB(), WIDGET_BORDER);
		RGB_MAP.put(WIDGET_DARK_SHADOW.getRGB(), WIDGET_DARK_SHADOW);
		RGB_MAP.put(WIDGET_NORMAL_SHADOW.getRGB(), WIDGET_NORMAL_SHADOW);
		RGB_MAP.put(WIDGET_LIGHT_SHADOW.getRGB(), WIDGET_LIGHT_SHADOW);
		RGB_MAP.put(WIDGET_HIGHLIGHT_SHADOW.getRGB(), WIDGET_HIGHLIGHT_SHADOW);

		RGB_MAP.put(TITLE_BACKGROUND.getRGB(), TITLE_BACKGROUND);
		RGB_MAP.put(TITLE_FOREGROUND.getRGB(), TITLE_FOREGROUND);
		RGB_MAP.put(TITLE_INACTIVE_BACKGROUND.getRGB(), TITLE_INACTIVE_BACKGROUND);
		RGB_MAP.put(TITLE_INACTIVE_FOREGROUND.getRGB(), TITLE_INACTIVE_FOREGROUND);
		RGB_MAP.put(TITLE_BACKGROUND_GRADIENT.getRGB(), TITLE_BACKGROUND_GRADIENT);
		RGB_MAP.put(TITLE_INACTIVE_BACKGROUND_GRADIENT.getRGB(), TITLE_INACTIVE_BACKGROUND_GRADIENT);
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

		Color color = RGB_MAP.get(rgb);

		if(isNull(color))
			RGB_MAP.put(rgb, color = new Color(Display.getCurrent(), rgb));

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
		for(Color color : RGB_MAP.values())
			color.dispose();

		RGB_MAP.clear();
	}
}
