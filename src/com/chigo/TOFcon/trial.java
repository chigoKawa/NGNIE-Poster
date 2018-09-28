package com.chigo.TOFcon;

import java.util.concurrent.TimeUnit;

import javafx.scene.paint.Color;

public class trial extends TOFController implements Runnable  {
	
	public void setter() {
	 	
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
	public void run() {
		setter();
		
	}
    
		
	
	
	

	    }
	    

 

