/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.html.example.news;

import static java.util.Calendar.DECEMBER;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 05.01.2011
 */
public final class NewsGenerator extends Thread
{
	public static final List<NewsTO> news = new ArrayList<NewsTO>(5);

	static
	{
		news.add(createNews1());
		news.add(createNews2());
		news.add(createNews3());
	}

	private static NewsTO createNews1()
	{
		LoggerFactory.getLogger("NewsGenerator").debug("NewsGenerator1");
		Calendar date = Calendar.getInstance();
		date.set(2010, DECEMBER, 26, 17, 30, 00);

		String section = "Politics";
		String reporter = "Rosa Prince, Political Correspondent";
		String title = "John Redwood: Liberal Democrats are taking credit for 'nice things' Government does";
		String note = "A leading Conservative has accused Liberal Democrats within the Coalition of seeking to claim credit for the ‘nice things’ the Government is doing.";
		String body = "<b><font color=\"darkcyan\">Nick Clegg</font></b>, the Lib Dem Deputy Prime Minister, is understood to have ordered his MPs to embrace their Coalition partners amid fears that strains between the two sides could begin to undermine the Government.<p>Last week, a number of ministers were embarrassed after making highly critical remarks about the Tories to reporters from The Daily Telegraph posing as constituents.<p>With both sides said to be bruised by the row, John Redwood, a former Conservative Cabinet minister, rejected angrily suggestions that the role of the <b><font color=\"darkcyan\">Liberal Democrats</font></b>, in government was to \"bridle the instincts\" of the Conservatives.<p>He accused the party’s MPs of seeking to claim the credit for policies which the Tories had also campaigned for, such as cutting income tax for the low paid, channelling funding to poorer pupils and restoring civil liberties.<p>In words which will alarm <b><font color=\"darkcyan\">David Cameron</font></b> and Mr Clegg, Mr Redwood also warned of real differences between the two parties on issues such as the European Union.";

		return new NewsTO(section, reporter, title, date, note, body);
	}

	private static NewsTO createNews2()
	{
		LoggerFactory.getLogger("NewsGenerator").debug("NewsGenerator2");
		Calendar date = Calendar.getInstance();
		date.set(2010, DECEMBER, 26, 18, 22, 00);

		String section = "Science Obituaries";
		String reporter = "Micle Rosennberg, correspondent";
		String title = "Walter Haeussermann";
		String note = "Walter Haeussermann, who died on December 8 aged 96, was a key member of the rocket team under Wernher von Braun which was spirited away from Germany by the Americans after the Second World War, under the noses of the Russians, to work on the American space programme.";
		String body = "Haeussermann first met von Braun during the Second World War in Germany, and helped him to develop the V-2 rockets that were launched against London. In particular he worked with gyroscopes and accelerometers to develop simulators and used analogue computers to test and design rocket guidance and control systems – the same fields in which he would later work in the American space programme.<p>At the beginning of May 1945 von Braun decided to transfer his loyalties from the crumbling Third Reich to the Americans, loading his team – along with their rocket secrets – into lorries and heading for the American lines. On May 2, with his brother Magnus, von Braun surrendered to the 44th Infantry Division of the US Army. He was finally smuggled out of Germany as part of a secret American mission code-named Operation Paperclip.<p>Due to the fact that his wife was ill, however, Haeussermann was unable to join von Braun and other colleagues, who had soon established themselves at Fort Bliss, Texas. But he remained in contact with the group and came with his wife to America in 1947.<p>Haeussermann rejoined von Braun’s team, working on ballistic missile guidance and control engineering at Fort Bliss. The group was later moved to Redstone Arsenal where he was in charge of guidance and control systems for the Redstone rocket, the US Army’s first ballistic missile; the Jupiter rocket, America’s first intercontinental ballistic missile; and Explorer I, the country’s first satellite. By 1954, he had taken American citizenship.<p>After Nasa was formed in 1960, he became director of the Guidance and Control Laboratory and head of the so-called Astrionics Division at the Marshall Space Flight Centre in Huntsville, Alabama. There he was responsible for guidance, navigation, electrical and computer systems for the Saturn project rockets, including the Saturn V that took the first men to the moon on Apollo 11 in 1969.";

		return new NewsTO(section, reporter, title, date, note, body);
	}

	private static NewsTO createNews3()
	{
		LoggerFactory.getLogger("NewsGenerator").debug("NewsGenerator3");
		Calendar date = Calendar.getInstance();
		date.set(2010, DECEMBER, 25, 23, 02, 00);

		String section = "Science";
		String reporter = "Machmud Abdurakhman";
		String title = "Indian rocket explodes after take-off";
		String note = "An Indian space rocket carrying the country's largest communications satellite into orbit has exploded in mid-air.";
		String body = "Live television images showed the rocket exploding into plumes of smoke and fire less than a minute after it launched from the Sriharikota space center in Andhra Pradesh state this afternoon.<p>The Indian Space Reserach Organisation said it believed the explosion was caused by an electronic failure secondsafter take-off that led to the flight taking a higher angle.<p>It is the second launch failure for India's space agency this year after a similar rocket on a developmental flight plunged into the Bay of Bengal in April.<p>India is the fifth country after United States, Russia, China and France to enter the commercial satellite launch market and is planning its first manned space flight in 2016.";

		return new NewsTO(section, reporter, title, date, note, body);

	}

	/*
	 * Runnable
	 */

	@Override
	public void run()
	{

	}
}
