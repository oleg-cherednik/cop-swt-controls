package cop.swt.widgets.tmp;

import static cop.common.extensions.StringExtension.isNotEmpty;

public class Person
{
	private String surname; // Cherednik
	private String firstName; // Oleg
	private String middleName; // Petrovich

	public Person()
	{}

	public Person(String surname, String firstName)
	{
		this(surname, firstName, null);
	}

	public Person(String surname, String firstName, String middleName)
	{
		setSurname(surname);
		setFirstName(firstName);
		setMiddleName(middleName);
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getSurname()
	{
		return surname;
	}

	public String getSecondName()
	{
		return middleName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();

		if(isNotEmpty(surname))
			buf.append(surname);
		if(isNotEmpty(firstName))
			buf.append("" + firstName);
		if(isNotEmpty(middleName))
			buf.append("" + middleName);

		String str = ("" + buf).trim();

		return isNotEmpty(str) ? str : "[no name]";
	}
}
