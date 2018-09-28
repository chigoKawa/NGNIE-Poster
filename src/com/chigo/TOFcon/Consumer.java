package com.chigo.TOFcon;

import java.io.IOException;
import java.math.BigInteger;

///*|----------------------------------------------------------------------------------------------------
// *|            This source code is provided under the Apache 2.0 license      	--
// *|  and is provided AS IS with no warranty or guarantee of fit for purpose.  --
// *|                See the project's LICENSE.md for details.                  					--
// *|           Copyright Thomson Reuters 2016. All rights reserved.            		--
///*|----------------------------------------------------------------------------------------------------

import com.thomsonreuters.ema.access.*;
import com.thomsonreuters.ema.access.DataType.*;
import com.thomsonreuters.ema.rdm.EmaRdm;


class AppClient implements OmmConsumerClient {
	
	private OmmConsumer _ommConsumer;
	private long _streamHandle;
	private int _postStreamID;
	int _postID = 1;
	int _bid = 340, _ask = 341;
	public static String RIC1 = configProps.RIC1;
	public static String RIC2 = configProps.RIC2;
	public static String cont_service = configProps.cont_service;
	
	
	
	public void onRefreshMsg(RefreshMsg refreshMsg, OmmConsumerEvent event)	{
		System.out.println("----- Refresh message ----");
		System.out.println(refreshMsg);
		TOFstart.logger.info(refreshMsg.toString());
		TOFstart.logger.info(refreshMsg.state().toString());

		if(refreshMsg.domainType() == EmaRdm.MMT_LOGIN && refreshMsg.state().streamState() == OmmState.StreamState.OPEN && refreshMsg.state().dataState() == OmmState.DataState.OK )	{
			// login successful, save variables
			_postStreamID = refreshMsg.streamId();
			_ommConsumer = (OmmConsumer) event.closure();
			_streamHandle = event.handle();

			System.out.println("System refresh, starting posting...");
			//postMessage();
		}
		else	{
			System.out.println("Stream not open");
			TOFstart.logger.warning("Not Connected to ADS: !!");
		}
	}
	
	
	
	public void onStatusMsg(StatusMsg statusMsg, OmmConsumerEvent event) 	{
		System.out.println("----- Status message ----");
		System.out.println(statusMsg);
	}
	
	
	
	public void onAckMsg(AckMsg ackMsg, OmmConsumerEvent event)	{
		System.out.println("----- Ack message ----");
		TOFstart.logger.info(ackMsg.toString());

		System.out.println("Continue posting...");
	
	}
	
	
	
	public void postMessage(double BID, double volume1, String direction1 )	{
		TOFstart.logger.info("About to POST message ... > ");
		try	{ Thread.sleep(300); } catch(Exception e) {}
		int direction;
		if (direction1.equals(1)) {
			direction = 31;
		}else {
			direction = 32;
		}
		
		FieldList nestedFieldList = EmaFactory.createFieldList();
		


		//nestedFieldList.add(EmaFactory.createFieldEntry().real(22, _bid, OmmReal.MagnitudeType.EXPONENT_NEG_1)); 13.89
		//nestedFieldList.add(EmaFactory.createFieldEntry().real(25, _ask, OmmReal.MagnitudeType.EXPONENT_NEG_1));
		nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(393, BID, OmmReal.MagnitudeType.EXPONENT_NEG_2));
		nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(22, BID, OmmReal.MagnitudeType.EXPONENT_NEG_2));//(22, 40, OmmReal.MagnitudeType.EXPONENT_NEG_1));
		if(Double.toString(volume1).length() >= 5 ) {
			TOFstart.logger.info("Volume size greater tahn 5 ... > " + volume1);
			nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(791, volume1, OmmReal.MagnitudeType.EXPONENT_NEG_2));//(791, String.valueOf(volume1)));
			
		}else {
			TOFstart.logger.info("Volume size LESS than 5 ... > " + volume1);
			nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(791, volume1, OmmReal.MagnitudeType.EXPONENT_NEG_2));
		}
		//nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(791, volume1, OmmReal.MagnitudeType.EXPONENT_NEG_2 ));
		//nestedFieldList.add(EmaFactory.createFieldEntry().real(270, direction, OmmReal.MagnitudeType.EXPONENT_0));
		nestedFieldList.add(EmaFactory.createFieldEntry().enumValue(270, direction));
		nestedFieldList.add(EmaFactory.createFieldEntry().enumValue(820, 1));	
		nestedFieldList.add(EmaFactory.createFieldEntry().ascii(831, "NGNIE Poster"));
		
		// create an update message for our item
		UpdateMsg nestedUpdateMsg = EmaFactory.createUpdateMsg()
			.streamId(_postStreamID)
			.name(RIC1)
			.payload(nestedFieldList);
				
		PostMsg postMsg = EmaFactory.createPostMsg()
			.postId(_postID++)
			.serviceName( cont_service )
			.name( RIC1 )
			.solicitAck(true)
			.payload(nestedUpdateMsg)	
			.complete(true);
			
		_ommConsumer.submit(postMsg, _streamHandle);
	}		
	
	
	public void postMessage2(double BID, double volume1, String direction1 )	{
		System.out.println("calling post msg... > ");
		try	{ Thread.sleep(300); } catch(Exception e) {}
		int direction;
		if (direction1.equals(1)) {
			direction = 31;
		}else {
			direction = 32;
		}
		
		FieldList nestedFieldList = EmaFactory.createFieldList();


		//nestedFieldList.add(EmaFactory.createFieldEntry().real(22, _bid, OmmReal.MagnitudeType.EXPONENT_NEG_1));
		//nestedFieldList.add(EmaFactory.createFieldEntry().real(25, _ask, OmmReal.MagnitudeType.EXPONENT_NEG_1));
		nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(393, BID, OmmReal.MagnitudeType.EXPONENT_NEG_2)); //(22, 40, OmmReal.MagnitudeType.EXPONENT_NEG_1));
		double lll = volume1;
		if(Double.toString(volume1).length() >= 5 ) {
			nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(791, volume1, OmmReal.MagnitudeType.EXPONENT_NEG_2));
			
		}else {
			nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(791, volume1, OmmReal.MagnitudeType.EXPONENT_NEG_2));
		}
		nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(22, BID, OmmReal.MagnitudeType.EXPONENT_NEG_2));
		//nestedFieldList.add(EmaFactory.createFieldEntry().realFromDouble(791, volume1, OmmReal.MagnitudeType.EXPONENT_NEG_1));
		//nestedFieldList.add(EmaFactory.createFieldEntry().real(270, direction, OmmReal.MagnitudeType.EXPONENT_0));
		nestedFieldList.add(EmaFactory.createFieldEntry().enumValue(270, direction));
		nestedFieldList.add(EmaFactory.createFieldEntry().enumValue(820, 1));
		nestedFieldList.add(EmaFactory.createFieldEntry().ascii(831, "NGNIE Poster"));
		
		// create an update message for our item
		UpdateMsg nestedUpdateMsg = EmaFactory.createUpdateMsg()
			.streamId(_postStreamID)
			.name(RIC2)
			.payload(nestedFieldList);
				
		PostMsg postMsg = EmaFactory.createPostMsg()
			.postId(_postID++)
			.serviceName( cont_service )
			.name( RIC2 )
			.solicitAck(true)
			.payload(nestedUpdateMsg)	
			.complete(true);
			
		_ommConsumer.submit(postMsg, _streamHandle);
	}		
	
	
	public void onUpdateMsg(UpdateMsg updateMsg, OmmConsumerEvent event) {}
	public void onGenericMsg(GenericMsg genericMsg, OmmConsumerEvent event) {}
	public void onAllMsg(Msg msg, OmmConsumerEvent event) {}

}


public class Consumer implements Runnable{
	public static double ll;
	public static void main(String[] args)	{
		
	}

	@Override
	public void run() {
		TOFstart tf = new TOFstart();
		OmmConsumer consumer = null;
		OmmConsumer consumer2 = null;
		configProps conf2 = new configProps();
		try {
			conf2.getValues();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String price_lower = configProps.price_lower;
		String ADS1 = configProps.ADS1_IP;
		String ADS2 = configProps.ADS2_IP;
		String ADS1P = configProps.ADS1_PORT;
		String ADS2P = configProps.ADS2_PORT;
		String ADSuser = configProps.ADSuser;
		
		try		{
			AppClient appClient = new AppClient();
			AppClient appClient2 = new AppClient();
			TOFstart.logger.info("Connecting to ADS : " + ADS1+":"+ADS1P + "\nOR : " +ADS2+":"+ADS2P); 
			System.out.println("will be connecting to ADS :"+ ADS1);
			System.out.println("\nand also  to :  "+ ADS2);
			consumer = EmaFactory.createOmmConsumer(EmaFactory.createOmmConsumerConfig()
				//.host(ADS1)
				.host(ADS2)
				.username(ADSuser)
				.applicationId("NGNIE Poster"));
			
			//consumer2 = EmaFactory.createOmmConsumer(EmaFactory.createOmmConsumerConfig()
					//.host(ADS2)
					//.host("10.223.73.124:14005")
					//.username(ADSuser)
					//.applicationId("NGNIE Poster"));
				
			ReqMsg reqMsg = EmaFactory.createReqMsg();
			
			long closure = 1;
			long loginHandle = consumer.registerClient(reqMsg.domainType( EmaRdm.MMT_LOGIN ), appClient, consumer);
			//long loginHandle2 = consumer2.registerClient(reqMsg.domainType( EmaRdm.MMT_LOGIN ), appClient2, consumer2);
			
			//consumer2.reissue( reqMsg.serviceName( "MLIP" ).name( "CHIIE=DMT1" ).priority( 2, 2 ),
	        		//loginHandle );
			try {
				double BID = tf.prcray.get(tf.prcray.size() -1);
				double volume = tf.volray.get(tf.volray.size() -1);
				TOFstart.logger.info("volume that will be posted is > : " + volume);
				System.out.println("GUAVA" + volume);
				//int volume = (int)volume2;
				String direction1 = tf.dire;
				/*
				if(BID >= Integer.parseInt(price_lower)) {
					TOFstart.logger.info("Starting Initial Contribution to Eikon.. ");
					appClient.postMessage(BID, volume, direction1);
					appClient.postMessage2(BID, volume, direction1);
					
				}
				*/
				int BIDsize = tf.prcray.size();
				while(true) {
					
					//System.out.println("BIDsize is here -- "+BIDsize);
					//System.out.println("prcarr size ll)) " + tf.prcray.size());
					
		
					if(BIDsize +1 == tf.prcray.size()&& tf.prcray.get(tf.prcray.size() -1) >= Integer.parseInt(price_lower) ) {
						
						double ll2 = tf.prcray.get(tf.prcray.size() -1);
						ll = ll2;
						TOFController con = new TOFController();
						//con.generate2();
						TOFstart.logger.info("Contributing to Eikon : : " );
						double vol1 = tf.volray.get(tf.volray.size() -1);
						appClient.postMessage(tf.prcray.get(tf.prcray.size() -1), tf.volray.get(tf.volray.size() -1), tf.dire);
						
						appClient.postMessage2(tf.prcray.get(tf.prcray.size() -1), tf.volray.get(tf.volray.size() -1), tf.dire);
						
						
					}
					BIDsize = tf.prcray.size();
					Thread.sleep(600);
					
				}
				
			}catch(ArrayIndexOutOfBoundsException a) {
				System.out.println(a.getMessage());
			}
			
			
			
			



			
			//while(BIDsize != tf.prcray.size() ) {
				//System.out.println(BIDsize + " prcarr went up... " + tf.prcray.size());
				//appClient.postMessage(BID, volume, direction1);
				
			//}
			
			
		} 
		catch (InterruptedException | OmmException excp) {
			System.out.println(excp.getMessage());
			TOFstart.logger.warning(excp.getMessage());
			run();
		}
		finally {
			if (consumer != null) 
				
				consumer.uninitialize();
			
			
			
		}
		
		
	}
}
