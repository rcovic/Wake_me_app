package com.example.rcovi.wake_me_app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.Random;
import com.example.rcovi.wake_me_app.R;

public class Startup extends Activity {
Button b1,b2,b3;
 NotificationCompat.Builder builder;
static NotificationManager nm;
 static  final int not_id=4561;
private Context context;
	public String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		title=getIntent().getExtras().getString("ringtone-uri");

		b1=(Button) findViewById(R.id.button1);
		b2=(Button) findViewById(R.id.button2);
		b3=(Button) findViewById(R.id.button3);

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent stop=new Intent(Startup.this,MathGame.class);
				stop.putExtra("ringtone-uri",title);
				startActivity(stop);
			}
		});

		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent stop2=new Intent(Startup.this, DodgeGame.class);
				stop2.putExtra("ringtone-uri",title);
				startActivity(stop2);
			}
		});

		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Random r = new Random();
				int flag = r.nextInt(2);

				if (flag==0){
				Intent stop2=new Intent(Startup.this, DodgeGame.class);
				stop2.putExtra("ringtone-uri",title);
				startActivity(stop2);}
				else {
					Intent stop=new Intent(Startup.this,MathGame.class);
					stop.putExtra("ringtone-uri",title);
					startActivity(stop);
				}
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		context=this;
		//No=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(context);
		builder.setOngoing(true);
		builder.setSmallIcon(R.drawable.unnamed);
		builder.setWhen(System.currentTimeMillis());
		builder.setContentTitle("Alarm Time");
		builder.setContentText("Play the game to dismiss alarm!!");
		Intent inte=new Intent(this.getApplicationContext(),MathGame.class);
		PendingIntent pin=PendingIntent.getActivity(this,0,inte,PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pin);
		nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		nm.notify(not_id,builder.build());
		super.onStart();
	}
}
