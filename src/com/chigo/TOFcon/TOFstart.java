package com.chigo.TOFcon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;



public class TOFstart implements Runnable {

	public static int last;
	public connector con = new connector();
	public static String jah = "";
	public static String FIDentry;
	public static Logger logger = Logger.getLogger("NGNIE Poster");
	public static FileHandler fh;
	configProps conf = new configProps();
	
	
	
	
	
	//public static String TCID = "CBTX";
	public static char FS = 28;
	public static char GS = 29;
	public static char RS = 30;
	public static char US = 31;
	public static char ACK = 6;
	public static char STX = 2;
	public static char ETX = 3;
	public static String getTag;
	public static String DataTag = "KC";
	public static String DataTag2 = "HB";
	public static String hsh = "#";
	public static int req = 333;
	public static ArrayList<Integer> tknums = new ArrayList<Integer>();
	public static ArrayList<Double> prcray = new ArrayList<Double>();
	public static ArrayList<Double> volray = new ArrayList<Double>();
	public String dire = "";
	
	
	
	private static String  MessageEndsWith   = String.valueOf(FS); // Change this to whatever you need
	private static String  MessageSeparator  = Pattern.quote( MessageEndsWith );   
	static Pattern EndOfMessageRegEx = Pattern.compile( MessageSeparator );
	public static ArrayList<Double> price;
	public static ArrayList<Double> volume;
	public static ArrayList<String> direction;
	public static ArrayList<String> TxIDD;
	//private static String serverName = "127.0.0.1";
	//private static int port = Integer.parseInt("2509");
	private int num = 333;
	//private static int numb = 332;
	
	public ArrayList<Integer> getTknums() {
		return tknums;
	}

	
	public static void setTknums(ArrayList<Integer> tknums) {
		tknums = tknums;
	}
	

	public ArrayList<Double> getPrice() {
		return price;
	}

	public static void setPrice(ArrayList<Double> price) {
		price = price;
	}

	public ArrayList<Double> getVolume() {
		return volume;
	}

	public static void setVolume(ArrayList<Double> volume) {
		volume = volume;
	}

	public ArrayList<String> getDirection() {
		return direction;
	}

	public void setDirection(ArrayList<String> direction) {
		this.direction = direction;
	}



	
	
	//public static String GS = Character.(29);
	//public static String RS = new char(28);
	
	//public static String msg = String.valueOf(FS) + req + String.valueOf(US) + DataTag + String.valueOf(GS) + configProps.TCID + hsh + 20 + String.valueOf(FS);
	//public static String msgi = String.valueOf(FS) + 332 + String.valueOf(US) + DataTag + String.valueOf(GS) + configProps.TCID + hsh + "INFO" + String.valueOf(FS);

	public static String msg2 = FS + req + US + DataTag + GS + configProps.TCID + hsh + 20 + FS;

	public TOFstart() {
		
	}
	
	public void run() {
		

		//Getting values from config file
		
		try {
			conf.getValues();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    try {  

	        // configure the logger with handler and formatter  
	        fh = new FileHandler("log\\NGNIE_Poster.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	        logger.info("NGNIE Poster App");  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  

	    logger.info("STARTING..");  

	
		
		
		
		
		
		
		//Open connection to TOF server
		try {
			con.Connect();
		} catch (ConnectException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			con.setupStreams();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		//Send request to TOF server
		

		
		

		
		Thread th1 = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						System.out.println("Trying to recon");
						//if(1==1) {
							//System.out.println("TOF DISCONNECTED!!");
							
							reCon();
							
						}
					//}
				});
		th1.start();
		
		
		sendRequest();
		setLast();
		
	
		
		
	}
	//This is like a heartbeat to maintain connectivity. this only works at the socket level
	public void reCon() {
		System.out.println("HeartBEAT!!!!!!!");
		try {
			conf.getValues();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(60), 
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
						String TCID = configProps.TCID;
						
						String msgi2 = String.valueOf(FS) + 333 + String.valueOf(US) + DataTag2 + String.valueOf(GS) + TCID + hsh + "INFO" + String.valueOf(FS);
						
						logger.info("Sending request to TCID from HeartBeat checker >: " + TCID + " :> " + msgi2);
						/*
						try {
							con.setupStreams2();
						} catch (NullPointerException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

*/
						try {
							con.out.writeBytes(msgi2 );
							con.out.flush();
						}catch(IOException e) {
							logger.warning("ERROR!! will reconnect" +e.getMessage());
						
							try {
								con.Connect();
								
							} catch (ConnectException e1) {

								logger.warning(" :( TOF Disconnected! Error, will try to reconnect..  " );
								System.out.println(e1.getMessage());
							}
							try {
								con.setupStreams();
							} catch (IOException e3) {
								
								e.printStackTrace();
							}
							sendRequest();
							setLast();
						
						
						}
		            	

		            	
		        		
		            }

		        }
		    ));
		    timeline2.setCycleCount(Animation.INDEFINITE);
		    timeline2.play();

	}
	
	

	
	
//This method sends request to TOF
		public void sendRequest() {
			try {
				conf.getValues();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			String TCID = configProps.TCID;
			
			String msg = String.valueOf(FS) + req + String.valueOf(US) + DataTag + String.valueOf(GS) + TCID + hsh + 20 + String.valueOf(FS);
			String msgi = String.valueOf(FS) + 332 + String.valueOf(US) + DataTag + String.valueOf(GS) + TCID + hsh + "INFO" + String.valueOf(FS);
			
			logger.info("Sending request to TCID >: " + TCID);

			try {
				con.out.writeBytes(msgi );
				con.out.flush();
			}catch(IOException e) {
				logger.warning("ERROR!! " +e.getMessage());
				e.printStackTrace();
				System.out.println("FAIL: Can't send request");
				
				
			}
			
		}
		
//This sends second request with the last ticket number		
		public void sendRequest2() {
			try {
				conf.getValues();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String TCID = configProps.TCID;
			logger.info("Last ticket Number is >:  "+ last);
			logger.info("Requesting ::  " + TCID + ">" + last);
			//msgi = String.valueOf(FS) + numb +  String.valueOf(US) + DataTag + String.valueOf(GS) + TCID + hsh + INFO + String.valueOf(FS);
			
			String tmsg = String.valueOf(FS) + num +  String.valueOf(US) + DataTag + String.valueOf(GS) + TCID + hsh + last + String.valueOf(FS);

			try {
				con.out.writeBytes(tmsg );
				con.out.flush();
			}catch(IOException e2) {
				logger.warning("ERROR!!" +e2.getMessage());
				e2.printStackTrace();
				System.out.println("FAIL: Can't send request2");
				
			}
			
		}
		
		//This reads and processes message from TOF
		public void setLast() {
			
			

			try {
				con.reader = new BufferedReader(
						  	new InputStreamReader(con.in, "UTF-8"));
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
				System.out.println("FAIL: Nothin to read");
			}

			      //Scanner     ReceivedData =  new Scanner( reader ).useDelimiter( EndOfMessageRegEx );
			      Scanner     ReceivedData =  new Scanner( con.reader ).useDelimiter( EndOfMessageRegEx );
			      //System.out.println("scanner length --- " + ReceivedData.next().length());
			      int index = -1;

			      while (ReceivedData.hasNext()){
			    	  
			    	  jah = ReceivedData.next();
			    	  
			    	  logger.info("Received Message length is : ) " + jah.length());
			    	  if(jah.length() == 18) {
			    		  logger.warning("TOF message has an Error ");
			    		  System.out.println("TOF message has an Error ");
			    		  //ReceivedData.close();
			    		  try {
			    				TimeUnit.SECONDS.sleep(30);
			    			} catch (InterruptedException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			}
			    		  
			    		  
	
			    		  con.killCon();
			    		  run();

			    		  
			    	  }

			    	  jah = jah.replace(String.valueOf(FS), "<FS>");
			    	  jah = jah.replace(String.valueOf(RS), "<RS>");
			    	  jah = jah.replace(String.valueOf(GS), "<GS>");
			    	  jah = jah.replace(String.valueOf(US), "<US>");

			    	  logger.info("Received Message from TOF : " + jah);

			    	  if(jah.length() >= 200) {
			    		  String jah2 = jah.substring(24);
			    		  String jah3 = jah2.replaceAll("<RS>", ",");
			    		  String jah4 = jah3.replaceAll("<US>", ":");
			    		  
			    		  getTag = jah.substring(7, 9);
			    		  System.out.println("--_+b3 " + getTag);
			    		  String FID = "522:";
			    		  String FID2 = "519:";
			    		  String FID3 = "514:";
			    		  int forPrice = 10;
			    		  int forVolume = 19;
			    		  int forChar = 5;
			    		  if(getTag.equals(DataTag)) {
			    		  String price1 = getFieldData(jah4, FID, forPrice);
			    		  if (price1.contains(",")) {
			    			  String[] price2 = price1.split(",");
			    			  double prc2 = Double.parseDouble(price2[0]);
			    			  prcray.add(prc2);
			    			  
			    		  }else {
			    			  double prc2 = Double.parseDouble(price1);
			    			  prcray.add(prc2);
			    			  
			    		  }
			    		  
			    		  

			    		  logger.info("updating  price array : " + prcray);
			    		  String volume1 = getFieldData(jah4, FID2, forVolume);
			    		  String direction1 = getFieldData(jah4, FID3, forChar);
			    		  dire = direction1;
			    		  String volume2 = "";
			    		  int volume3;
			    		  //System.out.println(" main direction>> " + dire + " price : " + price1 + " direction:  " + direction1 + " volume : " + volume1);
			    		  if (volume1.contains(",")) {
			    			  String[] vol = volume1.split(",");
			    			  logger.info("volume message contains comma  " +  vol[0] + "  "  );
			    			  

			    			  String volu = vol[0];
			    			  
			    			 
			    			  double vv = Double.parseDouble(volu);
			    	
			    			  BigDecimal bg1 = BigDecimal.valueOf(vv);
			    			  
			    			  double mar = vv/1000000;
			    			  int mar2 = (int)mar;
			    			  
			    			  volume3 = new Double(volu).intValue();
			    			  
			    			  
			    			  logger.info("volun " + volume3 + "   :" + mar);
			    			  volray.add(mar);
			    			  logger.info("Creating double value for volume and dividing by 1000000; "+mar2 + "volray badT" + volray.get(volray.size() -1));
			    		  }else {
			    			  double vv = Double.parseDouble(volume1);
			    			  double mar = vv/1000000;
			    			  int mar2 = (int)mar;
			    			  
			    			  //volume3 = Integer.parseInt(volume1);
			    			  logger.info("volun " + volume1);
			    			  //System.out.println("Creating double for vol; "+volume3/1000000);
			    			  volray.add(mar);
			    		  }
			    		  }
		

			    	  }
			    	  
			    	  //if(jah.length() < 18) {
			    		  //logger.warning("hmm.. something is wrong with message from TOF server ");
			    		  //run();  
			    	  //}
			    	  try {
				    	  System.out.println("get tag----++ " + jah.substring(6,10));
				    	  String getTag2 = jah.substring(7, 9);
				    	  
			    		  


			    	  if(getTag2.equals(DataTag)) {

				  	  //Map<String, String> m = new HashMap<String, String>();
				  	  String tp = jah;
				  	  //System.out.println("From get Latest on TOFstart " + jah );
				  	  String[] tksp = tp.split("<RS>");
				  	  //String[] tksp = jah.split("<RS>");
				  	  System.out.println("here is array size from getlast : " + tksp.length);
				  	  
				  	  for (int i = 0; i<tksp.length; i++) {
				  		  if(tksp[i].matches("^(536).*$")) {
				  			  index = i;
				  			  System.out.println("L " + index);
				  			  String lastT = tksp[index].replaceAll("536<US>" + configProps.TCID + "#", "");
				  			  System.out.println("Last ticket number! : " + lastT);
				  			  logger.info("Last ticket number! : " + lastT);
				  			  int lasTnum = Integer.parseInt(lastT);

				  			  tknums.add(lasTnum);
				  			  setTknums(tknums);
				  			  System.out.println("after set tknums%%%" + tknums);
				  			
				  			  //ticknum.add(lasTnum);
				  			  //return lasTnum;
				  			  last = tknums.get(tknums.size() -1);
				  			  
				  			  retLast(last);
				  			  sendRequest2();

				  	  }
				  		  

				  		  }
				  		  
				  		  }
			    	  }catch (StringIndexOutOfBoundsException s) {
			    		  logger.info("TOF message is short but, I'll wait.. " +s.getMessage());
			    		  //s.printStackTrace();
			    		  
			    	  }
			      }

		}
		
		
		
		
		

		private String getFieldData(String jah4, String FID, int p1) {
			
			if(jah4.contains(FID)) {
				  int ty = jah4.indexOf(FID);
				  FIDentry = jah4.substring(ty+4, ty+p1);
				  logger.info("FID data : |  " +FIDentry);
				  return FIDentry;
			  }
			
			return FIDentry;
			
			
		}
		
		public static int retLast(int last) {
			//System.out.println("tknums >>>" + tknums.size());
			last = tknums.get(tknums.size() -1);
			//System.out.println("LASTLASTLASTLAST^^ " + last);
			return last;
		}
		
		
		
		
		
		
}

		
		//class onTOF implements Runnable{
			

			//@Override
			//public void run() {
				// TODO Auto-generated method stub
				
			//}
			
		//}
			
			


