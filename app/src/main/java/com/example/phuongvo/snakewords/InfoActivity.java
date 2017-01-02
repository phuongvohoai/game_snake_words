package com.example.phuongvo.snakewords;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class InfoActivity extends Activity {
	Button goback_btn;
	MediaPlayer mp3_play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.info);
        goback_btn = (Button) findViewById(R.id.goback_btn);
        mp3_play = MediaPlayer.create(getApplicationContext(),R.raw.info);
        mp3_play.start();
        mp3_play.setLooping(true);
        goback_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),CreateActivity.class));
			}
		});
        
        goback_btn.setOnTouchListener(new View.OnTouchListener() {
			
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
    protected void onPause() {
        super.onPause();
        mp3_play.release();
        finish();
     }
}