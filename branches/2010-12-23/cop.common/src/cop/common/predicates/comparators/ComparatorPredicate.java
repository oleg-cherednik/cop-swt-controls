package cop.common.predicates.comparators;

import cop.common.predicates.Predicate;

public interface ComparatorPredicate<T> extends Predicate
{
	boolean check(T obj1, T obj2);
}
