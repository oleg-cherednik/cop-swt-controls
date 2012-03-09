package cop.swt.example.examples;

import static cop.swt.extensions.ColorExtension.WHITE;

import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

import cop.i18.LocalizationExt;
import cop.swt.extensions.ColorExtension;

public class NumericExample implements IExample
{
	@Override
	public void run(Composite parent)
	{
		parent = createComposite(parent);
		
		Locale locale = LocalizationExt.RU;
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		
		System.out.println();
		

		
//		DoubleNumeric num = new DoubleNumeric(parent, SWT.CENTER);
//		GridData layoutData = new GridData();
//		
//		layoutData.widthHint = 40;
//		
//		num.setLayoutData(layoutData);
//		
//		num.nf.setMinimumFractionDigits(1);
//		num.nf.setMinimumIntegerDigits(2);
//		num.nf.setMaximumFractionDigits(1);
//		num.nf.setMaximumIntegerDigits(2);
//		
//		Spinner spinner = new Spinner(parent, SWT.NONE);
		
		

	}
	
	private static Composite createComposite(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);

		composite.setBackground(WHITE);
		composite.setLayout(createLayout(2));
		composite.setLayoutData(createLayoutData(2));
		composite.setBackground(ColorExtension.CYAN);

		return composite;
	}

	private static Layout createLayout(int numColumns)
	{
		GridLayout layout = new GridLayout(numColumns, false);

		return layout;
	}

	private static Object createLayoutData(int horizontalSpan)
	{
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);

		layoutData.horizontalSpan = horizontalSpan;

		return layoutData;
	}

}
