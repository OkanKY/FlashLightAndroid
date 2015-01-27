package com.ok.flashlight;





import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class FlashLightActivity extends Activity{
	private RadioButton rb0;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private final Context context = this;
	private Camera camera;
	private Thread runnable;
	private boolean isActivityPaused = false;
	private int frequency;
	TextView txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		frequency=1000;
		setContentView(R.layout.flashlightactivity);
		rb0=(RadioButton) findViewById(R.id.radio0);
		rb1=(RadioButton) findViewById(R.id.radio1);
		rb2=(RadioButton) findViewById(R.id.radio2);
		rb3=(RadioButton) findViewById(R.id.radio3);
		final Button butFinish=(Button)findViewById(R.id.finish);
		final Button butStart=(Button)findViewById(R.id.start);
		final Button butStop=(Button)findViewById(R.id.stop);
	    	  final PackageManager pm = context.getPackageManager();
	  		if (isCameraSupported(pm)) 
	  		{
	  			try
		  		{
		  			camera = Camera.open();
		  			 onFlash(true);
		  		}
		  		catch(Throwable t) {
	                t.printStackTrace();
	            }
	  		}
	  	  
	      
		  butFinish.setOnClickListener(new OnClickListener() {  
	            @Override
	            public void onClick(View v) {   
	    			stopThread(runnable);
	    			finish();
	            }
	        });
		  butStop.setOnClickListener(new OnClickListener() {  
	            @Override
	            public void onClick(View v) {   
	            	frequency=-1;
	            }
	        });
		  butStart.setOnClickListener(new OnClickListener() {  
	            @Override
	            public void onClick(View v) {   
	            	if(rb0.isChecked())
	            		frequency=1000;
	    			else if(rb1.isChecked())
	    				frequency=200;
	    			else if(rb2.isChecked())
	    				frequency=500;
	    			else if(rb3.isChecked())
	    				frequency=800;

	            }
	        });
		  
	}
	@Override
	public void onBackPressed() {
			super.onBackPressed();
			stopThread(runnable);
			finish();
	}
	
	@Override
	protected void onDestroy() {
	super.onDestroy();
	
      stopThread(runnable);
	}
	public void onFlash(boolean on) {
		
		PackageManager pm = context.getPackageManager();
		final Parameters p = camera.getParameters();
		
		if (isFlashSupported(pm)) {
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
	
		} 
		else {
			
			AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle("No Camera Flash");
			alertDialog
					.setMessage("The device's camera doesn't support flash.");
			alertDialog.setButton(RESULT_OK, "OK",
					new DialogInterface.OnClickListener() {
						public void onClick(final DialogInterface dialog,
								final int which) {
							Log.e("err",
									"The device's camera doesn't support flash.");
						}
					});
			alertDialog.show();
		}
	}
	
	private synchronized void stopThread(Thread runnable)
	{
		
	    if (runnable != null)
	    {
	    	isActivityPaused = true;
	        runnable = null;
	        if (camera != null) {
				camera.release();
			}
	    }
	    
	}

	/**
	 * @param packageManager
	 * @return true <b>if the device support camera flash</b><br/>
	 *         false <b>if the device doesn't support camera flash</b>
	 */
	private boolean isFlashSupported(PackageManager packageManager) {
		// if device support camera flash?
		if (packageManager
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
			return true;
		}
		return false;
	}

	/**
	 * @param packageManager
	 * @return true <b>if the device support camera</b><br/>
	 *         false <b>if the device doesn't support camera</b>
	 */
	private boolean isCameraSupported(PackageManager packageManager) {
		// if device support camera?
		if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		}
		return false;
	}
}
