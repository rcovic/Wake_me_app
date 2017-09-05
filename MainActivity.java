package com.example.rcovi.wake_me_app;

import java.util.ArrayList;
import java.util.Calendar;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class  MainActivity extends Activity {
	public AlarmManager alarmManager;
	RadioGroup radio_group;
	RadioButton radio_button;
	TimePicker alarmTimePicker;
	CheckBox checkbok;
	Cursor cursor;
	Spinner spinner;
	//Ringtone ringtone;
	
	//MediaPlayer mp;
	RingtoneManager manager;
	public long time;
	private int dd=0;
	ArrayList<String> list_title=new ArrayList();
	ArrayList<String> list_uri=new ArrayList();
	ArrayList<String> list_id=new ArrayList();
	Button turnon_button,cancel_button;
	public static int id=0;
	public PendingIntent pending_intent;
	Context context;
	int song;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;
        
        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmTimePicker=(TimePicker)findViewById(R.id.timePicker1);
        radio_group=(RadioGroup)findViewById(R.id.radioGroup1);
        final Calendar calendar=Calendar.getInstance();
        final Intent i=new Intent(MainActivity.this,AlarmReceiver.class);
        turnon_button=(Button)findViewById(R.id.button1 );
		cancel_button=(Button)findViewById(R.id.button2);
        checkbok=(CheckBox) findViewById(R.id.checkBox1);
        spinner=(Spinner) findViewById(R.id.spinner1);
        manager = new RingtoneManager(this);
		manager.setType(RingtoneManager.TYPE_RINGTONE);
		alarmTimePicker.setIs24HourView(false);
		cursor=manager.getCursor();

		 while(cursor.moveToNext())
		 {
        	 String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
             String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
             String notId=cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
             list_uri.add(notificationUri);
             list_id.add(notId);
             list_title.add(notificationTitle);
 		 }

       ArrayAdapter<String> array_adapter =new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,list_title);
       array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setAdapter(array_adapter);
       spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3)
		{
			int ind=spinner.getSelectedItemPosition();
			String mus=list_uri.get(ind);
		    String ic=list_id.get(ind);
		    Uri songg=Uri.parse(mus+"/"+ic);

			try
			{
				Intent ii=new Intent(MainActivity.this,SerTest.class);
			    ii.putExtra("ringtone-uri", String.valueOf(songg));
			    stopService(ii);
			}
			catch(Exception exx){
				
			}

			if(dd!=0){
		    Intent ii=new Intent(MainActivity.this,SerTest.class);
		    ii.putExtra("ringtone-uri", String.valueOf(songg));
		    startService(ii);
		     
			}
			else
				dd++;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	});
        turnon_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int ind=spinner.getSelectedItemPosition();
				String mus=list_uri.get(ind);
			    String ic=list_id.get(ind);
			    Uri songg=Uri.parse(mus+"/"+ic);
				Intent ii=new Intent(MainActivity.this,SerTest.class);
			    ii.putExtra("ringtone-uri", String.valueOf(songg));
			    stopService(ii);
				int sid=radio_group.getCheckedRadioButtonId();
				radio_button=(RadioButton)findViewById(sid);
				String r=radio_button.getText().toString();
				time=System.currentTimeMillis();
				calendar.set(Calendar.HOUR_OF_DAY,alarmTimePicker.getCurrentHour());
				calendar.set(Calendar.MINUTE,alarmTimePicker.getCurrentMinute());
				calendar.set(Calendar.SECOND,0);
				long hl=alarmTimePicker.getCurrentHour();
				long ml=alarmTimePicker.getCurrentMinute();
				song=spinner.getSelectedItemPosition();
				String h=String.valueOf(hl);
				String m=String.valueOf(ml);
				if(h.length()==1)
					h="0"+h;
				if(m.length()==1)
					m="0"+m;

				i.putExtra("Extra","alarm on");
				i.putExtra("idd", String.valueOf(time));
				i.putExtra("play", String.valueOf(song));
				String vibration="no";
				if(checkbok.isChecked()){
					i.putExtra("vib","yes");
					vibration="yes";
				}
				else
					i.putExtra("vib","no");
				pending_intent=PendingIntent.getBroadcast(MainActivity.this,(int) time,i,PendingIntent.FLAG_UPDATE_CURRENT);
	

				long time=System.currentTimeMillis();
				long t=calendar.getTimeInMillis();
				if(t>=time){

					if(r.equals("Once"))
						alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);
					else if(r.equals("Daily"))
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),60*1000*24*60,pending_intent);
					else
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),60*1000*24*60*7,pending_intent);
					Toast.makeText(getApplicationContext(), "Alarm set at "+h+":"+m+".", Toast.LENGTH_LONG).show();
				}

				else
				{
						if(r.equals("Once"))
							alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);
						else if(r.equals("Daily"))
							alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+86400000,60*1000*24*60,pending_intent);
						else
							alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+86400000,60*1000*24*60*7,pending_intent);
					Toast.makeText(getApplicationContext(), "Alarm set at "+h+":"+m+"  tomorrow.", Toast.LENGTH_LONG).show();
				}
	
				First.db.execSQL("INSERT INTO alarm VALUES"+"('"+id+"','alarm','"+h+":"+m+"','"+time+"','"+r+"','"+vibration+"','"+song+"')");
				Intent back=new Intent(MainActivity.this,First.class);
				back.putExtra("S",h+":"+m);
				startActivity(back);
			}
		});

		cancel_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent kkkkk=new Intent(MainActivity.this,SerTest.class);
				kkkkk.putExtra("ringtone-uri", String.valueOf(2));
				stopService(kkkkk);
				Intent off = new Intent(MainActivity.this,First.class);
				startActivity(off);
			}
		});

    }

	@Override
	public void onBackPressed() {
		Intent kkkk=new Intent(MainActivity.this,SerTest.class);
		kkkk.putExtra("ringtone-uri", String.valueOf(2));
		stopService(kkkk);
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//dd=0;
		super.onDestroy();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
