package com.hit.dao;

import java.io.IOException;

public interface IDao<ID extends java.io.Serializable,T>{

	public void save(T entity) throws IOException;
	public void delete(T entity) throws IOException;
	public T find(ID id) throws IOException;
}
