package br.com.pontualmobile.utils.collection;

import java.util.*;

/**
 * Created by feandrad on 09/05/16.
 */
public class Centipede<T> extends ArrayList<T> {
	
	public interface ItemProcessor<O, I> {
		O doIt(I object);
	}
	
	public Centipede() {
		super();
	}
	
	public Centipede(Collection<? extends T> collection) {
		super(collection);
	}
	
	public Centipede(T[] vector) {
		super(Arrays.asList(vector));
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 * @param <O>       return type
	 * @return biggest result value from processor
	 */
	public <O extends Comparable<O>> T max(ItemProcessor<O, T> processor) {
		T biggest = null;
		O biggestValue = null;
		
		for (T elem : this) {
			O value = processor.doIt(elem);
			
			if (elem == null || value == null) {
				continue;
			}
			
			if (biggest == null) {
				biggestValue = processor.doIt(elem);
				biggest = elem;
			} else if (processor.doIt(elem).compareTo(biggestValue) >= 0) {
				biggestValue = processor.doIt(elem);
				biggest = elem;
			}
		}
		
		return biggest;
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 * @param <O>       processor's output
	 * @return
	 */
	public <O extends Comparable<O>> T min(ItemProcessor<O, T> processor) {
		T smallest = null;
		O smallestValue = null;
		
		for (T elem : (Centipede<T>) this) {
			O value = processor.doIt(elem);
			
			if (elem == null || value == null) {
				continue;
			}
			
			if (smallest == null) {
				smallestValue = processor.doIt(elem);
				smallest = elem;
			} else if (processor.doIt(elem).compareTo(smallestValue) <= 0) {
				smallestValue = processor.doIt(elem);
				smallest = elem;
			}
		}
		
		return smallest;
	}
	
	/**
	 * Return the position that matches the predicate
	 *
	 * @param processor applied to each item in the centipede
	 * @return a Centipede with filtered objects
	 */
	public int indexOf(ItemProcessor<Boolean, T> processor) {
		
		for (int i = 0; this.size() > i; i++) {
			if (processor.doIt(this.get(i))) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 * @return a Centipede with filtered objects that matches processor
	 */
	public Centipede<Integer> allIndexesOf(ItemProcessor<Boolean, T> processor) {
		Centipede<Integer> found = new Centipede<>();
		
		for (int i = 0; this.size() > i; i++) {
			if (processor.doIt(this.get(i))) {
				found.add(i);
			}
		}
		
		return found;
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 * @return a Centipede with filtered objects that matches processor
	 */
	public Centipede<T> where(ItemProcessor<Boolean, T> processor) {
		Centipede<T> found = new Centipede<>();
		
		for (T elem : this) {
			if (processor.doIt(elem)) {
				found.add(elem);
			}
		}
		
		return found;
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 * @param <T>
	 * @param <O>
	 * @return
	 */
	public <T, O extends Comparable<O>> Centipede<T> removeDuplicates(ItemProcessor<O, T> processor) {
		HashSet<O> keys = new HashSet<>();
		
		for (int i = 0; i < size(); i++) {
			O key = processor.doIt((T) get(i));
			if (keys.contains(key)) {
				remove(i);
			} else {
				keys.add(key);
			}
		}
		
		return (Centipede<T>) this;
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 * @param <T>
	 */
	public <T> void foreach(ItemProcessor<Void, T> processor) {
		for (int i = 0; i < size(); i++) {
			processor.doIt((T) get(i));
		}
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 * @param <T>
	 */
	public <T> void foreachInverted(ItemProcessor<Void, T> processor) {
		for (int i = size() - 1; i >= 0; i--) {
			processor.doIt((T) get(i));
		}
	}
	
	/**
	 * @return
	 */
	public T first() {
		if (size() > 0) {
			return get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 * @return first element that matches ItemProcessor's condition
	 */
	public T first(ItemProcessor<Boolean, T> processor) {
		for (int i = 0; i < size(); i++) {
			if (processor.doIt(get(i))) {
				return get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * @return
	 */
	public T last() {
		if (size() > 0) {
			return get(size() - 1);
		} else {
			return null;
		}
	}
	
	public T last(ItemProcessor<Boolean, T> processor) {
		for (int i = size() - 1; i >= 0; i--) {
			if (processor.doIt(get(i))) {
				return get(i);
			}
		}
		return null;
	}
	
	/**
	 * @param processor condition to be matched
	 * @return true if any element matches processor
	 */
	public boolean contains(ItemProcessor<Boolean, T> processor) {
		for (int i = 0; i < size(); i++) {
			if (processor.doIt(get(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param processor applied to each item in the centipede
	 */
	public boolean removeWhere(ItemProcessor<Boolean, T> processor) {
		for (int i = 0; i < size(); i++) {
			if (processor.doIt(get(i))) {
				return this.remove(get(i));
			}
		}
		return false;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public Centipede<T> last(int count) {
		if (size() > 0) {
			return new Centipede<>(subList(size() - count, size() - 1));
		} else {
			return null;
		}
	}
	
	public Centipede<T> first(int count) {
		if (size() > 0) {
			return new Centipede<>(subList(0, count - 1));
		} else {
			return null;
		}
	}
	
	public void append(T object) {
		add(object);
	}
	
	public void prepend(T object) {
		add(0, object);
	}
	
	public <I> Centipede<I> map(ItemProcessor<I, T> processor) {
		
		Centipede<I> result = new Centipede<>();
		
		for (T elem : this) {
			result.add(processor.doIt(elem));
		}
		
		return result;
	}
	
	public Centipede<T> subCentipede(int index) {
		Centipede<T> result = new Centipede<>();
		
		for (int i = 0; i < index && i < this.size(); i++) {
			result.add(this.get(i));
		}
		
		return result;
	}
	
	public <I extends Comparable> void sort(final ItemProcessor<I, T> processor) {
		
		Collections.sort(this, new Comparator<T>() {
			@Override public int compare(T left, T right) {
				return processor.doIt(left).compareTo(processor.doIt(right));
			}
		});
	}
}