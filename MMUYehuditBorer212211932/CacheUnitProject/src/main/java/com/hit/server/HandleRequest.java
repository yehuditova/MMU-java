package com.hit.server;

import java.lang.reflect.Type;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.server.Request;
import com.hit.services.CacheUnitController;


public class HandleRequest<T> implements java.lang.Runnable {

	private Socket s;
    private CacheUnitController<T> controller;

	private String result;
    private boolean succeed=false;
	Scanner reader;
	PrintWriter writer;
	public HandleRequest(Socket s,CacheUnitController<T> controller) {
		this.s = s;
		this.controller=controller;
	}

	@Override
	public void run() {
		 Gson gson = new Gson();
			try {
	            boolean statsRequest = false;

	            reader = new Scanner(new InputStreamReader(s.getInputStream()));
	            writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
	            
	            String req = reader.nextLine();
	            Type ref = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
	            Request<DataModel<T>[]> request = new Gson().fromJson(req,ref);
	          
	            Map<String, String> headers = request.getHeaders();
	            DataModel<T>[] body = request.getBody();
	
	            String action = headers.get("action");

				switch(action) {
				case "DELETE":
					succeed=controller.delete(request.getBody());
					break;
				case "GET":
					succeed=(controller.get(request.getBody()) != null);
					break;
		
				case "UPDATE":
					succeed=controller.update(request.getBody());
					break;
					
		        case "STATS":
		        	result = controller.getStatistics();
		            statsRequest = true;
		            break;
		            
		    	default:
					System.out.println("HandleRequest: Not valid");
					break;
				}
			    
			    if(!statsRequest){
		    	   if(succeed){
		                result = "true";
		            }
		            else result = "false";
			    }
	         
	            writer.println(result);
	            writer.flush();
	            writer.close();
	            reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			}				
		}














	


