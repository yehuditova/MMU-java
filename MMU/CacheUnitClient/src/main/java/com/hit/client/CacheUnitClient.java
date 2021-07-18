package com.hit.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class CacheUnitClient {
    private int serverPort=12345;
	public CacheUnitClient() {
		
	};
	public String send(String request) throws ClassNotFoundException{
		String res = "None";
		StringBuilder req = new StringBuilder();
		try {
			
			if (request.equals("stats")) {
				req.append("{ \"headers\":{\"action\":\"STATS\"},\"body\":[]}");
			}
			
			else {
			    String line= null;
	            BufferedReader reader = null;
	            File file = new File(request);
	            reader = new BufferedReader(new FileReader(file));
	            line = reader.readLine();
	            while (line != null) {
	            	req.append(line);
	                line = reader.readLine();
	            }
	            reader.close();
			}			
			
            Socket myServer = new Socket(InetAddress.getLocalHost(), serverPort);
            Scanner my_reader = new Scanner(new InputStreamReader(myServer.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(myServer.getOutputStream()));
            writer.println(req);
            writer.flush();          
            res = (String) my_reader.nextLine();//Convert  the response to String      
            writer.close();
            my_reader.close();
            myServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		   return res;
	};
}














