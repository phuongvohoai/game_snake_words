package com.example.phuongvo.snakewords;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button pause_btn,sound_btn, hint_btn, goback_btn;
	MediaPlayer mp3= new MediaPlayer();
	boolean loop = true;
	
	CountDown timeCount;
	long totaltime=0L;
	long millis=0L;
	boolean passed=false;
	
	int G_int;
	Dialog dialog;
	TextView text, result_tv, vie_w, score_tv, timer_tv, special_tv, bonus_tv,tvText, tvMessage;
	ImageView snake_hint;
	private GridView gridView;
	Random r= new Random();
	Button btn_final, tooltip_btn, btnYes, btnNo;
    String items[];
    int WordsEachLevel;
    int true_way[];
    int _maxsize=6;
    
    int time_sw;
    int timeend_sw=10;
    String specialword;
    boolean isshow_sw=false;
    boolean answered=false;
    int _bonus;
    
    int _maxlength=6;
    int current_level;
    int current_score=0; //score use to load and save to DB
    int level_score=0;
    int time_score=0;
    int score_require;
    
    char matrix[][];
    List <String> ListWords= new ArrayList<String>();
    List <vector2d> Listvect= new ArrayList<vector2d>();
    vector2d ListHint[];
    MainActivity context1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_screen);
        
        //
        context1 = this;
    	
    	dialog= new Dialog(context1);
    	
    
		
		
        snake_hint= (ImageView) findViewById(R.id.snakehint_btn);
    	result_tv= (TextView) findViewById(R.id.result_tv);
		vie_w = (TextView) findViewById(R.id.vie_w);
		score_tv= (TextView) findViewById(R.id.score_tv);
	    tooltip_btn = (Button) findViewById(R.id.tooltip_btn);
        hint_btn= (Button) findViewById(R.id.replay_btn);
        timer_tv= (TextView) findViewById(R.id.timer_tv);
        special_tv = (TextView) findViewById(R.id.specialw_tv);
        bonus_tv = (TextView) findViewById(R.id.bonus_tv);
        //
        this.Current_Load();
        this.Level_initialize();
        hint_btn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound_button);			
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {
						  
	                    @Override
	                    public void onCompletion(MediaPlayer mp) {
	                        mp.release();
	                    }
	                });	
				}
				return false;
			}
		});

        goback_btn = (Button) findViewById(R.id.goback_btn);
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
				return false;
			}
		});
        goback_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.setContentView(R.layout.custom_dialog);
		 	   	tvMessage = (TextView) dialog.findViewById(R.id.tvMessage_dialog);
		 	   	tvText = (TextView) dialog.findViewById(R.id.tvText_dialog);
				tvMessage.setText("TRỞ LẠI");		
				tvText.setText("Bạn chắc chắn quay lại màn hình chờ ?");
				btnYes = (Button) dialog.findViewById(R.id.btnYes);
		    	btnNo = (Button) dialog.findViewById(R.id.btnNo);
				btnYes.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.cancel();
						startActivity(new Intent(MainActivity.this,CreateActivity.class));
						MainActivity.this.finish();
						
					}
				});
				btnNo.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.cancel();			
					}
				});
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
				
			}
		});

        sound_btn = (Button) findViewById(R.id.music_btn);
        sound_btn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.sound1_button);
					
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {
						  
	                    @Override
	                    public void onCompletion(MediaPlayer mp) {
	                        mp.release();
	                    }
	                });
					
					if (loop == true){
						mp3.pause();
						loop = false;
					}
					else{
						mp3.start();
						loop = true;
					}	
				}
				// TODO Auto-generated method stub
				return false;
			}
		});
        
        
        
        pause_btn = (Button) findViewById(R.id.pause_btn);
        pause_btn.setOnClickListener(new View.OnClickListener() {

        	@Override
        	public void onClick(View v) {
	        	// TODO Auto-generated method stub
        		totaltime=millis/1000;
				timeCount.cancel();
				
	        	dialog.setContentView(R.layout.pause_dialog);	
	        	tvMessage = (TextView) dialog.findViewById(R.id.tvMessage_pause);
	        	tvMessage.setText("TẠM DỪNG");
	        	Button btnPlay = (Button) dialog.findViewById(R.id.btnPlay_pause);
	        	Button btnReplay = (Button) dialog.findViewById(R.id.btnReplay_pause);
	        	Button btnBack = (Button) dialog.findViewById(R.id.btnBack_pause);
	      		btnPlay.setOnClickListener(new View.OnClickListener() {
	      			
	      			@Override
	      			public void onClick(View v) {
	      				dialog.cancel();
	      				timeCount = new CountDown(totaltime*1000, 1000);
			    		timeCount.start();
		        		
	      			}
	      		});
	      		btnReplay.setOnClickListener(new View.OnClickListener() {
	      			
	      			@Override
	      			public void onClick(View v) {	
	      				dialog.cancel();
	      				MainActivity.this.finish();
	    				Intent i = new Intent(MainActivity.this, MainActivity.class);  
	    		 		overridePendingTransition(0, 0);
	    		 		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);		
	    		 		overridePendingTransition(0, 0);
	    		 		startActivity(i);
	    		 		onRestart();
	      			
		        		
	      			}
	      		});
	      		btnBack.setOnClickListener(new View.OnClickListener() {
	      			
	      			@Override
	      			public void onClick(View v) {
	      				dialog.cancel();
	      				startActivity(new Intent(MainActivity.this,CreateActivity.class));
	    				MainActivity.this.finish();	
		        		
	      			}
	      		});
	      		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	      		dialog.show();
	      	    
	        	}
        	});
        pause_btn.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.pause_button);			
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {
						  
	                    @Override
	                    public void onCompletion(MediaPlayer mp) {
	                        mp.release();
	                    }
	                });	
				}
				return false;
			}
		});
        
      
        /*
        for(String s: ListWords)
        {
    	  str+=s+" ";
      	}
        TextView result_tv= (TextView) findViewById(R.id.result_tv);
        result_tv.setText(str);
        */
    }
    
    public void itemClicked(int position) {
        text.setText(items[position]);
    }

    
    //Nhan nut Back hien dialog
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
    exitByBackKey();
    return true;
    }
    return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

	    dialog.setContentView(R.layout.custom_dialog);
	    btnYes = (Button) dialog.findViewById(R.id.btnYes);
    	btnNo = (Button) dialog.findViewById(R.id.btnNo);
	    tvMessage = (TextView) dialog.findViewById(R.id.tvMessage_dialog);
 	   	tvText = (TextView) dialog.findViewById(R.id.tvText_dialog);
		tvMessage.setText("THÔNG TIN");		
		tvText.setText("Bạn chắc chắn muốn thoát trò chơi ?");		
		btnYes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				finish();
			}
		});
		btnNo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				dialog.cancel();
			}
		});
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
	    
    }
    @Override
    protected void onStart() {
        super.onStart();
        mp3 = MediaPlayer.create(getApplicationContext(),R.raw.playgame);
        mp3.start();
        mp3.setLooping(true);
     }
    
     @Override
     protected void onPause() {
        super.onPause();
        mp3.release();
     }
   
 
     @Override
     protected void onRestart() {

         super.onRestart();
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

    private void Current_Load()
    {
    	Cursor testdata;
    	TestAdapter mDbHelper = new TestAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	testdata= mDbHelper.getCurrent_Load();
    	current_level= Utility.IntGetColumnValue(testdata,"cur_level");
    	current_score= Utility.IntGetColumnValue(testdata,"cur_score");
    	level_score=current_score;
    	mDbHelper.close();
    	
    	
    }
    private void Level_initialize()
    {
    	//current level
    	//Neu level hien tai =
    	//check OnPause()  
    	String str="";
    	matrix =  new char [_maxsize][_maxsize];
    	ListWords.clear();
    	int level_info[]= new int[7];
    	Cursor testdata;
    	TestAdapter mDbHelper = new TestAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	testdata = mDbHelper.getlevel_require(current_level);
    	level_info[0]=Utility.IntGetColumnValue(testdata, "level_id");
    	level_info[1]=Utility.IntGetColumnValue(testdata, "timetofinish");
    	level_info[2]=Utility.IntGetColumnValue(testdata, "score_require");
    	level_info[3]=Utility.IntGetColumnValue(testdata, "letters_3");
    	level_info[4]=Utility.IntGetColumnValue(testdata, "letters_4");
    	level_info[5]=Utility.IntGetColumnValue(testdata, "letters_5");
    	level_info[6]=Utility.IntGetColumnValue(testdata, "letters_6");
    	
    	totaltime =level_info[1];
    	timer_tv.setText(totaltime+"");
    	int hafttime=level_info[1]/4;
    	time_sw=r.nextInt(hafttime)+hafttime;
    	
    	score_require=level_info[2];
    	
        this.Load_word(level_info[3],level_info[4],level_info[5],level_info[6]);
    	this.placeWords(); 
    	this.getwordfrom_matrix();
    	mDbHelper.close();
    	//fill to gridview
    	//fill to textview
    	gridView = (GridView) this.findViewById(R.id.gridView1);
        CustomGridAdapter gridAdapter = new CustomGridAdapter(this, items);
        gridView.setAdapter(gridAdapter);
        TextView level_tv = (TextView) findViewById(R.id.level_tv);
        str="LEVEL "+current_level;
        level_tv.setText(str);
        TextView score_tv= (TextView) findViewById(R.id.score_tv);
        score_tv.setText(String.valueOf(current_score));

	    dialog.setContentView(R.layout.custom_dialog);
	    tvMessage = (TextView) dialog.findViewById(R.id.tvMessage_dialog);
 	   	tvText = (TextView) dialog.findViewById(R.id.tvText_dialog);
 	   	btnYes = (Button) dialog.findViewById(R.id.btnYes);
 	 	btnNo = (Button) dialog.findViewById(R.id.btnNo);
		tvMessage.setText("THÔNG TIN");		
		tvText.setText("Điểm số vượt qua : "+level_info[2]+"\nThời Gian : "+totaltime);		
		btnYes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				timeCount= new CountDown(totaltime*1000,1000);
	            timeCount.start();
			}
		});
		btnNo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				timeCount= new CountDown(totaltime*1000,1000);
	            timeCount.start();				
			}
		});
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();

    }
    private void getwordfrom_matrix(){
    	items=new String[36];
    	int k=0;
    	for(int i=0; i<_maxsize; i++)
    		for(int j=0;j<_maxsize;j++)
    			items[k++]=Character.toString(matrix[i][j]);
    	matrix=null;
    }
    private void Fill_rest()
    {
    	
    	for(int i=-0; i<_maxsize; i++)
    		for(int j=0;j<_maxsize;j++)
    		{
    			int ch=65;
    			if(matrix[i][j]==0)
    			{		
    				ch+=r.nextInt(26);
    				matrix[i][j]=(char)ch;
    			}
    		}
    }
  
    private boolean Check_Exist(String _temp)
    {
		for(String s: ListWords)
		{
			if(_temp==s)
				return true;
		}
		return false;
    }
    private void Load_word(int n3, int n4, int n5, int n6)
    {
    	WordsEachLevel=n3+n4+n5+n6;
    	ListHint = new vector2d[WordsEachLevel];
    	
    	int i;
    	String temp;
    	Cursor testdata;
    	TestAdapter mDbHelper = new TestAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	while(n6-->0)
    	{
    		 i= r.nextInt(166)+1;
    		 testdata = mDbHelper.getTestData(i,6); 
    		 temp=Utility.StringGetColumnValue(testdata, "eng_w");
    		 if(!Check_Exist(temp))
    			 ListWords.add(temp);
    		 else
    			 n6++;
    		
 
    	 }
    	while(n5-->0)
    	{
    		 i= r.nextInt(246)+1;
    		 testdata = mDbHelper.getTestData(i,5); 
    		 temp=Utility.StringGetColumnValue(testdata, "eng_w");
    		 if(!Check_Exist(temp))
    			 ListWords.add(temp);
    		 else
    			 n5++;
 
    	 }
    	while(n4-->0)
    	{
    		 i= r.nextInt(324)+1;
    		 testdata = mDbHelper.getTestData(i,4); 
    		 temp=Utility.StringGetColumnValue(testdata, "eng_w");
    		 if(!Check_Exist(temp))
    			 ListWords.add(temp);
    		 else
    			 n4++;
 
    	 }
    	while(n3-->0)
    	{
    		 i= r.nextInt(127)+1;
    		 testdata = mDbHelper.getTestData(i,3); 
    		 temp=Utility.StringGetColumnValue(testdata, "eng_w");
    		 if(!Check_Exist(temp))
    			 ListWords.add(temp);
    		 else
    			 n3++;
 
    	 }
    	mDbHelper.close();
    
    }
    private void placeWords()
    {
    	int wordcount=0;
        vector2d vt = new vector2d();
    	for(String s : ListWords)
    	{
    		boolean placed=false;
    		int _row = 0,_col = 0;
    		while(placed==false&&!checkfull())
    		{
    			_row= r.nextInt(_maxsize);
    	        _col= r.nextInt(_maxsize);
    			int count_out=0;
    	    	while(matrix[_row][_col]!=0)
    	    	{
    	    		if(count_out==9)
    	    		{
    	    			vt=Quick_Find();
    	    			_row=vt.getx();
    	    			_col=vt.gety();
    	    		}
    	        	_row= r.nextInt(_maxsize);
    	        	_col= r.nextInt(_maxsize);
    	        	count_out++;
    	        	if(count_out==10)
    	    			break;	
    	        	
    	    	}
    	    	if(count_out==10)
	    			break;	
    			placed= find_true_way(s,_row,_col);
    		}  //end while  	
    		ListHint[wordcount]=new vector2d(_row,_col);
    		wordcount++;
    	}//end for
    	Fill_rest();
    }

    private boolean find_true_way(String word,int _row,int _col){
    	int xx =_row, yy=_col;	 
    	int max=_maxsize-1;
    	matrix[xx][yy]=word.charAt(0);
    	Listvect.add(new vector2d(xx,yy));
    	int l= word.length()-1;
    	int j=1;
    	while(l-->0&&!checkfull())
    	{
	    	int _x=0, _y=0; 	
	    	while((_x==0&&_y==0)&&checkout(xx,yy)==false)
	    	{    	
	    		
	    		 if(xx==0)
	    		{
	    			_x=r.nextInt(2);
	    			if(yy==max)	    				
	    				_y=r.nextInt(2)-1;
	    			else if(yy==0)
	    				_y=r.nextInt(2);
	    			else
	    				_y=r.nextInt(3)-1;
	    		}
	    		else if(yy==0)
	    		{
	    			_y=r.nextInt(2);
	    			if(xx==max)
	    				_x=r.nextInt(2)-1;
	    			else
	    				_x=r.nextInt(3)-1;	
	    		}
	    		else if(xx==max)
	    		{
	    			_x=r.nextInt(2)-1;
	    			if(yy==max)
	    				_y=r.nextInt(2)-1;
	    			else
	    				_y=r.nextInt(3)-1;
	    		}else if(yy==max)
	    		{
	    			_y=r.nextInt(2)-1;
	    			_x=r.nextInt(3)-1;
	    		}
	    		else
	    		{
	    			_x=r.nextInt(3)-1;
	    			_y=r.nextInt(3)-1;
	    		}
	    		if((matrix[xx+_x][yy+_y]==0))
	    		{
	    			xx+=_x;
	    			yy+=_y;
	    		}
	    		else
	    		{
	    			_x=_y=0;
	    		}
	    	}	//end while 2
	    	if(!checkout(xx,yy))
    		{
	    		matrix[xx][yy]=word.charAt(j);
	    		Listvect.add(new vector2d(xx,yy));
	    		j++;
    		}   
	    	else
	    	{
	    		for(vector2d v : Listvect)
	    			matrix[v.getx()][v.gety()]=0;
	    		return false;
	    	}
    	}//end while 1
    	if(j==word.length())
    	{
    		Listvect.clear();
    		return true;
    	}
    	return false;
    }
    private boolean checkout(int _row, int _col)
    {
    	for(int i=-1; i<2; i++)
    		for(int j=-1;j<2;j++)
    		{  
    			if(i!=0 || j!=0)
    			{
	    			if(_row+i>=0 && _col+j>=0)
	    			{
	    				if(_row+i<=_maxsize-1 && _col+j<=_maxsize-1)
	    					if(matrix[_row+i][_col+j]==0)
	    						return false;
	    			}
	    		}
    		}
    	return true;
    }
    private boolean checkfull()
    {
		for(int _c=0;_c<_maxsize; _c++)
			for(int _r=0;_r<_maxsize; _r++)
				if(matrix[_c][_r]==0)
					return false;
		return true;
    }
    private vector2d Quick_Find()
    {
		for(int _c=0;_c<_maxsize; _c++)
			for(int _r=0;_r<_maxsize; _r++)
				if(matrix[_c][_r]==0&&!checkout(_c,_r))
					return new vector2d(_c,_r);
		return new vector2d(0,0);
    }
    private void GetSpecialWord() {
        //Load SpecialWord
        int randomspeacialword;
        String str = result_tv.getText().toString();
        Log.d("resulttv", "GetSpecialWord: " + str);
        String[] resultList = str.split(" ");
        List<String> itemList = Arrays.asList(resultList);
        String resultWord = "";
        do {
            randomspeacialword = r.nextInt(WordsEachLevel - 1);
            specialword = ListWords.get(randomspeacialword);
            resultWord = new String(specialword);
            Log.d("specialword", "GetSpecialWord: " + specialword);
        } while (itemList.contains(resultWord.toUpperCase()));
    }

    public void SetScore(int score, MainActivity _context)
    {
        _context.level_score+=score;
        int _score=_context.level_score;
		_context.score_tv.setText(_score+"");
		if(_score >= score_require)
		{
			_context.passed=true;
			//time_score = (int) (_context.millis/1000);
			//_score+=time_score;
			_context.level_score=_score;
			_context.score_tv.setText(_score+"");
			_context.PassLevel();	
		}
    }
    public void SaveCurrent2DB()
    {
    	TestAdapter mDbHelper = new TestAdapter(this);         
    	mDbHelper.createDatabase();       
    	mDbHelper.open(); 
    	mDbHelper.Update_CurrentLevel(current_level, current_score);
    	mDbHelper.close();
    }
    public void GameOver()
    {
    	timer_tv.setText("0");
 	   	dialog.setContentView(R.layout.custom_dialog);
 	   	tvMessage = (TextView) dialog.findViewById(R.id.tvMessage_dialog);
 	   	tvText = (TextView) dialog.findViewById(R.id.tvText_dialog);
		tvMessage.setText("THẤT BẠI");		
		tvText.setText("Bạn muốn chơi lại màn này không?");	
		btnYes = (Button) dialog.findViewById(R.id.btnYes);
    	btnNo = (Button) dialog.findViewById(R.id.btnNo);
		btnYes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				MainActivity.this.finish();
				Intent i = new Intent(MainActivity.this, MainActivity.class);  
		 		overridePendingTransition(0, 0);
		 		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);		
		 		overridePendingTransition(0, 0);
		 		startActivity(i);
		 		onRestart();
		 		dialog.cancel();
				
			}
		});
		btnNo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				startActivity(new Intent(MainActivity.this,CreateActivity.class));
				MainActivity.this.finish();				
			}
		});
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
    }
    public void PassLevel()
    {
    	current_level++;
       	if(current_level<=10)
    		Go2NextLevel();
    	else
    	{
    		current_score+=level_score;
        	SaveCurrent2DB();
    		overridePendingTransition(0, 0);
        	startActivity(new Intent(context1,Congrat_activity.class));
        	overridePendingTransition(0, 0);
    	}
    		
    }
    private void Go2NextLevel()
    {
		dialog.setContentView(R.layout.nextlevel_dialog);
		Button btnBack = (Button) dialog.findViewById(R.id.btnBack_next);
    	Button btnNext = (Button) dialog.findViewById(R.id.bntNext_next);
    	Button btnReplay = (Button) dialog.findViewById(R.id.btnReplay_next);
    	tvMessage = (TextView) dialog.findViewById(R.id.tvMessage_nextlevel);
    	tvText = (TextView)  dialog.findViewById(R.id.tvText_nextlevel);
		tvMessage.setText("HOÀN THÀNH");
		int total_score= time_score+level_score;
		tvText.setText("Điểm số hiện tại : "+level_score+"\nĐiểm thưởng : "+time_score+"\nTổng điểm : "+total_score);		
		btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				current_score+=level_score;
	        	SaveCurrent2DB();
				MainActivity.this.finish();
				Intent i = new Intent(MainActivity.this, MainActivity.class);  
		 		overridePendingTransition(0, 0);
		 		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);		
		 		overridePendingTransition(0, 0);
		 		startActivity(i);
		 		onRestart();
				
			}
		});
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				current_score+=level_score;
	        	SaveCurrent2DB();
				startActivity(new Intent(MainActivity.this,CreateActivity.class));
				MainActivity.this.finish();			
			}
		});
		btnReplay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,CreateActivity.class));
				MainActivity.this.finish();			
			}
		});
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
    }
    private class CountDown extends CountDownTimer{
    	
	    public CountDown(long millisInFuture, long countDownInterval) {
	        super(millisInFuture, countDownInterval);
	      }
				// TODO Auto-generated constructor stub

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub	
			millis=millisUntilFinished;
			int time =(int)millis/1000;
			timer_tv.setText(time+"");
			if(!isshow_sw)
			{
                //Log.d("get isshow", "onTick: " + isshow_sw);
                GetSpecialWord();
                //Log.d("special word", "onTick: " + specialword);
                special_tv.setText(specialword.toUpperCase());
				_bonus=r.nextInt(specialword.length())+1;
				bonus_tv.setText("+"+_bonus);
				//time end special word not smaller 5
				timeend_sw=r.nextInt(15)+5;
				isshow_sw=true;
			}
			if(isshow_sw)
			{
                //Log.d("timeend_sw", "onTick: " + timeend_sw);
                //Log.d("answered", "onTick: " + answered);
                if(timeend_sw==0 || answered)
				{
					isshow_sw=false;
                    answered = false;
                    //Log.d("set isshow", "onTick: " + isshow_sw);
                    special_tv.setText("");
					bonus_tv.setText(" ");
				}
				timeend_sw--;
			}
				
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			if(!passed)
				GameOver();
		}
    }
}
