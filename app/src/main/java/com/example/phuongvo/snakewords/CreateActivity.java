package com.example.phuongvo.snakewords;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class CreateActivity extends Activity {
	Button play_btn,other1_btn,other2_btn,other3_btn;
	MediaPlayer mp3_play,mp3_btn1,mp3_btn2;
	Context context;
	int current_level;
	Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        context=this;
        
        
    	Cursor testdata;
    	TestAdapter mDbHelper = new TestAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	testdata= mDbHelper.getCurrent_Load();
    	current_level= Utility.IntGetColumnValue(testdata,"cur_level");
    	mDbHelper.close();
      
    	other1_btn = (Button) findViewById(R.id.howtoplay_btn);
        other2_btn = (Button) findViewById(R.id.pause_btn);
        other3_btn = (Button) findViewById(R.id.music_btn);
        play_btn = (Button) findViewById(R.id.replay_btn);
        mp3_play = MediaPlayer.create(getApplicationContext(),R.raw.maingame);
        mp3_play.start();
        mp3_play.setLooping(true);
        play_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(current_level>1)
				{
					dialog= new Dialog(CreateActivity.this);
					dialog.setContentView(R.layout.custom_dialog);
					TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage_dialog);
					tvMessage.setText("Xin Chào!");
					TextView tvText = (TextView) dialog.findViewById(R.id.tvText_dialog);
					tvText.setText("Bạn muốn chơi lại từ đầu không?");
					Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
					btnYes.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							dialog.cancel();
							// TODO Auto-generated method stub
							TestAdapter mDbHelper = new TestAdapter(context);         
			            	mDbHelper.createDatabase();       
			            	mDbHelper.open(); 
			            	mDbHelper.Return2Level0();
			            	mDbHelper.close();
			            	overridePendingTransition(0, 0);
							startActivity(new Intent(CreateActivity.this,MainActivity.class));
							overridePendingTransition(0, 0);
							
						}
					});
					Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
					btnNo.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							overridePendingTransition(0, 0);
							startActivity(new Intent(CreateActivity.this,MainActivity.class));
							overridePendingTransition(0, 0);
							
						}
					});
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
				}
				else
				{
					overridePendingTransition(0, 0);
					startActivity(new Intent(CreateActivity.this,MainActivity.class));
					overridePendingTransition(0, 0);
				}
			}
		});
        
        play_btn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.play_button);
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
        
        other1_btn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.other_button);
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
        
        
        other2_btn.setOnTouchListener(new View.OnTouchListener() {
		
        	@Override
        	public boolean onTouch(View v, MotionEvent event) {
        		if (event.getAction() == MotionEvent.ACTION_DOWN){
        			MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.other_button);
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
        
         other3_btn.setOnTouchListener(new View.OnTouchListener() {
    		
        	@Override
        	public boolean onTouch(View v, MotionEvent event) {
        		if (event.getAction() == MotionEvent.ACTION_DOWN){
        			MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.other_button);
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
        
       
		other1_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(CreateActivity.this);
				alert.setTitle("Hướng dẫn");
				alert.setIcon(R.drawable.howtoplay_btn1_bg); 
				
				// set dialog message
				alert
					.setMessage("				Tìm càng nhiều từ vựng càng tốt trong ma trận. Những từ càng dài sẽ được càng nhiều điểm. Bạn nối các từ theo hướng di chuyển của con rắn (ngang, dọc hoặc chéo). Ngay khi bạn nối được một từ có nghĩa, bạn có thể ghi điểm.")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
					AlertDialog alertDialog = alert.create();
					alertDialog.show();
			}
		});
		
		other2_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(CreateActivity.this);
				alert.setTitle("Bảng xếp hạng");
				alert.setIcon(R.drawable.highscore_btn1_bg); 
				
				// set dialog message
				alert
					.setMessage("Legendary:    Shockwave.JF \r\n" + "Godlike:          Quân LM \r\n" + "Unstoppable: Rum KT")
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
					AlertDialog alertDialog = alert.create();
					alertDialog.show();
			}
		});
		
		other3_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				overridePendingTransition(0, 0);
				startActivity(new Intent(CreateActivity.this,InfoActivity.class));
				overridePendingTransition(0, 0);
			}
		});
	}
	  
       
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
       super.onPause();
       mp3_play.release();
       finish();
    }
}
