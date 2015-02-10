# FlashLightAndroid
Uygulamanın amacı Android İşletim sistemlerine sahip akıllı telefonların var olan Flash'larını kullanarak kullanıcıya sunulan üç değişik frekans seçeneğine göre flashın secilen ritimde yanıp sönmesidir. Kullanıcı Flash'ını gerektiği durumlarda Değişik frekanslarda veya Sürekli yanan durumda calıstırabilmektedir.
  Bu durumu
  
      
                  
                runnable = new Thread() {
			       @Override 
			       public void run() {
			    	   try {
			    	   while(!isActivityPaused ) {
			    		      if(frequency!=-1)
			    		      {
			    		       Thread.sleep(1000-frequency);
			    		    	p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			    				camera.setParameters(p);
			    				camera.startPreview();
			    	          }
			    		    	
			    		    	if(frequency!=1000)
			    		    	{
			    		       Thread.sleep(1000-frequency);
			    			    p.setFlashMode(Parameters.FLASH_MODE_OFF);
			    				camera.setParameters(p);
			    			    camera.stopPreview();
			    		    	}
			    			            }
			    	       }
			    	   catch(Throwable t) {}

			       }
			};
			new Thread(runnable).start(); 
 Thread Bloğu sağlamaktadır.
Uygulamamın Google Play Storedeki yayınlanma adresi https://play.google.com/store/apps/details?id=com.ok.flashlight&hl=tr
Yukarıdaki adrese girerek gerekli bilgilere erişebilirsiniz

