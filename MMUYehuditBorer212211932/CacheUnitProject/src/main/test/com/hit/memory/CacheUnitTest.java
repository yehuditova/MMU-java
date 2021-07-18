package com.hit.memory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;



import java.io.IOException;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.MFUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnitTest {
	private IAlgoCache<Long,DataModel<String>> algorithm= new MFUAlgoCacheImpl<>(3);
	private CacheUnit<String> cache;
	
	public void testDataModel() {
	cache=new CacheUnit<String>(algorithm);
	DataModel<String>[] data=new DataModel[3];
	DataModel<String>[] data2;
	data[0]=(new DataModel<String>(212211932l, "Yehudit Bohrer"));
	data[1]=(new DataModel<String>(212211933l, "Naama Shteinberg"));
	data[2]=(new DataModel<String>(212211934l, "Shulamit Kister"));
	cache.putDataModels(data);

	data2=cache.getDataModels(new Long[] {212211932l,212211933l,212211934l});
	assertArrayEquals(data2, data);
	assertNull(cache.getDataModels(new Long[] {212211937l})[0]);
	cache.removeDataModels(new Long[] {212211932l,212211934l,212211936l});
	data2=cache.getDataModels(new Long[] {212211932l,212211933l,212211934l});
	assertNull(data2[0]);
	assertNull(data2[2]);
	assertNotNull(data2[1]);
	cache.putDataModels(new DataModel[] {new DataModel<String>(212211935l, "racheli")});
	cache.putDataModels(new DataModel[] {new DataModel<String>(212211936l, "miri")});
	cache.putDataModels(new DataModel[] {new DataModel<String>(212211937l, "efrat")});
	data2=cache.getDataModels(new Long[] {212211932l,212211933l,212211934l,212211935l,212211936l,212211937l});
    assertNull(data2[1]);
	}
	public void testDaoFileImpl() {
		IDao<Long, DataModel<String>> idao=new DaoFileImpl("src/main/resources/datasource.txt",3);
		DataModel<String> entity1=new DataModel<>(212l,"hello1");
		try {
			idao.save(entity1);
	
		DataModel<String> entity2=new DataModel<>(523l,"hello2");
		idao.save(entity2);
		assertNotNull(idao.find(523l));
		assertNull(idao.find(521l));
		DataModel<String> entity3=new DataModel<>(456l,"hello3");
		idao.delete(entity3);
		
		
		IDao<Long, DataModel<String>> idao2=new DaoFileImpl("src/main/resources/datasource.txt");
		DataModel<String> entity4=new DataModel<>(212l,"hello1");
		idao2.save(entity4);
		DataModel<String> entity5=new DataModel<>(523l,"hello2");
		idao2.save(entity5);
		assertNotNull(idao2.find(523l));
		assertNull(idao2.find(521l));
		DataModel<String> entity6=new DataModel<>(456l,"hello3");
		idao2.delete(entity6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
          System.out.println("Cache unit project-\nYehudit Borer");
          CacheUnitTest test=new CacheUnitTest();  
          test.testDataModel();
          test.testDaoFileImpl();
          System.out.println("Tests succeeded");
	}
}
