package com.example.rcovi.wake_me_app;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.Toast;

import com.example.rcovi.wake_me_app.First;
import com.example.rcovi.wake_me_app.RingToneService;

public class AlarmReceiver extends BroadcastReceiver {


	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

		String val;
		int z=1;
		
		String get_your_string=arg1.getExtras().getString("Extra");
		String get_your_idd=arg1.getExtras().getString("idd");
		String get_vib=arg1.getExtras().getString("vib");
		int get_song=Integer.parseInt(arg1.getExtras().getString("play"));
		Intent serve=new Intent(arg0,RingToneService.class);
		serve.putExtra("Extra",get_your_string);
		serve.putExtra("idd",get_your_idd);
		Log.e("vib",get_vib);
		//Toast.makeText(arg0, "vib"+get_vib, Toast.LENGTH_LONG).show();
		serve.putExtra("vib", get_vib);
		serve.putExtra("play",String.valueOf(get_song));
	
		try{
		SQLiteDatabase db1=arg0.openOrCreateDatabase("DeletedAlarmDB",Context.MODE_PRIVATE,null);
		Log.e("We are in","the receiver");
		
		 Log.e("What is your string",get_your_string);
		Log.e("What",get_your_idd);
		
		Cursor cx= First.db1.rawQuery("SELECT * FROM del", null);
		if(cx.moveToFirst()){
			do{
				String strr=cx.getString(0);
			
				if(strr.equals(get_your_idd)){
					z=2;
					//Toast.makeText(arg0, "deleted Alarm", Toast.LENGTH_LONG).show();
				}
					
				}while(cx.moveToNext());
		
			if(z!=2){
			arg0.startService(serve);
		    //Toast.makeText(arg0, "New Alarm", Toast.LENGTH_LONG).show();
			}
		}
		if(z!=2){
			
			
			arg0.startService(serve);
		    //Toast.makeText(arg0, "New Alarm", Toast.LENGTH_LONG).show();
			}
		cx.close();
		}
		catch(Exception exc){
			//cx.close();
			Log.e("exeption exc= ", exc.getMessage());
			Toast.makeText(arg0, "New alarm", Toast.LENGTH_LONG).show();
			arg0.startService(serve);
		}
		
		}	
		
	}
