package com.example.phuongvo.snakewords;

import java.util.Random;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomGridAdapter extends BaseAdapter {

    private MainActivity context;
    private String[] items;
    LayoutInflater inflater;
    
    int countout=0;
    int HintEachLevel=5;
	String word="";
	String mean="";
    Stack<NewButton> Buttons_Press= new Stack<NewButton>();
    NewButton ButtonsInMatrix[]= new NewButton[36];
    Random rand= new Random();
    
    public CustomGridAdapter(MainActivity _context, String[] items) {
        this.context = _context;
        this.items = items;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context.snake_hint.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			MediaPlayer mp3 = MediaPlayer.create(context.getBaseContext(), R.raw.chonsai);
    			if(mean.length()!=0)
    			{
    				String str=context.result_tv.getText().toString();
    				if(!str.matches(".*\\b"+word+"\\b.*"))
    				{
    					context.SetScore(word.length(), context);
    					SetResult(word);
    					MediaPlayer mp = MediaPlayer.create(context.getBaseContext(), R.raw.glass_button);
    					mp.start();
    					//Neu tim duoc tu thi xoa vi tri chu cai dau trong danh sach vi tri goi y cua tu do
	    				for( int i=0; i<context.WordsEachLevel; i++)
	    				{
	    					vector2d v1 = context.ListHint[i];
	    					if(v1.getx()==Buttons_Press.firstElement().Location.getx()
	    							&& v1.gety()==Buttons_Press.firstElement().Location.gety()
	    							)
	    					{
	    						context.ListHint[i]=new vector2d(-1,-1);
	    					}	
	    					if(v1.getx()==-1)
	    						countout++;
	    				}
	    				Return2Default();
    				}
    				return;
    			}
    			mp3.start();
    		}
    	});
        context.hint_btn.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			int k=context.WordsEachLevel;
    			if(context.level_score>=2 && countout<k)
    			{
	    			if(HintEachLevel>0 )
	    			{
	    				context.SetScore(-2, context);
	    				HintEachLevel--;
	    				vector2d hint;
	    				int pos=-1;
	        			int n=0;
		    			while(pos<0)
		    			{
		    				int q=k-1;
		    				n=rand.nextInt(q);
		    				hint = context.ListHint[n];
		    				pos=hint.getx()*6 + hint.gety();
		    			}
		    			Return2Default();
		    			ButtonsInMatrix[pos].clicked=true;
		    			ButtonsInMatrix[pos].btn.setBackgroundColor(Color.GREEN);
		    			Buttons_Press.push(ButtonsInMatrix[pos]);   
		    			SetText();
	    			}
	    			else
	    				Toast.makeText(context.getApplicationContext(), "Bạn đã sử dụng hết 5 lượt gợi ý trong màn này!",
    							Toast.LENGTH_LONG).show();
	    				
    			}
    			else
    			{
    				if(context.current_score<10)
    					Toast.makeText(context.getApplicationContext(), "Số điểm của bạn không đủ để dùng gợi ý :D",
    							Toast.LENGTH_LONG).show();
    				if(countout==k)
    					Toast.makeText(context.getApplicationContext(), "Khó quá! không gợi ý được",
    		    				Toast.LENGTH_LONG).show();
    				
    					

    			}
    		}
    	});
    }

    @SuppressLint("InflateParams") public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cell, null);
        }
        
        NewButton newBtn= new NewButton();
        newBtn.btn = (Button) convertView.findViewById(R.id.grid_item);
       
        int x= position;
        int y=x;

        if(x<6)
        	x=0;
        else if(x<12)
        	x=1;
        else if(x<18)
        	x=2;
        else if(x<24)
        	x=3;
        else if(x<30)
        	x=4;
        else
        	x=5;
        y=position%6;
        newBtn.Location= new vector2d(x,y);
        newBtn.btn.setText(items[position].toUpperCase());
        newBtn.btn.setOnClickListener(new HandleClick(newBtn));
        ButtonsInMatrix[position]=newBtn;
        

      
        
        return convertView;
    }
   
    private void SetResult(String _word)
    {
    	String str=context.result_tv.getText().toString();
    	str+=_word+" ";
    	context.result_tv.setText(str);
    }
    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private void Return2Default()
    {	
    	for(NewButton nbtn : Buttons_Press)
    	{
    		nbtn.btn.setBackgroundColor(Color.rgb(52, 152, 219));
    		nbtn.clicked=false;
    		nbtn.allow=false;

    	}
    	Buttons_Press.clear();
    	context.tooltip_btn.setText("");
    	context.viet_tv.setText("");
    	
    	
    }
    private class HandleClick implements OnClickListener
    {
    	HandleClick(NewButton _new){
    		newBtn=_new;
    	}
    	NewButton newBtn;
	    public void onClick(View arg0)
	    {
	    	int x= newBtn.Location.getx();
	    	int y= newBtn.Location.gety();
	    	if(Buttons_Press.isEmpty()|| CheckAllow(x,y))
	    	{
	    		newBtn.allow=true;
	    	}else
	    		Return2Default();
	    	if(!newBtn.clicked && newBtn.allow)//click 
	    	{
	    		newBtn.clicked=true;
	    		newBtn.btn.setBackgroundColor(Color.GREEN);
	    		Buttons_Press.push(newBtn);
	    		SetText();
	    		
	    	}
	    	else if(newBtn.clicked==true)//unclick
	    	{
	    		if(newBtn.btn == Buttons_Press.peek().btn)
	    		{
	    			newBtn.clicked=false;
	    			newBtn.allow=false;
	    			newBtn.btn.setBackgroundColor(Color.rgb(255,102,255));
	    			Buttons_Press.pop();
		    		SetText();
	    		}
	    	}
            
	    }

		private boolean CheckAllow(int x,int y) {
			
			vector2d top = Buttons_Press.peek().Location;
			int top_x= top.getx();
			int top_y= top.gety();
			
			for(int i=-1; i<2; i++)
	    		for(int j=-1;j<2;j++)
	    		{  
	    			if(i!=0 || j!=0)
	    			{
		    			if(top_x+i==x && top_y+j==y)
		    				return true;	    			
		    		}
	    		}
	    	return false;
		}
				
    }
    @SuppressLint("DefaultLocale") private void SpecialWord_Event(String _word)
    {
    	TextView special_tv = (TextView) context.findViewById(R.id.specialw_tv);
    	String str=special_tv.getText().toString();
    	if(_word.equals(str))//if special word is show 
    	{
			SetResult(_word);
			context.SetScore(_word.length()+context._bonus, context);
			MediaPlayer mp3 = MediaPlayer.create(context.getBaseContext(), R.raw.coin);
			mp3.start();
			Return2Default();
			context.answered=true;
    	}
    }
    @SuppressLint("DefaultLocale") private void SetText()
	{
	
		word="";
		mean="";
		for(NewButton newBtn : Buttons_Press)
		{
			word+=newBtn.btn.getText().toString();   		
		}
		int length=word.length();
		if(length>=3 && length<7)
		{
			if(context.isshow_sw)
				SpecialWord_Event(word);
			Cursor testdata;
	    	TestAdapter mDbHelper = new TestAdapter(context);         
	    	mDbHelper.createDatabase();       
	    	mDbHelper.open(); 
	    	testdata = mDbHelper.getWordData(word.toLowerCase());
	    	mean=Utility.StringGetColumnValue(testdata, "vie_w");
	    	mDbHelper.close();
		}
		context.viet_tv.setText(mean);
		context.tooltip_btn.setText(word);	
	}
    
   
}
	    
    	
