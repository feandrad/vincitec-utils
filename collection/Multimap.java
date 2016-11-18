package br.com.pontualmobile.utils.collection;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by feandrad on 07/11/16.
 */
public class Multimap<R, T> {
	
	private HashMap<R, ArrayList<T>> map;
	
	public Multimap() {
		this.map = new HashMap<>();
	}
	
	public Multimap(int capacity) {
		this.map = new HashMap<>(capacity);
	}
	
	public Multimap(Map<? extends R, ? extends ArrayList<T>> map) {
		this.map = new HashMap<>(map);
	}
	
	public ArrayList<T> get(Object key) {
		return map.get(key);
	}
	
	public boolean put(R key, T value) {
		
		if (!containsKey(key)) {
			map.put(key, new ArrayList<T>());
		}
		
		return map.get(key).add(value);
	}
	
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	
	public boolean containsValue(Object value) {
		
		for (ArrayList<T> keyList : map.values()) {
			for (T elem : keyList) {
				if (elem.equals(value)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int size() {
		
		int size = 0;
		
		for (ArrayList<T> keyList : map.values()) {
			size += keyList.size();
		}
		
		return size;
	}
	
	public boolean isEmpty() {
		
		for (ArrayList<T> keyList : map.values()) {
			if (!keyList.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	public void putAll(@NonNull Map<? extends R, ? extends ArrayList<T>> map) {
		this.map.putAll(map);
	}
	
	@NonNull public Collection<T> values() {
		
		Collection<T> values = new ArrayList<>();
		
		for (ArrayList<T> keyList : map.values()) {
			values.addAll(keyList);
		}
		
		return values;
	}
	
	public Collection<ArrayList<T>> valuesList() {
		return map.values();
	}
}
