package cop.system.store.interfaces;

public interface SystemPropertyPath<T>
{
	String[] getPropertyPath(T property);
}
