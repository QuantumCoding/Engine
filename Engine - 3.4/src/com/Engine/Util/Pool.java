package com.Engine.Util;

public class Pool<E> {

	@FunctionalInterface
	public static interface Instantiator<E> { public E create(); }
	
	private Instantiator<E> instantiator;
	private Object[] pool;
	private int count;
	
	public Pool(Instantiator<E> instantiator, int initSize) {
		if(initSize < 2) throw new IllegalArgumentException("Inishal Size must be > 2");
		
		pool = new Object[initSize];
		this.instantiator = instantiator;
		for(int i = 0; i < initSize; i ++) returnEntry(instantiator.create());
	}
	
	@SuppressWarnings("unchecked")
	public E checkOut() {
		if(count == 0) pool[count ++] = instantiator.create();
		return (E) pool[-- count];
	}
	
	public void returnEntry(E e) {
		if(count == pool.length) {
			Object[] expand = new Object[(int) (pool.length * 1.5)];
			System.arraycopy(pool, 0, expand, 0, pool.length);
			pool = expand;
		}
		
		pool[count ++] = e;
	}
	
	public int size() { return count; }
	public boolean isEmpty() { return count <= 0; }
}
