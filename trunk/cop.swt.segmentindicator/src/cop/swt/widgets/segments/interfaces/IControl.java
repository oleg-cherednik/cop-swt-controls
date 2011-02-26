package cop.swt.widgets.segments.interfaces;

public interface IControl<N>
{
	boolean isVisible();

	void setVisible(boolean visible);

	void setValue(N value);

	N getValue();

	void dispose();
}
