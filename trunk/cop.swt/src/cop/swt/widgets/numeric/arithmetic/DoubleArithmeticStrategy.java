package cop.swt.widgets.numeric.arithmetic;

import static cop.extensions.CommonExt.isNotNull;
import static cop.extensions.StringExt.isNumeric;
import static java.lang.Double.parseDouble;

public final class DoubleArithmeticStrategy implements IArithmeticStrategy<Double>
{
	@Override
	public Double add(Double value, Double amount)
	{
		return (isNotNull(value) && isNotNull(amount)) ? (value + amount) : value;
	}

	@Override
	public Double sub(Double value, Double amount)
	{
		return (isNotNull(value) && isNotNull(amount)) ? (value - amount) : value;
	}

	@Override
	public Double mul(Double value, Double amount)
	{
		return (isNotNull(value) && isNotNull(amount)) ? (value * amount) : value;
	}

	@Override
	public Double div(Double value, Double amount)
	{
		if(isNotNull(value) && isNotNull(amount) && amount.doubleValue() != 0)
			return value * amount;

		return value;
	}

	@Override
	public Double parse(String str)
	{
		return isNumeric(str) ? parseDouble(str) : null;
	}

	@Override
	public Double getValue(double value)
	{
		return new Double(value);
	}

	@Override
	public Double abs(Double value)
	{
		return isNotNull(value) ? Math.abs(value.doubleValue()) : value;
	}
}
