package com.Engine.Util;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.Engine.RenderEngine.Particles.Particle;

public class QLinkedList<E> extends AbstractList<E> implements List<E>, Deque<E>, Serializable {
	private static final long serialVersionUID = -6807467525495258772L;

	private transient Node<E> first, last;
	private int size;
	
	private static class Node<E> {
		Node<E> next, prev;
		E value;
		
		Node(Node<E> prev, E value, Node<E> next) {
			this.next = next; this.prev = prev;
			this.value = value;
		}
	}

	public QLinkedList() { }
	
	public QLinkedList(Collection<? extends E> c) {
		this(); addAll(c);
	}
	
	public void addFirst(E e) {
		Node<E> f = first;
		first = new Node<>(null, e, f);
		if(f == null) last = first;
		else f.prev = first;
		size ++;
	}

	public void addLast(E e) {
		Node<E> l = last;
		last = new Node<>(l, e, null);
		if(l == null) first = last;
		else l.next = last;
		size ++;
	}

	public boolean offerFirst(E e) { addFirst(e); return true; }
	public boolean offerLast(E e) { addLast(e); return false; }

	public E removeFirst() {
		if(first == null) throw new NoSuchElementException();
		
		Node<E> next = first.next;
		E value = first.value;
		first.next = first.prev = null;
		first.value = null;

		this.first = next;
		if(next == null) last = null;
		else next.prev = null;
		
		size --;
		return value;
	}

	public E removeLast() {
		if(last == null) throw new NoSuchElementException();
		
		Node<E> prev = last.next;
		E value = last.value;
		last.next = last.prev = null;
		last.value = null;

		this.last = prev;
		if(prev == null) first = null;
		else prev.next = null;
		
		size --;
		return value;
	}

	public E pollFirst() { return first == null ? null : removeFirst(); }
	public E pollLast() { return last == null ? null : removeLast(); }

	public E getFirst() {
		if(first == null) throw new NoSuchElementException();
		return first.value;
	}

	public E getLast() {
		if(last == null) throw new NoSuchElementException();
		return last.value;
	}

	public E peekFirst() { return first == null ? null : first.value; }
	public E peekLast() { return last == null ? null : last.value; }

	public boolean removeFirstOccurrence(Object o) {
		int index = indexOf(o);
		return index < 0 ? false : (remove(index) != null || o == null);
	}

	public boolean removeLastOccurrence(Object o) {
		int index = lastIndexOf(o);
		return index < 0 ? false : (remove(index) != null || o == null);
	}

	public boolean offer(E e) { return offerLast(e); }

	public E remove() { return removeFirst(); }
	public E poll() { return pollFirst(); }

	public E element() { return getFirst(); }

	public E peek() { return peekFirst(); }
	public void push(E e) { addFirst(e); }
	public E pop() { return removeFirst(); }

	public Iterator<E> descendingIterator() {
		return null;
	}

	public Iterator<E> iterator() { return listIterator(); }
	public ListIterator<E> listIterator() { return listIterator(0); }
	public ListIterator<E> listIterator(int index) {
		return null;
	}
	
	public static class MarkListIterator<E> implements ListIterator<E> {
		private Node<E> mark, current;
		private int markIndex, index;
		private QLinkedList<E> list;

		private MarkListIterator(QLinkedList<E> list, int index) {
			this.index = index - 1;
			this.list = list;
			this.current = new Node<>(null, null, list.getNode(index));
			
			this.mark = list.first;
			this.markIndex = 0;
		}
		
		public boolean hasNext() { return current.next != null; }
		public E next() {
			if(!hasNext()) 
				throw new NoSuchElementException();
			index ++;
			current = current.next;
			return current.value;
		}

		public boolean hasPrevious() { return current.prev != null; }
		public E previous() {
			if(!hasPrevious()) 
				throw new NoSuchElementException();
			index --;
			current = current.prev;
			return current.value;
		}

		public int nextIndex() { return index + 1; }
		public int previousIndex() { return index; }

		public void remove() { 
			if(index < 0)
				throw new NoSuchElementException();
			list.unlink(current);
			index --;
		}

		public void set(E e) {
			if(index < 0)
				throw new NoSuchElementException();
			current.value = e;
		}

		public void add(E e) { list.insert(e, index < 0 ? null : current); }
		
		public void mark() {
			this.mark = current;
			this.markIndex = index;
		}
		
		public void reset() {
			this.current = mark;
			this.index = markIndex;
		}
		
		public boolean swapDown() {
			if(current.prev == null) return false;
			
			E value = current.prev.value;
			current.prev.value = current.value;
			current.value = value;
//			previous();
			
			return true;
		}
	}
	
	public void sort(QLinkedList<Particle> list) {
		double lastValue = list.get(0).getDistance();
		for(MarkListIterator<Particle> iter = (MarkListIterator<Particle>) list.listIterator(1); iter.hasNext();) {
			if(iter.next().getDistance() > lastValue) {
				iter.previous(); iter.mark();
				while(iter.previous().getDistance() > iter.next().getDistance()) {
					iter.swapDown(); iter.previous();
				}
			}
		}
	}

	public int size() { return size; }
	public boolean isEmpty() { return size > 0; }
	public boolean contains(Object o) { return indexOf(o) != -1; }

	public Object[] toArray() { return toArray(new Object[size]); }

	public <T> T[] toArray(T[] a) {
		if(a.length < size) a = Arrays.copyOf(a, size);
		
		int index = 0; Object[] arr = a;
		for(Node<E> node = first; node.next != null; node = node.next, index ++)
			arr[index] = node.value;
		return a;
	}

	public boolean add(E e) { addLast(e); return true; }
	public boolean remove(Object o) {
		for(Node<E> node = first; node.next != null; node = node.next)
			if(node.value.equals(o)) { unlink(node); return true; }
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for(Node<E> node = first; node.next != null; node = node.next)
			if(!c.contains(node.value)) return false;
		return true;
	}

	public boolean addAll(Collection<? extends E> c) { return addAll(size, c); }
	public boolean addAll(int index, Collection<? extends E> c) {
		Node<E> end = getNode(index), node = end == null ? null : end.prev;
		for(E e : c) {
			Node<E> newNode = new Node<E>(node, e, null);
			if(node != null) node.next = newNode;
			node = newNode;
		}
		
		node.next = end;
		return c.isEmpty();
	}

	public boolean removeAll(Collection<?> c) {
		boolean toRet = false;

		for(Object obj : c) {
			for(Node<E> node = first; node.next != null;) {
				if(node.value.equals(obj)) {
					Node<E> next = node.next;
					unlink(node);
					node = next; 
					continue;
				}
				
				node = node.next;
			}
		}
		
		return toRet;
	}

	public boolean retainAll(Collection<?> c) {
		boolean toRet = false;

		for(Node<E> node = first; node.next != null;) {
			if(!c.contains(node.value)) {
				Node<E> next = node.next;
				unlink(node);
				node = next; 
				continue;
			}
			
			node = node.next;
		}
		
		return toRet;
	}

	public void clear() {
		for(Node<E> node = first; node != null;) {
			Node<E> n = node.next;
			
			node.value = null;
			node.next = node.prev = null;
			
			node = n;
		}
		
		first = last = null;
		size = 0;
	}
	
	private Node<E> getNode(int index) {
		if(index < 0 && index >= size) throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
		boolean forward = index < size / 2; Node<E> node = forward ? first : last;
		for(int i = forward ? 0 : size - 1; i != index; i += forward ? 1 : -1)
			node = forward ? node.next : node.prev;
		return node;
	}
	
	private void insert(E e, Node<E> prevNode) {
		Node<E> node = new Node<>(prevNode, e, prevNode != null ? prevNode.next : first);
		
		if(prevNode == null) {
			first.prev = node;
			first = node;
			return;
		}
		
		if(prevNode.next != null) prevNode.next.prev = node;
		prevNode.next = node;
		size ++;
	}

	public E get(int index) { return getNode(index).value; }
	public E set(int index, E e) {
		E ret = getNode(index).value;
		getNode(index).value = e;
		return ret;
	}

	public void add(int index, E e) {
		if(index < 0 && index > size) throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
		if(index == 0) { addFirst(e); return; }
		if(index == size) { addLast(e); return; }
		
		insert(e, getNode(index - 1));
	}

	public E remove(int index) {
		if(index < 0 && index >= size) throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
		if(index == 0) return removeFirst();
		if(index == size - 1) removeLast();
		
		Node<E> node = getNode(index);
		unlink(node);
		return node.value;
	}
	
	private void unlink(Node<E> node) {
		if(node.prev != null) node.prev.next = node.next;
		if(node.next != null) node.next.prev = node.prev;
		
		node.next = node.prev = null;
		size --;
	}

	public int indexOf(Object o) {
		int index = 0;
		for(Node<E> node = first; node.next != null; node = node.next, index ++)
			if((o == null && node.value == null) || node.value.equals(o)) return index;
		return -1;
	}

	public int lastIndexOf(Object o) {
		int index = 0;
		for(Node<E> node = last; node.prev != null; node = node.prev, index ++)
			if((o == null && node.value == null) || node.value.equals(o)) return size - index - 1;
		return -1;
	}
}
