package com.example.phuongvo.snakewords;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
 
public class TestAdapter  
{ 
    protected static final String TAG = "DataAdapter"; 
 
    private final Context mContext; 
    private SQLiteDatabase mDb; 
    private DataBaseHelper mDbHelper; 
 
    public TestAdapter(Context context)  
    { 
        this.mContext = context; 
        mDbHelper = new DataBaseHelper(mContext); 
    } 
 
    public TestAdapter createDatabase() throws SQLException  
    { 
        try  
        { 
            mDbHelper.createDataBase(); 
        }  
        catch (IOException mIOException)  
        { 
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase"); 
            throw new Error("UnableToCreateDatabase"); 
        } 
        return this; 
    } 
    
    public TestAdapter open() throws SQLException  
    { 
        try  
        { 
            mDbHelper.openDataBase(); 
            mDbHelper.close(); 
            mDb = mDbHelper.getReadableDatabase(); 
        }  
        catch (SQLException mSQLException)  
        { 
            Log.e(TAG, "open >>"+ mSQLException.toString()); 
            throw mSQLException; 
        } 
        return this; 
    } 
 
    public void close()  
    { 
        mDbHelper.close(); 
    } 
   public void Update_CurrentLevel(int curlevel, int curscore)
   {
	   try{
		   ContentValues updateCon = new ContentValues();
		   updateCon.put("cur_level", curlevel);
		   updateCon.put("cur_score", curscore);
		   mDb.update("CUR_LOAD", updateCon, "id=1",null);
	   }
	   catch(Exception e){
		   System.out.println("Error Update Current Load");
	   }
   }
   public void Return2Level0()
   {
	   try{
		   ContentValues updateCon = new ContentValues();
		   updateCon.put("cur_level", 1);
		   updateCon.put("cur_score", 0);
		   mDb.update("CUR_LOAD", updateCon, "id=1",null);
	   }
	   catch(Exception e){
		   System.out.println("Error Return Level 0");
	   }
   }
   public Cursor getCurrent_Load()
   {
	   try 
       { 
      	 String sql = "select * from CUR_LOAD where id=1";
           Cursor mCur = mDb.rawQuery(sql, null); 
           if (mCur!=null) 
           { 
              mCur.moveToNext(); 
           } 
           return mCur; 
       } 
       catch (SQLException mSQLException)  
       { 
           Log.e(TAG, "getCurrent_Load>>"+ mSQLException.toString()); 
           throw mSQLException; 
       } 
   }
   public Cursor getlevel_require(int n)
   {
	   try 
       { 
		   String sql = "select * from LEVEL_ where level_id="+n;
           Cursor mCur = mDb.rawQuery(sql, null); 
           if (mCur!=null) 
           { 
              mCur.moveToNext(); 
           } 
           return mCur; 
       } 
       catch (SQLException mSQLException)  
       { 
           Log.e(TAG, "getLevel_require >>"+ mSQLException.toString()); 
           throw mSQLException; 
       } 
   }
     public Cursor getTestData(int i, int n ) 
     { 
         try 
         { 
        	 String sql = "select * from LETTERS_"+n+" where id="+i;
             Cursor mCur = mDb.rawQuery(sql, null); 
             if (mCur!=null) 
             { 
                mCur.moveToNext(); 
             } 
             return mCur; 
         } 
         catch (SQLException mSQLException)  
         { 
             Log.e(TAG, "getTestData >>"+ mSQLException.toString()); 
             throw mSQLException; 
         } 
     }
     public Cursor getWordData(String word)
     {
    	 int n = word.length();
    	 try 
         { 
        	 String sql = "select * from LETTERS_"+n+" where eng_w='"+word+"'";
             Cursor mCur = mDb.rawQuery(sql, null); 
             if (mCur!=null) 
             { 
                mCur.moveToNext(); 
             } 
             return mCur; 
         } 
         catch (SQLException mSQLException)  
         { 
             Log.e(TAG, "getTestData >>"+ mSQLException.toString()); 
             throw mSQLException; 
         } 
     }
     

} 

