import java.util.StringTokenizer;

import cop.common.beans.JavaBean;

public class Ubung
{
	public static void main(String[] args)
	{
		String getter = "isAmount";
		String setter = "setAmount";
		String property = "amount";

		String tmp = JavaBean.getPropertyNameByMethodName(getter);
		String tmp1 = JavaBean.getSetterMethodNameByGetterMethodName(getter);
		
//		Calendar date = Calendar.getInstance();
//
//		System.out.println(date.getTimeInMillis());
//
//		date.clear(Calendar.MONTH);
//
//		System.out.println(date.getTimeInMillis());
//
//		SashForm sf = new SashForm(null, SWT.NONE);
		
		//sf.setWeights(weights)
		
		check("c0nst@money.simply.net");
		check("somebody@dev.com.ua");
		check("Name.Sur_name@gmail.com");
		check("useR33@somewhere.in.the.net");
		check("denis.@mail.ru");
		check("asads&&)&denis.@mail.ru");
		check("oleg.cherednik@mail.ru");
		check("abba-best@mail.ru");
		check("abba_best@mail.ru");
		check("abba best@mail.ru");
		check("abba_best@sdfsdf@mail.ru");
	}
	
	private static void check(String email)
	{
		System.out.println("email: " + email + ", valid: " + isEmailValid(email));
	}
	
	/*
	 * maxLength: 31
	 * letters: A-Z, a-z, 0-9, '_', '.', '-'
	 * first/last leter: A-Z, a-z, 0-9
	 */
	
	public static boolean isEmailValid(String email)
	{
		if(email == null || email.isEmpty())
			return true;
		
		String EMAIL_REGEX = "[a-zA-Z0-9]{1}(\\w|-|\u002e)*[a-zA-Z0-9]{1}@{1}[a-zA-Z0-9\u002e]+\u002e{1}[a-zA-Z]+";
		
		if(new StringTokenizer(email).countTokens() > 1)
			return false;
		
		return email.matches(EMAIL_REGEX) && email.substring(0, email.indexOf('@')).length() <= 31;
	}
}
