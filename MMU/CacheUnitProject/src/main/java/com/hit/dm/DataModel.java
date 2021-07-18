package com.hit.dm;

import java.io.Serializable;

public class DataModel<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private T content;
	public DataModel() {
		
	}
	public DataModel(Long id, T content) {
		this.id = id;
		this.content = content;
	}

	public Long getDataModelId() {
		return id;
	}

	public void setDataModelId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataModel other = (DataModel) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DataModel [id=" + id + ", content=" + content + "]";
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
