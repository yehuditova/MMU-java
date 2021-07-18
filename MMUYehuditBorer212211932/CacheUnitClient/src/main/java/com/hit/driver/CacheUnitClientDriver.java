package com.hit.driver;

import com.hit.client.CacheUnitClientObserver;
import com.hit.view.CacheUnitView;

public class CacheUnitClientDriver {
	public static void main(String[] args) {
		System.out.println("Cache unit client\nYehudit Borer");
		CacheUnitClientObserver cacheUnitClientObserver = new CacheUnitClientObserver();
		CacheUnitView view = new CacheUnitView();
		view.addPropertyChangeListener(cacheUnitClientObserver);
		view.start();
	
		}
}
