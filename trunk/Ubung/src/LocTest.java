import java.util.Currency;
import java.util.Locale;


public class LocTest
{
	private Locale[] locales = new Locale[] { Locale.US, Locale.UK, Locale.GERMANY, new Locale("ru", "RU") };
	
	public static void main(String[] args)
	{
		LocTest obj = new LocTest();
		
		obj.foo();
		

		
	}
	
	private void foo()
	{
		for(Locale locale : locales)
		{
//			Currency curr = Currency.getInstance(locale);
//			curr.getSymbol(locale)
		}
	}
}
