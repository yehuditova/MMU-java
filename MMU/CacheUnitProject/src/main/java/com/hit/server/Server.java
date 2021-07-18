
package com.hit.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hit.services.CacheUnitController;

public class Server implements PropertyChangeListener, Runnable {

	//private Executor executor;
	private ServerSocket serverSocket;
	private boolean isConected;
	private final int port = 12345;
	private CacheUnitController<String> cacheUnitController;

	public Server() {
		this.isConected = false;	
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			ExecutorService executor = Executors.newFixedThreadPool(20);
			cacheUnitController = new CacheUnitController<>();
			while (isConected) {
				Socket newClient = serverSocket.accept();
				HandleRequest<String> handleRequest = new HandleRequest<>(newClient, cacheUnitController);
		         executor.execute(new Thread(handleRequest));

			}
		    executor.shutdown();
		} catch (IOException e) {
			//e.printStackTrace();
		} 
		
		finally {
            try {
                if(serverSocket != null)
                    serverSocket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String command = (String) evt.getNewValue();
		switch (command) {
		case "start":
			if (isConected == false) {
				isConected=true;
				new Thread(this).start();
			}
			else {
				System.out.println("Server is already run");
			}
			break;
		case "shutdown":
			if (isConected == true) {
					cacheUnitController.updateDB();	
					try {
						(this.serverSocket).close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					isConected= false;
					 
			}
			else {
				System.out.println("Server is already down");
			}
			break;
		default:
			break;
		}
	}
}


