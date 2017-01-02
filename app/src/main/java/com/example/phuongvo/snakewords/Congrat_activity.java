package com.example.phuongvo.snakewords;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Congrat_activity extends Activity {

	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.congratulation);
	        Button btnReset, btnBack;
	        btnReset=(Button) findViewById(R.id.btnCongrat_reset);
	        btnBack=(Button) findViewById(R.id.btnCongrat_back);
	        btnBack.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getApplicationContext(),CreateActivity.class));
				}
			});
	        
	        btnBack.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN){
						MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.back_button);
						mp.start();		
						mp.setOnCompletionListener(new OnCompletionListener() {
							  
		                    @Override
		                    public void onCompletion(MediaPlayer mp) {
		                        mp.release();
		                    }
		                });
					}
					// TODO Auto-generated method stub
					return false;
				}
			});
	        btnReset.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder alert = new AlertDialog.Builder(Congrat_activity.this);
			    	alert.setTitle("Chơi lại nữa không?");
			    	alert
			    	.setCancelable(false)
			    	.setPositiveButton("Thôi, không rãnh!",new DialogInterface.OnClickListener()
			    		{
				        	public void onClick(DialogInterface dialog,int id) {
				        		
				        	}	
			    		})
			    	.setNegativeButton("Quất", new DialogInterface.OnClickListener() 
			    		{	
				        	@Override
				        	public void onClick(DialogInterface dialog, int which){
				        		dialog.cancel();	
				        		TestAdapter mDbHelper = new TestAdapter(Congrat_activity.this);         
				            	mDbHelper.createDatabase();       
				            	mDbHelper.open(); 
				            	mDbHelper.Return2Level0();
				            	mDbHelper.close();
				            	overridePendingTransition(0, 0);
								startActivity(new Intent(Congrat_activity.this,MainActivity.class));
								overridePendingTransition(0, 0);
				        	}
			    		}).show();
					
				}
			});
	        
	        btnReset.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN){
						MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.back_button);
						mp.start();		
						mp.setOnCompletionListener(new OnCompletionListener() {
							  
		                    @Override
		                    public void onCompletion(MediaPlayer mp) {
		                        mp.release();
		                    }
		                });
					}
					// TODO Auto-generated method stub
					return false;
				}
			});
	    }
}
