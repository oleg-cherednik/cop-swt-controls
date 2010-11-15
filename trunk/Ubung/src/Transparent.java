import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Transparent
{
	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);

		shell.setLayout(new GridLayout(2, false));

		ImageData imageData = new ImageData("c:\\idea.gif");
		int whitePixel = imageData.palette.getPixel(new RGB(255, 255, 255));
		imageData.transparentPixel = whitePixel;
		final Image transparentIdeaImage = new Image(display, imageData);

		Label transparentIdeaLabel = new Label(shell, SWT.NONE);
		transparentIdeaLabel.setImage(transparentIdeaImage);
		Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.addPaintListener(new PaintListener()
		{
			public void paintControl(PaintEvent e)
			{
				e.gc.drawImage(transparentIdeaImage, 0, 0);
			}
		});

		final Image ideaImage = new Image(display, imageData);
		Label label = new Label(shell, SWT.NONE);

//		label.setImage(ideaImage);
//		Canvas canvas = new Canvas(shell, SWT.NO_REDRAW_RESIZE);
//		canvas.addPaintListener(new PaintListener()
//		{
//			public void paintControl(PaintEvent e)
//			{
//				e.gc.drawImage(ideaImage, 0, 0);
//			}
//		});

		shell.pack();

		shell.open();
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
