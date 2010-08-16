/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets.interfaces;

import cop.swt.widgets.annotations.Label;
import cop.swt.widgets.annotations.services.LabelService;

/**
 * Use to set specific label name.<br>
 * Usually this interface use to set <u>label name</u> to viewer (client) which can be used with <b>Label</b>
 * annotation.
 * 
 * @author cop (Cherednik, Oleg)
 * @see Label
 * @see LabelService
 */
public interface LabelSupport
{
	/**
	 * Sets new name.
	 * 
	 * @param name not null string
	 */
	void setLabelName(String labelName);

	/**
	 * Gets curent name.
	 * 
	 * @return not null string
	 */
	String getLabelName();
}
