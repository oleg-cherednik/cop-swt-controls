package cop.swt.widgets.annotations.contents;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.CommonExtension.isNull;
import static cop.swt.widgets.annotations.services.ImageTextViewService.*;

import org.eclipse.core.runtime.Assert;

import cop.swt.widgets.annotations.ImageTextView;
import cop.swt.widgets.enums.ImageTextViewEnum;

public class ImageTextViewContent
{
	private ImageTextViewEnum view = DEF_IMAGETEXTVIEW;

	public ImageTextViewContent(ImageTextViewEnum view)
	{
		Assert.isNotNull(view);
		
		this.view = view;
	}

	public ImageTextViewContent(ImageTextView annotation)
	{
		if(isNull(annotation))
			return;

		this.view = annotation.view();
	}

	public ImageTextViewEnum getView()
	{
		return view;
	}

	public void setView(ImageTextViewEnum view)
	{
		this.view = isNotNull(view) ? view : DEF_IMAGETEXTVIEW;
	}

	@Override
	public String toString()
	{
		return "[view: " + view + "]";
	}
}
