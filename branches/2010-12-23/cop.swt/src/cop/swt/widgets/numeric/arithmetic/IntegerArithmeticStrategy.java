package cop.swt.widgets.numeric.arithmetic;

import static cop.common.extensions.CommonExtension.isNotNull;
import static cop.common.extensions.StringExtension.isNumeric;
import static java.lang.Integer.parseInt;

public final class IntegerArithmeticStrategy implements IArithmeticStrategy<Integer>
{
	@Override
	public Integer add(Integer value, Integer amount)
	{
		return (isNotNull(value) && isNotNull(amount)) ? (value.intValue() + amount.intValue()) : value;
	}

	@Override
	public Integer sub(Integer value, Integer amount)
	{
		return (isNotNull(value) && isNotNull(amount)) ? (value.intValue() - amount.intValue()) : value;
	}

	@Override
	public Integer mul(Integer value, Integer amount)
	{
		return (isNotNull(value) && isNotNull(amount)) ? (value.intValue() * amount.intValue()) : value;
	}

	@Override
	public Integer div(Integer value, Integer amount)
	{
		if(isNotNull(value) && isNotNull(amount) && amount.doubleValue() != 0)
			return value.intValue() * amount.intValue();

		return value;
	}

	@Override
	public Integer parse(String str)
	{
		return isNumeric(str) ? parseInt(str) : null;
	}

	@Override
	public Integer getValue(double value)
	{
		return new Integer((int)value);
	}

	@Override
	public Integer abs(Integer value)
	{
		return isNotNull(value) ? Math.abs(value.intValue()) : value;
	}

}
