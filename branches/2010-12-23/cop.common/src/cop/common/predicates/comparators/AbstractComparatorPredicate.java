package cop.common.predicates.comparators;

import java.util.Comparator;

public abstract class AbstractComparatorPredicate<T> implements ComparatorPredicate<T>
{
	protected final Comparator<T> cmp;

	public AbstractComparatorPredicate(Comparator<T> cmp)
	{
		if(cmp == null)
			throw new NullPointerException("Comparator can't be null");

		this.cmp = cmp;
	}

	/*
	 * Predicate
	 */

	@Override
	public String getName()
	{
		return toString();
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return "Predicate: " + getClass().getSimpleName();
	}
}
