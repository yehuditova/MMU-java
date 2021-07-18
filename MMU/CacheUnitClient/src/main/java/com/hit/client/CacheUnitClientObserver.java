package com.hit.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.hit.view.CacheUnitView;


public class CacheUnitClientObserver implements PropertyChangeListener{
	private CacheUnitClient CacheUnitClient;

	public CacheUnitClientObserver() {
		this.CacheUnitClient=new CacheUnitClient();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String res = null;
	    String file_path=(String) evt.getNewValue();
	   
        CacheUnitView newUI = (CacheUnitView) evt.getSource();
        try {
			res =CacheUnitClient.send(file_path);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        newUI.updateUIData(res);
	}

}
