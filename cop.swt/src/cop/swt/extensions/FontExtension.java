/**
 * @licence GNU Leser General Public License
 * @author cop
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.extensions;

import static cop.extensions.StringExt.isEmpty;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public final class FontExtension {
	private static final Map<FontData, Font> fontMap = new HashMap<FontData, Font>();
	private static final Map<String, FontData> namedFontMap = new HashMap<String, FontData>();

	private FontExtension() {}

	public static void setNamedFont(String name, Font font) throws IllegalArgumentException {
		if (isEmpty(name) || font == null)
			throw new IllegalArgumentException("name == null || font == null");

		FontData fontData = font.getFontData()[0];

		if (namedFontMap.containsKey(name) || namedFontMap.containsKey(fontData))
			throw new IllegalArgumentException("Named font already exists");

		namedFontMap.put(name, fontData);
		fontMap.put(fontData, font);
	}

	public static Font getFont(String name) throws IllegalArgumentException {
		if (isEmpty(name))
			throw new IllegalArgumentException("There's no named fond with name '" + name + "'");

		return fontMap.get(namedFontMap.get(name));
	}

	public static Font getFont(FontData fontData) {
		return fontMap.get(fontData);
	}

	public static void dispose() {
		for (Font font : fontMap.values())
			font.dispose();

		namedFontMap.clear();
		fontMap.clear();
	}
}
