package com.chigo.TOFcon;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXProgressBar;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TOFController extends TOFstart  {
	
	public TOFController()  {
		super();
		
	}
	//TOFstart tf2 = new TOFstart();
	begin1 bg = new begin1();
	
	public double bid = 0;
	public double newBid;
	public static Color bidcolor = Color.RED;
	@FXML
	public Button btn1;
    @FXML
    private MenuItem edtcon;
	
    @FXML
    private Button testbtn;
    private Service<Void> backgroundThread;
    
    @FXML
    private MenuItem connect;
    @FXML
    public Label lb1;
    @FXML
    public Label lb4;
    
    @FXML
    public Label lb5;
    
    @FXML
    public Label lb6;
    
    
    public Label getLb1() {
		return lb1;
	}


	public void setLb1(Label lb1) {
		this.lb1 = lb1;
	}
	private int size = prcray.size();
	@FXML
	public JFXProgressBar jbar;
	
	
    
   
    @FXML
    private TextField prcDisp;

    @FXML
    void start(MouseEvent event) {
    	begin1 bg = new begin1();

    }
    public void editConf(ActionEvent event) {
    	File conf = new File("resources\\config\\config.chigo");
    	//Desktop.getDesktop().open(conf);
    	
    	try {
			//Desktop.getDesktop().open(conf);
			
		    Runtime runTime = Runtime.getRuntime();
		    // Don't forget that '\' needs to be escaped with another '\'
		    // Also, there may be spaces in the name(s). Use quotes (with their own escapes!)
		    Process process = runTime.exec("\"C:\\Windows\\system32\\notepad.exe\"" +
		                                   " " +    // Separate argument with space
		                                   "\""+conf.getAbsolutePath()+"\"");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void generate(ActionEvent event) {
    	Random rand = new Random();
    	int myrand = rand.nextInt(30) + 1;
    	//lb1.setText(TOFstart.prcray.get(TOFstart.prcray.size() -1).toString());
    	double newBid = prcray.get(prcray.size() -1);
    	if(newBid >= bid) {
			bidcolor = Color.GREEN;
			lb1.setTextFill(bidcolor);
    		
    	}else {
			bidcolor = Color.RED;
			lb1.setTextFill(bidcolor);
    	}
    	bid = newBid;
    	lb1.setText(prcray.get(prcray.size() -1).toString());
    	//lb1.setText(Double.toString(bid));
    	
    	
    }
    
    public void generate2() {
    	Random rand = new Random();
    	int myrand = rand.nextInt(30) + 1;
    	//System.out.println("ll ll ll ll ll ll " +ll);
    	//lb1.setText(TOFstart.prcray.get(TOFstart.prcray.size() -1).toString());
    	
    	lb1.setText(Integer.toString(myrand));
    	
    }
    public Runnable setText1()throws ArrayIndexOutOfBoundsException {
    	
		try {
		TimeUnit.SECONDS.sleep(5);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
    	
		int BIDsize = 1;
		System.out.println(BIDsize);
		//lb1.setText(prcray.get(prcray.size() -1).toString());
		lb4.setText("0");
		
		
		try {

			newBid = prcray.get(prcray.size() -1);
			


			
		}catch(Exception e2) {
			e2.getMessage();
		}
		//return null;
		while(true){
		if (newBid >= bid) {
			bidcolor = Color.GREEN;
			
			lb4.setTextFill(bidcolor);
			//lb4.setText(Double.toString(newBid));
			
		}
		else {
			bidcolor = Color.RED;
			
			lb4.setTextFill(bidcolor);
			//lb4.setText(Double.toString(bid));
		}
		bid = newBid;
		lb4.setText(Double.toString(bid));
		
		}
		

    }
@FXML public void threads() {
	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), 
	        new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	
	        		Thread t1 = new Thread(new TOFstart());
	        		Thread t2 = new Thread(new Consumer());
	        		//Thread t4 = new Thread(new trial());
	        		//Thread t3 = new Thread(setText1());
	        		t1.setDaemon(true);
	        		t2.setDaemon(true);
	        		//t4.setDaemon(true);
	        		//t1.start();
	        		//t2.start();
	        		//t4.start();
	        		//t3.setDaemon(true);
	        		//t3.start();
	        		//new Thread(setText1()).start();
	        		//run3();
	        		//jbar.setProgress(-1.0f);
	        		//btn1.setText("STARTED!");
	        		
	        		while(!t1.isAlive()) {
	        			t1.start();
	        			
	        		}
	        		while(!t2.isAlive()) {
	        			t2.start();
	        		}
	            	
	            }

	        }
	    ));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	    
	
}
@FXML public void run3() {
	  Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), 
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                //run2();
		                

		        		try {

		        			newBid = prcray.get(prcray.size() -1);
		        			Consumer nn = new Consumer();
		        			lb6.setText(Double.toString(nn.ll));
		        			


		        			
		        		}catch(Exception e2) {
		        			e2.getMessage();
		        		}
		        		//return null;
		        		//while(true){
		        		if (newBid > bid) {
		        			bidcolor = Color.GREEN;
		        			
		        			lb4.setTextFill(bidcolor);
		        			lb4.setText(Double.toString(newBid));
		        			lb5.setText(Double.toString(volray.get(volray.size() -1)));
		        			
		        		}
		        		if (newBid < bid) {
		        			bidcolor = Color.RED;
		        			
		        			lb4.setTextFill(bidcolor);
		        			lb4.setText(Double.toString(newBid));
		        			lb5.setText(Double.toString(volray.get(volray.size() -1)));
		        			
		        		}
		        		
		        		else {
		        			//bidcolor = Color.GREEN;
		        			
		        			//lb4.setTextFill(bidcolor);
		        			//lb4.setText(Double.toString(newBid));
		        		}
		        		bid = newBid;
		        		//lb4.setText(Double.toString(bid));
		        		
		        		
		        		}
		        		
		                
		            //}
		        }
		    ));
		    timeline.setCycleCount(Animation.INDEFINITE);
		    timeline.play();
		    
		    
		    
	
}


public void stop() throws Exception {
    //super.stop(); //To change body of generated methods, choose Tools | Templates.
    System.exit(0);
}
   
@FXML public void run2() {
	backgroundThread = new Service<Void>() {

		@Override
		protected Task<Void> createTask() {
			
			return new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					
					try {
						TimeUnit.SECONDS.sleep(5);
						
						
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					
					
					
					//while(prcray.size() > bid) {
					//while(true) {
						newBid = prcray.get(prcray.size() -1);
						if(newBid >= bid) {
							System.out.println("newBid is greater !!!![]---+++");
							bidcolor = Color.GREEN;
							lb4.textFillProperty().setValue(bidcolor);
							updateMessage(Double.toString(newBid));
							
						}
						else {
							bidcolor = Color.RED;
							lb4.textFillProperty().setValue(bidcolor);
							updateMessage(Double.toString(newBid));
							System.out.println("newBid is less than +++++++++++((((()))))");
							
						}
						bid = newBid;
						//Thread.sleep(300);
						return null;
						
				//}
					
					/*
					for(int i = 0; i<=100000; i++) {
						updateMessage(Integer.toString(i));
						//updateValue();
						
					}
					
					*/
						//return null;
					
						}
				
				
				
			};
		}
		
		
	};
	
	backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
		
		@Override
		public void handle(WorkerStateEvent event) {
			System.out.println("done!");
			lb4.textProperty().unbind();
			
		}
	});

	   
		lb4.textProperty().bind(backgroundThread.messageProperty());
		//lb4.textFillProperty().setValue(bidcolor);
		//backgroundThread.notify();
		backgroundThread.restart();

	

		
	

	
		
	   
	
						
			
		    
		
	
		}
		
	
	
		


	







  
    @FXML
    
	public void start2() {
    	String[] arr = new String[2];
    	
    	/*
    	new Thread(new Runnable(){
    	    public void run(){
    	    	TOFstart tg = new TOFstart();
    	    	tg.run();
  
    	    	
    	    }
    	}).start();
    	
    	new Thread(new Runnable(){
    	    public void run(){

    	    	Consumer cn = new Consumer();
    	    	cn.run();
    	    	
    	    }
    	}).start();
    	
    	setText1();
    	
    	
*/
    	

    	System.out.println("triggered");
    	//Platform.setImplicitExit(false);
    	
		Thread t1 = new Thread(new TOFstart());
		Thread t2 = new Thread(new Consumer());

		t1.setDaemon(true);
		t2.setDaemon(true);

		t1.start();
		t2.start();
		/*
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
*/
		run3();
		jbar.setProgress(-1.0f);
		btn1.setText("STARTED!");

		
		

		
		

	
	
		
		
		

		

			
		}
		

	}



