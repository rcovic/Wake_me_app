package com.example.rcovi.wake_me_app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {
MediaPlayer mpp;
private Ringtone ringtone1;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mpp.stop();
		mpp.reset();
		mpp.release();
		try{
			ringtone1.stop();
		}
		catch(Exception ex){}
		//super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Uri ringtoneUri = Uri.parse(intent.getExtras().getString("ringtone-uri"));
		this.ringtone1 = RingtoneManager.getRingtone(this, ringtoneUri);
		 mpp=MediaPlayer.create(this,ringtoneUri);
		 if(mpp==null){
			 Toast.makeText(this, "could not play selected ringtone", Toast.LENGTH_LONG).show();
		 }
		 else{
			 Toast.makeText(this, "Wake Up!!", Toast.LENGTH_LONG).show();
			 mpp.setLooping(true);
			 mpp.start();
		 }
		return START_NOT_STICKY;
	}
}
