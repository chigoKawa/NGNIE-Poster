package com.chigo.TOFcon;

import java.io.IOException;





public class begin1 {
	public begin1() {
		
	}

	public static void main(String[] args) throws IOException {
		//TOFstart tf = new TOFstart();
		//writeUTF er = new writeUTF();
		
		//TOFstart.writeUTF wr = new TOFstart.writeUTF();
		//tf.Connect();
		//tf.setupStreams();
		//tf.run();

		Thread t1 = new Thread(new TOFstart());
		Thread t2 = new Thread(new Consumer());
		t2.start();
		t1.start();
		
		

	}

}
