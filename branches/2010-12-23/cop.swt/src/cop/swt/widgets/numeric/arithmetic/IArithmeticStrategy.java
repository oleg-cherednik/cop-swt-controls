package cop.swt.widgets.numeric.arithmetic;

public interface IArithmeticStrategy<T extends Number>
{
	T add(T value, T amount);

	T sub(T value, T amount);

	T mul(T value, T amount);

	T div(T value, T amount);

	T parse(String str);

	T getValue(double value);

	T abs(T value);
}
