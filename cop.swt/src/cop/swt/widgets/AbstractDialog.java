/**
 * $Id$
 * $HeadURL$
 */
package cop.swt.widgets;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractDialog extends Dialog
{
	private int BUTTON_WIDTH = 70;

	protected Button okButton, cancelButton;

	protected boolean isOK = true, isCancel = true;

	//private String okLabel = null, cancelLabel = null;

	protected AbstractDialog(Shell shell)
	{
		super(shell);

		init();
	}

	protected AbstractDialog(IShellProvider parentShell)
	{
		super(parentShell);

		init();
	}

	protected void init()
	{}

	public void setCreateButtons(boolean isOK, boolean isCancel)
	{
		this.isOK = isOK;
		this.isCancel = isCancel;
	}

	public void setLabelForButtons(String okLabel, String cancelLabel)
	{
//		this.okLabel = okLabel;
//		this.cancelLabel = cancelLabel;

		// if(okButton != null && PanbetGUIHelper.isTextNonEmpty(okLabel))
		// {
		// okButton.setText(okLabel);
		// }
		//
		// if(cancelButton != null && PanbetGUIHelper.isTextNonEmpty(cancelLabel))
		// {
		// cancelButton.setText(cancelLabel);
		// }
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		// if(isOK)
		// {
		// okButton = createButton(parent, IDialogConstants.OK_ID,
		// okLabel == null ? EclipseBundle.OK.i18n() : okLabel, true);
		// }
		//
		// if(isCancel)
		// {
		// cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, cancelLabel == null ? EclipseBundle.CANCEL
		// .i18n() : cancelLabel, false);
		// }

		doAfterCreateButtons(parent);
	}

	protected void doAfterCreateButtons(Composite parent)
	{
	// PanbetGUIHelper.setControlFont(parent, PanbetGUIHelper.getDefaultFont(), false);
	}

	@Override
	protected void setButtonLayoutData(Button button)
	{
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = convertHorizontalDLUsToPixels(BUTTON_WIDTH);
		Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		button.setLayoutData(data);
	}
}
