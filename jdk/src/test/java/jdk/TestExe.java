package jdk;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.SimpleFormatter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.corba.se.impl.orbutil.graph.Graph;

public class TestExe {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> l = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			l.add(i);
		}
		
		Calendar date = Calendar.getInstance();
		date.set(2016, 7, 2);
		System.out.println(date.getTime());
		date.add(Calendar.DAY_OF_YEAR, 100);
		System.out.println(date.getTime());
	}

	
	public void testCache() {
		
	}
}
