package com.hit.services;

import java.io.IOException;
import java.util.ArrayList;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.MFUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> {
	private int cache_capacity=2;
	private String algo_name="MFU";
	private IAlgoCache<Long,DataModel<T>> algorithm= new MFUAlgoCacheImpl<>(cache_capacity);
	private CacheUnit<T> cache=new CacheUnit<T>(algorithm);
	private IDao<Long, DataModel<T>> db=new DaoFileImpl("src/main/resources/datasource.txt");
	private int countSwaps, countRequests, countDataModels;
	
	public CacheUnitService() {
        this.countRequests = 0;
        this.countDataModels = 0;
        this.countSwaps = 0;
	}
	
	public boolean update(DataModel<T>[] dataModels) {
	    int length=dataModels.length;
        countRequests++;
        countDataModels += dataModels.length;
	    
		//update data in cache
		DataModel<T>[] dataToUpdateInDB =(DataModel<T>[]) cache.putDataModels(dataModels);
		
		//if data removed from the cache - update the DB
		for (DataModel<T> data:dataToUpdateInDB) {
			if (data!=null) {
				try {
					countSwaps++;
					db.save(data);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;	
	}

	public boolean delete(DataModel<T>[] dataModels) {
		countRequests++;
        countDataModels += dataModels.length;
		//delete data from cache
		int length=dataModels.length;
		Long[] ids= new Long[length];
		for(int i=0;i<length;i++) {
			ids[i]=dataModels[i].getDataModelId();
		}
		cache.removeDataModels(ids);
			
		//delete data from DB
		for (DataModel<T> data :dataModels)
			try {
				db.delete(data);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	
	

	public DataModel<T>[] get(DataModel<T>[] dataModels){
		
	
		int length=dataModels.length;
		
        countRequests ++;
        countDataModels += dataModels.length;
		
		Long[] ids= new Long[length];
		for(int i=0;i<length;i++) {
			ids[i]=dataModels[i].getDataModelId();
		}
		//get data from cache
		DataModel<T>[] NewDataModels =(DataModel<T>[]) cache.getDataModels(ids);
		
		ArrayList<DataModel<T>> dataToUpdateInCache=new ArrayList<>();
		DataModel<T>[] dataToUpdateInDB=new DataModel[length];
		
		
		//if data not exist in cache - get data from the DB
		for(int i=0;i<length;i++) {
			if(	NewDataModels[i]==null) {
				DataModel<T> DBdata = null;
				try {
					for (Long item : ids)
					DBdata = (DataModel<T>) db.find(ids[i]);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				if (DBdata!=null) {
					dataToUpdateInCache.add(DBdata);
					NewDataModels[i]=DBdata;
				}
			
			}
		}
		//update the cache from the data we get from the db
		dataToUpdateInDB=cache.putDataModels(dataToUpdateInCache.toArray(new DataModel[dataToUpdateInCache.size()]));
		
		//if data removed from the cache - update the DB
		for (DataModel<T> data:dataToUpdateInDB) {
			if (data!=null) {
				try {
					countSwaps++;
					db.save(data);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return NewDataModels;
	}
		
		
	public boolean updateDB(){
		DataModel<T>[] allData=cache.getAllDataModels();
		for (DataModel<T> data : allData) {
			try {
				countSwaps++;
				db.save(data);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
			
		
	}
	
    public String getStatistics(){
        return this.algo_name + "," + this.cache_capacity + "," + countSwaps + "," + countRequests + "," + countDataModels;
    }
	
}
