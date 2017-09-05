package com.example.rcovi.wake_me_app;


import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class RingToneService extends Service {
	
	int start_id;
	boolean isRunning;
	ArrayList<String> list_title=new ArrayList();
	ArrayList<String> m=new ArrayList();
	ArrayList<String> u=new ArrayList();
	Vibrator v;
	Ringtone rr;
	Cursor cr;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String mus,ic;
		Uri song;
		Log.i("Local service","Received start id"+startId+":"+intent);
		String state=intent.getExtras().getString("Extra");
		String vibb=intent.getExtras().getString("vib");
		int music=Integer.parseInt(intent.getExtras().getString("play"));
		Log.e("Ringtone state:Extra is",state);

		MediaPlayer mp=new MediaPlayer();
		AudioManager am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		assert state!=null;
		if(state.equals("alarm on"))
			start_id=1;
		else if(state.equals("alarm off"))
			start_id=0;
		
		else
			start_id=0;
		if(!this.isRunning && start_id==1)
		{
			if(vibb.equals("yes")){
				 v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				long[] pattern = {0, 100, 1000};
				v.vibrate(pattern, 0);
			}
			Log.e("There is no music ","and you want start");
			RingtoneManager manager = new RingtoneManager(this);
	        manager.setType(RingtoneManager.TYPE_RINGTONE);
	         cr=manager.getCursor();
	         while(cr.moveToNext()){
	        	 String notificationTitle = cr.getString(RingtoneManager.TITLE_COLUMN_INDEX);
	             String notificationUri = cr.getString(RingtoneManager.URI_COLUMN_INDEX);
	             String notId=cr.getString(RingtoneManager.ID_COLUMN_INDEX);
	             //Uri uri=RingtoneManager.getDefaultUri(c.getPosition());
	             m.add(notificationUri);
	             u.add(notId);
	             list_title.add(notificationTitle);

	 		}
	          mus=m.get(music);
 		      ic=u.get(music);
 		      song=Uri.parse(mus+"/"+ic);
 		     Log.e("creato ","uri canzone");
 		    Intent iii=new Intent(RingToneService.this,MusicService.class);
		    iii.putExtra("ringtone-uri", String.valueOf(song));
		    startService(iii);


			this.isRunning=true;
			this.start_id=0;
			
			Intent dialogIntent = new Intent(this,Startup.class);
			dialogIntent.putExtra("ringtone-uri", String.valueOf(song));
			 dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 this.startActivity(dialogIntent);
			
		}
		
		else if(this.isRunning && start_id==0)
		{
			Log.e("is Running=1, ", "start_id=0");
			try{
			v.cancel();
			}
			catch(Exception ex){
				
			}
			Log.e("There is music ","and you don't want to start");
			
		 //   Uri songg=Uri.parse(mus+"/"+ic);
		    
		    Intent iii=new Intent(this,MusicService.class);
		    iii.putExtra("ringtone-uri", "av");
		    stopService(iii);
			this.isRunning=false;
			start_id=0;

		}

		else if(!this.isRunning && start_id==0)
		{
			
			Log.e("There is no music ","and you don't want to start");
		}
		else if(this.isRunning && start_id==1)
		{
			Log.e("There is music ","and you want to start");
			
		}
		else
		{
			Log.e("Somehow you reached ","here");
		}
		return super.onStartCommand(intent, flags, startId);
	}

}
