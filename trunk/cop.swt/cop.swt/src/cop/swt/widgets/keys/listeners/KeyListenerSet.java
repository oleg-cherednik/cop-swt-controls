package cop.swt.widgets.keys.listeners;

import static cop.swt.widgets.keys.enums.KeyEnum.KEY_BACKSPACE;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_DELETE;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_GREY_DOT;
import static cop.swt.widgets.keys.enums.KeyEnum.KEY_SPACE;
import static cop.swt.widgets.keys.enums.KeyEnum.isArrow;
import static cop.swt.widgets.keys.enums.KeyEnum.isArrowLeftRight;
import static cop.swt.widgets.keys.enums.KeyEnum.isArrowUpDown;
import static cop.swt.widgets.keys.enums.KeyEnum.isSpace;
import static cop.swt.widgets.keys.enums.KeyEnum.parseKeyEnum;
import static java.lang.Character.isDigit;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import cop.swt.widgets.keys.enums.KeyEnum;

public final class KeyListenerSet
{
	private KeyListenerSet()
	{}

	// this listener must be called first
	public static KeyListener doClearKey = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit = false;
		}
	};

	public static KeyListener isDigit = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= isDigit(e.character);
		}
	};

	public static KeyListener isArrow = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= isArrow(e.keyCode);
		}
	};

	public static KeyListener isArrowUpDown = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= isArrowUpDown(e.keyCode);
		}
	};

	public static KeyListener isArrowLeftRight = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= isArrowLeftRight(e.keyCode);
		}
	};

	public static KeyListener isDelete = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= parseKeyEnum(e.keyCode) == KEY_DELETE;
		}
	};

	public static KeyListener isBackspase = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= parseKeyEnum(e.keyCode) == KEY_BACKSPACE;
		}
	};

	public static KeyListener isNegative = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= e.character == '-';
		}
	};

	public static KeyListener isDecimalSeparator()
	{
		return isDecimalSeparator(Locale.getDefault());
	}

	public static KeyListener isDecimalSeparator(Locale locale)
	{
		return isDecimalSeparator((DecimalFormat)NumberFormat.getInstance(locale));
	}

	public static KeyListener isDecimalSeparator(DecimalFormat df)
	{
		final char decimalSeparator = df.getDecimalFormatSymbols().getDecimalSeparator();

		KeyListener listener = new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				e.doit |= e.character == decimalSeparator;
			}
		};

		return listener;
	}

	public static KeyListener isGroupingSeparator()
	{
		return isGroupingSeparator(Locale.getDefault());
	}

	public static KeyListener isGroupingSeparator(Locale locale)
	{
		return isGroupingSeparator((DecimalFormat)NumberFormat.getInstance(locale));
	}

	public static KeyListener isGroupingSeparator(DecimalFormat df)
	{
		final char groupingSeparator = df.getDecimalFormatSymbols().getGroupingSeparator();

		KeyListener listener = new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if(parseKeyEnum(e.keyCode) == KEY_SPACE && isSpace(groupingSeparator))
					e.doit |= true;
				else
					e.doit |= e.character == groupingSeparator;
			}
		};

		return listener;
	}

	public static KeyListener isComma = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= e.character == ',';
		}
	};

	public static KeyListener isDot = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			e.doit |= e.character == '.';
		}
	};

	public static KeyListener isGrayDot = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			KeyEnum key = parseKeyEnum(e.keyCode);

			e.doit |= key == KEY_GREY_DOT;
		}
	};
}
