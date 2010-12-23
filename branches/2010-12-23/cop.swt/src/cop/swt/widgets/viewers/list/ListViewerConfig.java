package cop.swt.widgets.viewers.list;

import static cop.common.extensions.StringExtension.isNotEmpty;
import cop.swt.widgets.interfaces.LabelSupport;
import cop.swt.widgets.viewers.table.AbstractViewerConfig;

public class ListViewerConfig extends AbstractViewerConfig implements LabelSupport
{
	private String labelName;

	/*
	 * LabelSupport
	 */

	@Override
	public String getLabelName()
	{
		return labelName;
	}

	@Override
	public void setLabelName(String labelName)
	{
		this.labelName = isNotEmpty(labelName) ? labelName.trim() : null;
	}
}
