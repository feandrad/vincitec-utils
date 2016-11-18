package br.com.pontualmobile.utils.collection;

/**
 * Created by feandrad on 01/06/16.
 */
public class ArrayUtils {
	
	public static <T> boolean contains(final T[] array, final T value) {
		if (value == null) {
			for (final T elem : array) {
				if (elem == null) {
					return true;
				}
			}
		} else {
			for (final T elem : array) {
				if (elem == value || value.equals(elem)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void cocktail(int[] array, int size) {
		int start = 0, end = size - 1, swap = 0, aux = 0;
		
		while (swap == 0 && start < end) {
			swap = 1;
			for (int i = start; i < end; i = i + 1) {
				if (array[i] > array[i + 1]) {
					aux = array[i];
					array[i] = array[i + 1];
					array[i + 1] = aux;
					swap = 0;
				}
			}
			end = end - 1;
			for (int i = end; i > start; i = i - 1) {
				if (array[i] < array[i - 1]) {
					aux = array[i];
					array[i] = array[i - 1];
					array[i - 1] = aux;
					swap = 0;
				}
			}
			start = start + 1;
		}
	}
}
