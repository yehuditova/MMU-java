package com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class CLI implements Runnable{

private String command=null;
private BufferedReader in;
private PrintStream out;
private PropertyChangeSupport change;
	
	public CLI(InputStream in,OutputStream out) {
		this.in=new BufferedReader(new InputStreamReader(in));
		this.out=(PrintStream) out;
		this.change = new PropertyChangeSupport(this);
	}

	@Override
	public void run() {
	write("Please enter your command");
	try {
		while((command=in.readLine())!=null) {
			command = command.toLowerCase();
			if (command.equals("start")) {
				write("Starting server....");
			    change.firePropertyChange("CLI_command", "","start");
			}
			else if(command.contentEquals("shutdown")) {
				write("Shutdown server");
			    change.firePropertyChange("CLI_command", "","shutdown");
			}
			else {
				write("Not a valid command");
			}
					
		}
	} catch (IOException e) {
		e.printStackTrace();
	}		
	}
	
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		change.addPropertyChangeListener(pcl);
	}
		
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		  change.removePropertyChangeListener(pcl);
	}
	public void write(String string) {
		 out.println(string);
	}
	
}





