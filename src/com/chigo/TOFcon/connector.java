package com.chigo.TOFcon;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class connector {
	static Socket client;
	static DataInputStream in;
	static DataOutputStream out;
	static DataInputStream in2;
	static DataOutputStream out2;
	static BufferedReader reader;
	
	public static int last;
	//private static String serverName = "10.3.20.200";
	//private static int port = Integer.parseInt("2509");
	
	private static String serverName;
	private static int port ;
	
	public connector() {
		
	}
	
	public static void Connect() throws ConnectException, NullPointerException {
		try {
			configProps conf3 = new configProps();
			conf3.getValues();
			serverName = configProps.TOF_IP;
			port = Integer.parseInt(configProps.TOF_PORT);
			TOFstart.logger.info("Connecting to " + serverName + " on port " + port);
	         client = new Socket(serverName, port);
	         boolean on = true;
			 //client.setKeepAlive(true);
			
	         
	         
	         TOFstart.logger.info("Just connected to " + client.getRemoteSocketAddress());

		  }catch (Exception e) {
			   e.printStackTrace();
			   TOFstart.logger.warning(e.getMessage());
		
				   
			   }
				   
			   
				   
			   
	   }
	
	public static void setupStreams() throws IOException, NullPointerException{
		TOFstart.logger.info("setting up Streams..");
		try {
			//if(client.isClosed()) {
				//Connect();
				
			//}
			
				OutputStream outToServer = client.getOutputStream();
				out = new DataOutputStream(outToServer);
				
		
			 
	         
	         InputStream inFromServer = client.getInputStream();
	         in = new DataInputStream(inFromServer);
	         out.flush();
			
		}catch (IOException e7) {
			e7.printStackTrace();
			System.out.println(e7.getMessage());
			TOFstart.logger.warning(e7.getMessage());
			
		}
		

		
	}
	
	public static void setupStreams2() throws IOException, NullPointerException{
		TOFstart.logger.info("setting up Streams..");
		try {
			//if(client.isClosed()) {
				//Connect();
				
			//}
			
				OutputStream outToServer = client.getOutputStream();
				out2 = new DataOutputStream(outToServer);
				
		
			 
	         
	         InputStream inFromServer = client.getInputStream();
	         in2 = new DataInputStream(inFromServer);
	         out2.flush();
			
		}catch (IOException e7) {
			e7.printStackTrace();
			System.out.println(e7.getMessage());
			TOFstart.logger.warning(e7.getMessage());
			
		}
		

		
	}
	
	public void recon() {
		System.out.println("STarting recon!!!!!!!");

		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), 
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	try {
							System.out.println("STarting2 recon!!!!!!!" + in.read());
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
		            	if(client.isOutputShutdown()) {
		            		System.out.println("TOF DISCONNECTED2222222");
		            		//killCon();
		            		try {
								setupStreams();
							} catch (IOException e1) {
								
								e1.printStackTrace();
							}
		            		try {
								setupStreams();
							} catch (IOException e) {
								
								e.printStackTrace();
							}
		            		
		            	}
		            	
		        		
		            }

		        }
		    ));
		    timeline.setCycleCount(Animation.INDEFINITE);
		    timeline.play();

	}
	
	public void killCon() {
		try {
			System.out.println("Closing Network connections >");
			in.close();
			out.close();
			client.close();
		} catch (IOException e) {
			TOFstart.logger.warning(e.getMessage());
			e.printStackTrace();
		}
	}
	

}
