package com.chigo.TOFcon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class configProps {
	public static String TOF_IP = "";
	public static String TOF_PORT = "";
	public static String TCID = "";
	public static String ADS1_IP = "";
	public static String ADS1_PORT = "";
	public static String ADS2_IP = "";
	public static String ADS2_PORT = "";
	public static String price_lower = "";
	public static String restart_time = "";
	public static InputStream inputStream;
	public static String RIC1 = "";
	public static String RIC2 = "";
	public static String cont_service = "";
	public static String ADSuser = "";
	
	public configProps() {
		
	}
	public void getValues() throws IOException{
		try {
			Properties prop = new Properties();
			String propFileName = "resources\\config\\config.chigo";
			inputStream = new FileInputStream(propFileName);
			//inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			
			if(inputStream != null) {
				prop.load(inputStream);
				
			}else {
				throw new FileNotFoundException("config file ' " + propFileName + "'  I cannot find the file");
			}
			
			Date time = new Date(System.currentTimeMillis());
			
			TOF_IP = prop.getProperty("TOF_IP");
			TOF_PORT = prop.getProperty("TOF_PORT");
			TCID = prop.getProperty("TCID");
			ADS1_IP = prop.getProperty("ADS1_IP");
			ADS1_PORT = prop.getProperty("ADS1_PORT");
			ADS2_IP = prop.getProperty("ADS2_IP");
			ADS2_PORT = prop.getProperty("ADS2_PORT");
			price_lower = prop.getProperty("price_lower");
			restart_time = prop.getProperty("restart_time");
			RIC1 = prop.getProperty("RIC1");
			RIC2 = prop.getProperty("RIC2");
			cont_service = prop.getProperty("cont_service");
			ADSuser = prop.getProperty("ADSuser");
			
			TOFstart.logger.info("TCID is " + TCID);
		}catch (Exception e6) {
			TOFstart.logger.warning("ERROR! " + e6.getMessage() );
			
			
		}finally {
			inputStream.close();
		}
		

		
	}
	
	
	

}
