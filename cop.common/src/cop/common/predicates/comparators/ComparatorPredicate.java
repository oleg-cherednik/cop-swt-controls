package cop.common.predicates.comparators;

import cop.common.predicates.interfaces.Predicate;

public interface ComparatorPredicate<T> extends Predicate
{
	boolean check(T obj1, T obj2);
}
