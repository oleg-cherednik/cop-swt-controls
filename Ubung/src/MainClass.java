import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class MainClass extends ApplicationWindow {

  public MainClass() {

    super(null);
  }

  public static void main(String[] args) {

    ApplicationWindow w = new MainClass();

    w.setBlockOnOpen(true);

    w.open();

    Display.getCurrent().dispose();
  }

  protected Control createContents(Composite parent) {

    Composite container = new Composite(parent, SWT.NULL);

    this.getShell().setText("ButtonTest");

    GridLayout layout = new GridLayout();

    container.setLayout(layout);

    layout.numColumns = 4;

    layout.verticalSpacing = 9;

    Label label;

    label = new Label(container, SWT.NONE);

    label.setText("Button Type");

    label = new Label(container, SWT.NONE);

    label.setText("NONE");

    label = new Label(container, SWT.NONE);

    label.setText("BORDER");

    label = new Label(container, SWT.NONE);

    label.setText("FLAT");

    createLabel(container, SWT.NONE, "Push");

    createButton(container, SWT.PUSH, "button1");

    createButton(container, SWT.BORDER, "button2");

    createButton(container, SWT.FLAT, "button3");

    createLabel(container, SWT.NONE, "Radio");

    createButton(container, SWT.RADIO, "button1");

    createButton(container, SWT.RADIO | SWT.BORDER, "button2");

    createButton(container, SWT.RADIO | SWT.FLAT, "button3");

    createLabel(container, SWT.NONE, "Toggle");

    createButton(container, SWT.TOGGLE, "button1");

    createButton(container, SWT.TOGGLE | SWT.BORDER, "button2");

    createButton(container, SWT.TOGGLE | SWT.FLAT, "button3");

    createLabel(container, SWT.NONE, "Check");
    createButton(container, SWT.CHECK, "button1");

    createButton(container, SWT.CHECK | SWT.BORDER, "button2");

    createButton(container, SWT.CHECK | SWT.FLAT, "button3");

    createLabel(container, SWT.NONE, "Arrow | Left");

    createButton(container, SWT.ARROW | SWT.LEFT, "button1");

    createButton(container, SWT.ARROW | SWT.LEFT | SWT.BORDER, "button2");

    createButton(container, SWT.ARROW | SWT.LEFT | SWT.FLAT, "button3");

    return container;
  }

  public void createButton(Composite c, int style, String text) {

    Button b = new Button(c, style);

    b.setText(text);
  }

  public void createLabel(Composite c, int style, String text) {

    Label l = new Label(c, style);

    l.setText(text);
  }
}