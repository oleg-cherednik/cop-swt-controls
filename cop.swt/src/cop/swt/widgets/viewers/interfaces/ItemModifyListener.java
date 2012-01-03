/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.viewers.interfaces;

import java.util.EventListener;

import cop.swt.widgets.viewers.PViewer;
import cop.swt.widgets.viewers.model.enums.ModificationTypeEnum;

/**
 * @author <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * @since 16.08.2010
 */
public interface ItemModifyListener<T> extends EventListener
{
	void itemModified(PViewer<T> widget, T item, ModificationTypeEnum type);
}
