/*
 * Created on 09.03.2006
 */

package cop.swt.example.panbet;



import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

//import com.panbet.gui.core.dialogs.MessageDialogEx;
//import com.panbet.gui.core.localization.CommonBundle;


public class TimeStampPicker extends Text
{
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	private SimpleDateFormat sdf_1 = new SimpleDateFormat("ddMMyyHHmmss");

	private SimpleDateFormat sdf_2 = new SimpleDateFormat("ddMMyyyyHHmmss");

	private String patternText = "";

	private static final String patternText_1 = "dd-mm-20yy 00:00:00";

	private static final String patternText_2 = "dd-mm-yyyy 00:00:00";

	private static final String patternText_3 = "dd-mm-20yy 00:00";

	private static final String patternText_4 = "dd-mm-yyyy 00:00";

	private Pattern pattern = Pattern.compile(
			"((0[1-9])|([12][0-9])|(3[01]))-((0[1-9])|(1[0-2]))-20[0-9]{2} (([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])");

	private Pattern pattern_1 = Pattern.compile(
			"(\\d\\d)-(\\d\\d)-(20\\d\\d) (\\d\\d):(\\d\\d):(\\d\\d)");

	private int maxValue[] = 
		{'3', '1', '1', '1', '9', '9', '9', '9', '9', '9', '2', '2', '3', '5', '5', '9', '5', '5', '9'};

	private int pass[] = 
		{0, 0, 1, 0, 0, 3, 2, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0};

	private boolean more2000 = true;

	private boolean onlyDate = false; 

	private static enum TimeIndexes {
		day10(0), day(1),
		month10(3), month(4),
		year1000(6), year100(7), year10(8), year(9),
		hour10(11), hour(12);
		
		private int index;
		
		private TimeIndexes(int index)
		{
			this.index = index;
		}
	}

	public TimeStampPicker(Composite parent, int style, boolean more2000, boolean isSecond)
	{
		super(parent, style);

		this.more2000 = more2000;

		if (!isSecond) {
			sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			sdf_2 = new SimpleDateFormat("ddMMyyyyHHmm");
		}

		if (more2000 && isSecond) {
			patternText = patternText_1;
			pattern = Pattern.compile(
					"((0[1-9])|([12][0-9])|(3[01]))-((0[1-9])|(1[0-2]))-20[0-9]{2} (([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])");
			pattern_1 = Pattern.compile("(\\d\\d)-(\\d\\d)-(20\\d\\d) (\\d\\d):(\\d\\d):(\\d\\d)");
			pass[TimeIndexes.year1000.index - 1] = 3;
			pass[TimeIndexes.year1000.index] = 2;
			pass[TimeIndexes.year100.index] = 1;
			sdf_1 = new SimpleDateFormat("ddMMyyHHmmss");
			
		}
		else if (!more2000 && isSecond) {
			patternText = patternText_2;
			pass[TimeIndexes.year1000.index - 1] = 1;
			pass[TimeIndexes.year1000.index] = 0;
			pass[TimeIndexes.year100.index] = 0;
			pattern = Pattern.compile(
					"((0[1-9])|([12][0-9])|(3[01]))-((0[1-9])|(1[0-2]))-[0-9]{4} (([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])");
			pattern_1 = Pattern.compile(
					"(\\d\\d)-(\\d\\d)-(\\d\\d\\d\\d) (\\d\\d):(\\d\\d):(\\d\\d)");
			sdf_1 = new SimpleDateFormat("ddMMyyyyHHmmss");
			
		}
		else if (more2000 && !isSecond) {
			patternText = patternText_3;
			pass[TimeIndexes.year1000.index - 1] = 3;
			pass[TimeIndexes.year1000.index] = 2;
			pass[TimeIndexes.year100.index] = 1;
			pattern = Pattern.compile(
					"((0[1-9])|([12][0-9])|(3[01]))-((0[1-9])|(1[0-2]))-20[0-9]{2} (([0-1][0-9])|(2[0-3])):([0-5][0-9])");
			pattern_1 = Pattern.compile(
					"(\\d\\d)-(\\d\\d)-(20\\d\\d) (\\d\\d):(\\d\\d)");
			sdf_1 = new SimpleDateFormat("ddMMyyHHmm");
		}
		else if (!more2000 && !isSecond) {
			patternText = patternText_4;
			pass[TimeIndexes.year1000.index - 1] = 1;
			pass[TimeIndexes.year1000.index] = 0;
			pass[TimeIndexes.year100.index] = 0;
			pattern = Pattern.compile(
					"((0[1-9])|([12][0-9])|(3[01]))-((0[1-9])|(1[0-2]))-[0-9]{4} (([0-1][0-9])|(2[0-3])):([0-5][0-9])");
			pattern_1 = Pattern.compile(
					"(\\d\\d)-(\\d\\d)-(\\d\\d\\d\\d) (\\d\\d):(\\d\\d)");
			sdf_1 = new SimpleDateFormat("ddMMyyyyHHmm");
		}

		this.setText(patternText);

		this.setTextLimit(patternText.length() + 2);
		this.addVerifyListener(verifier);
		this.addFocusListener(focusListener);
	}
	
	/*
	 * Only Date
	 */
	public TimeStampPicker(Composite parent, int style)
	{
		super(parent, style);
		
		onlyDate = true;
		
		this.more2000 = false;
		
		patternText = "dd-mm-yyyy";
		
		pass[TimeIndexes.year1000.index - 1] = 1;
		pass[TimeIndexes.year1000.index] = 0;
		pass[TimeIndexes.year100.index] = 0;
		
		pattern = Pattern.compile("((0[1-9])|([12][0-9])|(3[01]))-((0[1-9])|(1[0-2]))-[0-9]{4}");
		pattern_1 = Pattern.compile("(\\d\\d)-(\\d\\d)-(\\d\\d\\d\\d)");
	
		sdf_1 = new SimpleDateFormat("ddMMyyyy");
	
		this.setText(patternText);

		this.setTextLimit(patternText.length() + 2);
		
		this.addVerifyListener(verifier);
		this.addFocusListener(focusListener);
	}
	
	@Override
    protected void checkSubclass()
	{
	}

	@Override
    public Point computeSize (int wHint, int hHint, boolean changed) {
		return super.computeSize(wHint, hHint, changed);
	}

	public Timestamp getTimestamp()
	{
		if (getText().equals(patternText)) {
			return null;
		}
		try {
			if(onlyDate) {
				return new Timestamp(sdf.parse(getText() + " 00:00:00").getTime());
			}
			else {
				return new Timestamp(sdf.parse(getText()).getTime());
			}
		}
		catch (ParseException e) {
			return null;
		}
	}

	public Date getDate()
	{
		return (getTimestamp() == null) ? null : new Date(getTimestamp().getTime());
	}
	
	public Calendar getCalendar()
	{
		Date date = getDate();
		
		if(date == null)
			return null;
		
		Calendar day = Calendar.getInstance();
		
		day.setTime(date);
		
		return day;
	}

	public void setSQLDate(java.sql.Date date)
	{
		setDate(date);
	}

	public java.sql.Date getSQLDate()
	{
		return (getDate() != null) ? new java.sql.Date(getDate().getTime()) : null;
	}

	public void setDate(Date date)
	{
		setText(patternText);
		if(date != null){
			setText(sdf_1.format(date));
			setSelection(0);
		}
	}
	
	public void setCurrentTime()
	{
		setDate(new Timestamp(System.currentTimeMillis()));
	}

	private String transform(String text)
	{
		Timestamp ts = null;
		
		try {
			ts = new Timestamp(sdf_2.parse(text).getTime());
		}
		catch (ParseException e) {
			return text;
		}
		
		return sdf_1.format(ts);
	}

	VerifyListener verifier = new VerifyListener() {
		
		Pattern numbers = Pattern.compile("[0-9]+");

		boolean ignore = false;

		@Override
        public void verifyText(VerifyEvent e)
		{
			if (ignore) {
				return;
			}

			e.doit = false;
			StringBuffer buffer = new StringBuffer();

			if (e.text.equals(patternText)) {
				((Text) e.getSource()).setSelection(0, patternText.length());
				ignore = true;
				((Text) e.getSource()).insert(patternText);
				ignore = false;
				((Text) e.getSource()).setSelection(0, 0);

				return;
			}

			if (e.character == SWT.BS || e.character == SWT.DEL) {
				for (int i = e.start; i < e.end; i++) {

					if (i < patternText.length()) {
						buffer.append(patternText.charAt(i));
					}
					else {
						return;
					}
				}
				((Text) e.getSource()).setSelection(e.start, e.start + buffer.length());
				ignore = true;
				((Text) e.getSource()).insert(buffer.toString());
				ignore = false;
				((Text) e.getSource()).setSelection(e.start, e.start);

				return;
			}

			if (e.start > patternText.length() - 1) {
				return;
			}
			
			e.text = e.text.replaceAll("-", "");
			e.text = e.text.replaceAll(":", "");
			e.text = e.text.replaceAll(" ", "");
			e.text = transform(e.text);
			
			if (e.text.length() > 12 && more2000)
				return;

			if (e.text.length() > 14 && !more2000)
				return;

			if (!numbers.matcher(e.text).matches())
				return;

			String text = ((Text) e.getSource()).getText();
			
			if (text.charAt(TimeIndexes.day10.index) == '3') {
				maxValue[TimeIndexes.day.index] = '1';
			}
			else {
				maxValue[TimeIndexes.day.index] = '9';
			}

			if (text.charAt(TimeIndexes.month10.index) == '1') {
				maxValue[TimeIndexes.month.index] = '2';
			}
			else {
				maxValue[TimeIndexes.month.index] = '9';
			}

			if(!onlyDate) {
				if (text.charAt(TimeIndexes.hour10.index) == '2') {
					maxValue[TimeIndexes.hour.index] = '3';
				}
				else {
					maxValue[TimeIndexes.hour.index] = '9';
				}
			}
			
			int dividersCount = 0;
			for (int i = 0; i < e.text.length(); i++) {
				char ch = e.text.charAt(i);
				if ((ch < '0' && ch > '9') || ch > maxValue[e.start + i + dividersCount])
					return;

				int index = e.start + i + dividersCount;
				
				//if ((e.start + i + dividersCount) != 0) 
				{
					String rr = patternText.substring(index, index + pass[index]);
					buffer.append(rr);
					dividersCount = dividersCount + pass[index];
				}

				buffer.append(ch);
			}
			
			((Text) e.getSource()).setSelection(e.start, e.start + buffer.length());
			ignore = true;
			((Text) e.getSource()).insert(buffer.toString());
			ignore = false;
		}
	};

	FocusListener focusListener = new FocusListener() {

		String text = "";
		
		boolean used = false;
		
		@Override
        public void focusGained(FocusEvent e)
		{
			used = false;
		}

		@Override
        public void focusLost(FocusEvent e)
		{
			if (used) {
				return;
			}
					
			used = true;
			
			text = ((Text) e.getSource()).getText();
			if (text.equals(patternText))
				return;
			
			if (pattern.matcher(text).matches()) {
				
				Matcher mat = pattern_1.matcher(text);
				mat.matches();

				int day = Integer.parseInt(mat.group(1));
				int month = Integer.parseInt(mat.group(2)) - 1;
				int year = Integer.parseInt(mat.group(3));

				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);

				if (day > calendar.getActualMaximum(Calendar.DATE)) {

					//MessageDialogEx.showWarning(CommonBundle.DATE_BIGGER_ITS_MONTH.i18n());
					((Text) e.getSource()).setText(patternText);
				}
			}
			else {
				if (!((Text)e.getSource()).isDisposed())
					((Text)e.getSource()).setText(patternText);
				//MessageDialogEx.showWarning("Wrong date value");
			}
			
			used = false;
		}
	};
}
