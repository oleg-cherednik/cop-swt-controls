import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

class Person
{
	private String firstName;
	private String lastName;

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setFirstName(String str)
	{
		firstName = str;
	}

	public void setLastName(String str)
	{
		lastName = str;
	}
}

public class Test
{
	private static long FRACT_LEN = 1000000; // 6 fractional digits

	public static void main(String[] args) throws ParseException, FileNotFoundException
	{
		FileOutputStream os = new FileOutputStream("C:/cust.xml");
		XMLEncoder encoder = new XMLEncoder(os);
		Person p = new Person();
		p.setFirstName("John");
		encoder.writeObject(p);
		encoder.close();

		Locale locale1 = Locale.getDefault();
		printNames();
		// Double val = add(-4.1, 0.1);

		// Locale locale = Locale.getDefault();
		// //NumberFormat df = DecimalFormat.getInstance(locale);
		//
		// DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(locale);
		//
		// df.setCurrency(Currency.getInstance(locale));

		// System.out.println(locale);
		// System.out.println();
		// System.out.println(df.format(1234));
		// System.out.println(df.format(1234.5674));

		Locale[] locales = { new Locale("ru", "RU") };// , new Locale("en", "GB"), new Locale("de", "DE") };

		for(Locale locale : locales)
		{
			// NumberFormatter foo = new NumberFormatter(locale);
			// NumberFormat df = NumberFormat.getCurrencyInstance(locale);
			//
			// System.out.println(df.format(12.35035));
			//
			//
			// System.out.println(df.getCurrency());
			//
			// foo.setFixTextLimit(false, -2000, 2000, 0.0001);
			//
			// // lengthNumber(12.1, locale);
			//
			// foo.setGroupingUsed(false);
			// // foo.setDecimalSeparatorAlwaysShown(true);
			//
			// df.setMinimumIntegerDigits(0);
			//
			// // df.setMinimumFractionDigits(5);
			// // df.setMaximumFractionDigits(5);
			//
			// // int a = df.getMaximumFractionDigits();
			// // int b = df.getMinimumFractionDigits();
			//
			// // df.applyPattern("###,###.000");
			// // System.out.print(locale + ": ");
			// System.out.println(foo.format(0.12));
			// System.out.println(foo.format(0.1234));
			// System.out.println(foo.format(10));
			// System.out.println(foo.format(1234.5674));
			// System.out.println(foo.format(-10));
			// System.out.println(foo.format(-1234.5674));

		}

		// df.format(val);

		// double val = -10.1003;
		// for(int i = 0; i < 100; i++)
		// {
		// val += 0.5;
		// val = df.parse(df.format(val)).doubleValue();
		// System.out.println(val);
		// }

		// System.out.println(val);

		int a = 0;
		a++;

	}

	public static void printNames()
	{
		Locale[] locales = new Locale[] { Locale.US, Locale.UK, Locale.GERMANY, new Locale("ru", "RU") };

		for(Locale locale : locales)
		{
			Currency currency = Currency.getInstance(locale);

			String code = currency.getCurrencyCode();
			String symbol = currency.getSymbol(locale);

			System.out.println(locale + ": " + code + " (" + symbol + ")");
			
		}

		// Locale locale = Locale.getDefault();
		// String[] names = LocalizationExtension.getLanguagesName(LanguageEnum.values(), locale);
		//
		//
		// for(String name : names)
		// System.out.println(name);
	}

	public static int[] lengthNumber(double number)
	{
		return lengthNumber(number, Locale.getDefault());
	}

	public static int[] lengthNumber(double number, Locale locale)
	{
		DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(locale);

		df.setMaximumFractionDigits(10);
		df.setMaximumIntegerDigits(10);

		// df.applyPattern("##########.##########");

		df.setGroupingUsed(false);
		String str = df.format(number);

		String[] parts1 = str.split("\\" + df.getDecimalFormatSymbols().getDecimalSeparator());
		int[] res = new int[] { 0, 0 };

		for(int i = 0; i < parts1.length; i++)
			res[i] = parts1[i].length();

		return res;
	}

	public static double max(double... numbers)
	{
		if(numbers == null || numbers.length == 0)
			return 0;

		double max = Double.MIN_VALUE;

		for(double number : numbers)
			max = Math.max(max, number);

		return max;
	}

	public static double min(double... numbers)
	{
		if(numbers == null || numbers.length == 0)
			return 0;

		double min = Double.MAX_VALUE;

		for(double number : numbers)
			min = Math.min(min, number);

		return min;
	}

	// private static Double add(Double value, Double amount)
	// {
	// double tmp1 = value.doubleValue();
	//
	// int tmpInt = (int)tmp1;
	//
	// double tmp2 = value * FRACT_LEN;
	//
	// BigDecimal valD = new BigDecimal(value);
	// BigDecimal amD = new BigDecimal(amount);
	//
	// BigDecimal resD = valD.subtract(amD);
	//
	// double dd = resD.doubleValue();
	//
	// int _value = (int)(value * FRACT_LEN);
	// int _amount = (int)(amount * FRACT_LEN);
	// int res = _value + _amount;
	// String str = ((value < 0) ? "-" : "") + Math.abs(value) / FRACT_LEN + "." + Math.abs(value) % FRACT_LEN;;
	//
	// return Double.parseDouble(str);
	// }

	// private static Double floor(Double value, int fracLen)
	// {
	// int _size = (int)Math.pow(10, fracLen);
	// int _value = (int)(value * _size);
	// String str = "" + _value / _size + "." + res % _size;
	//
	// }

}

enum NumberPartEnum
{
	PART_INTEGER,
	PART_FRACTIONAL
}

class NumberFormatter
{
	private final Locale locale;
	private DecimalFormat df;

	public NumberFormatter(double min, double max, double inc)
	{
		this(min, max, inc, Locale.getDefault());
	}

	public NumberFormatter(double min, double max, double inc, Locale locale)
	{
		this.locale = locale;
		this.df = (DecimalFormat)NumberFormat.getInstance(locale);
	}

	/**
	 * Specialization of format.
	 * 
	 * @exception ArithmeticException if rounding is needed with rounding mode being set to RoundingMode.UNNECESSARY
	 * @see java.text.Format#format
	 */
	public String format(double number)
	{
		return df.format(number);
	}

	/**
	 * Specialization of format.
	 * 
	 * @exception ArithmeticException if rounding is needed with rounding mode being set to RoundingMode.UNNECESSARY
	 * @see java.text.Format#format
	 */
	public final String format(long number)
	{
		return df.format(number);
	}

	/**
	 * Set whether or not grouping will be used in this format.
	 * 
	 * @see #isGroupingUsed
	 */
	public void setGroupingUsed(boolean newValue)
	{
		df.setGroupingUsed(newValue);
	}

	/**
	 * Returns true if grouping is used in this format. For example, in the English locale, with grouping on, the number
	 * 1234567 might be formatted as "1,234,567". The grouping separator as well as the size of each group is locale
	 * dependant and is determined by sub-classes of NumberFormat.
	 * 
	 * @see #setGroupingUsed
	 */
	public boolean isGroupingUsed()
	{
		return df.isGroupingUsed();
	}

	/**
	 * Allows you to get the behavior of the decimal separator with integers. (The decimal separator will always appear
	 * with decimals.)
	 * <P>
	 * Example: Decimal ON: 12345 -> 12345.; OFF: 12345 -> 12345
	 */
	public boolean isDecimalSeparatorAlwaysShown()
	{
		return df.isDecimalSeparatorAlwaysShown();
	}

	/**
	 * Allows you to set the behavior of the decimal separator with integers. (The decimal separator will always appear
	 * with decimals.)
	 * <P>
	 * Example: Decimal ON: 12345 -> 12345.; OFF: 12345 -> 12345
	 */
	public void setDecimalSeparatorAlwaysShown(boolean newValue)
	{
		df.setDecimalSeparatorAlwaysShown(newValue);
	}

	/**
	 * Sets the maximum number of digits allowed in the integer portion of a number. For formatting numbers other than
	 * <code>BigInteger</code> and <code>BigDecimal</code> objects, the lower of <code>newValue</code> and 309 is used.
	 * Negative input values are replaced with 0.
	 * 
	 * @see NumberFormat#setMaximumIntegerDigits
	 */
	public void setMaximumIntegerDigits(int newValue)
	{
		df.setMaximumIntegerDigits(newValue);
	}

	/**
	 * Sets the minimum number of digits allowed in the integer portion of a number. For formatting numbers other than
	 * <code>BigInteger</code> and <code>BigDecimal</code> objects, the lower of <code>newValue</code> and 309 is used.
	 * Negative input values are replaced with 0.
	 * 
	 * @see NumberFormat#setMinimumIntegerDigits
	 */
	public void setMinimumIntegerDigits(int newValue)
	{
		df.setMinimumIntegerDigits(newValue);
	}

	/**
	 * Sets the maximum number of digits allowed in the fraction portion of a number. For formatting numbers other than
	 * <code>BigInteger</code> and <code>BigDecimal</code> objects, the lower of <code>newValue</code> and 340 is used.
	 * Negative input values are replaced with 0.
	 * 
	 * @see NumberFormat#setMaximumFractionDigits
	 */
	public void setMaximumFractionDigits(int newValue)
	{
		df.setMaximumFractionDigits(newValue);
	}

	/**
	 * Sets the minimum number of digits allowed in the fraction portion of a number. For formatting numbers other than
	 * <code>BigInteger</code> and <code>BigDecimal</code> objects, the lower of <code>newValue</code> and 340 is used.
	 * Negative input values are replaced with 0.
	 * 
	 * @see NumberFormat#setMinimumFractionDigits
	 */
	public void setMinimumFractionDigits(int newValue)
	{
		df.setMinimumFractionDigits(newValue);
	}

	/**
	 * Gets the maximum number of digits allowed in the integer portion of a number. For formatting numbers other than
	 * <code>BigInteger</code> and <code>BigDecimal</code> objects, the lower of the return value and 309 is used.
	 * 
	 * @see #setMaximumIntegerDigits
	 */
	public int getMaximumIntegerDigits()
	{
		return df.getMaximumIntegerDigits();
	}

	/**
	 * Gets the minimum number of digits allowed in the integer portion of a number. For formatting numbers other than
	 * <code>BigInteger</code> and <code>BigDecimal</code> objects, the lower of the return value and 309 is used.
	 * 
	 * @see #setMinimumIntegerDigits
	 */
	public int getMinimumIntegerDigits()
	{
		return df.getMinimumIntegerDigits();
	}

	/**
	 * Gets the maximum number of digits allowed in the fraction portion of a number. For formatting numbers other than
	 * <code>BigInteger</code> and <code>BigDecimal</code> objects, the lower of the return value and 340 is used.
	 * 
	 * @see #setMaximumFractionDigits
	 */
	public int getMaximumFractionDigits()
	{
		return df.getMaximumFractionDigits();
	}

	/**
	 * Gets the minimum number of digits allowed in the fraction portion of a number. For formatting numbers other than
	 * <code>BigInteger</code> and <code>BigDecimal</code> objects, the lower of the return value and 340 is used.
	 * 
	 * @see #setMinimumFractionDigits
	 */
	public int getMinimumFractionDigits()
	{
		return df.getMinimumFractionDigits();
	}

	public void setFixTextLimit(boolean fixTextLimit, double minimum, double maximum, double inc)
	{
		if(!fixTextLimit)
		{
			DecimalFormat tmp = (DecimalFormat)NumberFormat.getInstance(locale);

			df.setMaximumIntegerDigits(tmp.getMaximumIntegerDigits());
			df.setMinimumIntegerDigits(tmp.getMinimumIntegerDigits());
			df.setMaximumFractionDigits(tmp.getMaximumFractionDigits());
			df.setMinimumFractionDigits(tmp.getMinimumFractionDigits());

			return;
		}

		int[] minLen = Test.lengthNumber(Math.abs(minimum));
		int[] maxLen = Test.lengthNumber(Math.abs(maximum));
		int[] incLen = Test.lengthNumber(inc);

		int maxInt = (int)Test.max(minLen[0], maxLen[0], incLen[0]);
		int maxFrac = (int)Test.max(minLen[1], maxLen[1], incLen[1]);

		df.setMaximumIntegerDigits(maxInt);
		df.setMinimumIntegerDigits(maxInt);
		df.setMaximumFractionDigits(maxFrac);
		df.setMinimumFractionDigits(maxFrac);

		return;
	}
}
