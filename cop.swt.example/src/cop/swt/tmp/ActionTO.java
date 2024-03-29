package cop.swt.tmp;

import static cop.extensions.CommonExt.isNotNull;
import static cop.swt.widgets.enums.ImageTextViewEnum.IMAGE_ONLY;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

import cop.i18.annotations.i18n;
import cop.swt.tmp.localization.BundleEnum1;
import cop.swt.tmp.localization.BundleEnum2;
import cop.swt.tmp.localization.CountEnum;
import cop.swt.tmp.localization.Name;
import cop.swt.widgets.annotations.Column;
import cop.swt.widgets.annotations.Currency;
import cop.swt.widgets.annotations.ImageTextView;
import cop.swt.widgets.annotations.Label;
import cop.swt.widgets.annotations.Percent;

public class ActionTO implements Cloneable
{
	@Label(name = "model")
	// @Column(name = "user name", order = 1, hideable = false)
	// private String userName;
	@Column(name = "user name", order = 1, sortable = true, hideable = false)
	private Name userName;
	@Column(name = "date", order = 2, movabale = true, alignment = SWT.LEFT, sortable = true, hideable = true)
	private Calendar date;
	@Column(name = "number", order = 3, movabale = true, alignment = SWT.CENTER, sortable = true, hideable = true)
	private int number;
	@Currency
	@Column(name = "price", order = 4, movabale = true, alignment = SWT.RIGHT, sortable = true, hideable = true)
	private double price;
	@Percent
	@ImageTextView(view = IMAGE_ONLY)
	@Column(name = "percent", order = 5, movabale = true, sortable = true, hideable = false)
	private double percent;
	@Column(name = "amount", order = 6, movabale = true, sortable = true, hideable = false, emptyable = false)
	private Double amount;
	// @Column(order = 4)
	// private List<String> titles = new ArrayList<String>();
	// @Label
	@Column(name = "marker", order = 7, sortable = true, hideable = true)
	@ImageTextView(view = IMAGE_ONLY)
	private boolean marker;
	@Label
	@Column(name = "count", order = 7, sortable = true, hideable = false)
	private CountEnum count;
	@Column(name = "color", order = 8, sortable = true, hideable = false)
	private RGB color;

	public ActionTO()
	{}

	public ActionTO(Name userName)
	{
		this.userName = userName;
	}

	public ActionTO(Name userName, Calendar date, int number, double price, double percent, Double amount,
	                boolean marker, CountEnum count, RGB color)
	{
		this.userName = userName;
		this.date = (Calendar)date.clone();
		this.number = number;
		this.price = price;
		this.percent = percent;
		this.amount = amount;
		// this.titles.addAll(titles);
		this.marker = marker;
		this.count = count;
		this.color = color;
	}

	@Override
	public ActionTO clone()
	{
		ActionTO action = new ActionTO(userName);

		action.date = (Calendar)date.clone();
		action.number = number;
		action.price = price;
		action.percent = percent;
		action.amount = amount;
		// this.titles.addAll(titles);
		action.marker = marker;
		action.count = count;
		action.color = color;

		return action;
	}

	// public List<String> getTitles()
	// {
	// return titles;
	// }
	//
	// public void setTitles(List<String> titles)
	// {
	// this.titles.clear();
	// this.titles.addAll(titles);
	// }

	public Name getUserName()
	{
		return userName;
	}

	public Calendar getDate()
	{
		return date;
	}

	public int getNumber()
	{
		return number;
	}

	public double getPrice()
	{
		return price;
	}

	public double getPercent()
	{
		return percent;
	}

	public Double getAmount()
	{
		return amount;
	}

	public boolean isMarker()
	{
		return marker;
	}

	public CountEnum getCount()
	{
		return count;
	}

	public RGB getColor()
	{
		return color;
	}

	public void setUserName(Name userName)
	{
		this.userName = userName;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public void setPercent(double percent)
	{
		this.percent = percent;
	}

	public void setAmount(Double amount)
	{
		this.amount = amount;
	}

	public void setMarker(boolean marker)
	{
		this.marker = marker;
	}

	public void setCount(CountEnum count)
	{
		this.count = count;
	}

	public void setColor(RGB color)
	{
		this.color = color;
	}

	@i18n
	public static String getLocalizedName(String key, Locale locale)
	{
		// System.out.println("getLocalizedName(" + key + ", " + locale + ")");
		if(key.equals("userName"))
			return BundleEnum1.COL_NAME.i18n(locale);
		if(key.equals("date"))
			return BundleEnum1.COL_DATE.i18n(locale);
		if(key.equals("number"))
			return BundleEnum1.COL_NUMBER.i18n(locale);
		if(key.equals("price"))
			return BundleEnum1.COL_PRICE.i18n(locale);
		if(key.equals("percent"))
			return BundleEnum2.COL_PERCENT.i18n(locale);
		if(key.equals("amount"))
			return BundleEnum2.COL_AMOUNT.i18n(locale);
		if(key.equals("marker"))
			return BundleEnum2.COL_MARKET.i18n(locale);
		if(key.equals("count"))
			return BundleEnum2.COL_COUNT.i18n(locale);
		if(key.equals("color"))
			return BundleEnum2.COL_COLOR.i18n(locale);

		return null;
	}

	@Override
	public String toString()
	{
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		StringBuilder buf = new StringBuilder();

		buf.append("[");
		buf.append("userName (" + userName + ")");
		buf.append(", date (" + (isNotNull(date) ? df.format(date.getTime()) : "---") + ")");
		buf.append(", number (" + number + ")");
		buf.append(", price (" + price + ")");
		buf.append(", percent (" + percent + ")");
		// buf.append(", titleName (" + titleName + ")");
		buf.append(", amount (" + amount + ")");
		buf.append(", marker (" + marker + ")");
		buf.append(", count (" + count + ")");
		buf.append(", color (" + color + ")");
		buf.append("]");

		return "" + buf;
	}
}
