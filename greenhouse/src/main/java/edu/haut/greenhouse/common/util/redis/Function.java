package edu.haut.greenhouse.common.util.redis;

public interface Function<T, E> {

	public T callback(E e);
	
}
