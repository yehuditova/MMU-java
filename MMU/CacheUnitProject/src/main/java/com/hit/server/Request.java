package com.hit.server;

import java.util.Map;

public class Request<T> implements java.io.Serializable {
	private Map<String,String> headers;
	private T body;
	
     public Request(Map<String,String> headers, T body){
    	 this.headers=headers;
    	 this.body=body;
		
	}
	public java.util.Map<String,String> getHeaders(){
		return this.headers;
		
	}
	public void setHeaders(Map<String,String> headers) {
		 this.headers=headers;
	}
	public T getBody() {
		return this.body;
		
	}
	public void setBody(T body) {
		 this.body=body;
	}
	public String toString(){
		return("{Headers:\n{"+headers+"},\nBody:\n"+body+"\n}");
	}
}
