package com.Engine.Demo.XML;

public interface XMLTypeParser<E> {
	public String encode(E e);
	public E parse(String s);
}
