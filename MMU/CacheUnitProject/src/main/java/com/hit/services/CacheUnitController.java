package com.hit.services;

import com.hit.dm.DataModel;

public class CacheUnitController<T>{
	CacheUnitService<T> cacheUnitService;
	
	public CacheUnitController() {
		cacheUnitService=new CacheUnitService<>();
	}

	public boolean update(DataModel<T>[] dataModels) {
		return  cacheUnitService.update(dataModels);
	}
	
	public boolean updateDB() {
		return  cacheUnitService.updateDB();
	}

	public boolean delete(DataModel<T>[] dataModels) {
		return  cacheUnitService.delete(dataModels);
		
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels){
		return  cacheUnitService.get(dataModels);
		
	}
   public String getStatistics(){
        return cacheUnitService.getStatistics();
    }
}
