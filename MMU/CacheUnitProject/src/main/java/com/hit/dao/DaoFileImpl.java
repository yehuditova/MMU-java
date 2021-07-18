
package com.hit.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hit.dm.DataModel;


public class DaoFileImpl<T> implements IDao<java.lang.Long, DataModel<T>> {
	private String filePath;
	private int capacity;
	private static int count = 0;
	GsonBuilder builder = new GsonBuilder();
	Gson gson = builder.create();
	FileWriter writer;
	BufferedReader reader;
	Scanner myScaner=null;
	
	
	private void clearData() {	
		try {
			writer = new FileWriter(filePath);
			writer.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}




	public DaoFileImpl(java.lang.String filePath, int capacity) {
		this.filePath = filePath;
		this.capacity = capacity;
		clearData();


	}

	public DaoFileImpl(java.lang.String filePath) {
		this.filePath = filePath;
		this.capacity = -1;
		clearData();

	}

	@Override
	public void save(DataModel<T> entity) throws IOException{
		writer = new FileWriter(filePath,true);
		if ((count >= capacity)&&(capacity>=0)) {
			System.out.println("The disk is full,can not save" + entity);
			return;
		}
		
		writer.write(gson.toJson(entity)+";");
		count++;

		writer.close();

	}

	@Override
	public void delete(DataModel<T> entity) throws IOException {
		if (entity==null) {
		    throw new IOException();
		}
		ArrayList<DataModel<T>> datalist=new ArrayList<DataModel<T>>();
		Boolean saveChanges=false;
		
		reader = new BufferedReader(new FileReader(filePath));

		myScaner=new Scanner(reader);
		myScaner.useDelimiter(";");

		while(myScaner.hasNext()){
			@SuppressWarnings("unchecked")
			DataModel<T> entry=gson.fromJson(myScaner.next(),DataModel.class); 
			if (entry.equals(entity)) {
				saveChanges=true;
				count--;
			}
			else {
				datalist.add(entry);
			}
		}
        reader.close();
		
		if(saveChanges) {
			writer = new FileWriter(filePath);
			for (DataModel<T> data : datalist) {
                writer.write(gson.toJson(data)+";");
			}
			writer.close();	
		}	
	}
			

	@Override
	public DataModel<T> find(Long id) throws IOException{

			if (id==null) {
			    throw new IOException();
			}


	
		
		reader = new BufferedReader(new FileReader(filePath));
		
		myScaner=new Scanner(reader);
		myScaner.useDelimiter(";");

		while(myScaner.hasNext()){
			DataModel entry=gson.fromJson(myScaner.next(),DataModel.class); 
			if (entry.getDataModelId()==id.longValue()) {
				reader.close();
				return  entry;
			}
		}
		
		reader.close();
		return null;
	}

}
