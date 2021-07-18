package com.hit.memory;
import java.util.ArrayList;

import com.hit.algorithm.IAlgoCache;

import com.hit.dm.DataModel;

public class CacheUnit<T> {

private IAlgoCache<Long,DataModel<T>> algo;

public CacheUnit(IAlgoCache<Long, DataModel<T>> algo) {
	super();
	this.algo = algo;
}
	
public DataModel<T>[] getDataModels(Long[] ids){
	@SuppressWarnings("unchecked")
	DataModel<T>[] datamodel= new DataModel[ids.length];
	DataModel<T> item;
	for (int i = 0; i < datamodel.length; i++) {
		item=algo.getElement(ids[i]);
		datamodel[i]=item;
	}
	return  datamodel;
}

public DataModel<T>[] putDataModels(DataModel<T>[] datamodels){
	
	@SuppressWarnings("unchecked")
	DataModel<T>[] datamodel= new DataModel[datamodels.length];
	DataModel<T> item;
	for (int i = 0; i < datamodel.length; i++) {
		item=algo.putElement(datamodels[i].getDataModelId(), datamodels[i]);
		datamodel[i]=item;
	}
	return  datamodel;
}

public void removeDataModels(Long[] ids) {
	
	for (Long item : ids) {
		algo.removeElement(item);
	}
}

@SuppressWarnings("unchecked")
public DataModel<T>[] getAllDataModels(){
	ArrayList<T> datamodel=(ArrayList<T>) algo.getAll();
	return  (datamodel.toArray(new DataModel[datamodel.size()]));
}

}